package com.example.islam.wireframe.Model;

/**
 * Created by islam on 24/12/2016.
 */

public class Costumer {

    private String name;
    private String id;
    private String address;
    private String phone;
    private String singniture;
    private float lat;
    private float lng;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSingniture() {
        return singniture;
    }

    public void setSingniture(String singniture) {
        this.singniture = singniture;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
