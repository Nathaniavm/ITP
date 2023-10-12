module bankapp.fxui {
    requires bankapp.core;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;

    opens ui to javafx.graphics, javafx.fxml;
}
