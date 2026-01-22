package controlador;

import controlador.utiles.ConstantesControladores;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorVentEmergTpSafe {

    @FXML
    private TextField indexTpCol;
    @FXML
    private TextField indexTpFil;
    @FXML
    private Label labelError;

    private final ControladorTablero controladorTablero;
    private final int cantFil;
    private final int cantCol;

    public ControladorVentEmergTpSafe(ControladorTablero controladorTablero, int cantFil, int cantCol){
        this.controladorTablero = controladorTablero;
        this.cantFil = cantFil;
        this.cantCol = cantCol;
    }

    @FXML
    public void enviarFilasColumnas() throws IOException {
        String fil = indexTpFil.getText();
        String col = indexTpCol.getText();
        Stage escenarioActual = (Stage)indexTpCol.getScene().getWindow();

        if(!ValidadorEntrada.validarEntrada(fil, col, ConstantesControladores.MIN_INDEX, cantFil - 1, cantCol - 1)) {
            labelError.setText(ConstantesControladores.MENSAJE_ERROR);
            return;
        }

        controladorTablero.teleportSafely(Integer.parseInt(fil), Integer.parseInt(col));
        escenarioActual.close();
    }
}

