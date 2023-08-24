module com.taskproject.task {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.taskproject.task to javafx.fxml;
    exports com.taskproject.task;
}