package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Calculator implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calculatorPane.toFront();
    }

    String firstNumber, secondNumber, biggerNumber, smallerNumber, result;
    int[] arrayOfBiggerNom, arrayOfSmallerNom;

    @FXML Label firstNumberBox, inputBox , signLabel, resultLabel;
    @FXML Button clearButton, division, multiplication, delete, minus, plus, equal;
    @FXML Button zero, one, two, three, four, five, six, seven, eight, nine, ten;


    //the function below is actionlistener for buttons named 1 to 9
    public void numbersClicked(ActionEvent event){
        Button clicked= (Button) ((Node) event.getSource());
        String nomClicked = clicked.getText();
        String nomAlreadyHave = inputBox.getText();
        if(!nomAlreadyHave.equals("0")){
            nomAlreadyHave = nomAlreadyHave + nomClicked;
            inputBox.setText(nomAlreadyHave);
//            System.out.println(nomAlreadyHave);
        }
        else if(nomAlreadyHave.equals("0")){
            if(!nomClicked.equals("0")){
                nomAlreadyHave = nomAlreadyHave.substring(1,nomAlreadyHave.length()) + nomClicked;
                inputBox.setText(nomAlreadyHave);
//                System.out.println(nomAlreadyHave);
            }
        }
    }
/*******************Every Mathematical Sign Action Listener**********************************/
    public void mathemathicalSignClicked(ActionEvent event){
        String sign = ((Button) ((Node) event.getSource())).getText();
//        System.out.println(sign +" clicked");
        signLabel.setText(sign);
        signLabel.setStyle("-fx-background-color:  #8DCBEB;"+"-fx-text-fill:  black;"+"-fx-font-size :25;");

        if(!firstNumberBox.getText().equals("")){
            firstNumber = firstNumberBox.getText();
            secondNumber = inputBox.getText();
            switch (sign) {
                case "+":
                    printResult(add(firstNumber, secondNumber));
                    break;
                case "-":
                    printResult(subtract(firstNumber, secondNumber));
                    break;
                case "×":
                    printResult(multiply(firstNumber, secondNumber));
                    break;
                case "÷":
                    divide(firstNumber, secondNumber);
                    break;
                case "^":
                    printResult(power(firstNumber, secondNumber));
                    break;
            }
        }
        else
            firstNumberBox.setText(inputBox.getText());
        inputBox.setText("0");

    }


    /***********************Add Function***********************/
    public String add(String firstNumber, String secondNumber){
        compareAndConvert2Integer(firstNumber, secondNumber);
        if(firstNumber.charAt(0) != '-'){
            int[] sum = new int[arrayOfBiggerNom.length+1];
            for(int i=0; i<sum.length;i++)
                sum[i] = 0;
            //doing the two lines below just for clarity
            int[] a = arrayOfBiggerNom;
            int[] b = arrayOfSmallerNom;

            for(int i=0; i<b.length; i++){
                if((sum[i] + a[i] + b[i]) < 10)
                    sum[i] += a[i] + b[i];
                else{
                    sum[i] += a[i]+ b[i] - 10;
                    sum[i+1]++;
                }
            }
            for(int i=b.length; i<a.length;i++){
                if((sum[i] + a[i]) < 10)
                    sum[i] += a[i];
                else /*if((sum[i] + a[i]) == 10)*/{
                    sum[i] = 0;
                    sum[i+1] ++;
                }
            }

            String result= prepareTheResult(sum);
            return result;
        }
        else /*if(firstNumber.charAt(0) == '-')*/{
            String sum = subtract(secondNumber,firstNumber.substring(1));
            result = sum;
            return sum;
        }
    }

