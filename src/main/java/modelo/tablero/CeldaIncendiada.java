package modelo.tablero;

import modelo.utiles.*;

public class CeldaIncendiada extends Celda {
    public CeldaIncendiada(Ubicacion ubicacion) {
        super(ubicacion, EstadosCeldas.INCENDIADA);
    }
}
