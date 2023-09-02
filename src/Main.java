
public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();

        ComputationHandler computationHandler = new ComputationHandler(controller);
        controller.setComputationHandler(computationHandler);

        MainFrame mainFrame = new MainFrame(controller);
        controller.setMainFrame(mainFrame);
    }



}