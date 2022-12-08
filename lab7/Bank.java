package smth;

import java.util.ArrayList;

public class Bank {
    private Repository repository;

    public Repository getRepository() {
        return repository;
    }

    private String name = "Delta bank";
    private ArrayList<CashBox> cashBoxes = new ArrayList<CashBox>();
    private int limit = 5;

    public Bank(Repository repository) {
        this.repository = repository;
        for (int i = 0; i < limit; i++) {
            cashBoxes.add(new CashBox(i + 1, this));
        }
    }

    public CashBox selectBestQueue() {
        CashBox cashBoxBest = null;
        synchronized(cashBoxes) {
            cashBoxBest = cashBoxes.get(0);
            for (CashBox box : cashBoxes) {
                if (cashBoxBest.getTotalClientsInQueue() > box.getTotalClientsInQueue()) {
                    cashBoxBest = box;
                }
            }
        }

        return cashBoxBest;
    }

}