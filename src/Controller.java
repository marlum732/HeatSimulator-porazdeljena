import javax.naming.ldap.Control;
import javax.swing.*;
import java.util.Random;

public class Controller {
    private int WIDTH = 200;
    private int HEIGHT = 200;
    private int RND_POINTS = 200;
    private int SEED = 555;
    private boolean chartVisible = true;
    private boolean alreadySimulated = false;
    private double[][] temperature;

    private MainFrame mainFrame;
    private ComputationThread computationThread;

    public Controller() {
        generateData();
        computationThread = new ComputationThread(this);
        computationThread.start();
    }

    public boolean isValidSetupPossible(){
        return WIDTH>=10 && HEIGHT >=10 && RND_POINTS >=1;
    }

    public void makeNewSetup() {
        generateData(); //new matrix
        alreadySimulated=false;

        Runnable afterDataGeneration = new Runnable() {
            @Override
            public void run() {
                if(isChartVisible()){
                    generateChart();
                }
            }
        };

        SwingUtilities.invokeLater(afterDataGeneration);
    }

    public void generateChart(){
        mainFrame.setupNewChart(); //new chart
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.pack();
    }

    public void updateExecutionTime(String timeTaken) {
        mainFrame.updateExecutionTime(timeTaken);
    }


    private double[][] generateTemperatureMatrix() {
        Random random = new Random(SEED);
        double[][] t = new double[WIDTH][HEIGHT];
        int x, y;
        for (int i = 0; i < RND_POINTS; i++) {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
            t[x][y] = 1;
        }
        return t;
    }

    public void generateData(){
        temperature = generateTemperatureMatrix();
    }

    public boolean isChartVisible(){
        return chartVisible;
    }


    public void startSimulation() {
        if(!alreadySimulated){
            if (computationThread == null || !computationThread.isAlive()) {
                computationThread = new ComputationThread(this);
                computationThread.setRunning(true);
                computationThread.start();
            } else {
                computationThread.setRunning(!computationThread.isRunning());
            }
            alreadySimulated=true;
        }
    }


    public void repaintChart(){
        mainFrame.getChart().repaint();
    }


    public double[][] getTemperature() {
        return temperature;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setComputationThread(ComputationThread computationThread) {
        this.computationThread = computationThread;
    }


    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public void setRND_POINTS(int RND_POINTS) {
        this.RND_POINTS = RND_POINTS;
    }

    public void setChartVisible(boolean chartVisible) {
        this.chartVisible = chartVisible;
    }
}
