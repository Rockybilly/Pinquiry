package com.pinquiry.api.service;

import com.pinquiry.api.model.User;

import java.util.List;


public interface IUserService {
    List<User> findAll();
    boolean createUser(User user);

    boolean deleteUser(long id);


    User findUserById(long id);
    User findUserByUsername(String username);

    boolean updatePassword(User u, String newPassword);


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
