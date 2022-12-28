package com.pinquiry.api.model.rest.request.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePasswordRequest {
    @JsonProperty("current_password")
    private String currentPassword;
    @JsonProperty("new_password")
    private String newPassword;

    public UpdatePasswordRequest() {
    }


    @JsonProperty("current_password")
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    @JsonProperty("new_password")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
