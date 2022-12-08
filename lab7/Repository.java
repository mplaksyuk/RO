package smth;

public class Repository {
    private double totalMoney = 800000;

    public double getTotalMoney() {
        return totalMoney;
    }

    synchronized public double getMoney(double limit) {
        return totalMoney - limit;
    }
    synchronized public double addMoney(double limit) {
        return totalMoney + limit;
    }
}