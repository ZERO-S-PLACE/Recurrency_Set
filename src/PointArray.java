
public class PointArray {

    private int height;
    private int width;
    private double xMax;
    private double iMax;
    private double step;


    public PointArray(int width,int height , double xMax, double iMax, double step) {
        this.height = height;
        this.width = width;
        this.xMax = xMax;
        this.iMax = iMax;
        this.step = step;
    }

    public int[][] generatePointArray() {


        ComplexNumber[][] complexNumberArray = new ComplexNumber[height][width];
        Equation equation = new Equation(Parameters.getEquationZ0(),Parameters.getEquationZN());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                complexNumberArray[i][j] = new ComplexNumber(xMax - step * j, iMax - step * i);
            }
        }
        return equation.iterateEquation(complexNumberArray);

    }


}


