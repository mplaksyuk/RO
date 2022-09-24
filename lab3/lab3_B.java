package lab3;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class lab3_B {

    private final AtomicBoolean isBarberFree = new AtomicBoolean(true);
    private final Object barberControl = new Object();
    private final Object clientControl = new Object();
    private final Queue<Client> queue = new ConcurrentLinkedQueue<>();
    
    private class Barber implements Runnable{
        private String name;

        public Barber(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (barberControl){
                while (true){
                    if (queue.isEmpty()){
                        try {
                            System.out.println(name + " falling asleep");
                            isBarberFree.set(true);
                            barberControl.wait();
                        }
                        catch (InterruptedException e){
                            System.out.println(e);
                        }
                    }
                    else {
                        Client current_client = queue.remove();
                        System.out.println(name + " getting next " + current_client.name);
                        current_client.startHaircut();
                    }
                }
            }
        }
    }

    public class Client extends Thread{
        private String name;

        public Client(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (clientControl){
                while (true){
                    if (isBarberFree.get()){
                        synchronized (barberControl){
                            isBarberFree.set(false);
                            barberControl.notify();
                            System.out.println(name + " starts to wake up the barber");
                        }
                    }
                    else {
                        try {
                            System.out.println(name + " falls asleep");
                            clientControl.wait();
                        } 
                        catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                }
            }
        }
        public void startHaircut(){
            try {
                System.out.println(name + " starts to get a haircut");
                Thread.sleep(2000);
            }
            catch (InterruptedException e ){
                System.out.println(e);
            }
        }
    }
    public void start(){
        Thread barber = new Thread(new Barber("Barber"));
        barber.start();

        try {
            Thread.sleep(2000);

            for (int i = 1; i <= 4; i++){
                Client client = new Client("Client " + i);
                queue.add(client);
                System.out.println(client.name + " comes to barbershop");
                client.start(); 
            }

        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        lab3_B barberShop = new lab3_B();
        barberShop.start();
    }
}