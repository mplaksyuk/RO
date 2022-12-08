package smth;

public class Observer implements Runnable {
    private CashBox cashBox;

    public Observer(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    public void run() {
        try {
            while(true) {
                Thread.sleep(1000);
                cashBox.observerTime();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}