module com.mycompany.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires java.base;
    opens com.mycompany.mavenproject1 to javafx.fxml;
    exports com.mycompany.mavenproject1;
    exports com.mycompany.mavenproject1.pojo;
}
