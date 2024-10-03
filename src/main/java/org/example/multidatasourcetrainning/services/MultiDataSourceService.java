package org.example.multidatasourcetrainning.services;

import org.example.multidatasourcetrainning.dto.AddNewDTO;
import org.example.multidatasourcetrainning.dto.CombiDataDTO;
import org.example.multidatasourcetrainning.db1.entities.Users;
import org.example.multidatasourcetrainning.db2.entities.NotUsers;
import org.example.multidatasourcetrainning.db2.repository.NotUsersRepository;
import org.example.multidatasourcetrainning.db1.repository.UsersRepository;
import org.example.multidatasourcetrainning.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
public class MultiDataSourceService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NotUsersRepository notUsersRepository;

    //    @Transactional(transactionManager = "transactionManager1")
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    //    @Transactional(transactionManager = "transactionManager2")
    public List<NotUsers> getNotUsers() {
        return notUsersRepository.findAll();
    }

    /**
     * Check and add the corresponding entity to the right database
     *
     * @param addNewDTO
     */
    public void addData(AddNewDTO addNewDTO) throws IllegalAccessException {
        if (DataUtils.nullField(addNewDTO)) throw new NullPointerException("addNewDTO is null");

        if (addNewDTO.getType().equalsIgnoreCase("Users")) {
            Users user = Users.builder()
                    .id(addNewDTO.getId())
                    .name(addNewDTO.getName())
                    .role(addNewDTO.getRole())
                    .build();
            usersRepository.save(user);
            return;
        }
        if(addNewDTO.getType().equalsIgnoreCase("NotUsers")) {
            NotUsers notUsers = NotUsers.builder()
                    .id(addNewDTO.getId())
                    .name(addNewDTO.getName())
                    .role(addNewDTO.getRole())
                    .build();
            notUsersRepository.save(notUsers);
            return;
        }
        throw new IllegalArgumentException("addNewDTO type is not supported");
    }

    /**
     * Check and delete corresponding row
     *
     * @param id
     * @param type
     */
    public void deleteData(Integer id, String type) {
        if (id == null || type == null) throw new IllegalArgumentException("Invalid data");
        if (type.equals("users")) {
            usersRepository.deleteById(id);
            return;
        }
        if (type.equals("notusers")) {
            notUsersRepository.deleteById(id);
            return;
        }
        throw new IllegalArgumentException("Invalid type");
    }

    /**
     * Save both {@code Users} and {@code NotUsers} at once
     *
     * @param dto
     */
    public void combiDataAdd(CombiDataDTO dto) throws IllegalAccessException {
        if(DataUtils.nullField(dto)) throw new NullPointerException("dto is null");
        Users user = Users.builder()
                .id(dto.getUserId())
                .name(dto.getUsername())
                .role(dto.getUserRole())
                .build();
        NotUsers notUsers = NotUsers.builder()
                .id(dto.getNotUserId())
                .name(dto.getNotUsername())
                .role(dto.getNotUserRole())
                .build();
        usersRepository.save(user);
        notUsersRepository.save(notUsers);
    }
}
