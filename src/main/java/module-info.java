module sidemart {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.boxicons;
    requires java.prefs;
    requires fr.brouillard.oss.cssfx;

    exports com.github.alphareso;
    exports com.github.alphareso.layout;

    opens com.github.alphareso;

}