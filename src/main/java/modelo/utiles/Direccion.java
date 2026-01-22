package modelo.utiles;

public enum Direccion {
    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1),
    ARRIBA_IZQUIERDA(-1, -1),
    ARRIBA_DERECHA(-1, 1),
    ABAJO_IZQUIERDA(1, -1),
    ABAJO_DERECHA(1, 1);

    private final Ubicacion movimiento;

    Direccion(int fila, int columna) {
        this.movimiento = new Ubicacion(fila, columna);
    }

    public Ubicacion getMovimiento() {
        return movimiento;
    }
}