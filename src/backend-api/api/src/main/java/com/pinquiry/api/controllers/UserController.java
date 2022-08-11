package com.pinquiry.api.controllers;

import com.pinquiry.api.model.User;
import com.pinquiry.api.model.rest.request.AdminRemoveUserRequest;
import com.pinquiry.api.model.rest.request.RemoveUserRequest;
import com.pinquiry.api.model.rest.request.TokenRequest;
import com.pinquiry.api.model.rest.request.UpdatePasswordRequest;
import com.pinquiry.api.model.rest.response.BasicUserAttrResponse;
import com.pinquiry.api.service.AuthService;
import com.pinquiry.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(@CookieValue(name = "jwt") String token){
        String username = authService.getUsernameFromToken(token);
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
    public ResponseEntity<?> findUsers(@CookieValue(name = "jwt") String token) {
        String username = authService.getUsernameFromToken(token);
        User u = userService.findUserByUsername(username);
        if(u.getRole() == User.UserRole.ADMIN){
            return ResponseEntity.ok(userService.findAll());
        }
        return ResponseEntity.status(401).body("Not Authorized");

    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody User user) {

        try {
            userService.findUserByUsername(user.getUsername());
        }catch (Exception e) {
            boolean succ = userService.createUser(user);
            if (succ) {
                return ResponseEntity.status(201).build();
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

        try {
            t = authService.createAuthenticationToken(u);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Wrong password");
        }

        ResponseCookie tokenCookie = ResponseCookie.from("jwt", t)
                .build();

        return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).build();

    }

    @GetMapping("/user_logout")
    public ResponseEntity<?> logout() {

        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("jwt", "")
                .maxAge(0)
                .build();


        return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString()).header(HttpHeaders.ALLOW, "GET").body("logged out");
    }






    @PostMapping("/delete-user")
    public ResponseEntity<?> removeUser(@CookieValue(name = "jwt") String token, @RequestBody RemoveUserRequest removeUserRequest) {
        String username = authService.getUsernameFromToken(token);
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

        boolean succ = userService.deleteUser(u);
        if (succ)
            return ResponseEntity.status(200).body("Deleted");
        else
            return ResponseEntity.status(409).body("Not deleted");

    }

    @PostMapping("/admin/delete-user")
    public ResponseEntity<?> adminRemoveUser(@CookieValue(name = "jwt") String token, @RequestBody AdminRemoveUserRequest request) {
        String username = authService.getUsernameFromToken(token);
        User u = userService.findUserByUsername(username);
        User deleted_user = userService.findUserByUsername(request.getDelete_username());
        if(u.getRole() == User.UserRole.ADMIN){
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
        String username = authService.getUsernameFromToken(token);
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
        String username = authService.getUsernameFromToken(token);
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



}
