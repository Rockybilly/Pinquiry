package com.pinquiry.api.model.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicUserAttrResponse {
    private long id;
    private boolean admin;
    private String username;

    public BasicUserAttrResponse() {
    }

    @JsonProperty("user_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @JsonProperty("is_admin")
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
