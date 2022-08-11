package com.pinquiry.api.model.rest.request;

public class AdminRemoveUserRequest {
    private String delete_username;

    public AdminRemoveUserRequest() {
    }

    public String getDelete_username() {
        return delete_username;
    }

    public void setDelete_username(String delete_username) {
        this.delete_username = delete_username;
    }
}
