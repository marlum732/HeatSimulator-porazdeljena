import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupPanel extends JPanel {

    private JLabel executionTime;
    private JTextField inputWidth, inputHeight, inputRndPoints;
    private JButton showGuiButton, startSimulationButton, newSetupButton;
    private Controller controller;

    public SetupPanel(int WIDTH, int HEIGHT, Controller controller) {
        this.controller = controller;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        SpringLayout layout = new SpringLayout();
        setLayout(layout);


        JLabel titleLabel = new JLabel("SEQUENTIAL Simulator");
        JLabel widthLabel = new JLabel("Input width:");
        JLabel heightLabel = new JLabel("Input height:");
        JLabel pointsLabel = new JLabel("Input points:");
        executionTime = new JLabel("Execution time: ");

        inputWidth = new JTextField(15);
        inputHeight = new JTextField(15);
        inputRndPoints = new JTextField(15);

        TextFieldChangeListener listener = new TextFieldChangeListener();
        inputWidth.getDocument().addDocumentListener(listener);
        inputHeight.getDocument().addDocumentListener(listener);
        inputRndPoints.getDocument().addDocumentListener(listener);


        DocumentFilter numberFilter = new NumberDocumentFilter();
        ((AbstractDocument) inputWidth.getDocument()).setDocumentFilter(numberFilter);
        ((AbstractDocument) inputHeight.getDocument()).setDocumentFilter(numberFilter);
        ((AbstractDocument) inputRndPoints.getDocument()).setDocumentFilter(numberFilter);

        EventHandler eh = new EventHandler();
        showGuiButton = new JButton("Show map");
        startSimulationButton = new JButton("Start simulation");
        newSetupButton = new JButton("Setup parameters");
        showGuiButton.addActionListener(eh);
        startSimulationButton.addActionListener(eh);
        newSetupButton.addActionListener(eh);




        add(titleLabel);
        add(widthLabel);
        add(heightLabel);
        add(pointsLabel);
        add(inputWidth);
        add(inputHeight);
        add(inputRndPoints);
        add(newSetupButton);
        add(showGuiButton);
        add(startSimulationButton);
        add(executionTime);

        layout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        layout.putConstraint(SpringLayout.WEST, widthLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, widthLabel, 20, SpringLayout.SOUTH, titleLabel);

        layout.putConstraint(SpringLayout.WEST, inputWidth, 10, SpringLayout.EAST, widthLabel);
        layout.putConstraint(SpringLayout.BASELINE, inputWidth, 0, SpringLayout.BASELINE, widthLabel);

        layout.putConstraint(SpringLayout.WEST, heightLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, heightLabel, 10, SpringLayout.SOUTH, inputWidth);

        layout.putConstraint(SpringLayout.WEST, inputHeight, 10, SpringLayout.EAST, heightLabel);
        layout.putConstraint(SpringLayout.BASELINE, inputHeight, 0, SpringLayout.BASELINE, heightLabel);

        layout.putConstraint(SpringLayout.WEST, pointsLabel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, pointsLabel, 10, SpringLayout.SOUTH, inputHeight);

        layout.putConstraint(SpringLayout.WEST, inputRndPoints, 10, SpringLayout.EAST, pointsLabel);
        layout.putConstraint(SpringLayout.BASELINE, inputRndPoints, 0, SpringLayout.BASELINE, pointsLabel);

        layout.putConstraint(SpringLayout.NORTH, newSetupButton, 20, SpringLayout.SOUTH, pointsLabel);
        layout.putConstraint(SpringLayout.WEST, newSetupButton, 10, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, showGuiButton, 10, SpringLayout.SOUTH, newSetupButton);
        layout.putConstraint(SpringLayout.WEST, showGuiButton, 10, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, startSimulationButton, 10, SpringLayout.SOUTH, showGuiButton);
        layout.putConstraint(SpringLayout.WEST, startSimulationButton, 0, SpringLayout.WEST, showGuiButton);

        layout.putConstraint(SpringLayout.NORTH, executionTime, 10, SpringLayout.SOUTH, startSimulationButton);
        layout.putConstraint(SpringLayout.WEST, executionTime, 10, SpringLayout.WEST, this);
    }


    private class TextFieldChangeListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (e.getDocument() == inputWidth.getDocument()) {
                String text = inputWidth.getText();
                controller.setWIDTH(Integer.parseInt(text));


            } else if (e.getDocument() == inputHeight.getDocument()) {
                String text = inputHeight.getText();
                controller.setHEIGHT(Integer.parseInt(text));


            } else if (e.getDocument() == inputRndPoints.getDocument()) {
                String text = inputRndPoints.getText();
                controller.setRND_POINTS(Integer.parseInt(text));
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (e.getDocument() == inputWidth.getDocument()) {
                String text = inputWidth.getText();
                if (text.length()==0){
                    controller.setWIDTH(0);
                }else{
                    controller.setWIDTH(Integer.parseInt(text));
                }


            } else if (e.getDocument() == inputHeight.getDocument()) {
                String text = inputHeight.getText();
                if (text.length()==0){
                    controller.setHEIGHT(0);
                }else{
                    controller.setHEIGHT(Integer.parseInt(text));
                }


            } else if (e.getDocument() == inputRndPoints.getDocument()) {
                String text = inputRndPoints.getText();
                if (text.length()==0){
                    controller.setRND_POINTS(0);
                }else{
                    controller.setRND_POINTS(Integer.parseInt(text));
                }
            }

        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    private class NumberDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    private class EventHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();

            if (btn == (newSetupButton)){
                if (controller.isValidSetupPossible()) {
                    controller.makeNewSetup();
                    controller.updateExecutionTime("");
                }

            }else if (btn==(showGuiButton)){
                ContentPanel parent = (ContentPanel) getParent();
                if(controller.isChartVisible()){
                    showGuiButton.setText("Show chart");
                    parent.showHideMap();
                    controller.setChartVisible(!controller.isChartVisible());
                }else{
                    showGuiButton.setText("Hide chart");
                    parent.showHideMap();
                    controller.generateData();
                    controller.setChartVisible(!controller.isChartVisible());
                }
            }else if (btn==(startSimulationButton)) {
                controller.startSimulation();

            }
        }
    }

    public void updateExecutionTime(String s) {
        executionTime.setText("Execution time: " + s);
    }
}