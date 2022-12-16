package rmi;

import util.Car;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class ClientRmiTask8 {

    public static void logCars(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("None!");
            return;
        }

        for (var car : cars) {
            System.out.println(car);
        }
    }

    public static void main(String[] args) {
        int choice;
        int a;
        int b;

        Scanner in = new Scanner(System.in);

        try {
            RMIServer rmiServer = (RMIServer) Naming.lookup("//localhost:1234/exam");

            System.out.println(
                    """
                    Choose option:
                    1 - display Cars in alphabetic order
                    2 - display Cars which deploy year 'a' and price bigger than 'b'
                    """
            );

            choice = in.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter 'a' value: ");
                    a=in.nextInt();
                    logCars(rmiServer.displayUsedLonger(a));
                }
                case 2 -> {
                    System.out.print("Enter 'a' value: ");
                    a = in.nextInt();

                    System.out.print("Enter 'b' value: ");
                    b = in.nextInt();

                    logCars(rmiServer.displaySelectedYearAndBiggerPrice(a, b));
                }
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
