import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class main_A {
    private boolean[][] field;

    private Integer size_field = 100;

    private final AtomicBoolean vinni_founded;

    private AtomicInteger next_task;

    private Thread[] bees;

    private class Bees extends Thread {
        private Integer row;

        public Bees() { }

        public void run() {
            while(!vinni_founded.get()) {

                synchronized(next_task) {
                    this.row = next_task.get();
                    next_task.incrementAndGet();
                }

                for(int column = 0; !vinni_founded.get() && column < size_field; column++) {
                    if (field[this.row][column]) {
                        vinni_founded.set(true);
                        System.out.println(Thread.currentThread().getName() + " Vinnie was founded in row " + this.row);
                        break;
                    }
                }
                if (vinni_founded.get()) { break; }
                System.out.println(Thread.currentThread().getName() + " group of bees in row " + this.row);               
            }
        }
    }

    public main_A(Integer size, Integer bees_count) {
        this.field = new boolean[size][size];
        this.size_field = size;
        this.bees = new Thread[bees_count];

        vinni_founded = new AtomicBoolean(false);
        next_task = new AtomicInteger(0);

        

        int vinni_index = (int)(Math.random() * Math.pow(size, 2));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i * size + j == vinni_index) {
                    field[i][j] = true;
                    System.out.println("Vinnie is in row: " + i + " column " + j);
                    continue;
                }
                field[i][j] = false;
            }
        }
    }

    private void checkField() {
        for (int i = 0; i < this.bees.length; i++) {
            bees[i] = new Bees();
            bees[i].start();
        }
    }

    public static void main(String[] args) {
        main_A field = new main_A(100, 4);

        field.checkField();
    } 
}


