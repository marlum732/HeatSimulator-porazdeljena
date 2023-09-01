import javax.swing.*;

public class MainFrame extends JFrame {

    private ContentPanel contentPanel;

    public MainFrame(Controller controller) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);


        contentPanel = new ContentPanel(controller);
        this.add(contentPanel);

        this.pack();
        this.setVisible(true);
    }

    public void updateExecutionTime(String s) {
        contentPanel.updateExecutionTime(s);
    }

    public void setupNewChart(){
        contentPanel.setupNewChart();
    }

    public Chart getChart() {
        return contentPanel.getChart();
    }

}