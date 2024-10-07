package org.example.multidatasourcetrainning.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.example.multidatasourcetrainning.db1.entities.ReadAndWrite;
import org.example.multidatasourcetrainning.db1.repository.ReadAndWriteRepository;
import org.example.multidatasourcetrainning.db2.entities.ReadOnly;
import org.example.multidatasourcetrainning.db2.repository.ReadOnlyRepository;
import org.example.multidatasourcetrainning.enums.TableHeaders;
import org.example.multidatasourcetrainning.mapper.ImportDataMapper;
import org.example.multidatasourcetrainning.utils.DB2AccessModifier;
import org.example.multidatasourcetrainning.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CSVService {
    private static final String FILE_NAME = "this_is_a_table";
    private static final String CSV_EXTENSION = ".csv";
    private static final String ZIP_EXTENSION = ".zip";

    @Autowired
    private ReadAndWriteRepository rw;

    @Autowired
    private ReadOnlyRepository ro;

    @Autowired
    private DB2AccessModifier accessModifier;

    public File exportCSV() throws IOException {
        List<ReadAndWrite> list = rw.findAll();
        Map<Long, String[]> map = new HashMap<>();

        for (ReadAndWrite readAndWrite : list) {
            map.put(
                    readAndWrite.getId(),
                    new String[]{
                            readAndWrite.getField(),
                            readAndWrite.getAnotherField(),
                            readAndWrite.getDoubleField().toString(),
                            readAndWrite.getDateCreated().toString()
                    }
            );
        }

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(TableHeaders.getTableHeaders())
                .build();

        String fileName = FILE_NAME + "_" + LocalDate.now();
        Path csvFilePath = Paths.get(fileName + CSV_EXTENSION);
        BufferedWriter stringWriter = Files.newBufferedWriter(csvFilePath);
        try (final CSVPrinter printer = new CSVPrinter(stringWriter, csvFormat)) {
            map.forEach((id, values) -> {
                try {
                    printer.printRecord(id, values[0], values[1], values[2], values[3]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            printer.flush();
        }
        return DataUtils.zip(fileName + ZIP_EXTENSION, csvFilePath);
    }

    public void importCSV(MultipartFile multipartFile) throws IOException, IllegalAccessException {
        Reader in = new InputStreamReader(multipartFile.getInputStream());

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(TableHeaders.getTableHeaders())
                .setSkipHeaderRecord(true)
                .build();

        List<ReadAndWrite> importList = new ArrayList<>();
        Iterable<CSVRecord> records = csvFormat.parse(in);
        for (CSVRecord record : records) {
            importList.add(ImportDataMapper.mapData(record));
        }

        List<ReadOnly> readOnlyList = new ArrayList<>();
        for(ReadAndWrite rw : importList) {
            readOnlyList.add((ReadOnly) DataUtils.transferData(rw, new ReadOnly()));
        }

        accessModifier.unlockDatabase();
        ro.deleteAll();
        ro.saveAll(readOnlyList);
        accessModifier.lockDatabase();

        rw.deleteAll();
        rw.saveAll(importList);
    }
}
