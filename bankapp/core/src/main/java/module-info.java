module bankapp.core {

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires junit;

    exports core;
    opens core to com.fasterxml.jackson.databind;
}
