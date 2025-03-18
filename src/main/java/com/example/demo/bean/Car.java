package com.example.demo.bean;

import org.springframework.stereotype.Component;

@Component
public class Car {

    private int carId;
    private String carName;
    private String color;

    public Car() {

    }

    public Car(int carId, String carName, String color) {
        this.carId = carId;
        this.carName = carName;
        this.color = color;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarName() {
        return carName;
    }

    public String getColor() {
        return color;
    }
}
