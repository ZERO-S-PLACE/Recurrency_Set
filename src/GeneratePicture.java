
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GeneratePicture {






    public  BufferedImage generate(int[][] pointArray) {

        // Image dimensions
        int width = pointArray[0].length;
        int height = pointArray.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Colors.backgroundColor);
        g2d.fillRect(0, 0, width, height);
        ArrayList<Color> colors = Colors.gradientColorList;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (pointArray[i][j] == -1) {
                    g2d.setColor(Colors.mainColor);
                    g2d.drawRect(width-j-1, i, 1, 1);
                }
                if (pointArray[i][j] != 0 && pointArray[i][j] != -1) {
                    g2d.setColor(colors.get((pointArray[i][j]) - 1));
                    g2d.drawRect(width-j-1, i, 1, 1);
                }

            }

        }

        g2d.dispose();
        return image;


    }
}







