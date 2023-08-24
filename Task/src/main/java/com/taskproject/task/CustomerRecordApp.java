package com.taskproject.task;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.sql.*;


public class CustomerRecordApp extends Application {


    private TableView<Customer> tableView;
    private ObservableList<Customer> customerList;

    private TextField customerIdTextField;
    private TextField customerNameTextField;
    private TextField addressTextField;
    private TextField postalCodeTextField;
    private TextField phoneTextField;
    private ComboBox<String> countryComboBox;
    private ComboBox<String> divisionComboBox;
    private String connectionStatus;
    private Connection connection;

    private void loadAllRecords() {
        loadCountryData();    // Load country data
        loadDivisionData();   // Load division data
        loadCustomerData();   // Load customer data
        tableView.refresh();  // Refresh the TableView
    }






    /**
     * The start method is called when the application is starting.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize database connection
        initializeDatabase();

        // Create UI components
        tableView = new TableView<>();
        customerList = FXCollections.observableArrayList();

        // Define the table columns and associate them with the corresponding properties in the Customer class
        TableColumn<Customer, Integer> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<Customer, String> customerNameColumn = new TableColumn<>("Customer Name");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> postalCodeColumn = new TableColumn<>("Postal Code");
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Customer, String> divisionColumn = new TableColumn<>("Division");
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

        // Add the columns to the TableView
        tableView.getColumns().addAll(customerIdColumn, customerNameColumn, addressColumn, postalCodeColumn, phoneColumn,divisionColumn);


        // Load customer data
        loadCustomerData();


        // Set the customerList as the data source for the tableView
        tableView.setItems(customerList);

        customerIdTextField = new TextField();
        customerIdTextField.setEditable(false);
        customerNameTextField = new TextField();
        addressTextField = new TextField();
        postalCodeTextField = new TextField();
        phoneTextField = new TextField();
        countryComboBox = new ComboBox<>();
        divisionComboBox = new ComboBox<>();

        Button addButton = new Button("Add Customer");
        Button updateButton = new Button("Update Customer");
        Button deleteButton = new Button("Delete Customer");
        Button appointment=new Button(" Appointments");




        // Set event handlers
        addButton.setOnAction(e -> addCustomer());
        updateButton.setOnAction(e -> updateCustomer());
        deleteButton.setOnAction(e -> deleteCustomer());
        appointment.setOnAction(e -> loadAppointment());

        // Create the layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Customer ID:"), 0, 0);
        gridPane.add(customerIdTextField, 1, 0);

        gridPane.add(new Label("Customer Name:"), 0, 1);
        gridPane.add(customerNameTextField, 1, 1);

        gridPane.add(new Label("Address:"), 0, 2);
        gridPane.add(addressTextField, 1, 2);

        gridPane.add(new Label("Postal Code:"), 0, 3);
        gridPane.add(postalCodeTextField, 1, 3);

        gridPane.add(new Label("Phone:"), 0, 4);
        gridPane.add(phoneTextField, 1, 4);

        gridPane.add(new Label("Country:"), 0, 5);
        gridPane.add(countryComboBox, 1, 5);

        gridPane.add(new Label("Division:"), 0, 6);
        gridPane.add(divisionComboBox, 1, 6);

        gridPane.add(addButton, 0, 7);
        gridPane.add(updateButton, 1, 7);
        gridPane.add(deleteButton, 2, 7);


        gridPane.add(new Label(connectionStatus.toUpperCase()), 3, 7);

        gridPane.add(tableView, 0, 8, 10, 1);



        // Load country and division data
        loadCountryData();
        loadDivisionData();

        // Load customer data
        loadCustomerData();

        // Set initial customer selection in the table view
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if (newSelection != null) {
                Customer customer = tableView.getSelectionModel().getSelectedItem();
                // Populate the form fields with customer data
                customerIdTextField.setText(String.valueOf(customer.getCustomerId()));
                customerNameTextField.setText(customer.getCustomerName());
                addressTextField.setText(customer.getAddress());
                postalCodeTextField.setText(customer.getPostalCode());
                phoneTextField.setText(customer.getPhone());
                countryComboBox.setValue(customer.getCountry());
                divisionComboBox.setValue(customer.getDivision());
            }
        });
        addressTextField.setPromptText("");

        // ...




        countryComboBox.setOnAction(e -> {
            String selectedCountry = countryComboBox.getValue();
            loadFirstLevelDivisions(selectedCountry);
        });
        // Add the code snippet here
        String hintText = addressTextField.getText();
        addressTextField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%); -fx-text-inner-color: black;");
        addressTextField.setPromptText(hintText);
        addressTextField.setPrefWidth(250); // Set the preferred width
        addressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                addressTextField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%); -fx-text-inner-color: black;");
            } else {
                addressTextField.setStyle("");
            }
        });

// ...


        // Create the scene and show the stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Customer Records");
        primaryStage.show();
    }

    /**
     * Loads the appointment scheduler.
     */
    private void loadAppointment() {
        AppointmentScheduler appointmentScheduler= new AppointmentScheduler();
        Stage primaryStage = new Stage();
        appointmentScheduler.start(primaryStage);
    }

