package modelo.entidades;

import modelo.utiles.EstadosCeldas;
import modelo.utiles.Ubicacion;
import modelo.tablero.Tablero;

public abstract class Robot {
    private Ubicacion ubicacion;

    public Robot(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public abstract Ubicacion mover(Tablero tablero);

    public Ubicacion moverHaciaJugador(Tablero tablero) {
        Jugador jugador = tablero.getJugador();
        Ubicacion ubicacionJugador = jugador.getUbicacion();
        int filaRobot = ubicacion.getFila();
        int columnaRobot = ubicacion.getColumna();

        int deltaFila = Integer.compare(ubicacionJugador.getFila(), filaRobot);
        int deltaColumna = Integer.compare(ubicacionJugador.getColumna(), columnaRobot);

        int nuevaFila = filaRobot + deltaFila;
        int nuevaColumna = columnaRobot + deltaColumna;

        Ubicacion destino = new Ubicacion(nuevaFila, nuevaColumna);

        if (esCeldaConObstaculo(tablero, destino)) {
            return buscarCeldaAlternativa(tablero, filaRobot, columnaRobot);
        }

        return destino;
    }

    private boolean esCeldaConObstaculo(Tablero tablero, Ubicacion destino) {
        EstadosCeldas estadoDestino = tablero.getEstadoCeldaPorUbicacion(destino);

        return estadoDestino == EstadosCeldas.DIAMANTE;
    }

    private Ubicacion buscarCeldaAlternativa(Tablero tablero, int filaRobot, int columnaRobot) {
        Ubicacion[] direccionesAlternativas = {
                new Ubicacion(filaRobot + 1, columnaRobot),
                new Ubicacion(filaRobot - 1, columnaRobot),
                new Ubicacion(filaRobot, columnaRobot + 1),
                new Ubicacion(filaRobot, columnaRobot - 1)
        };

        for (Ubicacion dir : direccionesAlternativas) {
            if (tablero.esCeldaValida(dir) && !esCeldaConObstaculo(tablero, dir)) {
                return dir;
            }
        }

        return new Ubicacion(filaRobot, columnaRobot);
    }

    public abstract EstadosCeldas getSimbolo();

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion nuevaUbicacion) {
        ubicacion = nuevaUbicacion;
    }
}
