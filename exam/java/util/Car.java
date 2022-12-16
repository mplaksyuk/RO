package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Car implements Serializable, Comparable<Car>{

    private String id;
    private String name;
    private Integer deployYear;
    private String color;
    private Integer price;
    private String number;

    public Car(String id, String name, Integer deployYear, String color, Integer price, String number){
        this.id=id;
        this.name=name;
        this.deployYear=deployYear;
        this.color=color;
        this.price=price;
        this.number=number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeployYear() {
        return deployYear;
    }

    public void setDeployYear(Integer deployYear) {
        this.deployYear = deployYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(Car other) {
        return this.deployYear.compareTo(other.deployYear);
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\ndeploy year: " + deployYear +
                "\ncolor: " + color +
                "\nprice: " + price +
                "\nnumber: " + number;
    }

    public static List<Car> getList() {
        return new ArrayList<>() {
            {
                add(new Car("01", "Toyoya", 1963,  "red",300000, "123453"));
                add(new Car("02", "Auwi", 2012,  "blue",3000000, "AA123453"));
                add(new Car("03", "BMV", 1963,  "white",200000, "BB123453"));
                add(new Car("04", "Subasu", 1963,  "black",100000, "CC123453"));
                add(new Car("05", "Mitsubisi", 2020,  "orange",700000, "DD123453"));
            }
        };
    }
}
