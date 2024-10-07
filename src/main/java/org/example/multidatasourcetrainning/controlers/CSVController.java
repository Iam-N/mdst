package org.example.multidatasourcetrainning.controlers;

import org.example.multidatasourcetrainning.services.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/csv")
public class CSVController {
    @Autowired
    private CSVService exportService;

    @GetMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> exportCSV() throws IOException {
        File exportFile = exportService.exportCSV();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exportFile.getName() + "\"");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(exportFile));
        return ResponseEntity
                .ok()
                .headers(header)
                .contentLength(exportFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void importCSV(
            @RequestParam("files") MultipartFile file
    ) throws IOException, IllegalAccessException {
        exportService.importCSV(file);
    }
}
