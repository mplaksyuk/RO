package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CarManager {
    private final List<Car> cars;

    public CarManager() {
        cars = new ArrayList<>();
        cars.add(new Car("01", "Toyoya", 1963, "red", 300000, "123453"));
        cars.add(new Car("02", "Auwi", 2012, "blue", 3000000, "AA123453"));
        cars.add(new Car("03", "BMV", 1963, "white", 200000, "BB123453"));
        cars.add(new Car("04", "Subasu", 1963, "black", 100000, "CC123453"));
        cars.add(new Car("05", "Mitsubisi", 2020, "orange", 700000, "DD123453"));
    }

    public synchronized void addCar(Car car) {
        cars.add(car);
    }

    public synchronized List<Car> getCars() {return cars;}

    public synchronized List<Car> getSortedCars() {
        List<Car> res = new ArrayList<>(cars);
        res.sort(Comparator.comparing(Car::getName));
        return res;
    }

    public synchronized List<Car> getCarsByYearAndPrice(int a, int b) {
        List<Car> res = new ArrayList<>();
        cars.forEach(car -> {
            if (Objects.equals(car.getDeployYear(), a) && car.getPrice()>b)
                res.add(car);
        });
            return res;
        }
    }