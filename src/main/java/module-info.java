module ru.shishkin.javaopencv {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires opencv;
    requires java.desktop;

    opens ru.shishkin.javaopencv to javafx.fxml;
    exports ru.shishkin.javaopencv;
}