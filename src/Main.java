
public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();

        MainFrame mainFrame = new MainFrame(controller);

        controller.setMainFrame(mainFrame);
    }



}