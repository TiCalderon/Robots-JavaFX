package controlador;

import controlador.utiles.ConstantesControladores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vista.VistaTablero;

import java.io.IOException;

public class ControladorMenu {

    @FXML
    private TextField fieldFilas;
    @FXML
    private TextField fieldColumnas;
    @FXML
    private Button jugar;
    @FXML
    private Label labelError;

    @FXML
    private void cambiarDeVista(ActionEvent click) throws IOException {
        Stage stageActual = (Stage)jugar.getScene().getWindow();

        String cantidadFilas = fieldFilas.getText();
        String cantidadColumnas = fieldColumnas.getText();

        if(!ValidadorEntrada.validarEntrada(cantidadFilas, cantidadColumnas, ConstantesControladores.CANT_MIN_COL_FIL, ConstantesControladores.CANT_MAX_FIL, ConstantesControladores.CANT_MAX_COL)) {
            labelError.setText(ConstantesControladores.MENSAJE_ERROR);
            return;
        }

        new VistaTablero(stageActual, Integer.parseInt(cantidadFilas), Integer.parseInt(cantidadColumnas));
    }
}
