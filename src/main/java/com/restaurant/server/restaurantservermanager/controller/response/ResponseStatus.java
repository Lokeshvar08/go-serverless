package com.restaurant.server.restaurantservermanager.controller.response;

public class ResponseStatus {
    private boolean status;
    private String reason;

    public ResponseStatus(boolean status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
