import java.util.ArrayList;
import java.util.Arrays;

public class Equation {

    private final String firstExpression;
    private final String recurrentExpression;

    private final ArrayList<Character> validCharacters = new ArrayList<>(Arrays.asList('z', '+', '-', '*', '/', '^', '.',
            '(', ')','[', ']','{', '}',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'i', 'e',Parameters.getEquationVariable()));
    private final ArrayList<Character> validOperators = new ArrayList<>(Arrays.asList('+', '-', '*', '/', '^'));

    private ArrayList<ComplexNumber[][]> partialResultsList;
    private ComplexNumber[][] resultValueArray;
    private ComplexNumber[][] inputValueArray;
    private boolean[][] marked;
    private String expression;
    private int tempNumber;
    int[][] mValues;


    public Equation(String z0, String zN) {
        this.firstExpression = z0;
        this.recurrentExpression = zN;

    }




    public int[][] iterateEquation(ComplexNumber[][] inputValueArray) {

        mValues = new int[inputValueArray.length][inputValueArray[1].length];
        marked = new boolean[inputValueArray.length][inputValueArray[1].length];


        //creating array of values of z0
        this.inputValueArray = inputValueArray;
        resultValueArray = new ComplexNumber[this.inputValueArray.length][this.inputValueArray[1].length];

        resultValueArray[0][0] = new ComplexNumber(0, 0);
        resultValueArray = makeAllEqual(resultValueArray);
        resultValueArray = calculateExpression(firstExpression, resultValueArray, this.inputValueArray,marked);


        for (int iter = 1; iter <= Parameters.getCheckIterations(); iter++) {

            resultValueArray = calculateExpression(recurrentExpression, resultValueArray,
                    this.inputValueArray,marked);

            for (int i = 0; i < resultValueArray.length; i++) {
                for (int j = 0; j < resultValueArray[0].length; j++) {
                    if (!marked[i][j]) {
                        checkIfConditionSatisfied(i, j, iter);
                    }

                }
            }
        }

        return mValues;

    }

    private void checkIfConditionSatisfied(int i, int j, int iter) {
        if (ComplexNumber.absoluteValue(resultValueArray[i][j]) > 2) {

            if (iter < Parameters.getBackgroundFade()) {
                mValues[i][j] = 0;
            } else {
                mValues[i][j] = (int) log2(iter);
            }
            resultValueArray[i][j] = null;

            marked[i][j] = true;

        } else if (iter == Parameters.getCheckIterations()) {
            mValues[i][j] = -1;
        }
    }


    private ComplexNumber[][] calculateExpression(String exp, ComplexNumber[][] z1, ComplexNumber[][] p1, boolean [][]marked) {

        if (checkIfCalculationNeeded(exp)) {return findValueOf(exp);}
        if(this.marked==null){this.marked=marked;}

        tempNumber = 0;
        partialResultsList = new ArrayList<>(tempNumber);
        this.expression = exp;
        resultValueArray = z1;
        inputValueArray = p1;

        //calculating expressions in braces
        simplifyBrackets("(",")");
        simplifyBrackets("[","]");
        simplifyBrackets("{","}");

        simplifyExpression('^');
        simplifyExpression('*');
        simplifyExpression('/');
        simplifyExpression('+');
        simplifyExpression('-');

        return findValueOf(expression);

    }

    private void simplifyBrackets(String bracket1,String bracket2) {

        while (expression.contains(bracket1)) {

            int i = expression.indexOf(bracket1);
            int j = expression.indexOf(bracket2);
            int i1 = expression.indexOf(bracket1, i + 1);

            while (i1 < j) {

                if (expression.indexOf(bracket2, j + 1) == -1) {
                    break;
                }

                j = expression.indexOf(bracket2, j + 1);
                i1 = expression.indexOf(bracket1, i1 + 1);

            }

            Equation equation = new Equation(firstExpression,recurrentExpression);
            partialResultsList.add(equation.calculateExpression(expression.substring(i + 1, j),
                    resultValueArray, inputValueArray,marked));
            expression = expression.replace(expression.substring(i, j + 1), "Q" + tempNumber);
            tempNumber++;
        }
    }

    private boolean checkIfCalculationNeeded(String exp) {
        return !exp.contains("*") && !exp.contains("^") &&
                !exp.contains("/") && !exp.contains("-") && !exp.contains("+");
    }

