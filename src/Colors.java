import java.awt.*;
import java.util.ArrayList;

public class Colors {
    public static Color chooseAreaColor= new Color(238, 147, 147, 161);
    public static Color backgroundColor = new Color(8, 34, 119);
    public static Color mainColor = Color.BLACK;
    public static Color startGrad= new Color(8, 34, 119);
    public static Color endGrad= new Color(71, 213, 131);
    public static ArrayList<Color> gradientColorList;
    public static Color buttonColor = new Color(13, 26, 77);
    public static Color panelColor = new Color(10, 21, 65);
    public static Color fontColor = new Color(156, 178, 255);
    public static Font fontButton =new Font("Arial", Font.BOLD, 20);



    public static ArrayList<Color> createColors() {

        gradientColorList = new ArrayList<Color>();
        int rEnd = endGrad.getRed();
        int gEnd = endGrad.getGreen();
        int bEnd = endGrad.getBlue();
        int rStart = startGrad.getRed();
        int gStart = startGrad.getGreen();
        int bStart = startGrad.getBlue();

        int h=(int)Equation.log2(Parameters.getCheckIterations());
        for (int i = 0; i < h; i++) {
            gradientColorList.add(new Color((rStart + i * (rEnd - rStart) / h), (gStart + i * (gEnd - gStart) / h), (bStart + i * (bEnd - bStart) / h)));
        }

        return gradientColorList;
    }
}
