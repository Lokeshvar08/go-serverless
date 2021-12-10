package com.restaurant.server.restaurantservermanager.controller;

import java.util.List;

public class ResponseGenericListObject<T> {
    private List<T> data;
    private boolean status;
    private String reason;

    public List<T> getList() {
        return data;
    }

    public void setList(List<T> list) {
        this.data = list;
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

    public ResponseGenericListObject(List<T> list, boolean status, String reason) {
        this.data = list;
        this.status = status;
        this.reason = reason;
    }


    public ResponseGenericListObject(boolean status, String reason) {
        this.status = status;
        this.reason = reason;
    }
}
