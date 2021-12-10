package com.restaurant.server.restaurantservermanager.controller.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ResponseGenericVarArgs<T> extends ResponseStatus{
    private HashMap<String, T> map;

    public ResponseGenericVarArgs(boolean status, String reason, HashMap<String, T> map) {
        super(status, reason);
        this.map = map;
    }

    public HashMap<String, T> getMap() {
        return map;
    }

    public void setMap(HashMap<String, T> map) {
        this.map = map;
    }
}
