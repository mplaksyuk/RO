import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadsManager extends Thread {
    private StringWithCounting string;
    private final StringEditor modifier;
    private final CyclicBarrier barrier;
    private final CyclicBarrier gate;

    public ThreadsManager(CyclicBarrier gate, CyclicBarrier barrier) {
        this.modifier = new StringEditor();
        this.barrier = barrier;
        this.gate = gate;

        int coef = (int) 1e+4;
        initializaString(5 * coef, 10 * coef);
    }

}