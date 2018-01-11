package org.astanis;

import org.astanis.calculator.CalculatorFactory;

public class MainApp {
    public static void main(String[] args) throws NoSuchMethodException {
        String result = CalculatorFactory.createCalculator().calculate();
        System.out.println(result);
    }
}
