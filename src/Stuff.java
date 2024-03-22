/*import java.util.ArrayList;
import java.util.Arrays;

public class Equation {

    private String z0String;
    private String zNString;
    private String variableName;
    private char variableSymbol;

    private ArrayList<Character> validCharacters = new ArrayList<>(Arrays.asList(
            'z', '+', '-', '*', '/', '^', '.', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'i', 'e'));

    public Equation(String z0, String zN, String variableName) {
        this.z0String = z0;
        this.zNString = zN;
        this.variableName = variableName;
    }

    public Equation() {
        this.z0String = Parameters.getEquationZ0();
        this.zNString = Parameters.getEquationZN();
        this.variableSymbol = Parameters.getEquationVariable();
    }

    public boolean checkExpressions() {
        if (variableName.length() == 1) {
            variableSymbol = variableName.charAt(0);
            validCharacters.add(variableSymbol);
            int count = 0;
            for (char a : validCharacters) {
                for (int i1 = 0; i1 < z0String.length(); i1++) {
                    if (z0String.charAt(i1) == a) {
                        count++;
                    }
                }
                for (int i1 = 0; i1 < zNString.length(); i1++) {
                    if (zNString.charAt(i1) == a) {
                        count++;
                    }
                }
            }
            return count == z0String.length() + zNString.length();
        }
        return false;

    }

    public int checkEquation(ComplexNumber p) {
        z0String = z0String.replace(variableSymbol, 'p');
        zNString = zNString.replace(variableSymbol, 'p');


        ComplexNumber z = new ComplexNumber(0, 0);
        z = calculateExpression(z0String, z, p);

        for (int i = 1; i <= Parameters.getCheckIterations(); i++) {
            z = calculateExpression(zNString, z, p);
            if (ComplexNumber.absoluteValue(z) > 2) {
                if (i < Parameters.getBackgroundFade()) {
                    return 0;
                }
                return (int) log2(i);
            }
        }
        return -1;

    }


    private ComplexNumber calculateExpression(String expr, ComplexNumber z, ComplexNumber p) {
        String[] numbers;
        ComplexNumber[] complexValues;
        String exp;
        exp = expr;
        String temp = exp;
        temp = temp.replace('(', '#');
        temp = temp.replace(')', '#');
        temp = temp.replace('*', '#');
        temp = temp.replace('/', '#');
        temp = temp.replace('^', '#');
        temp = temp.replace('+', '#');
        temp = temp.replace('-', '#');
        numbers = temp.split("#");
        complexValues = new ComplexNumber[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            String number = numbers[i];
            if (number.length() == 1) {
                switch (number) {
                    case "z" -> complexValues[i] = z;
                    case "p" -> complexValues[i] = p;
                    case "i" -> complexValues[i] = new ComplexNumber(0, 1);
                    case "e" -> complexValues[i] = new ComplexNumber(Math.E, 0);
                    default -> complexValues[i] = new ComplexNumber(Double.parseDouble(number), 0);
                }
            } else {
                if (number.charAt(0) == 'i') {
                    complexValues[i] = new ComplexNumber(0, Double.parseDouble(number.substring(1)));
                }
                if (number.charAt(number.length() - 1) == 'i') {
                    complexValues[i] = new ComplexNumber(0, Double.parseDouble(number.substring(0, i - 1)));
                } else {
                    complexValues[i] = new ComplexNumber(Double.parseDouble(number), 0);
                }
            }
        }

        return calculatePart(numbers, complexValues, exp);


    }

    private ComplexNumber calculatePart(String[] numbers, ComplexNumber[] complexValues, String exp) {
        ComplexNumber z = complexValues[0];
        int n = 1;
        if (complexValues.length > 1) {
            for (int i = 0; i + numbers[n - 1].length() < exp.length() && n < complexValues.length; ) {
                i = i + numbers[n - 1].length();
                if (exp.charAt(i + 1) != '(' && exp.charAt(0) != '(') {
                    operation(z, complexValues[n], exp.charAt(i));

                } else {
                    int i1 = exp.indexOf('(');
                    int j1 = i1;

                    for (int t = 0; t < 1; ) {
                        i1 = exp.indexOf('(', i1);
                        j1 = exp.indexOf(')', i1);
                        //if(j1<i1){calculateExpression(exp.su)}
                    }
                }
                i++;
                n++;


            }
        }
        return z;
    }

    private ComplexNumber operation(ComplexNumber z, ComplexNumber y, char sign) {
        if (sign == '*') {
            z = ComplexNumber.mulityply(z, y);
        } else if (sign == '/') {
            z = ComplexNumber.divide(z, y);
        } else if (sign == '+') {
            z = ComplexNumber.add(z, y);
        } else if (sign == '-') {
            z = ComplexNumber.add(z, new ComplexNumber(-y.getReal(), -y.getImaginary()));
        } else if (sign == '^') {
            z = ComplexNumber.powerOf(z, y);
        }
        return z;
    }






    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }



}

WERSJA 2
import java.util.ArrayList;
import java.util.Arrays;

public class Equation {

    private String z0String;
    private String zNString;
    private String variableName;
    private char variableSymbol;

    private ArrayList<Character> validCharacters = new ArrayList<>(Arrays.asList(
            'z', '+', '-', '*', '/', '^', '.', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'i', 'e'));

    public Equation(String z0, String zN, String variableName) {
        this.z0String = z0;
        this.zNString = zN;
        this.variableName = variableName;
    }

    public Equation() {
        this.z0String = Parameters.getEquationZ0();
        this.zNString = Parameters.getEquationZN();
        this.variableSymbol = Parameters.getEquationVariable();
    }

    public boolean checkExpressions() {
        if (variableName.length() == 1) {
            variableSymbol = variableName.charAt(0);
            validCharacters.add(variableSymbol);
            int count = 0;
            for (char a : validCharacters) {
                for (int i1 = 0; i1 < z0String.length(); i1++) {
                    if (z0String.charAt(i1) == a) {
                        count++;
                    }
                }
                for (int i1 = 0; i1 < zNString.length(); i1++) {
                    if (zNString.charAt(i1) == a) {
                        count++;
                    }
                }
            }
            return count == z0String.length() + zNString.length();
        }
        return false;

    }

    public int checkEquation(ComplexNumber p) {
        z0String = z0String.replace(variableSymbol, 'p');
        zNString = zNString.replace(variableSymbol, 'p');


        ComplexNumber z = new ComplexNumber(0, 0);
        z = calculateExpression(z0String, z, p);

        for (int i = 1; i <= Parameters.getCheckIterations(); i++) {

            z = calculateExpression(zNString, z, p);

            if (ComplexNumber.absoluteValue(z) > 2) {
                if (i < Parameters.getBackgroundFade()) {
                    return 0;
                }
                return (int) log2(i);
            }
        }
        return -1;

    }

     ArrayList<String> numbers;
     ArrayList<ComplexNumber> complexValues = new ArrayList<>();
     ArrayList<Integer> bracesInd=new ArrayList<>();
     ArrayList<Character> operators=new ArrayList<>();
     ArrayList<Integer> operatorInd=new ArrayList<>();


    private ComplexNumber calculateExpression(String expr, ComplexNumber z, ComplexNumber p) {


        String temp = expr;

        temp = temp.replace('(', '#');
        temp = temp.replace(')', '#');



        for(int i = 0; i < expr.length(); i++){
            char tempChar=temp.charAt(i);
            if(tempChar=='#')
            {
                bracesInd.add(i);
            }
        }
        temp = temp.replace('*', '#');
        temp = temp.replace('/', '#');
        temp = temp.replace('^', '#');
        temp = temp.replace('+', '#');
        temp = temp.replace('-', '#');
        String[] numbersString= temp.split("#");
        numbers=new ArrayList<>(Arrays.asList(numbersString));

        for(int i = 0; i < expr.length(); i++){
            char tempChar=temp.charAt(i);

            if(tempChar=='#'){
                operators.add(expr.charAt(i));
                operatorInd.add(i);
            }
        }



        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i);
            if (number.length() == 1) {
                switch (number) {
                    case "z" -> complexValues.add(z);
                    case "p" -> complexValues.add(p);
                    case "i" -> complexValues.add(new ComplexNumber(0, 1));
                    case "e" -> complexValues.add(new ComplexNumber(Math.E, 0));
                    default -> complexValues.add( new ComplexNumber(Double.parseDouble(number), 0));
                }
            } else {
                if (number.charAt(0) == 'i') {
                    complexValues.add(new ComplexNumber(0, Double.parseDouble(number.substring(1))));
                }
                if (number.charAt(number.length() - 1) == 'i') {
                    complexValues.add(new ComplexNumber(0, Double.parseDouble(number.substring(0, i - 1))));
                } else {
                    complexValues.add(new ComplexNumber(Double.parseDouble(number), 0));
                }
            }
        }

        while(!operators.isEmpty()) {

            tryOperation('^');
            tryOperation('*');
            tryOperation('/');
            tryOperation('+');
            tryOperation('-');
            checkBraces();
        }




        return complexValues.getFirst();


    }

    public void tryOperation(char tryChar){



        for (int i = 0; i < operators.size(); i++) {
            char c = operators.get(i);
            if (c == tryChar)
            {
                int isLegal=0;
                if( i>=1&&operators.get(i-1)!=')'){isLegal++;}
                else if( i==0){isLegal++;}
                if(i<operators.size()-1&&operators.get(i+1)!='('){isLegal++;}
                else if(i==operators.size()-1){isLegal++;}


                if(isLegal==2){


                    int t = i - countBraces(i, bracesInd);
                    complexValues.set(t, operation(complexValues.get(t), complexValues.get(t + 1), tryChar));
                    complexValues.remove(t + 1);
                    operators.remove(i);
                    operatorInd.remove(operatorInd.get(i));



                }

            }

        }


    }


    public void checkBraces() {
        if (bracesInd.size() > 1) {
            for (int i = 0; i < operators.size(); i++) {
                char c = operators.get(i);
                if (c == '(' && operators.get(i + 1) == ')') {
                    int index1 = operatorInd.get(i);
                    int index2 = operatorInd.get(i + 1);
                    bracesInd.remove(index1);
                    bracesInd.remove(index2);


                    operators.remove(i);
                    operatorInd.remove(index1);
                    operators.remove(i);
                    operatorInd.remove(index2);
                }
            }
        }
    }


    public int countBraces(int index,ArrayList<Integer> bracesInd){
        int count=0;
        for(int i:bracesInd){
            if(i<index){count++;}
        }
        return count;
    }
    private ComplexNumber operation(ComplexNumber z, ComplexNumber y, char sign) {
        if (sign == '*') {
            z = ComplexNumber.mulityply(z, y);
        } else if (sign == '/') {
            z = ComplexNumber.divide(z, y);
        } else if (sign == '+') {
            z = ComplexNumber.add(z, y);
        } else if (sign == '-') {
            z = ComplexNumber.add(z, new ComplexNumber(-y.getReal(), -y.getImaginary()));
        } else if (sign == '^') {
            z = ComplexNumber.powerOf(z, y);
        }
        return z;
    }


    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }



}

WERSJA 3

import java.util.ArrayList;
import java.util.Arrays;

public class Equation {

    private String z0String;
    private String zNString;
    private String variableName;
    private char variableSymbol;

    private ArrayList<Character> validCharacters = new ArrayList<>(Arrays.asList(
            'z', '+', '-', '*', '/', '^', '.', '(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'i', 'e'));

    public Equation(String z0, String zN, String variableName) {
        this.z0String = z0;
        this.zNString = zN;
        this.variableName = variableName;
    }

    public Equation() {
        this.z0String = Parameters.getEquationZ0();
        this.zNString = Parameters.getEquationZN();
        this.variableSymbol = Parameters.getEquationVariable();
    }

    public boolean checkExpressions() {
        if (variableName.length() == 1) {
            variableSymbol = variableName.charAt(0);
            validCharacters.add(variableSymbol);
            int count = 0;
            for (char a : validCharacters) {
                for (int i1 = 0; i1 < z0String.length(); i1++) {
                    if (z0String.charAt(i1) == a) {
                        count++;
                    }
                }
                for (int i1 = 0; i1 < zNString.length(); i1++) {
                    if (zNString.charAt(i1) == a) {
                        count++;
                    }
                }
            }
            return count == z0String.length() + zNString.length();
        }
        return false;

    }

    public int checkEquation(ComplexNumber p) {
        z0String = z0String.replace(variableSymbol, 'p');
        zNString = zNString.replace(variableSymbol, 'p');


        ComplexNumber z = new ComplexNumber(0, 0);
        z = calculateExpression(z0String, z, p);

        for (int i = 1; i <= Parameters.getCheckIterations(); i++) {

            z = calculateExpression(zNString, z, p);

            if (ComplexNumber.absoluteValue(z) > 2) {
                if (i < Parameters.getBackgroundFade()) {
                    return 0;
                }
                return (int) log2(i);
            }
        }
        return -1;

    }

     ArrayList<String> numbers;
     ArrayList<ComplexNumber> complexValues = new ArrayList<>();

     ArrayList<Character> operators=new ArrayList<>();



    private ComplexNumber calculateExpression(String expr, ComplexNumber z, ComplexNumber p) {


        String temp = expr;

        temp = temp.replace('(', '#');
        temp = temp.replace(')', '#');

        temp = temp.replace('*', '#');
        temp = temp.replace('/', '#');
        temp = temp.replace('^', '#');
        temp = temp.replace('+', '#');
        temp = temp.replace('-', '#');
        String[] numbersString= temp.split("#");
        numbers=new ArrayList<>(Arrays.asList(numbersString));

        for(int i = 0; i < expr.length(); i++){
            char tempChar=temp.charAt(i);

            if(tempChar=='#'){
                operators.add(expr.charAt(i));

            }
        }



        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i);
            if (number.length() == 1) {
                switch (number) {
                    case "z" -> complexValues.add(z);
                    case "p" -> complexValues.add(p);
                    case "i" -> complexValues.add(new ComplexNumber(0, 1));
                    case "e" -> complexValues.add(new ComplexNumber(Math.E, 0));
                    default -> complexValues.add( new ComplexNumber(Double.parseDouble(number), 0));
                }
            } else {
                if (number.charAt(0) == 'i') {
                    complexValues.add(new ComplexNumber(0, Double.parseDouble(number.substring(1))));
                }
                if (number.charAt(number.length() - 1) == 'i') {
                    complexValues.add(new ComplexNumber(0, Double.parseDouble(number.substring(0, i - 1))));
                } else {
                    complexValues.add(new ComplexNumber(Double.parseDouble(number), 0));
                }
            }
        }
        ComplexNumber z1=complexValues.getFirst();

        for(int i =1;i<complexValues.size();) {
            z1=operation(z1,complexValues.get(i),operators.get(i-1));

        }




        return z1;


    }



    private ComplexNumber operation(ComplexNumber z, ComplexNumber y, char sign) {
        if (sign == '*') {
            z = ComplexNumber.mulityply(z, y);
        } else if (sign == '/') {
            z = ComplexNumber.divide(z, y);
        } else if (sign == '+') {
            z = ComplexNumber.add(z, y);
        } else if (sign == '-') {
            z = ComplexNumber.add(z, new ComplexNumber(-y.getReal(), -y.getImaginary()));
        } else if (sign == '^') {
            z = ComplexNumber.powerOf(z, y);
        }
        return z;
    }


    public static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }



}



*/