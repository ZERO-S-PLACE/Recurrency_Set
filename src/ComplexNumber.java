

public class ComplexNumber {

    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public  double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
    public static ComplexNumber add(ComplexNumber x, ComplexNumber y){
        return new ComplexNumber(x.getReal()+y.getReal(),x.getImaginary()+y.getImaginary());

    }
    public static ComplexNumber subtract(ComplexNumber x, ComplexNumber y){
        return new ComplexNumber(x.getReal()-y.getReal(),x.getImaginary()-y.getImaginary());

    }

    public static ComplexNumber mulityply(ComplexNumber x, ComplexNumber y){
        return new ComplexNumber(x.getReal()*y.getReal()-x.getImaginary()*y.getImaginary(),x.getImaginary()*y.getReal()+x.getReal()*y.getImaginary());

    }
    public static ComplexNumber powerOf(ComplexNumber x,ComplexNumber exp){
        /*double getReal() =x.getReal();
        double getImaginary()=x.getImaginary();
        double magnitude = Math.sqrt(getReal() * getReal() + getImaginary() * getImaginary());
        double argument = Math.atan2(getImaginary(), getReal());

        double resultMagnitude = Math.pow(magnitude, exponent.getReal()) * Math.exp(-exponent.getImaginary() * argument);
        double resultArgument = exponent.getReal()* argument + exponent.getImaginary() * Math.log(magnitude);

        double resultReal = resultMagnitude * Math.cos(resultArgument);
        double resultImaginary = resultMagnitude * Math.sin(resultArgument);

        return new ComplexNumber(resultReal, resultImaginary);*/
        double a=x.getReal();
        double b=x.getImaginary();
        double n=exp.getReal();
        double magnitude = Math.pow(Math.pow(a, 2) + Math.pow(b, 2), 0.5);
        double theta = Math.atan2(b, a);

        // Calculate the result in polar form
        double resultMagnitude = Math.pow(magnitude, n);
        double resultTheta = n * theta;

        // Convert back to rectangular form
        double resultReal = resultMagnitude * Math.cos(resultTheta);
        double resultImaginary = resultMagnitude * Math.sin(resultTheta);

        return new ComplexNumber(resultReal, resultImaginary);
    }


    public static ComplexNumber divide(ComplexNumber x,ComplexNumber y){
        double realD=x.getReal()* y.getReal()+x.getImaginary()* y.getImaginary();
        double imaginaryD=x.getImaginary()* y.getReal()-x.getReal()* y.getImaginary();
        double temp=y.getImaginary()*y.getImaginary()+y.getReal()*y.getReal();
        if (temp!=0){
        return new ComplexNumber(realD/temp, imaginaryD/temp);}
        else{
            return new ComplexNumber(0, 0);}
        }



    public static double absoluteValue(ComplexNumber x){
        return Math.sqrt(x.getReal()*x.getReal()+x.getImaginary()*x.getImaginary());
    }
}
