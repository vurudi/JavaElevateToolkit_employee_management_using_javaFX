package com.taskproject.task;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * The Register class represents the registration page of the application.
 */

public class Register extends Application {
    private static final String CSV_FILE_PATH = "log_details.csv";
    Login login= new Login();

    /**
     * The start method is called when the application is starting.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration Page");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label repeatPasswordLabel = new Label("Repeat Password:");
        PasswordField repeatPasswordField = new PasswordField();
        Button registerButton = new Button("Register");
        Button regloginButton = new Button("Go to login");

        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(passwordField, 1, 3);
        gridPane.add(repeatPasswordLabel, 0, 4);
        gridPane.add(repeatPasswordField, 1, 4);
        gridPane.add(registerButton, 0, 5);
        gridPane.add(regloginButton,1,5 );

        registerButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String repeatPassword = repeatPasswordField.getText();

            if (password.equals(repeatPassword)) {
                saveRegistrationDetails(firstName, lastName, email, password);
                showSuccessMessage(primaryStage);
                login.start(primaryStage);

            } else {
                showAlert("Passwords do not match");
            }
        });
        regloginButton.setOnAction(e -> {

            login.start(primaryStage);
        });

        Scene scene = new Scene(gridPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Saves the registration details to a CSV file.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param password  the password
     */
    private void saveRegistrationDetails(String firstName, String lastName, String email, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            writer.write(firstName + "," + lastName + "," + email + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a success message dialog after successful registration.
     *
     * @param primaryStage the primary stage
     */
    private void showSuccessMessage(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Registration Successful!");

        alert.showAndWait();
        primaryStage.close();
        login.start(primaryStage);
    }

    /**
     * Shows an error alert with the specified message.
     *
     * @param message the error message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
