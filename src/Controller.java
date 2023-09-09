
import javax.swing.*;


public class Controller {
    private int WIDTH = 200;
    private int HEIGHT = 200;
    private int RND_POINTS = 200;
    private int SEED = 777;
    private boolean chartVisible = true;
    private boolean alreadySimulated = false;

    private MainFrame mainFrame;
    private ComputationHandler computationHandler;



    public boolean isValidSetupPossible(){
        return WIDTH>=10 && HEIGHT >=10 && RND_POINTS >=1;
    }

    public void makeNewSetup() {
        computationHandler.generateTemperatureMatrix(WIDTH,HEIGHT,RND_POINTS, SEED);
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



    public boolean isChartVisible(){
        return chartVisible;
    }

    public void startSimulation() {
        computationHandler.compute();
    }




    public void repaintChart(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.getChart().repaint();
            }
        });

    }


    public double[][] getTemperature() {
        return computationHandler.getTemperature();
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
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

    public void setComputationHandler(ComputationHandler computationHandler) {
        this.computationHandler = computationHandler;
    }

    public void setAlreadySimulated(boolean alreadySimulated) {
        this.alreadySimulated = alreadySimulated;
    }

    public boolean isAlreadySimulated() {
        return alreadySimulated;
    }
}
