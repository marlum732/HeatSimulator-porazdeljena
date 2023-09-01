import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

    private SetupPanel setupPanel;
    private Chart chart;
    private boolean mapVisible = true;
    private Controller controller;



    public ContentPanel(Controller controller) {
        this.controller=controller;
        this.setLayout(new BorderLayout());

        setupPanel = new SetupPanel(300, 500, controller);
        this.add(setupPanel, BorderLayout.CENTER);

        chart = new Chart(controller);
        chart.setVisible(mapVisible);
        this.add(chart, BorderLayout.EAST);

    }


    public void setupNewChart() {
        Chart newChart = new Chart(controller);
        remove(chart);
        chart = newChart; //new chart
        add(chart, BorderLayout.EAST); //to panel
        revalidate();
        repaint();
    }

    public void showHideMap() {
        mapVisible = !mapVisible;
        chart.setVisible(mapVisible);

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (mapVisible) {
            chart.setPreferredSize(new Dimension(600, chart.getHeight()));
        } else {
            chart.setPreferredSize(new Dimension(0, chart.getHeight()));
        }

        revalidate();
        repaint();

        if (parentFrame != null) {
            parentFrame.pack();
        }

    }

    public void updateExecutionTime(String s){
        setupPanel.updateExecutionTime(s);
    }

    public Chart getChart() {
        return chart;
    }
}