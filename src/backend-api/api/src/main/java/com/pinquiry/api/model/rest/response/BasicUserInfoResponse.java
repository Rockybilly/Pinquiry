package com.pinquiry.api.model.rest.response;

public class BasicUserInfoResponse {
    private Long user_id;
    private String username;
    private int numberOfMonitors;

    public BasicUserInfoResponse() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfMonitors() {
        return numberOfMonitors;
    }

    public void setNumberOfMonitors(int numberOfMonitors) {
        this.numberOfMonitors = numberOfMonitors;
    }
}
