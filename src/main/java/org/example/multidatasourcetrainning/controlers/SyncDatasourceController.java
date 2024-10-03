package org.example.multidatasourcetrainning.controlers;

import org.example.multidatasourcetrainning.dto.DB1Add;
import org.example.multidatasourcetrainning.dto.DB2Res;
import org.example.multidatasourcetrainning.services.SyncDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/sync")
public class SyncDatasourceController {
    @Autowired
    private SyncDataSourceService serv;

    @GetMapping("/list")
    public DB2Res listDataFromDatasource2(){
        return serv.findDataFromDatasource2();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addDataToDatasource1(@RequestBody DB1Add dto) throws IllegalAccessException, SQLException {
        serv.addDataToDatasource1(dto);
    }
}