/*****************Subtraction Function*************************/
    public String subtract(String firstNumber, String secondNumber){
        if(firstNumber.equals(secondNumber)){
            return "0";
        }
        compareAndConvert2Integer(firstNumber, secondNumber);
        if(firstNumber.charAt(0) != '-'){
            int[] a = arrayOfBiggerNom;
            int[] b = arrayOfSmallerNom;
            int[] sub = a;

            for(int i=0; i<b.length; i++){
                if(sub[i]-b[i]>=0)
                    sub[i] = sub[i] - b[i];
                else{
                    boolean foundTheDigit = false;
                    for(int j= i+1; foundTheDigit == false; j++){
                        if(sub[j]!=0){
                            foundTheDigit = true;
                            sub[j]--;
                        }
                        else if(sub[j] == 0)
                            sub[j] = 9;
                    }
                    sub[i] = 10 + sub[i] - b[i];
                }
            }

            String result = prepareTheResult(sub);
            if(secondNumber.equals(biggerNumber))
                result = "-" + result;
            return result;
        }
        else /*if(firstNumber.charAt(0) == '-')*/{
            String sub = add(firstNumber.substring(1), secondNumber);
            sub = "-" + sub;
            return sub;
        }

    }

    /*****************************Multiplication Function***************************/
    public String multiply(String firstNumber, String secondNumber) {
        if(!secondNumber.equals("0")){
            compareAndConvert2Integer(firstNumber, secondNumber);
            boolean firstOneIsNegative = false;
            if (firstNumber.charAt(0) == '-') {
                firstOneIsNegative = true;
                firstNumber = firstNumber.substring(1);
            }
            String result = "";


            if (firstNumber.equals("0") || secondNumber.equals("0"))
                return "0";

            else {
                String biggerNumber = this.biggerNumber;
                int[] a = arrayOfBiggerNom;
                int[] b = arrayOfSmallerNom;
                int[] product = new int[a.length + b.length];

                String totalSum = "0";
                String tempSum = "0";
                for(int i=0; i<b.length; i++){
                    tempSum = "0";
                    for(int j=0; j<b[i];j++){
//                        System.out.println(tempSum +" added to "+ biggerNumber);
                        tempSum = add(tempSum,biggerNumber);
//                        System.out.println("temp: "+ tempSum);
                    }
                    for(int z= 0; z<i;z++){
                        tempSum = tempSum +"0";
                    }
//                    System.out.println("tempsum: "+tempSum + " added to total: " + totalSum);
                    totalSum = add(totalSum, tempSum);
                }
                result = totalSum;
            }
            if (firstOneIsNegative) {
                firstNumber = "-" + firstNumber;
                result = "-" + result;
            }
            return result;
        }
        else
            return firstNumber;
    }


    /*********************Division Function***************************/
    public String divide(String firstNumber, String secondNumber){
        compareAndConvert2Integer(firstNumber, secondNumber);
        if(secondNumber.equals("0")) {
            resultLabel.setText("CAN NOT DIVIDE BY ZERO!!");
            firstNumberBox.setText(firstNumber);
            inputBox.setText(secondNumber);
            return "";
        }
        if(firstNumber.equals("0")) {
            resultLabel.setText("Quotient : " +0+" and Remainder : "+ 0);
            firstNumberBox.setText("0");
            inputBox.setText("0");
            firstNumberBox.setText(firstNumber + signLabel.getText() + secondNumber + " = " + 0);
            return "0";
        }
        else if(firstNumber.equals(secondNumber)){
            resultLabel.setText("Quotient : " +1+" and Remainder : "+ 0);
            firstNumberBox.setText("1");
            inputBox.setText("0");
            firstNumberBox.setText(firstNumber + signLabel.getText() + secondNumber + " = " + 1);
            return "1";
        }
        else if(biggerNumber.equals(secondNumber)){
            resultLabel.setText("Quotient : " +0+" and Remainder : "+ firstNumber);
            firstNumberBox.setText("0");
            inputBox.setText("0");
            firstNumberBox.setText(firstNumber + signLabel.getText() + secondNumber + " = " + 0);
            return "0";
        }
        else{
            compareAndConvert2Integer(firstNumber, secondNumber);
            boolean firstOneIsNegative = false;
            if (firstNumber.charAt(0) == '-') {
                firstOneIsNegative = true;
                firstNumber = firstNumber.substring(1);
            }
            boolean foundIt= false;
            int[] a = arrayOfBiggerNom;
            String dividend = biggerNumber;
            int[] b = arrayOfSmallerNom;
            String divisor = smallerNumber;
            String quotient = "";
            String remainder = "";
            String sum = "0";
            String count = "0";
            for(; foundIt == false; count = add("1", count)){
                sum = add(sum, divisor);
//                System.out.println(sum);
                compareAndConvert2Integer(sum, dividend);
                /*following if checks if sum is greater(not equal and greater, just greater) than the the number which is being divided*/
                if(biggerNumber.equals(sum) && !biggerNumber.equals(dividend)) {
                    foundIt = true;
                    remainder = subtract(dividend, (subtract(sum, divisor)));
                }
                /*folowing checks if sum and the number being divided are equal, in which case, the remainder is 0*/
                else if(biggerNumber.equals(sum) && biggerNumber.equals(dividend)){
                    foundIt = true;
                    remainder = "0";
                }
            }

            if(!remainder.equals("0")) {
                quotient = subtract(count, "1");
                boolean foundNoneZeroDigit = false;
                for(int i= 0; i<quotient.length();i++){
                    if(remainder.charAt(0) == '0')
                        remainder = remainder.substring(1);
                    else
                        foundNoneZeroDigit = true;
                }
            }
                else {
                quotient = count;
            }
            /****if first number is negative....*****/
            if (firstOneIsNegative) {
                if(!remainder.equals("0")){
                    firstNumber = "-" + firstNumber;
                    quotient = "-" + add(quotient, "1");
                    remainder = subtract(divisor, remainder);
                }
                else if(remainder.equals("0")){
                    quotient = "-" + quotient;
                }
            }


            firstNumberBox.setText(quotient);
            resultLabel.setText(firstNumber + signLabel.getText() + secondNumber + " = " + quotient+ "\n" +
                    "Quotient : " +quotient+" and Remainder : "+ remainder);
            inputBox.setText("0");
            return quotient;
        }

    }


    /*******************Exponential function************************/
    public String power(String firstNumber, String secondNumber){
        String exponentAsString = secondNumber;
        compareAndConvert2Integer(exponentAsString, "10");
        if(biggerNumber.equals(exponentAsString))
            return "Please Enter A Power Less Than 10";
        if(exponentAsString.equals("0"))
            return "1";
        if(firstNumber.equals("0"))
            return "0";

        else{
            int exponent = Integer.parseInt(exponentAsString);
            String product ="1";
            for(int i=0; i<exponent; i++){
                product = multiply(product, firstNumber);
            }
            return product;
        }
    }
    /***********************BackSpace********************************/
    public void backspace(){
        String input = inputBox.getText();
        if(!input.equals("0")){
            if(input.length()>1){
                input = input.substring(0, input.length()-1);
                inputBox.setText(input);
            }
            else if(input.length() == 1){
                inputBox.setText("0");
            }
        }
    }
    /**************************Clear Sign Clicked*********************/
    public void clear(){
        firstNumberBox.setText("");
        inputBox.setText("0");
        signLabel.setText("");
        resultLabel.setText("");
    }
    /****************Equal Sign Clicked***************************/
    public void equal(){
        if(!signLabel.getText().equals("") && !firstNumberBox.getText().equals("")){
            firstNumber = firstNumberBox.getText();
            secondNumber = inputBox.getText();
            switch (signLabel.getText()) {
                case "+":
                    printResult(add(firstNumber, secondNumber));
                    break;
                case "-":
                    printResult(subtract(firstNumber, secondNumber));
                    break;
                case "×":
                    printResult(multiply(firstNumber, secondNumber));
                    break;
                case "÷":
                    divide(firstNumber, secondNumber);
                    break;
                case "^":
                    printResult(power(firstNumber, secondNumber));
                    break;
            }
        }
    }





    public void printResult(String result){
        if(secondNumber.equals("0")){
            resultLabel.setText("");
            firstNumberBox.setText(result);
            inputBox.setText("0");
        }
        else {
            resultLabel.setText(firstNumber + signLabel.getText() + secondNumber + " = " + result);
            firstNumberBox.setText(result);
            inputBox.setText("0");
        }
    }



    public void compareAndConvert2Integer(String firstNumber, String secondNumber){
        boolean firstOneIsNegative = false;
        if(firstNumber.charAt(0) == '-'){
            firstOneIsNegative = true;
            firstNumber = firstNumber.substring(1);
        }

        if(firstNumber.length() > secondNumber.length()){
            biggerNumber = firstNumber;
            smallerNumber = secondNumber;
        }
        else if(secondNumber.length() > firstNumber.length()){
            biggerNumber = secondNumber;
            smallerNumber = firstNumber;
        }
        else if(firstNumber.equals(secondNumber)){
            smallerNumber = biggerNumber = firstNumber;
        }
        else if(firstNumber.length() == secondNumber.length()){
            boolean foundBiggerNumber = false;
            for(int i=(firstNumber.length()-1); i>=0; i--){
                if(firstNumber.charAt(i) > secondNumber.charAt(i)){
                    biggerNumber = firstNumber;
                    smallerNumber = secondNumber;
                    foundBiggerNumber = true;
                }
                else if(secondNumber.charAt(i) > firstNumber.charAt(i)){
                    biggerNumber = secondNumber;
                    smallerNumber = firstNumber;
                    foundBiggerNumber = true;
                }
            }
        }
        //below "for"s make for example 4565 String to int array[0]=4 and array[5] and so on...
        //and the most important thing is !! index of The String should be 01234(it starts from left)
        // but array of number's index is like 543210(it starts from right) !!
        arrayOfBiggerNom = new int[biggerNumber.length()];
        arrayOfSmallerNom = new int[smallerNumber.length()];
        for(int i=0; i<biggerNumber.length(); i++){
            arrayOfBiggerNom[i] = Integer.parseInt(Character.toString(biggerNumber.charAt(biggerNumber.length()-1-i)));
        }
        for(int i=0; i<smallerNumber.length(); i++){
            arrayOfSmallerNom[i] = Integer.parseInt(Character.toString(smallerNumber.charAt(smallerNumber.length()-1-i)));
        }
//        System.out.println("Bigger number = "+ biggerNumber);
//        System.out.println("Smaller number = "+ smallerNumber);


        //now we have bigger and smaller numbers(their absolute value) both as String and array of integers
        //now we add the minus sign back to the begining of the negative number's String
        if(firstOneIsNegative)
            firstNumber= "-" + firstNumber;
    }


    //this function reverse the index and convert the array of integer to a String which is ready to be printed out
    public String prepareTheResult(int[] resultIntegerArray){
        String resultString = "";
        for(int i=0; i<resultIntegerArray.length; i++){
            resultString = Integer.toString(resultIntegerArray[i]) + resultString;
        }
        if(resultString.charAt(0) == '0')
            resultString = resultString.substring(1);
        return resultString;
    }



    @FXML Pane integralPane;
    public void goToIntegralScene(){
        integralPane.toFront();
    }



    /*************************Integral Scene*************************/



    String integrand, upperBound, lowerBound;
    Label selectedLabel;
    @FXML Label integrandBox,upperBoundLabel, lowerBoundLabel,resultLabel1;
    @FXML Pane calculatorPane;
    public void goBackTo(){
        clear();
        clear2();
        calculatorPane.toFront();
    }
    public void backSpace2(){
        if(selectedLabel!= null &&!selectedLabel.getText().equals("")) {
            String text = selectedLabel.getText();
            // if the text is like "...(x^" it should delete the "(x^"
            if (text.charAt(text.length() - 1) == '^')
                selectedLabel.setText(text.substring(0, text.length() - 3));
            //if it's like "....(x^2)" it should delete the "2)"
            else if (text.charAt(text.length() - 1) == ')')
                selectedLabel.setText(text.substring(0, text.length() - 2));

            else if (!text.equals("") || !text.equals(null)) {
                selectedLabel.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    /******************Entering Bounds**************************/
    @FXML Button upperBoundButton, integrandButton, lowerBoundButton;
    public void enterBounds(ActionEvent event){
        integrandButton.setStyle("-fx-background-color:#d8ecf6");
        upperBoundButton.setStyle("-fx-background-color:#d8ecf6");
        lowerBoundButton.setStyle("-fx-background-color:#d8ecf6");
        String sign = ((Button) ((Node) event.getSource())).getText();
        if(sign.equals("UpperBound")) {
            upperBoundButton.setStyle("-fx-background-color:#BBD4E1");
            selectedLabel = upperBoundLabel;
        }
        else if(sign.equals("LowerBound")){
            selectedLabel = lowerBoundLabel;
            lowerBoundButton.setStyle("-fx-background-color:#BBD4E1");
        }
    }
    public void enterIntegrand(){
        integrandButton.setStyle("-fx-background-color:#BBD4E1");
        upperBoundButton.setStyle("-fx-background-color:#d8ecf6");
        lowerBoundButton.setStyle("-fx-background-color:#d8ecf6");
        selectedLabel = integrandBox;
    }

        public void mathemathicalSignClicked2(ActionEvent event){
        String sign = ((Button) ((Node) event.getSource())).getText();
//        System.out.println(sign +" clicked");
        switch (sign) {
            case "C":
                clear2();
                break;
            case "+":
                plusSignClicked();
                break;
            case "X":
                xCliked();
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                numbersClicked(sign);
                break;
            case "=":
                equal2();
                break;

        }

    }


    public void clear2(){
        integrandButton.setStyle("-fx-background-color:#d8ecf6");
        upperBoundButton.setStyle("-fx-background-color:#d8ecf6");
        lowerBoundButton.setStyle("-fx-background-color:#d8ecf6");
        integrandBox.setText("");
        upperBoundLabel.setText("");
        lowerBoundLabel.setText("");
    }


    public void plusSignClicked(){
        if(selectedLabel != null &&!integrandBox.getText().equals("") &&
                selectedLabel == integrandBox && integrandBox.getText().charAt(integrandBox.getText().length()-1) != '+'
                &&integrandBox.getText().charAt(integrandBox.getText().length()-1) !='^')
            selectedLabel.setText(selectedLabel.getText() + "+");
    }

    public void xCliked(){
        if(selectedLabel != null && selectedLabel == integrandBox) {
            if(integrandBox.getText().equals(""))
                integrandBox.setText("(X^");
            else if (integrandBox.getText().charAt(integrandBox.getText().length() - 1) != ')' || integrandBox.getText().equals(""))
                integrandBox.setText(integrandBox.getText() + "(X^");
        }
    }

    public void numbersClicked(String number){
        if(selectedLabel != null && !selectedLabel.getText().equals("")&& selectedLabel == integrandBox
                && selectedLabel.getText().charAt(selectedLabel.getText().length()-1) == '^')
                selectedLabel.setText(selectedLabel.getText() + number +")+");
        else if(selectedLabel !=null)
            selectedLabel.setText(selectedLabel.getText() + number);
    }


    public String equal2(){
        if(!upperBoundLabel.getText().equals("") && !lowerBoundLabel.getText().equals("") &&
            !integrandBox.getText().equals("") ) {
            if (integrandBox.getText().contains("(") &&integrandBox.getText().charAt
                    (integrandBox.getText().lastIndexOf("(") + 4) != ')'){
                return "";
            }
            else {
                String integrand = integrandBox.getText(), upperBound = upperBoundLabel.getText(),
                        lowerBound = lowerBoundLabel.getText(), sum1 = "0", sum2 = "0";
                if (integrand.charAt(integrand.length() - 1) == '+')
                    integrand = integrand.substring(0, integrand.length() - 2);

                String[] parts = integrand.split("\\+");
                for (int i = 0; i < parts.length; i++) {
                    parts[i].replace("+", "");
//                    System.out.println(parts[i]);
                    if (parts[i].contains("(X^")) {
                        String coefficient = parts[i].substring(0, parts[i].indexOf('('));
                        String exponent = Character.toString(parts[i].charAt(parts[i].indexOf('^') + 1));
                        if (coefficient.equals(""))
                            coefficient = "1";
//                        System.out.println("coefficient: " + coefficient + " and exponent: " + exponent);
                        sum1 = add(sum1, multiply(divide(coefficient, add(exponent, "1")), power(upperBound, add(exponent, "1"))));
                        sum2 = add(sum2, multiply(divide(coefficient, add(exponent, "1")), power(lowerBound, add(exponent, "1"))));
                    } else {
                        sum1 = add(sum1, multiply(parts[i], upperBound));
                        sum2 = add(sum2, multiply(parts[i], lowerBound));
                    }
                }
                result = subtract(sum1, sum2);
                resultLabel1.setText(result);
            }
        }
        return result;
    }




}
