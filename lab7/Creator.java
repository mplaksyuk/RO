package smth;

import java.util.concurrent.Semaphore;

/**
 * Created by AMakas on 31.01.2017.
 */
public class Creator implements Runnable {
    private Client client;
    private Semaphore semaphore;

    public Creator(Client client) {
        this.client = client;
        semaphore = new Semaphore(1);
    }

    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000 + 1000));
            System.out.println(client.toString() + " пришел в банк. Определяет в какую очередь стать. ");

            semaphore.acquire();
            CashBox cashBox = client.getBank().selectBestQueue();
            cashBox.addClient(client);
            semaphore.release();


            // ожидание

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}