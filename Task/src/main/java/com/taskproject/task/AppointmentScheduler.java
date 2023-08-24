package com.taskproject.task;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.*;
/**
 * The AppointmentScheduler class is responsible for managing and displaying appointments in a TableView.
 */

public class AppointmentScheduler extends Application {
    private TableView<Appointment> tableView;


    /**
     * The start method initializes and sets up the JavaFX application.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Set up the TableView
        tableView = new TableView<>();
        // ... (column definitions)

// Define TableColumn instances for each column in the TableView
        TableColumn<Appointment, Integer> appointmentIdColumn = new TableColumn<>("Appointment ID");
        appointmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().appointmentIdProperty().asObject());

        TableColumn<Appointment, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Appointment, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        TableColumn<Appointment, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        TableColumn<Appointment, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());

        TableColumn<Appointment, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        TableColumn<Appointment, LocalDateTime> startColumn = new TableColumn<>("Start Date and Time");
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startDateTimeProperty());

        TableColumn<Appointment, LocalDateTime> endColumn = new TableColumn<>("End Date and Time");
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endDateTimeProperty());

        TableColumn<Appointment, Integer> customerIdColumn = new TableColumn<>("Customer ID");
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());

        TableColumn<Appointment, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());

// Add the TableColumn instances to the TableView
        tableView.getColumns().addAll(
                appointmentIdColumn,
                titleColumn,
                descriptionColumn,
                locationColumn,
                contactColumn,
                typeColumn,
                startColumn,
                endColumn,
                customerIdColumn,
                userIdColumn
        );

        // ... (tab definitions)

// Set up the filter options (tabs or radio buttons)
        TabPane tabPane = new TabPane();
        Tab monthTab = new Tab("Month");
        Tab weekTab = new Tab("Week");
        tabPane.getTabs().addAll(monthTab, weekTab);


        // ... (tab selection and filtering methods)

// Handle tab selection
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == monthTab) {
                // Filter appointments for the month
                filterAppointmentsByMonth();
            } else if (newValue == weekTab) {
                // Filter appointments for the week
                filterAppointmentsByWeek();
            }

    });



// ... (filtering methods)





        // Set up the root layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(tabPane, tableView);

        // Load data from the database
        ObservableList<Appointment> appointments = loadDataFromDatabase();
        tableView.setItems(appointments);

        // ... (tab selection and filtering methods)

        // Create the scene
        Scene scene = new Scene(root, 800, 600);

        // Set up the stage
        primaryStage.setTitle("Appointment Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Filters the appointments displayed in the TableView by month.
     */
    private void filterAppointmentsByMonth() {
        // Get the current month
        int currentMonth = LocalDateTime.now().getMonthValue();

        // Filter appointments by month
        ObservableList<Appointment> filteredAppointments = tableView.getItems().filtered(appointment ->
                appointment.getStartDateTime().getMonthValue() == currentMonth);

        // Update the table view with the filtered appointments
        tableView.setItems(filteredAppointments);
    }
    /**
     * Filters the appointments displayed in the TableView by week.
     */
    private void filterAppointmentsByWeek() {
        // Get the start and end of the current week
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startOfWeek = currentDate.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = currentDate.with(DayOfWeek.SUNDAY).toLocalDate().atTime(23, 59, 59);

        // Filter appointments by week
        ObservableList<Appointment> filteredAppointments = tableView.getItems().filtered(appointment ->
                appointment.getStartDateTime().isAfter(startOfWeek) && appointment.getStartDateTime().isBefore(endOfWeek));

        // Update the table view with the filtered appointments
        tableView.setItems(filteredAppointments);
    }
    /**
     * Loads appointment data from the database and returns an ObservableList of appointments.
     *
     * @return the ObservableList of appointments
     */

    private ObservableList<Appointment> loadDataFromDatabase() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_source1", "root", "vurudi100@gmail.com");

            // Execute a SQL query to retrieve appointment data
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Process the result set and populate the observable list
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String contact = resultSet.getString("Contact_ID");
                String type = resultSet.getString("Type");
                LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                // Convert UTC to user's local time zone
                ZoneId localTimeZone = ZoneId.systemDefault();
                ZonedDateTime startLocalDateTime = startDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(localTimeZone);
                ZonedDateTime endLocalDateTime = endDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(localTimeZone);


                // Create Appointment object with adjusted local time zone
                Appointment appointment = new Appointment(appointmentId, title, description, location, contact, type,
                        startLocalDateTime.toLocalDateTime(), endLocalDateTime.toLocalDateTime(), customerId, userId);
                appointments.add(appointment);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors that occurred during database access
        }

        return appointments;
    }

    /**
     * Saves an appointment to the database.
     *
     * @param appointment the appointment to be saved
     */

    private void saveAppointment(Appointment appointment) {
        // Convert appointment start and end times to UTC
        ZoneId utcTimeZone = ZoneOffset.UTC;
        ZonedDateTime startUtcDateTime = appointment.getStartDateTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(utcTimeZone);
        ZonedDateTime endUtcDateTime = appointment.getEndDateTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(utcTimeZone);

        // Validate appointment time against EST business hours (9 AM to 5 PM)
        ZoneId estTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime startEstDateTime = startUtcDateTime.withZoneSameInstant(estTimeZone);
        ZonedDateTime endEstDateTime = endUtcDateTime.withZoneSameInstant(estTimeZone);
        int startHour = startEstDateTime.getHour();
        int endHour = endEstDateTime.getHour();
        if (startHour < 9 || endHour >= 17) {
            // Display an error message or handle business hour violation
            // For example: throw an exception or show an alert dialog
            System.out.println("Appointment time is outside of business hours (9 AM to 5 PM EST).");
            return;
        }

        // Save the appointment to the database
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_source1", "root", "vurudi100@gmail.com");

            // Prepare the INSERT or UPDATE statement
            String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the parameters
            statement.setInt(1, appointment.getAppointmentId());
            statement.setString(2, appointment.getTitle());
            statement.setString(3, appointment.getDescription());
            statement.setString(4, appointment.getLocation());
            statement.setString(5, appointment.getType());
            statement.setTimestamp(6, Timestamp.valueOf(appointment.getStartDateTime()));
            statement.setTimestamp(7, Timestamp.valueOf(appointment.getEndDateTime()));
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(9, appointment.getCreatedBy());
            statement.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(11, appointment.getLastUpdatedBy());
            statement.setInt(12, appointment.getCustomerId());
            statement.setInt(13, appointment.getUserId());
            statement.setInt(14, appointment.getContactId());

            // Execute the INSERT or UPDATE statement
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                // Handle the case when the INSERT or UPDATE statement didn't modify any rows
                System.out.println("Failed to save appointment.");
            } else {
                System.out.println("Appointment saved successfully.");
            }

            // Close resources
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // Handle any errors that occurred during database access
            e.printStackTrace();
        }

    }

}
