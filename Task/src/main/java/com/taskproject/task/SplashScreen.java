package com.taskproject.task;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The SplashScreen class represents a splash screen that is displayed while the application is loading.
 * It shows a progress bar indicating the progress of the loading process.
 */
public class SplashScreen extends Application {

    private ProgressBar progressBar;

    /**
     * The main entry point for the JavaFX application.
     * This method sets up the splash screen and starts the loading process.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        // Create a label saying "Please wait"
        Label label = new Label("Please wait");

        // Create a progress bar
        progressBar = new ProgressBar();
        progressBar.setPrefSize(200, 20);
        progressBar.setStyle("-fx-accent: green;");

        // Stack the label and progress bar in a vertical order
        StackPane.setAlignment(label, Pos.CENTER);
        StackPane.setAlignment(progressBar, Pos.CENTER);
        root.getChildren().addAll(label, progressBar);

        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Simulate a long-running task
        new Thread(() -> {
            try {
                // Perform any initialization tasks here
                double progress = 0.0;
                while (progress < 1.0) {
                    // Update the progress bar
                    final double currentProgress = progress;
                    Platform.runLater(() -> progressBar.setProgress(currentProgress));

                    // Simulate a delay
                    Thread.sleep(1000); // Adjust the sleep duration to control the progress speed

                    // Increment the progress
                    progress += 0.1; // Adjust the increment value to control the progress speed
                }

                // Pause for 2 seconds after finishing loading
                Thread.sleep(2000);

                // Close the splash screen and launch your main application window
                Platform.runLater(() -> {
                    primaryStage.close();
                    showMainApplicationWindow();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Displays the main application window.
     * Replace this method with your own code to launch the main application window.
     */
    private void showMainApplicationWindow() {
        // Launch your main application window here
        // Replace the following line with your own code
        Login login = new Login();
        Stage primaryStage = new Stage();
        login.start(primaryStage);
    }

}
