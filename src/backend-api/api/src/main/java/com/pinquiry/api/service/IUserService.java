package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IUserService {


    List<User> findAll(Pageable pageable);

    boolean createUser(User user);

    boolean deleteUser(User user);

    User findUserById(long id);
    User findUserByUsername(String username);

    boolean updatePassword(User u, String newPassword);

    boolean updateUser(User u);


}
