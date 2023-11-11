module bankapp.core {

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports core;
    exports json;
    exports core.accounts;

    opens core to com.fasterxml.jackson.databind;
    opens core.accounts to com.fasterxml.jackson.databind;
    opens json;
}
