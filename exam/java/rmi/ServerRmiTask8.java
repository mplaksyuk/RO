package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import util.Car;

interface RMIServer extends Remote {

    List<Car> displayUsedLonger(Integer a) throws RemoteException;

    List<Car> displaySelectedYearAndBiggerPrice(Integer a, Integer b) throws RemoteException;
}


public class ServerRmiTask8 {
    static class Service extends UnicastRemoteObject implements RMIServer {

        List<Car> cars;

        Service() throws RemoteException {
            super();
            cars = Car.getList();
        }

        @Override
        public List<Car> displayUsedLonger(Integer a) {
            List<Car> result = new ArrayList<>();
            for( Car car: cars){
                if ((2022-car.getDeployYear())>a){
                    result.add(car);
                }
            }
            Collections.sort(result);

            return result;
        }

        @Override
        public List<Car> displaySelectedYearAndBiggerPrice(Integer a,Integer b) {
            List<Car> result = new ArrayList<>();

            for (Car car: cars) {
                if (Objects.equals(car.getDeployYear(), a) && car.getPrice()>b) {
                    result.add(car);
                }
            }

            return result;
        }
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(123);
        RMIServer service = new Service();

        registry.rebind("exam", service);

        System.out.println("Server started!");
    }
}

