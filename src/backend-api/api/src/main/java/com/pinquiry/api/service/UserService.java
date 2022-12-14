package com.pinquiry.api.service;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.monitor.Monitor;
import com.pinquiry.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{


    @Autowired
    private UserRepository repository;

    @Autowired
    private ServiceWorkerService serviceWorkerService;


    @Override
    public List<User> findAll(Pageable pageable) {
        return  repository.findAll(pageable).getContent();
    }
    @Override
    public boolean createUser(User user){
        try {
            Date date = new Date();
            user.setSignupDate(new Timestamp(date.getTime()));
            if(user.getRole() == null){
                user.setRole(User.UserRole.USER);
            }
            repository
                    .save(user);
            System.out.println("User created with username: " + user.getUsername());
            return true;
        } catch (Exception e) {
            System.out.println("Could not save user with " + user.getUsername() + " " + e.getCause().toString());
            return false;
        }
    }
    @Override
    public boolean deleteUser(User u){
        boolean success = true;
        for(Monitor m: u.getMonitors()) {

                serviceWorkerService.addMonitorToServiceWorkerByLocation(m.getLocation(), m, ServiceWorkerService.OperationType.DELETE);
        }
        try {
            repository.delete(u);
        } catch (Exception e) {
            success = false;
            System.out.println("Could not delete user with username: " + u.getUsername() );
        }
        System.out.println("Deleted user with username: " + u.getUsername());
        return success;
    }
    @Override
    public User findUserById(long id){
        Optional<User> u = repository.findById(id);
        return u.orElseThrow();
    }

    @Override
    public User findUserByUsername(String username) {
        Pageable firstPageWithOneElement = PageRequest.of(0, 1);
        Page<User> pu = repository.findByUsername(username, Pageable.unpaged());
        Optional<User> u = pu.get().findFirst();
        return u.orElseThrow();
    }

    @Override
    public boolean updatePassword(User u, String newPassword ){
        u.setPassword(newPassword);
        repository.save(u);
        System.out.println("User " + u.getUsername() + " password updated");
        return true;
    }

    @Override
    public boolean updateUser(User u) {
        try {
            repository
                    .save(u);
            return true;
        } catch (Exception e) {
            System.out.println("Could not update user " + u.getUsername() + " " + e.getCause().toString());
            return false;
        }
    }




    public int findAdmins(){
        Pageable firstPageWithOneElement = PageRequest.of(0, 1);
        Page<User> pu = repository.countByRole(User.UserRole.ADMIN, Pageable.unpaged());
        return (int) pu.getTotalElements();
    }



}
