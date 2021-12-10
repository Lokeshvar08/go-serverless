package com.restaurant.server.restaurantservermanager.controller.response;

import java.util.List;

public class ResponseGenericListObject<T> extends ResponseStatus {
    private List<T> data;

    public List<T> getList() {
        return data;
    }

    public void setList(List<T> list) {
        this.data = list;
    }

    public ResponseGenericListObject(List<T> list, boolean status, String reason) {
        super(status, reason);
        this.data = list;
    }


    public ResponseGenericListObject(boolean status, String reason) {
        super(status, reason);
    }
}
