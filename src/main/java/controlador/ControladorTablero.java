package controlador;

import controlador.utiles.ConstantesControladores;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.tablero.Tablero;
import modelo.utiles.Direccion;
import modelo.utiles.EstadosCeldas;
import modelo.utiles.Ubicacion;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ControladorTablero {

    @FXML
    private GridPane tableroVista;
    @FXML
    private Label puntaje;
    @FXML
    private Label nivel;
    @FXML
    private Label teleportLabel;
    @FXML
    private Label diamantesNoRecogidos;


    private Tablero tableroModelo;
    private Map<EstadosCeldas, Supplier<Node>> conectorImagenesEstados;
    private int cantidadFilas;
    private int cantidadColumnas;
    private Map<KeyCode, Direccion> movimientoTecla;
    private Juego juego;
    private ManejadorJuego manejadorJuego;
    private final Stage stage;

    public ControladorTablero(Stage stageActual){
        stage = stageActual;
    }

    public void setFilasYColumnas(int cantidadFilas, int cantidadColumnas){
        this.cantidadFilas = cantidadFilas;
        this.cantidadColumnas = cantidadColumnas;
    }

    public void cargarConectorImagenesEstados(){
        conectorImagenesEstados = new HashMap<>();

        conectorImagenesEstados.put(EstadosCeldas.INCENDIADA, () -> new ImageView(CargadorImagenes.getImage("fuego.gif")));
        conectorImagenesEstados.put(EstadosCeldas.JUGADOR, () -> new ImageView(CargadorImagenes.getImage("jugador.png")));
        conectorImagenesEstados.put(EstadosCeldas.ROBOTX1, () -> new ImageView(CargadorImagenes.getImage("robot1.png")));
        conectorImagenesEstados.put(EstadosCeldas.ROBOTX2, () -> new ImageView(CargadorImagenes.getImage("robot2.png")));
        conectorImagenesEstados.put(EstadosCeldas.DIAMANTE, () -> new ImageView(CargadorImagenes.getImage("diamante.gif")));
    }


    public void cargarMovimientosYTeclas(){
        movimientoTecla = new HashMap<>();

        movimientoTecla.put(KeyCode.Q, Direccion.ARRIBA_IZQUIERDA);
        movimientoTecla.put(KeyCode.W, Direccion.ARRIBA);
        movimientoTecla.put(KeyCode.E, Direccion.ARRIBA_DERECHA);
        movimientoTecla.put(KeyCode.A, Direccion.IZQUIERDA);
        movimientoTecla.put(KeyCode.D, Direccion.DERECHA);
        movimientoTecla.put(KeyCode.Z, Direccion.ABAJO_IZQUIERDA);
        movimientoTecla.put(KeyCode.X, Direccion.ABAJO);
        movimientoTecla.put(KeyCode.C, Direccion.ABAJO_DERECHA);
    }


    @FXML
    public void initialize() {
        juego = new Juego(cantidadFilas, cantidadColumnas);
        manejadorJuego = new ManejadorJuego(stage, this, juego);
        tableroModelo = juego.getTablero();
        cargarConectorImagenesEstados();
        cargarMovimientosYTeclas();
        tableroVista.setPrefSize(cantidadColumnas * ConstantesControladores.TAMANIO_CELDA, cantidadFilas * ConstantesControladores.TAMANIO_CELDA);

        cambiarGrid();
    }

    @FXML
    public void cambiarGrid(){
        puntaje.setText(juego.getStrPuntaje());
        nivel.setText(juego.getStrNivelActual());
        diamantesNoRecogidos.setText(juego.getStrDiamantesRestantes());
        teleportLabel.setText("(Remaining: " + juego.getStrTpsSeguros() + ")");

        for (int fila = 0; fila < cantidadFilas; fila++) {
            for (int columna = 0; columna < cantidadColumnas; columna++) {
                StackPane celda = new StackPane();
                celda.setPrefSize(ConstantesControladores.TAMANIO_CELDA, ConstantesControladores.TAMANIO_CELDA);
                String colorCelda = (fila + columna) % 2 == 0 ? ConstantesControladores.COLOR_CELDA_CLARA : ConstantesControladores.COLOR_CELDA_OSCURA;
                celda.setStyle("-fx-background-color:" + colorCelda + ";");

                EstadosCeldas estadoCelda = tableroModelo.getEstadoCeldaPorUbicacion(new Ubicacion(fila, columna));
                Supplier<Node> imagenAAgregar = conectorImagenesEstados.get(estadoCelda);
                if (imagenAAgregar != null)
                    celda.getChildren().add(imagenAAgregar.get());

                tableroVista.add(celda, columna, fila);
            }
        }
    }

    @FXML
    public void moverJugador(KeyEvent eventoTeclaPresionada) throws IOException {
        KeyCode teclaPresionada = eventoTeclaPresionada.getCode();
        Direccion direccionMovimiento =  movimientoTecla.get(teclaPresionada);

        manejadorJuego.moverJugador(direccionMovimiento);
        cambiarGrid();
        manejadorJuego.verificarFinDelJuego();
    }

    @FXML
    public void waitForRobots(ActionEvent click) throws IOException {
        manejadorJuego.waitForRobots();
        cambiarGrid();
        manejadorJuego.verificarFinDelJuego();
    }

    @FXML
    public void teleportRandomly(ActionEvent click) throws IOException {
        manejadorJuego.teleportRandomly();
        cambiarGrid();
        manejadorJuego.verificarFinDelJuego();
    }

    @FXML
    public void teleportSafely(int filaElegida, int columnaElegida) throws IOException {
        manejadorJuego.teleportSafely(filaElegida, columnaElegida);
        cambiarGrid();
        manejadorJuego.verificarFinDelJuego();
    }

    @FXML
    public void abrirVentanaEmergente(ActionEvent click) throws IOException {
        if(juego.getTpsSeguros() == 0)
            teleportLabel.setText(ConstantesControladores.MENSAJE_ERROR_TPS_SEGUROS);

        manejadorJuego.abrirVentanaEmergente(cantidadFilas, cantidadColumnas, this);
    }
}