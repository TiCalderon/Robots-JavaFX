package vista;

import controlador.ControladorVentanaPerder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaVentanaPerder {
    public final String RUTA_VENTANA_PERDER = "/ventanaPerder.fxml";

    public VistaVentanaPerder(Stage stage) throws IOException {
        FXMLLoader loaderPerder = new FXMLLoader(getClass().getResource(RUTA_VENTANA_PERDER));
        Scene scene = new Scene(loaderPerder.load());
        stage.setScene(scene);
        stage.show();
    }
}
