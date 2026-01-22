package modelo.tablero;

import modelo.utiles.EstadosCeldas;
import modelo.utiles.Ubicacion;

public class CeldaVacia extends Celda {
    public CeldaVacia(Ubicacion ubicacion) {
        super(ubicacion, EstadosCeldas.VACIA);
    }
}
