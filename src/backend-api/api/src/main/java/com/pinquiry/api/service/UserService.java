package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{


    @Autowired
    private UserRepository repository;


    @Override
    public List<User> findAll() {

        return  repository.findAll();
    }
    @Override
    public boolean createUser(User user){
        try {
            Date date = new Date();
            user.setSignupDate(new Timestamp(date.getTime()));
            List<User> ul = new ArrayList<>();
            ul.add(user);
            repository
                    .saveAll(ul);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteUser(long id){
        boolean success = true;
        User u = findUserById(id);
        try {
            repository.delete(u);
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }
    @Override
    public User findUserById(long id){
        Optional<User> u = repository.findById(id);
        return u.orElseThrow();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> u = repository.findByUsername(username);
        return u.orElseThrow();
    }

    @Override
    public boolean updatePassword(User u, String newPassword ){
        u.setUser_password(newPassword);
        System.out.println(u.getUser_password());
        repository.save(u);
        return true;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }




}
