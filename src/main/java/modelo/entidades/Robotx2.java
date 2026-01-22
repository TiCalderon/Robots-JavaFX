package modelo.entidades;

import modelo.utiles.*;
import modelo.tablero.*;

public class Robotx2 extends Robot {
    public Robotx2(Ubicacion ubicacion) {
        super(ubicacion);
    }

    @Override
    public EstadosCeldas getSimbolo() {
        return EstadosCeldas.ROBOTX2;
    }

    @Override
    public Ubicacion mover(Tablero tablero) {
        Ubicacion destino = null;
        Ubicacion primeraCelda = moverHaciaJugador(tablero);

        int filaRobot = getUbicacion().getFila();
        int columnaRobot = getUbicacion().getColumna();

        int deltaFila = Integer.compare(primeraCelda.getFila(), filaRobot);
        int deltaColumna = Integer.compare(primeraCelda.getColumna(), columnaRobot);

        Ubicacion segundaCelda = new Ubicacion(filaRobot + 2 * deltaFila, columnaRobot + 2 * deltaColumna);

        if (tablero.esCeldaValida(segundaCelda) && tablero.getEstadoCeldaPorUbicacion(segundaCelda) != EstadosCeldas.DIAMANTE) {
            destino = segundaCelda;
        } else {
            if (tablero.esCeldaValida(primeraCelda) && tablero.getEstadoCeldaPorUbicacion(primeraCelda) != EstadosCeldas.DIAMANTE) {
                destino = primeraCelda;
            }
        }

        return destino;
    }

}
