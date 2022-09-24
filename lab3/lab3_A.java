package lab3;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class lab3_A {
    public static final int N = 10;

    private static Integer HoneyBank = 0;
    private static final Semaphore semaphore = new Semaphore(1);
    private static final CyclicBarrier cb = new CyclicBarrier(N, new WinniePooh());

    private static class Bee implements Runnable {

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    semaphore.acquire();
                    HoneyBank++;
                    semaphore.release();

                    cb.await();
                } 
                catch (InterruptedException | BrokenBarrierException e) {
                    System.err.println(e);
                }
            }

        }
    }

    private static class WinniePooh implements Runnable {

        public void run() {

            System.out.println("Last bee woke up Winnie Pooh");
            try {
                semaphore.acquire();
                System.out.println("Winnie Pooh is eating honey = " + HoneyBank);
                HoneyBank = 0;
                semaphore.release();
            } 
            catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] bees = new Thread[N];
        for (int i = 0; i < N; ++i) {
            bees[i] = new Thread(new Bee());
            bees[i].start();
        }

        Thread.sleep(10000);

        for (Thread t : bees) {
            t.interrupt();
        }
    }
}