package com.example.demo.controller;

import com.example.demo.bean.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {

    private List<Car> cars = new ArrayList<>();

    // Constructor to initialize the list of cars
    public CarController() {
        cars.addAll(Arrays.asList(
                new Car(1, "Toyota", "red"),
                new Car(2, "Honda", "black"),
                new Car(3, "Ford", "white"),
                new Car(4, "BMW", "black"),
                new Car(5, "Chevrolet", "red")
        ));
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to home Page!";
    }

    @GetMapping("/getCar")
    public ResponseEntity<Car> getCar() {
        Car car = new Car(1, "Maruti", "green");
        return ResponseEntity.ok().header("my header", "car detail").body(car);
    }

    @GetMapping("/getCars")
    public ResponseEntity<List<Car>> getCars() {
        return ResponseEntity.ok().header("my header", "cars detail").body(cars);
    }

    //http://localhost:8080/car/getCar/4/BMW/black
    @GetMapping("/getCar/{id}/{carName}/{color}")
    public ResponseEntity<Car> getCarbyPathVariable(@PathVariable(name = "id") int carId,

                                                    @PathVariable String carName, @PathVariable String color) {

        Optional<Car> carOptional = cars.stream()
                .filter(car -> car.getCarId() == carId && car.getCarName().equals(carName) && car.getColor().equals(color))
                .findFirst();
        if (carOptional.isPresent()) {

            return ResponseEntity.ok(carOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }


    }

    //http://localhost:8080/car/query?carId=4&&carName=BMW&&color=black
    @GetMapping("/query")
    public ResponseEntity<Car> getCarByRequestParam(@RequestParam int carId, @RequestParam String carName,
                                                    @RequestParam String color) {

        Optional<Car> optionalCar = cars.stream().filter(car -> car.getCarId() == carId && car.getCarName().equals(carName) && car.getColor().equals(color)).findFirst();
        if (optionalCar.isPresent()) {
            return ResponseEntity.ok(optionalCar.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    // Create a new car and add it to the list
    @PostMapping("/createCar")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        cars.add(car); // Add the car to the list
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    // Update a car in the list by its carId
    @PutMapping("/updateCar/{id}")
    public ResponseEntity<Car> updateCar(@RequestBody Car updatedCar, @PathVariable("id") int carId) {
        Optional<Car> carOptional = cars.stream()
                .filter(car -> car.getCarId() == carId)
                .findFirst();

        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setCarName(updatedCar.getCarName());
            car.setColor(updatedCar.getColor());
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Delete a car from the list by its carId
    @DeleteMapping("/deleteCar/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(name = "id") int carId) {
        Optional<Car> carOptional = cars.stream()
                .filter(car -> car.getCarId() == carId)
                .findFirst();

        if (carOptional.isPresent()) {
            cars.remove(carOptional.get());
            return ResponseEntity.ok("Car deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car not found");
        }
    }
}
