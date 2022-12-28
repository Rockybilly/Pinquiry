package com.pinquiry.api.controllers;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.rest.request.webapp.UpdateEmailRequest;
import com.pinquiry.api.model.rest.request.webapp.admin.AdminRemoveUserRequest;
import com.pinquiry.api.model.rest.request.webapp.RemoveUserRequest;
import com.pinquiry.api.model.rest.request.webapp.TokenRequest;
import com.pinquiry.api.model.rest.request.webapp.UpdatePasswordRequest;
import com.pinquiry.api.model.rest.response.BasicUserAttrResponse;
import com.pinquiry.api.model.rest.response.BasicUserInfoResponse;
import com.pinquiry.api.service.AuthService;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(@CookieValue(name = "jwt") String token){
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        BasicUserAttrResponse buar = new BasicUserAttrResponse();
        buar.setId(u.getId());
        buar.setUsername(u.getUsername());
        if(u.getRole() == User.UserRole.ADMIN){
            buar.setAdmin(true);
        }
        return ResponseEntity.ok().body(buar);
    }



    @GetMapping("/admin/showUsers")
    public ResponseEntity<?> findUsers(@CookieValue(name = "jwt") String token, @RequestParam(defaultValue = "1") int page) {
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        if(u.getRole() == User.UserRole.ADMIN){
            List<User> lu = userService.findAll(Pageable.unpaged());
            List<BasicUserInfoResponse> lbuir = new ArrayList<>();
            for(User user: lu){
                BasicUserInfoResponse buir = new BasicUserInfoResponse();
                buir.setUser_id(user.getUserId());
                buir.setUsername(user.getUsername());
                buir.setNumberOfMonitors(user.getMonitors().size());
                lbuir.add(buir);
            }
            return ResponseEntity.ok().body(lbuir);
        }
        return ResponseEntity.status(401).body("Not Authorized");

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody User user) {

        try {
            userService.findUserByUsername(user.getUsername());
        }catch (Exception e) {
            boolean succ = userService.createUser(user);
            if (succ) {
                String t;

                try {
                    t = authService.createAuthenticationToken(user);
                } catch (Exception e1) {
                    return ResponseEntity.status(401).body("JWT token creation error");
                }

                ResponseCookie tokenCookie = ResponseCookie.from("jwt", t)
                        .maxAge(604800)
                        .build();

                BasicUserAttrResponse buar = new BasicUserAttrResponse();
                buar.setId(user.getId());
                buar.setUsername(user.getUsername());
                if(user.getRole() == User.UserRole.ADMIN){
                    buar.setAdmin(true);
                }
                return ResponseEntity.status(201).header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).body(buar);
            } else
                return ResponseEntity.status(409).body("Could not signup");
        }
        return ResponseEntity.status(409).body("Username is already taken");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TokenRequest request) {
        User u;
        try {
            u = userService.findUserByUsername(request.getUsername());
        }catch (Exception e){
            return ResponseEntity.status(401).body("User not found");
        }

        String t;

        if( ! Objects.equals( u.getPassword(), request.getPassword() ) ){
            return ResponseEntity.status(401).body("Wrong password");
        }

        try {
            t = authService.createAuthenticationToken(u);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("JWT token creation error");
        }

        ResponseCookie tokenCookie = ResponseCookie.from("jwt", t)
                .maxAge(604800)
                .build();

        BasicUserAttrResponse buar = new BasicUserAttrResponse();
        buar.setId(u.getId());
        buar.setUsername(u.getUsername());
        if(u.getRole() == User.UserRole.ADMIN){
            buar.setAdmin(true);
        }

        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).body(buar);

    }

    @GetMapping("/user_logout")
    public ResponseEntity<String> logout() {

        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("jwt", "")
                .maxAge(0)
                .build();


        return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString()).header(HttpHeaders.ALLOW, "GET").body("logged out");
    }






    @PostMapping("/delete-user")
    public ResponseEntity<?> removeUser(@CookieValue(name = "jwt") String token, @RequestBody RemoveUserRequest removeUserRequest) {
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);

        if(u.getRole() == User.UserRole.ADMIN){
            int c =userService.findAdmins();
            if(c<=1){
                return ResponseEntity.status(412).body("You can not delete last Admin User");
            }
        }

        if(!Objects.equals(u.getPassword(), removeUserRequest.getPassword())){
            return ResponseEntity.status(412).body("Wrong Password");
        }

        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("jwt", "")
                .maxAge(0)
                .build();

        boolean succ = userService.deleteUser(u);
        if (succ)
            return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString()).header(HttpHeaders.ALLOW, "GET").body("Deleted");
        else
            return ResponseEntity.status(409).body("Not deleted");

    }

    @PostMapping("/admin/delete-user")
    public ResponseEntity<String> adminRemoveUser(@CookieValue(name = "jwt") String token, @RequestBody AdminRemoveUserRequest request) {
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        User deleted_user = null;


        if(u.getRole() == User.UserRole.ADMIN){
            try {
                deleted_user = userService.findUserById(request.getUser_id());
            }catch (Exception e){
                return ResponseEntity.status(400).body("There is no user by this id");
            }
            if(deleted_user.getRole() == User.UserRole.ADMIN){
                int c =userService.findAdmins();
                if(c<=1){
                    return ResponseEntity.status(412).body("You can not delete last Admin User");
                }
            }

            boolean succ = userService.deleteUser(deleted_user);
            if (succ)
                return ResponseEntity.status(200).body("Deleted");
            else
                return ResponseEntity.status(409).body("Not deleted");

        }

        return ResponseEntity.status(401).body("Unauthorized");
    }

    @PostMapping("/admin/update-user")
    public ResponseEntity<?> adminUpdateUser(@CookieValue(name = "jwt") String token, User update_user) {
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        if(u.getRole() == User.UserRole.ADMIN){
            boolean succ = userService.updateUser(update_user);
            if (succ)
                return ResponseEntity.status(200).body("Updated");
            else
                return ResponseEntity.status(409).body("Not updated");

        }

        return ResponseEntity.status(401).body("Unauthorized");
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@CookieValue(name = "jwt") String token, @RequestBody UpdatePasswordRequest body) {
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        if(!Objects.equals(body.getCurrentPassword(), u.getPassword())){
            return ResponseEntity.status(412).body("Current passwords not matching");
        }
        else{
            boolean succ =userService.updatePassword(u, body.getNewPassword());
            if(succ){
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(409).build();
    }


    @PostMapping("/update-email")
    public ResponseEntity<String> updateEmail(@CookieValue(name = "jwt") String token, @RequestBody UpdateEmailRequest body) {
        String username = null;
        try {
            username = authService.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User u = userService.findUserByUsername(username);
        u.setEmail(body.getEmail());

        boolean succ = userService.updateUser(u);
        if (succ) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(409).build();
    }



}
