public class ComputationThread extends Thread {

    private int WIDTH;
    private int HEIGHT;
    private double[][] temperature;
    private final Controller controller;
    private boolean isRunning = false;
    private double CONDITION = 0.0025;


    public ComputationThread(Controller controller) {
        this.controller=controller;
        this.temperature = controller.getTemperature();
        this.WIDTH = temperature.length;
        this.HEIGHT = temperature[0].length;
    }


    @Override
    public void run() {
        while (!isRunning){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean stable = false;

        long start = System.currentTimeMillis();
        while (!stable) {
            stable = true;

            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    if (temperature[i][j] == 1) continue;

                    double value = 0;
                    if (i > 0) value += temperature[i - 1][j];
                    if (i < WIDTH - 1) value += temperature[i + 1][j];
                    if (j > 0) value += temperature[i][j - 1];
                    if (j < HEIGHT - 1) value += temperature[i][j + 1];
                    value /= 4;

                    double diff = Math.abs(value - temperature[i][j]);
                    if (diff > CONDITION) {
                        stable = false;
                    }
                    temperature[i][j] = value;
                }
            }
            if (!stable && controller.isChartVisible()) {
                controller.repaintChart();
            }
        }
        long end = System.currentTimeMillis();

        controller.updateExecutionTime((end-start) + "ms");
        System.out.println("Execution time: " + (end - start) + " ms");
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        temperature = controller.getTemperature();
        WIDTH = temperature.length;
        HEIGHT = temperature[0].length;
        isRunning = running;
    }
}