package vista;

import controlador.ControladorTablero;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaTablero {
    public final String RUTA_TABLERO = "/tablero.fxml";

    public VistaTablero(Stage stage, int filas, int columnas) throws IOException {
        FXMLLoader loaderTablero = new FXMLLoader(getClass().getResource(RUTA_TABLERO));
        ControladorTablero controlador = new ControladorTablero(stage);
        controlador.setFilasYColumnas(filas, columnas);
        loaderTablero.setController(controlador);

        Scene scene = new Scene(loaderTablero.load());
        stage.setScene(scene);
        stage.show();
    }
}