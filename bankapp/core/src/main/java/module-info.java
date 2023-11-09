module bankapp.core {

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports core;
    exports json;
    exports core.Accounts;

    opens core to com.fasterxml.jackson.databind;
    opens core.Accounts to com.fasterxml.jackson.databind;
    opens json;
}
