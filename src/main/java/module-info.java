module com.neoterux.proyecto2p {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.logging.log4j;

    opens com.neoterux.proyecto2p to javafx.fxml, org.apache.logging.log4j;
    opens com.neoterux.proyecto2p.ui.controllers to javafx.fxml, org.apache.logging.log4j, java.desktop;
    exports com.neoterux.proyecto2p;
}
