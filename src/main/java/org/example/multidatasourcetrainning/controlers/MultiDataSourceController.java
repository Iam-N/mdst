package org.example.multidatasourcetrainning.controlers;

import org.example.multidatasourcetrainning.dto.AddNewDTO;
import org.example.multidatasourcetrainning.dto.CombiDataDTO;
import org.example.multidatasourcetrainning.dto.MDSR;
import org.example.multidatasourcetrainning.db1.entities.Users;
import org.example.multidatasourcetrainning.db2.entities.NotUsers;
import org.example.multidatasourcetrainning.services.MultiDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
@RestController
@RequestMapping("/multi-datasource")
public class MultiDataSourceController {
    @Autowired
    private MultiDataSourceService MDSService;

    @GetMapping("/list")
    public MDSR list() {
        List<Users> users = MDSService.getUsers();
        List<NotUsers> notUsers = MDSService.getNotUsers();
        return new MDSR(users, notUsers);
    }

    @PostMapping("/add")
    public void add(@RequestBody AddNewDTO dto) throws IllegalAccessException {
        MDSService.addData(dto);
    }

    @PostMapping("/combi-add")
    public void combiAdd(@RequestBody CombiDataDTO dto) throws IllegalAccessException {
        MDSService.combiDataAdd(dto);
    }

    @PostMapping("/address-add")
    public void addressAdd(@RequestBody AddNewDTO dto) {

    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "type", defaultValue = "null") String type,
                       @RequestParam(value = "id", defaultValue = "null") Integer id) {
        MDSService.deleteData(id, type);
    }
}