    private void simplifyExpression(char c) {

        ComplexNumber[][] x0;
        ComplexNumber[][] y0;

        while (expression.contains(String.valueOf(c))) {

            int i = expression.indexOf(c);
            int j = lowerEnd(expression, i);
            int k = upperEnd(expression, i);

            x0 = findValueOf(expression.substring(j + 1, i));
            y0 = findValueOf(expression.substring(i + 1, k));

            partialResultsList.add(operation(x0, y0, c));
            expression = expression.replace(expression.substring(j + 1, k), "Q" + tempNumber);
            tempNumber++;
        }
    }


    private int lowerEnd(String expr, int i) {

        int j = -1;
        String temp = expr.substring(0, i);

        for (char c : validOperators) {
            int p1 = temp.lastIndexOf(c);
            if (p1 > j) {
                j = p1;
            }
        }
        return j;
    }

    private int upperEnd(String expr, int i) {
        int k = expr.length();
        for (char c : validOperators) {
            int p1 = expr.indexOf(c, i + 1);
            if (p1 < k && p1 != -1) {
                k = p1;
            }
        }

        return k;

    }


    private ComplexNumber[][] findValueOf(String number) {
        ComplexNumber[][] n = new ComplexNumber[1][1];

        if (number.length() == 1) {

            switch (number) {

                case "z" -> {
                    return this.resultValueArray;
                }

                case "p" -> {
                    return this.inputValueArray;
                }

                case "i" -> {

                    n[0][0] = new ComplexNumber(0, 1);
                    return n;
                }

                case "e" -> {
                    n[0][0] = new ComplexNumber(Math.E, 0);
                    return n;
                }

                default -> {
                    n[0][0] = new ComplexNumber(Double.parseDouble(number), 0);
                    return n;
                }
            }

        } else {

            if (number.contains("i")) {

                number = number.replace("i", "");
                n[0][0] = new ComplexNumber(0, Double.parseDouble(number));
                return n;

            } else if (number.contains("Q")) {

                number = number.replace("Q", "");
                return partialResultsList.get(Integer.parseInt(number));
            } else {

                n[0][0] = new ComplexNumber(Double.parseDouble(number), 0);
                return n;
            }
        }
    }

    public ComplexNumber[][] makeAllEqual(ComplexNumber[][] f) {

        ComplexNumber f1 = f[0][0];
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[1].length; j++) {
                f[i][j] = f1;
            }
        }
        return f;
    }


    private ComplexNumber[][] operation(ComplexNumber[][] x, ComplexNumber[][] y, char sign) {

        int iIterations = Math.max(x.length, y.length);
        int jIterations = Math.max(x[0].length, y[0].length);

        ComplexNumber[][] r = new ComplexNumber[iIterations][jIterations];
        ComplexNumber x11 = new ComplexNumber(0, 0);
        ComplexNumber y11 = new ComplexNumber(0, 0);

        if (x.length == 1) {
            x11 = x[0][0];
        }
        if (y.length == 1) {
            y11 = y[0][0];
        }


        for (int i = 0; i < iIterations; i++) {

            for (int j = 0; j < jIterations; j++) {
                if (!marked[i][j] || (iIterations == 1 && jIterations == 1)) {

                    if (x.length != 1) {
                        x11 = x[i][j];
                    }
                    if (y.length != 1) {
                        y11 = y[i][j];
                    }
                    if (x11 != null && y11 != null) {

                        switch (sign) {
                            case '^' -> r[i][j] = ComplexNumber.powerOf(x11, y11);
                            case '*' -> r[i][j] = ComplexNumber.mulityply(x11, y11);
                            case '/' -> r[i][j] = ComplexNumber.divide(x11, y11);
                            case '+' -> r[i][j] = ComplexNumber.add(x11, y11);
                            case '-' -> r[i][j] = ComplexNumber.subtract(x11, y11);
                        }
                    }
                }
            }
        }
        return r;
    }


    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    public boolean checkExpressions() {

        int count = 0;

        for (char a : validCharacters) {
            for (int i1 = 0; i1 < firstExpression.length(); i1++) {
                if (firstExpression.charAt(i1) == a) {
                    count++;
                }
            }
            for (int i1 = 0; i1 < recurrentExpression.length(); i1++) {
                if (recurrentExpression.charAt(i1) == a) {
                    count++;
                }
            }
        }
        return count == firstExpression.length() + recurrentExpression.length();


    }


}
