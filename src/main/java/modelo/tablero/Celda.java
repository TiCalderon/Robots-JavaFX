package modelo.tablero;

import modelo.utiles.*;

public abstract class Celda {
    Ubicacion ubicacion;
    private EstadosCeldas estadoCelda;

    public Celda(Ubicacion ubicacion, EstadosCeldas estadoCelda) {
        this.ubicacion = ubicacion;
        this.estadoCelda = estadoCelda;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public EstadosCeldas getEstadoCelda() {
        return estadoCelda;
    }

    public void setEstadoCelda(EstadosCeldas estadoCelda) {
        this.estadoCelda = estadoCelda;
    }

    public boolean estaVacia(Ubicacion ubicacion) {
        return estadoCelda == EstadosCeldas.VACIA;
    }
}
