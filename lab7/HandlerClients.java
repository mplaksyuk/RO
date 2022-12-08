package smth;


public class HandlerClients implements Runnable {
    private CashBox cashBox;

    public HandlerClients(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    public void run() {
        while(true) {
            cashBox.handlerClients();
        }
    }

}
