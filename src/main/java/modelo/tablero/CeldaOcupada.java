package modelo.tablero;

import modelo.utiles.*;

public class CeldaOcupada extends Celda {
    private EstadosCeldas ocupante;

    public CeldaOcupada(Ubicacion ubicacion, EstadosCeldas ocupante) {
        super(ubicacion, ocupante);
        this.ocupante = ocupante;
    }

    public EstadosCeldas getOcupante() {
        return ocupante;
    }

    public void setOcupante(EstadosCeldas ocupante) {
        this.ocupante = ocupante;
        setEstadoCelda(ocupante);
    }
}
