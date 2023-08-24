package com.taskproject.task;

import javafx.beans.property.*;
import java.time.LocalDateTime;

/**
 * The Appointment class represents an appointment with its associated properties.
 */
public class Appointment {
    private final IntegerProperty appointmentId;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty location;
    private final StringProperty contact;
    private final StringProperty type;
    private final ObjectProperty<LocalDateTime> startDateTime;
    private final ObjectProperty<LocalDateTime> endDateTime;
    private final IntegerProperty customerId;
    private final IntegerProperty userId;

    /**
     * Constructs an Appointment object with the specified properties.
     *
     * @param appointmentId  the ID of the appointment
     * @param title          the title of the appointment
     * @param description    the description of the appointment
     * @param location       the location of the appointment
     * @param contact        the contact person associated with the appointment
     * @param type           the type/category of the appointment
     * @param startDateTime  the start date and time of the appointment
     * @param endDateTime    the end date and time of the appointment
     * @param customerId     the ID of the customer associated with the appointment
     * @param userId         the ID of the user who created the appointment
     */
    public Appointment(
            int appointmentId, String title, String description, String location, String contact,
            String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.type = new SimpleStringProperty(type);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.userId = new SimpleIntegerProperty(userId);
    }

    /**
     * Returns the appointment ID.
     *
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId.get();
    }

    /**
     * Returns the title of the appointment.
     *
     * @return the title of the appointment
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Returns the description of the appointment.
     *
     * @return the description of the appointment
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Returns the location of the appointment.
     *
     * @return the location of the appointment
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * Returns the contact person associated with the appointment.
     *
     * @return the contact person associated with the appointment
     */
    public String getContact() {
        return contact.get();
    }

    /**
     * Returns the type/category of the appointment.
     *
     * @return the type/category of the appointment
     */
    public String getType() {
        return type.get();
    }

    /**
     * Returns the start date and time of the appointment.
     *
     * @return the start date and time of the appointment
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime.get();
    }

    /**
     * Returns the end date and time of the appointment.
     *
     * @return the end date and time of the appointment
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime.get();
    }

    /**
     * Returns the ID of the customer associatedwith the appointment.
     *
     * @return the ID of the customer associated with the appointment
     */
    public int getCustomerId() {
        return customerId.get();
    }

    /**
     * Returns the ID of the user who created the appointment.
     *
     * @return the ID of the user who created the appointment
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Returns the appointment ID property.
     *
     * @return the appointment ID property
     */
    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    /**
     * Returns the title property of the appointment.
     *
     * @return the title property of the appointment
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Returns the description property of the appointment.
     *
     * @return the description property of the appointment
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Returns the location property of the appointment.
     *
     * @return the location property of the appointment
     */
    public StringProperty locationProperty() {
        return location;
    }

    /**
     * Returns the contact property of the appointment.
     *
     * @return the contact property of the appointment
     */
    public StringProperty contactProperty() {
        return contact;
    }

    /**
     * Returns the type property of the appointment.
     *
     * @return the type property of the appointment
     */
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * Returns the start date and time property of the appointment.
     *
     * @return the start date and time property of the appointment
     */
    public ObjectProperty<LocalDateTime> startDateTimeProperty() {
        return startDateTime;
    }

    /**
     * Returns the end date and time property of the appointment.
     *
     * @return the end date and time property of the appointment
     */
    public ObjectProperty<LocalDateTime> endDateTimeProperty() {
        return endDateTime;
    }

    /**
     * Returns the customer ID property associated with the appointment.
     *
     * @return the customer ID property associated with the appointment
     */
    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    /**
     * Returns the user ID property of the user who created the appointment.
     *
     * @return the user ID property of the user who created the appointment
     */
    public IntegerProperty userIdProperty() {
        return userId;
    }

    /**
     * Returns the name of the user who created the appointment.
     *
     * @return the name of the user who created the appointment
     */
    public String getCreatedBy() {
        return userId.getName();
    }

    /**
     * Returns the ID of the contact person associated with the appointment.
     *
     * @return the ID of the contact person associated with the appointment
     */
    public int getContactId() {
        return getUserId();
    }

    /**
     * Returns the name of the user who last updated the appointment.
     *
     * @return the name of the user who last updated the appointment
     */
    public String getLastUpdatedBy() {
        return getLastUpdatedBy();
    }
}
