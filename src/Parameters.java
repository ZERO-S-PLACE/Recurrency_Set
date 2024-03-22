public class Parameters {

    private  static int height=800;
    private  static int width=1200;
    private  static int exportHeight=3600;
    private  static int exportWidth=6000;
    private  static double xMax=0.6;
    private  static double iMax=1.1;
    private  static double step= 0.0034;
    private  static int CheckIterations=500;
    private  static int exportCheckIterations=1500;
    private  static int BackgroundFade=10;

    private static String equationName="Mandelbrot_Set";
    private static String equationZ0="0";
    private static String equationZN="p+z^2";
    private static char equationVariable='p';
    public static String getEquationName() {
        return equationName;
    }

    public static void setEquationName(String equationName) {
        Parameters.equationName = equationName;
    }

    public static String getEquationZ0() {
        return equationZ0;
    }

    public static void setEquationZ0(String equationZ0) {
        Parameters.equationZ0 = equationZ0;
    }

    public static String getEquationZN() {
        return equationZN;
    }

    public static void setEquationZN(String equationZN) {
        Parameters.equationZN = equationZN;
    }

    public static char getEquationVariable() {
        return equationVariable;
    }

    public static void setEquationVariable(char equationVariable) {
        Parameters.equationVariable = equationVariable;
    }

    public static int getExportCheckIterations() {
        return exportCheckIterations;
    }

    public static void setExportCheckIterations(int exportCheckIterations) {
        Parameters.exportCheckIterations = exportCheckIterations;
    }
    public static int getExportHeight() {
        return exportHeight;
    }

    public static void setExportHeight(int exportHeight) {
        Parameters.exportHeight = exportHeight;
    }

    public static int getExportWidth() {
        return exportWidth;
    }

    public static void setExportWidth(int exportWidth) {
        Parameters.exportWidth = exportWidth;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Parameters.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Parameters.width = width;
    }

    public static double getxMax() {
        return xMax;
    }

    public static void setxMax(double xMax) {
        Parameters.xMax = xMax;
    }

    public static double getiMax() {
        return iMax;
    }

    public static void setiMax(double iMax) {
        Parameters.iMax = iMax;
    }

    public static double getStep() {
        return step;
    }

    public static void setStep(double step) {
        Parameters.step = step;
    }

    public static int getCheckIterations() {
        return CheckIterations;
    }

    public static void setCheckIterations(int checkIterations) {
        CheckIterations = checkIterations;
    }

    public static int getBackgroundFade() {
        return BackgroundFade;
    }

    public static void setBackgroundFade(int backgroundFade) {
        BackgroundFade = backgroundFade;
    }
}
