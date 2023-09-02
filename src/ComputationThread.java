import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class ComputationThread extends Thread {

    private final Controller controller;
    private final double CONDITION = 0.0025;
    private int WIDTH;
    private int HEIGHT;
    private double[][] temperature;
    private final int start;
    private final int end;
    private Phaser phaser;


    public ComputationThread(int start, int end, double[][] temperature, Phaser phaser, Controller controller) {
        this.controller = controller;
        this.WIDTH = temperature.length;
        this.HEIGHT = temperature[0].length;
        this.temperature = temperature;
        this.start = start;
        this.end = end;
        this.phaser = phaser;
    }


    @Override
    public void run() {
        System.out.println("Thread started: " + Thread.currentThread().getId());

        int iterationCount = 0;
        boolean localStable = false;

        while (!localStable) {
            iterationCount++;
            localStable = true;

            for (int i = start; i < end; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (temperature[i][j] == 1) continue;

                    double value = 0;
                    if (i > 0) value += temperature[i - 1][j];
                    if (i < WIDTH - 1) value += temperature[i + 1][j];
                    if (j > 0) value += temperature[i][j - 1];
                    if (j < HEIGHT - 1) value += temperature[i][j + 1];
                    value /= 4;

                    double diff = Math.abs(value - temperature[i][j]);
                    if (diff > CONDITION) {
                        localStable = false;
                    }
                    temperature[i][j] = value;
                }
            }

            if (start == 0) controller.repaintChart();
            phaser.arriveAndAwaitAdvance();
        }
        phaser.arriveAndDeregister();
        System.out.println("Thread finished: " + Thread.currentThread().getId() + " Iteration count: " + iterationCount);
    }
}