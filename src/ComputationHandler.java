import java.util.Random;
import java.util.concurrent.Phaser;


public class ComputationHandler {
    private Controller controller;
    private double[][] temperature;

    public ComputationHandler(Controller controller) {
        this.controller = controller;
        generateTemperatureMatrix(200,200, 20, 1);
    }


    public void generateTemperatureMatrix(int width, int height, int nPoints, int seed) {
        Random random = new Random(seed);
        double[][] t = new double[width][height];
        int x, y;
        for (int i = 0; i < nPoints; i++) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            t[x][y] = 1;
        }

        temperature = t;
    }


    public void compute() {
        if (controller.isAlreadySimulated()) return;

        long startTime = System.currentTimeMillis();

        int nThreads = Runtime.getRuntime().availableProcessors()-1;
        ComputationThread[] computationThreads = new ComputationThread[nThreads];

        Phaser phaser = new Phaser();

        int chunk = temperature.length / nThreads;
        for (int i = 0; i < nThreads; i++) {
            if (i == nThreads-1) {
                computationThreads[i] = new ComputationThread(i*chunk, temperature.length, temperature, phaser, controller);
            } else {
                computationThreads[i] = new ComputationThread(i*chunk, i*chunk+chunk, temperature, phaser, controller);
            }
            computationThreads[i].start();
            phaser.register();
        }

        for (int i = 0; i < nThreads; i++) {
            try {
                computationThreads[i].join();
            } catch (InterruptedException e) {
                System.err.println("Thread unable to join");
            }
        }


        long endTime = System.currentTimeMillis();

        controller.setAlreadySimulated(true);
        controller.updateExecutionTime(String.valueOf(endTime - startTime) + " ms");
    }


    public double[][] getTemperature() {
        return temperature;
    }
}
