package socket;

import util.CarManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerSocketTask8 {
    private ServerSocket serverSocket;
    private final Executor executor;
    private final CarManager manager;
    public ServerSocketTask8(int port, int size) {
        executor = Executors.newSingleThreadExecutor();
        manager = new CarManager();
        try {
            serverSocket = new ServerSocket(port, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocketTask8 server = new ServerSocketTask8(25565, 5);
        server.run();
    }
    public void run() {
        while (true){
            try {
                var client = serverSocket.accept();
                System.out.println(client + " connected!");
                executor.execute(new ClientRunnable(manager,client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static class ClientRunnable implements Runnable{
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private final CarManager manager;
        private final Socket client;
        public ClientRunnable(CarManager manager, Socket client) {
            this.manager = manager;
            this.client = client;
            try {
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void getCars() throws IOException {
            var cars = manager.getCars();
            out.writeObject(Objects.requireNonNullElseGet(cars, ArrayList::new));
        }
        private void getSortedCars() throws IOException {
            var cars = manager.getSortedCars();
            out.writeObject(Objects.requireNonNullElseGet(cars, ArrayList::new));
        }
        private void getCarsByYearAndRange() throws IOException, ClassNotFoundException {
            Integer a = (Integer) in.readObject();
            Integer b = (Integer) in.readObject();
            var cars = manager.getCarsByYearAndPrice(a,b);
            out.writeObject(Objects.requireNonNullElseGet(cars, ArrayList::new));
        }
        @Override
        public void run() {
            while (!Thread.interrupted()){
                try {
                    if(client.isClosed() || !client.isConnected()) break;
                    String code = (String)in.readObject();
                    System.out.println(">" + code);
                    switch (code) {
                        case "get cars" -> getCars();
                        case "get sorted cars" -> getSortedCars();
                        case "get cars by year and price" -> getCarsByYearAndRange();
                        default -> out.writeObject(new ArrayList<>());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println(client + " disconnected!");
                    return;
                }
            }
        }
    }
}
