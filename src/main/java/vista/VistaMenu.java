package vista;

import controlador.ControladorMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaMenu {
    public final String RUTA_MENU = "/menu.fxml";

    public VistaMenu(Stage stage) throws IOException {
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource(RUTA_MENU));
        Scene scene = new Scene(loaderMenu.load());
        stage.setScene(scene);
        stage.show();
    }
}