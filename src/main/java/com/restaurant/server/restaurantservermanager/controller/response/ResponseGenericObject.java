package com.restaurant.server.restaurantservermanager.controller.response;

public class ResponseGenericObject<T> extends ResponseStatus{
    private T data;
    private String reason;

    public ResponseGenericObject(T data, boolean status, String reason) {
        super(status, reason);
        this.data = data;
    }

    public ResponseGenericObject(boolean status, String reason) {
        super(status, reason);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
