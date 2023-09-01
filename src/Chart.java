import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Chart extends JComponent {

    private final int TARGET_WIDTH = 600;
    private final int TARGET_HEIGHT = 600;
    private final Controller controller;
    private double[][] temperature;


    public Chart(Controller controller) {
        this.controller=controller;
        this.temperature=controller.getTemperature();
        this.setPreferredSize(new Dimension(TARGET_WIDTH,TARGET_HEIGHT));
        this.setDoubleBuffered(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        BufferedImage scaledHeatMap = scaleImage(createHeatMap(), TARGET_WIDTH, TARGET_HEIGHT);
        g2d.drawImage(scaledHeatMap,0,0,null);
    }




    private BufferedImage createHeatMap() {
        double min = 0.0; // blue
        double max = 1.0; // red

        BufferedImage bufferedImage = new BufferedImage(temperature.length, temperature[0].length, BufferedImage.TYPE_INT_RGB);
        int[] rgbArray = new int [temperature.length * temperature[0].length];
        for (int i = 0; i < temperature.length; i++) {
            for (int j = 0; j < temperature[0].length; j++) {
                double temp = temperature[i][j];
                float hue = (float) (0.66 * (1 - (temp - min) / (max - min)));
                int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
                rgbArray[i + j * temperature.length] = rgb;

            }
        }
        bufferedImage.setRGB(0,0,temperature.length, temperature[0].length,rgbArray,0,temperature.length);
        return bufferedImage;
    }

    private BufferedImage scaleImage(BufferedImage image, int targetWidth, int targetHeight) {
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();

        return bufferedImage;
    }

}