import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class lab4_2 {
    public static void main(String[] args)  {
        try {
            FileWriter fw = new FileWriter("./task_b.txt");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Garden garden = new Garden();
        ThreadGarden threadGarden1 = new ThreadGarden(1, garden);
        ThreadGarden threadGarden2 = new ThreadGarden(2, garden);
        ThreadGarden threadGarden3 = new ThreadGarden(3, garden);
        ThreadGarden threadGarden4 = new ThreadGarden(4, garden);

        threadGarden2.setDaemon(true);
        threadGarden3.setDaemon(true);
        threadGarden1.setDaemon(true);
        threadGarden4.setDaemon(true);
        try {
            threadGarden2.start();
            Thread.sleep(1000);
            threadGarden3.start();
            Thread.sleep(500);
            threadGarden1.start();
            Thread.sleep(1000);
            threadGarden4.start();
            Thread.sleep(500);

            threadGarden2.join();
            threadGarden3.join();
            threadGarden1.join();
            threadGarden4.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Garden {
    private int size = 10;
    private ReadWriteLock readWriteLock;
    public int [][] plants = new int[size][size];

    Garden(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                plants[i][j] = 1;
            }
        }
        readWriteLock = new ReentrantReadWriteLock();
    }
    public void setPlants(int[][] plants) {
        this.plants = plants;
    }
    public int getSize() {
        return size;
    }
    public void lockRead() {
        readWriteLock.readLock().lock();
    }
    public void lockWrite(){
        readWriteLock.writeLock().lock();
    }
    public void unlockRead(){
        readWriteLock.readLock().unlock();
    }
    public void unlockWrite(){
        readWriteLock.writeLock().unlock();
    }
}

class ThreadGarden extends Thread {
    private int task;
    private Garden garden;

    public ThreadGarden(int task, Garden garden) {
        this.task = task;
        this.garden = garden;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i <=5; i++) {
                switch (this.task) {
                    case 1:
                        System.out.println("gardener work");
                        gardener();
                        break;
                    case 2:
                        System.out.println("nature work");
                        nature();
                        break;
                    case 3:
                        System.out.println("monitor 1 work");
                        monitor1();
                        break;
                    case 4:
                        System.out.println("monitor 2 work");
                        monitor2();
                        break;
                    default:
                        System.out.println("error");
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void monitor2(){ //to console
        garden.lockRead();
        
        int size = garden.getSize();
        for (int i = 0; i < size-1; i++){
            System.out.println(Arrays.toString(garden.plants[i]));
        }
        System.out.println("___________________");
        
        garden.unlockRead();
        
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
    }

    private void monitor1() throws IOException { //to txt file
        FileWriter fw = new FileWriter("./task_b.txt", true);
        
        garden.lockRead();
        
        int size = garden.getSize();
        for (int i = 1; i < size; i++){
            fw.write(Arrays.toString(garden.plants[i]));
            fw.write("\n");
        }
        fw.write("__________________________\n");
        fw.close();
        
        garden.unlockRead();
        
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
    }

    private void nature (){
        garden.lockWrite();
        
        int size = garden.getSize();
        Random rand = new Random();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++) {
                if (garden.plants[i][j] == 1)
                    garden.plants[i][j] = rand.nextInt(2);
            }
        }

        garden.setPlants(garden.plants);
        garden.unlockWrite();

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }
    }

    private void gardener (){
        garden.lockWrite();

        int size = garden.getSize();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                if (garden.plants[i][j] == 0)
                    garden.plants[i][j] = 1;
            }
        }

        garden.setPlants(garden.plants);
        garden.unlockWrite();

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e ){
            e.printStackTrace();
        }

    }
}