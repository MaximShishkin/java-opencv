module ru.shishkin.opencv {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires opencv;
    requires java.desktop;

    opens ru.shishkin.opencv to javafx.fxml;
    exports ru.shishkin.opencv;
}