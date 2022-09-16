import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Ivanov implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i != main_B.SIZE;) {
            try {
                while (main_B.queue1.size() != 0)
                    Thread.sleep(10);

                System.out.println("Ivanov " + i);
                main_B.queue1.put(i++);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class Petrov implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Integer item = main_B.queue1.take();
                System.out.println("Petrov took " + item);
                main_B.queue2.put(item); 
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class Nechip implements Runnable {
    @Override
    public void run(){
        while(true){
            try {
                Integer item = main_B.queue2.take();
                System.out.println("Nechip count " + item);        
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class main_B {
    public static int SIZE = 100;
    public static BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
    public static BlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        new Thread(new Ivanov()).start();
        new Thread(new Petrov()).start();
        new Thread(new Nechip()).start();
    }
}