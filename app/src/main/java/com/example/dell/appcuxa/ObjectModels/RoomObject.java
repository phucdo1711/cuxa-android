package com.example.dell.appcuxa.ObjectModels;

import java.io.Serializable;

public class RoomObject implements Serializable {
    public String type;
    public String name;
    public String price;
    public String electricityPrice;
    public String waterPrice;
    public String downPayment;
    public LocationRoom location;
    public String address;
    public String[] images;
    public String area;

    public RoomObject(String type, String name, String price, String electricityPrice, String waterPrice, String downPayment, LocationRoom location, String address, String[] images, String area) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.electricityPrice = electricityPrice;
        this.waterPrice = waterPrice;
        this.downPayment = downPayment;
        this.location = location;
        this.address = address;
        this.images = images;
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(String electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public String getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(String waterPrice) {
        this.waterPrice = waterPrice;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public LocationRoom getLocation() {
        return location;
    }

    public void setLocation(LocationRoom location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
