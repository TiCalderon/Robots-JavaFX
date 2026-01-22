package modelo.utiles;

import java.util.Objects;

public class Ubicacion {
    private int fila;
    private int columna;

    public Ubicacion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Si las dos ubicaciones son la misma referencia
        if (obj == null || getClass() != obj.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) obj;
        return fila == ubicacion.fila && columna == ubicacion.columna; // Compara las filas y columnas
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna); // Usa fila y columna para calcular el hash
    }
}