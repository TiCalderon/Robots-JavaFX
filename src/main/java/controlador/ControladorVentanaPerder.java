package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import vista.VistaMenu;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControladorVentanaPerder {

    @FXML
    private Button botonMenu;

    private Stage stageActual;

    @FXML
    public void volverMenu(ActionEvent click) throws IOException {
        stageActual = (Stage) botonMenu.getScene().getWindow();
        new VistaMenu(stageActual);
    }

    @FXML
    public void salirJuego(){
        stageActual = (Stage) botonMenu.getScene().getWindow();
        stageActual.close();
    }
}
