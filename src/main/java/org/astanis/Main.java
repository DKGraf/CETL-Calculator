package org.astanis;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.astanis.calculator.CalculatorFactory;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) throws NoSuchMethodException {
        String result = CalculatorFactory.createCalculator().calculate();
        System.out.println(result);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CETL Calculator");
    }
}
