package com.taskproject.task;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class represents a login application using JavaFX.
 */
public class Login extends Application {
    private static final String CSV_FILE_PATH = "log_details.csv";
    private final Map<String, Map<String, String>> translations = new HashMap<>();


    CustomerRecordApp customerRecordApp= new CustomerRecordApp();



    /**
     * Initializes the translations for different languages.
     */
    private void initializeTranslations() {
        // Load English translations
        Map<String, String> englishTranslations = new HashMap<>();
        englishTranslations.put("emailLabel", "Email");
        englishTranslations.put("passwordLabel", "Password");
        englishTranslations.put("locationLabel", "Location");
        englishTranslations.put("loginButton", "Login");
        englishTranslations.put("logRegisterButton", "Register");
        // Add more translations as needed
        // ...

        // Load French translations
        Map<String, String> frenchTranslations = new HashMap<>();
        frenchTranslations.put("emailLabel", "Adresse email");
        frenchTranslations.put("passwordLabel", "Mot de passe");
        frenchTranslations.put("locationLabel", "Emplacement");
        frenchTranslations.put("loginButton", "Connexion");
        frenchTranslations.put("logRegisterButton", "S'inscrire");
        // Add more translations as needed
        // ...

        // Store the translations based on language code
        translations.put("en", englishTranslations);
        translations.put("fr", frenchTranslations);
        // Add more languages as needed
        // ...
    }

    /**
     * Retrieves the translated text for a given key and language.
     *
     * @param key      the translation key
     * @param language the language code
     * @return the translated text if found, null otherwise
     */
    private String getTranslatedText(String key, String language) {
        Map<String, String> languageTranslations = translations.get(language);
        if (languageTranslations != null) {
            String translation = languageTranslations.get(key);
            if (translation != null) {
                return translation;
            } else {
                System.out.println("Translation not found for key: " + key);
            }
        } else {
            System.out.println("Translations not found for language: " + language);
        }
        return null;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");
        initializeTranslations();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Retrieve user's preferred language
        String userLanguage = Locale.getDefault().getLanguage();
        System.out.println(userLanguage);

        Label emailLabel = new Label(getTranslatedText("emailLabel", userLanguage));
        TextField emailField = new TextField();
        Label passwordLabel = new Label(getTranslatedText("passwordLabel", userLanguage));
        PasswordField passwordField = new PasswordField();
        Label locationLabel = new Label(getTranslatedText("locationLabel", userLanguage));

        Button loginButton = new Button(getTranslatedText("loginButton", userLanguage));
        Button logRegisterButton = new Button(getTranslatedText("logRegisterButton", userLanguage));

        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(logRegisterButton, 1, 2);
        gridPane.add(locationLabel, 0, 3, 2, 1);

        // Determine user's location and set the label text
        String userLocation = determineUserLocation();
        locationLabel.setText(locationLabel.getText() + ": " + userLocation);

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (validateLogin(email, password)) {
                showSuccessMessage(primaryStage, userLanguage);
            } else {
                String errorMessage = getTranslatedText("invalidCredentialsError", userLanguage);
                showAlert(errorMessage);
            }
        });

        logRegisterButton.setOnAction(e -> {
            Register logregister = new Register();
            logregister.start(primaryStage);
        });

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Determines the user's location based on the system's default time zone.
     *
     * @return the user's location
     */
    private String determineUserLocation() {
        ZoneId userZoneId = ZoneId.systemDefault();
        return userZoneId.toString();
    }

    /**
     * Validates the user's login credentials.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return true if the login is successful, false otherwise
     */
    private boolean validateLogin(String email, String password) {
        boolean success = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                String storedEmail = details[2].trim();
                String storedPassword = details[3].trim();
                if (storedEmail.equals(email) && storedPassword.equals(password)) {
                    success = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        trackLoginActivity(email, success);

        return success;
    }

    /**
     * Displays a success message dialog and loads the home panel.
     *
     * @param primaryStage the primary stage
     * @param userLanguage the user's language code
     */
    private void showSuccessMessage(Stage primaryStage, String userLanguage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(getTranslatedText("loginSuccessTitle", userLanguage));
        alert.setHeaderText(null);
        alert.setContentText(getTranslatedText("loginSuccessMessage", userLanguage));

        alert.showAndWait();
        primaryStage.close();
        // Load the third panel
        customerRecordApp.start(primaryStage);
    }

    /**
     * Displays an error message dialog.
     *
     * @param message the error message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(getTranslatedText("errorTitle", "en")); // Default to English for error titles
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Tracks the login activity by recording the email, timestamp, and status.
     *
     * @param email   the user's email
     * @param success the login success status
     */
    private void trackLoginActivity(String email, boolean success) {
        String timestamp = LocalDateTime.now().toString();
        String loginStatus = success ? "Successful" : "Failed";

        try (PrintWriter writer = new PrintWriter(new FileWriter("login_activity.txt", true))) {
            writer.println("Email: " + email);
            writer.println("Timestamp: " + timestamp);
            writer.println("Status: " + loginStatus);
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