    /**
     * Initializes the database connection.
     */
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_source1", "root", "vurudi100@gmail.com");
            System.out.println("database connection succeeded!!");
            connectionStatus=" database is connected";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the country data.
     */
    private void loadCountryData() {
        // Clear the existing items in the countryComboBox
        countryComboBox.getItems().clear();

        // Add the extracted countries from the text
        countryComboBox.getItems().addAll(
                "Canada", "United Kingdom", "Guernsey", "Jersey", "Isle of Man",
                "Akrotiri and Dhekelia", "Anguilla", "Bermuda", "British Indian Ocean Territory",
                "British Virgin Islands", "Cayman Islands", "Falkland Islands", "Gibraltar",
                "Montserrat", "Pitcairn Islands", "Saint Helena, Ascension and Tristan da Cunha",
                "South Georgia and the South Sandwich Islands", "Turks and Caicos Islands",
                "United States", "Guam", "Northern Mariana Islands", "Puerto Rico",
                "United States Virgin Islands", "American Samoa"
        );

        // Set the default value
        countryComboBox.setValue("Select a Country");
    }


    /**
     * Loads the division data based on the selected country.
     */
    private void loadDivisionData() {
        countryComboBox.setOnAction(e -> {
            divisionComboBox.getItems().clear();
            String selectedCountry = countryComboBox.getValue();
            try (PreparedStatement statement = connection.prepareStatement("SELECT Division FROM FIRST_LEVEL_DIVISIONS INNER JOIN COUNTRIES ON FIRST_LEVEL_DIVISIONS.Country_ID = COUNTRIES.Country_ID WHERE COUNTRIES.Country = ?")) {
                statement.setString(1, selectedCountry);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    divisionComboBox.getItems().add(resultSet.getString("Division"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
    /**
     * Loads the first-level divisions based on the selected country.
     *
     * @param selectedCountry the selected country
     */
    private void loadFirstLevelDivisions(String selectedCountry) {
        // Clear the existing items in the divisionComboBox
        divisionComboBox.getItems().clear();

        // Add the extracted divisions based on the selected country
        if (selectedCountry.equals("Canada")) {
            divisionComboBox.getItems().addAll(
                    "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador",
                    "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan"
            );
        } else if (selectedCountry.equals("United Kingdom")) {
            divisionComboBox.getItems().addAll(
                    "England", "Northern Ireland", "Scotland", "Wales"
            );
        } else if (selectedCountry.equals("Guernsey")) {
            divisionComboBox.getItems().addAll(
                    "Parish 1", "Parish 2", "Parish 3", "Parish 4", "Parish 5",
                    "Parish 6", "Parish 7", "Parish 8", "Parish 9", "Parish 10"
            );
        } else if (selectedCountry.equals("Jersey")) {
            divisionComboBox.getItems().addAll(
                    "Parish 1", "Parish 2", "Parish 3", "Parish 4", "Parish 5",
                    "Parish 6", "Parish 7", "Parish 8", "Parish 9", "Parish 10",
                    "Parish 11", "Parish 12"
            );
        } else if (selectedCountry.equals("Isle of Man")) {
            divisionComboBox.getItems().addAll(
                    "Shedding 1", "Shedding 2", "Shedding 3", "Shedding 4", "Shedding 5",
                    "Shedding 6"
            );
        } else if (selectedCountry.equals("Akrotiri and Dhekelia")) {
            divisionComboBox.getItems().addAll(
                    "Division 1", "Division 2", "Division 3"
            );
        } else if (selectedCountry.equals("Anguilla")) {
            divisionComboBox.getItems().addAll(
                    "District 1", "District 2", "District 3", "District 4", "District 5",
                    "District 6", "District 7", "District 8", "District 9", "District 10",
                    "District 11", "District 12", "District 13", "District 14"
            );
        } else if (selectedCountry.equals("Bermuda")) {
            divisionComboBox.getItems().addAll(
                    "Parish 1", "Parish 2", "Parish 3", "Parish 4", "Parish 5",
                    "Parish 6", "Parish 7", "Parish 8", "Parish 9"
            );
        } else if (selectedCountry.equals("British Virgin Islands")) {
            divisionComboBox.getItems().addAll(
                    "District 1", "District 2", "District 3", "District 4", "District 5"
            );
        }
        // Add divisions for other countries based on the extracted information

        // Set the default value
        divisionComboBox.setValue(divisionComboBox.getItems().get(0));
    }


    private void loadCustomerData() {
        customerList.clear();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT CUSTOMERS.*, FIRST_LEVEL_DIVISIONS.Division FROM CUSTOMERS INNER JOIN FIRST_LEVEL_DIVISIONS ON CUSTOMERS.Division_ID = FIRST_LEVEL_DIVISIONS.Division_ID");
            while (resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                String country = resultSet.getString("Country");
                String division = resultSet.getString("Division");

                Customer customer = new Customer(customerId, customerName, address, postalCode, phone, country, division);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Adds a new customer to the database.
     */
    private void addCustomer() {
        String customerName = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, NOW(), 'user', NOW(), 'user', (SELECT Division_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?))", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customerName);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phone);
            statement.setString(5, division);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phone, country, division);
                    // Add the new customer to the customerList
                    customerList.add(newCustomer);
                    clearFormFields();
                    showSuccessMessage("Customer added successfully.");

                    // Refresh the TableView
                    tableView.refresh();
                }
            } else {
                showErrorMessage("Failed to add customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Failed to add customer.");
        }
    }


    /**
     * Updates the details of an existing customer in the database.
     */
    private void updateCustomer() {
        int customerId = Integer.parseInt(customerIdTextField.getText());
        String customerName = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();

        try (PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = 'user', Division_ID = (SELECT Division_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?) WHERE Customer_ID = ?")) {
            statement.setString(1, customerName);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phone);
            statement.setString(5, division);
            statement.setInt(6, customerId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                Customer updatedCustomer = new Customer(customerId, customerName, address, postalCode, phone, country, division);
                customerList.set(tableView.getSelectionModel().getSelectedIndex(), updatedCustomer);
                clearFormFields();
                showSuccessMessage("Customer updated successfully.");
            } else {
                showErrorMessage("Failed to update customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessage("Failed to update customer.");
        }
    }

    /**
     * Deletes a customer from the database along with their appointments.
     */
    private void deleteCustomer() {
        Customer customer = tableView.getSelectionModel().getSelectedItem();
        if (customer != null) {
            int customerId = customer.getCustomerId();

            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM APPOINTMENTS WHERE Customer_ID = ?")) {
                statement.setInt(1, customerId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorMessage("Failed to delete customer appointments.");
                return;
            }

            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE Customer_ID = ?")) {
                statement.setInt(1, customerId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    customerList.remove(customer);
                    clearFormFields();
                    showSuccessMessage("Customer deleted successfully.");
                } else {
                    showErrorMessage("Failed to delete customer.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorMessage("Failed to delete customer.");
            }
        } else {
            showErrorMessage("Please select a customer to delete.");
        }
    }
    /**
     * Clears all form fields.
     */
    private void clearFormFields() {
        customerIdTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        phoneTextField.clear();
        countryComboBox.setValue(null);
        divisionComboBox.setValue(null);
    }

    /**
     * Displays a success message.
     *
     * @param message the success message to display
     */
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
