package controlador;

import controlador.utiles.ConstantesControladores;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.utiles.Direccion;
import modelo.utiles.EstadosJuego;
import vista.VistaVentanaPerder;

import java.io.IOException;

public class ManejadorJuego {

    private final Stage stageActual;
    private final Juego juego;

    public ManejadorJuego(Stage stageActual, ControladorTablero controladorTablero, Juego juego) {
        this.stageActual = stageActual;
        this.juego = juego;
    }

    public void verificarFinDelJuego() throws IOException {
        if (juego.getEstadoJuego() == EstadosJuego.NO_JUGANDO)
            new VistaVentanaPerder(stageActual);
    }

    public void teleportRandomly() throws IOException {
        juego.teletransportarJugador();
    }

    public void teleportSafely(int filaElegida, int columnaElegida) throws IOException {
        juego.teletransportarJugadorSeguro(filaElegida, columnaElegida);
    }

    public void waitForRobots() throws IOException {
        juego.moverRobots();
    }

    public void moverJugador(Direccion direccionMovimiento) throws IOException {
        juego.moverJugador(direccionMovimiento);
    }

    public void abrirVentanaEmergente(int cantidadFilas, int cantidadColumnas, ControladorTablero controladorTablero) throws IOException {
        FXMLLoader loaderVentanaEmergente = new FXMLLoader(getClass().getResource(ConstantesControladores.RUTA_VENTA_EMERGENTE));
        loaderVentanaEmergente.setController(new ControladorVentEmergTpSafe(controladorTablero, cantidadFilas, cantidadColumnas));
        Stage nuevoStage = new Stage();
        Scene nuevaScene = new Scene(loaderVentanaEmergente.load());

        nuevoStage.initModality(Modality.APPLICATION_MODAL);

        nuevoStage.initOwner(stageActual);
        nuevoStage.setScene(nuevaScene);
        nuevoStage.showAndWait();
    }
}