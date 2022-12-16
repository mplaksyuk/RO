package socket;

import util.Car;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientSocketTask8 {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientSocketTask8(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Car> getCars() {
        try {
            out.writeObject("get cars");
            return (List<Car>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Car> getSortedCars() {
        try {
            out.writeObject("get sorted cars");
            return (List<Car>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Car> getCarsByCard(int a, int b) {
        try {
            out.writeObject("get cars by year and price");
            out.writeObject(a);
            out.writeObject(b);
            return (List<Car>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ClientSocketTask8 client = new ClientSocketTask8("localhost", 25565);
        System.out.println("All cars");
        System.out.println(client.getCars());
        System.out.println("Sorted cars");
        System.out.println(client.getSortedCars());
        System.out.println("year 1963&& price>190000");
        System.out.println(client.getCarsByCard(1963, 190000));
    }
}
