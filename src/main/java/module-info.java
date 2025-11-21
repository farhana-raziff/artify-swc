module com.myproject.artify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.base;

    opens com.myproject.artify to javafx.fxml;
    opens com.myproject.model to javafx.base, javafx.fxml;
    
    opens com.myproject.dao;
    opens com.myproject.util;

    exports com.myproject.artify;
}
