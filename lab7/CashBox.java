package smth;


import java.util.ArrayList;

public class CashBox {
    private Bank bank;
    private int id;
    public int getId() {
        return id;
    }

    private double limit = 2000;
    private double limitDown = 3000;
    private double limitUp = 7000;

    private double totalCash = 5000; // даем кассирам по 5000$
    private Object totalCashLocked = new Object();

    private HandlerClients handlerClients;

    private int totalClientsInQueue = 0; // количество человек в очереди к кассиру
    public int getTotalClientsInQueue() {
        return totalClientsInQueue;
    }

    private ArrayList<Client> clients = new ArrayList<Client>(); // не используем очередь так как клиенты могут мигрировать в другую очередь
    public ArrayList<Client> getClients() {
        return clients;
    }

    public CashBox(int id, Bank bank) {
        this.bank = bank;
        this.id = id;
        handlerClients = new HandlerClients( this );
        new Thread( handlerClients ).start();

        // стартуем поток наблюдатель, который переводит наличность более 8000$ в хранилище
        // или берет из хранилище при сумме менее 3000$
        new Thread( new Observer(this) ).start();

    }

    public void addClient(Client client) {
        clients.add(client);
        totalClientsInQueue += 1;
        System.out.println(client.toString() + " встал в очередь в кассу № " + this.id  + " Всего в очереди: " + totalClientsInQueue);
        this.notifyHandlerClients();
    }

    synchronized private void notifyHandlerClients() {
        notify();
    }

    private void payForSomething() {
        double summa = Math.random()*1000 + 100;
        synchronized (totalCashLocked) {
            totalCash += summa;
        }
    }

    private void takeMoney() {
        double summa = Math.random()*1000 + 100;
        synchronized (totalCashLocked) {
            if (totalCash >= summa) {
                totalCash -= summa;
            }
        }
    }

    private void Operation() {
        int rand = (int)(Math.random()*2);
        switch(rand) {
            case 0:
                payForSomething();
                break;
            default:
                takeMoney(); // снять деньги со счета или еще чего
        }
    }

    public void observerTime() {
        synchronized (totalCashLocked) {
            if (totalCash < limitDown) {
                System.out.println("Поток наблюдатель за финансами: getMoney " + totalCash);
                bank.getRepository().getMoney(limit);
                totalCash += limit;
                System.out.println("Поток наблюдатель за финансами: getMoney " + totalCash);
            } else if (totalCash > limitUp) {
                System.out.println("Поток наблюдатель за финансами: addMoney " + totalCash);
                bank.getRepository().addMoney(limit);
                totalCash -= limit;
                System.out.println("Поток наблюдатель за финансами: addMoney " + totalCash);
            }
        }
    }

    synchronized public void handlerClients() {
        try {
            if (this.totalClientsInQueue != 0) {
                Client firstClientInQuiue = clients.get(0);

                System.out.println(firstClientInQuiue.toString() + " производит операции в кассе № " + this.id);
                Operation();
                System.out.println(firstClientInQuiue.toString() + " № " + this.id + " Наличные в кассе: " + totalCash);
                Thread.sleep( (int)( Math.random()*3000 + 500 ));

                System.out.println(firstClientInQuiue.toString() + " ушел из кассы № " + this.id);

                clients.remove(0);
                --totalClientsInQueue;

                Thread.sleep( (int)( Math.random()*500 + 500 ));
            } else {
                System.out.println("Свободная касса № " + this.id);
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
