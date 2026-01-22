package modelo.entidades;

import modelo.utiles.*;
import modelo.tablero.*;

public class Jugador {
    private Ubicacion ubicacion;
    private int tpSeguros;

    public Jugador(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        this.tpSeguros = 1;
    }

    public Ubicacion mover(Tablero tablero, Direccion direccion) {
        Ubicacion movimiento = direccion.getMovimiento();
        Ubicacion actual = getUbicacion();

        int nuevaFila = actual.getFila() + movimiento.getFila();
        int nuevaColumna = actual.getColumna() + movimiento.getColumna();
        Ubicacion destino = new Ubicacion(nuevaFila, nuevaColumna);

        if (nuevaFila < 0 || nuevaFila >= tablero.getFilas() || nuevaColumna < 0 || nuevaColumna >= tablero.getColumnas()) {
            return actual;
        }

        return destino;
    }

    public EstadosJuego teletransportarse(Tablero tablero) {
        Ubicacion ubicacionActual = getUbicacion();

        while (true) {
            int nuevaFila = (int) (Math.random() * tablero.getFilas());
            int nuevaColumna = (int) (Math.random() * tablero.getColumnas());
            Ubicacion nuevaUbicacion = new Ubicacion(nuevaFila, nuevaColumna);

            Celda celdaDestino = tablero.getCelda(nuevaUbicacion);
            EstadosCeldas estadoDestino = celdaDestino.getEstadoCelda();

            if (estadoDestino == EstadosCeldas.INCENDIADA || estadoDestino == EstadosCeldas.ROBOTX1 || estadoDestino == EstadosCeldas.ROBOTX2) {
                return EstadosJuego.NO_JUGANDO;
            }

            Celda celdaActual = tablero.getCelda(ubicacionActual);
            celdaActual.setEstadoCelda(EstadosCeldas.VACIA);

            setUbicacion(nuevaUbicacion);
            celdaDestino.setEstadoCelda(EstadosCeldas.JUGADOR);

            return EstadosJuego.JUGANDO;
        }
    }


    public EstadosJuego teletransportarseSeguro(int fila, int columna, Tablero tablero) throws IllegalStateException {
        if (tpSeguros <= 0 || fila < 0 || fila >= tablero.getFilas() || columna < 0 || columna >= tablero.getColumnas()) {
            throw new IllegalStateException("No tiene teletransportes seguros o la ubicación es inválida.");
        }

        Ubicacion nuevaUbicacion = new Ubicacion(fila, columna);
        Celda celdaDestino = tablero.getCelda(nuevaUbicacion);
        EstadosCeldas estadoDestino = celdaDestino.getEstadoCelda();

        if (estadoDestino == EstadosCeldas.INCENDIADA || estadoDestino == EstadosCeldas.ROBOTX1 || estadoDestino == EstadosCeldas.ROBOTX2) {
            return EstadosJuego.NO_JUGANDO;
        }

        Celda celdaActual = tablero.getCelda(getUbicacion());
        celdaActual.setEstadoCelda(EstadosCeldas.VACIA);
        setUbicacion(nuevaUbicacion);
        celdaDestino.setEstadoCelda(EstadosCeldas.JUGADOR);
        tpSeguros--;

        return EstadosJuego.JUGANDO;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getTpSeguros() {
        return tpSeguros;
    }

    public void setTpSeguros(int tpSeguros) {
        this.tpSeguros = tpSeguros;
    }

    public int getFila(){
        return ubicacion.getFila();
    }

    public int getColumna(){
        return ubicacion.getColumna();
    }
}