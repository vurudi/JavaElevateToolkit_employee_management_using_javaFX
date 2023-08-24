package com.taskproject.task;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the application.
 */
public class Main extends Application {

    /**
     * The main method launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is called when the application is starting.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Load your desired page here
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.start(primaryStage);
    }
}
