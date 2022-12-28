package com.pinquiry.api.model.rest.request.webapp.admin;

public class AdminRemoveUserRequest {
    private Long user_id;

    public AdminRemoveUserRequest() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
