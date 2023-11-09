module bankapp.fxui {
    requires bankapp.core;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml, com.fasterxml.jackson.databind;
}
