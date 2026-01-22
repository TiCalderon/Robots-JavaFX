package modelo.entidades;

import modelo.utiles.*;
import modelo.tablero.*;

public class Robotx1 extends Robot {
    public Robotx1(Ubicacion ubicacion) {
        super(ubicacion);
    }

    @Override
    public EstadosCeldas getSimbolo() {
        return EstadosCeldas.ROBOTX1;
    }

    @Override
    public Ubicacion mover(Tablero tablero) {
        Ubicacion destino = moverHaciaJugador(tablero);

        return destino;
    }
}
