package com.src.fookiiapp.model.entity;

import java.io.Serializable;

/**
 * Created by Ping on 2017/4/19 0019.
 */

public class CityEntity implements Serializable {
    public String city_id;
    public String city_name;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
