package modelo;

import java.util.*;
import modelo.entidades.*;
import modelo.tablero.*;
import modelo.utiles.*;

public class Juego {
    private Jugador jugador;
    private Tablero tablero;
    private int nivel;
    private int puntaje;
    private EstadosJuego estadoJuego;

    public Juego(int filas, int columnas) {
        this.nivel = 1;
        this.puntaje = 0;
        this.estadoJuego = EstadosJuego.JUGANDO;

        this.jugador = new Jugador(new Ubicacion(filas / 2, columnas / 2));

        this.tablero = new Tablero(filas, columnas, jugador);
        tablero.inicializar(nivel);
    }

    public void moverJugador(Direccion direccion) {
        Ubicacion destino = jugador.mover(tablero, direccion);

        if (tablero.getEstadoCeldaPorUbicacion(destino) == EstadosCeldas.INCENDIADA) {
            estadoJuego = EstadosJuego.NO_JUGANDO;
            System.out.println("El jugador ha caído en un fuego en: " + destino.getFila() + ", " + destino.getColumna());
            verificarEstadoDelJuego();
        }

        tablero.moverEntidad(jugador.getUbicacion(), destino, EstadosCeldas.JUGADOR);
        jugador.setUbicacion(destino);

        verificarDiamanteRecogido();
        moverRobots();
        verificarEstadoDelJuego();
    }

    public void teletransportarJugador() {
        tablero.getJugador().teletransportarse(tablero);
        verificarDiamanteRecogido();
        moverRobots();
        verificarEstadoDelJuego();
    }

    public void teletransportarJugadorSeguro(int fila, int columna) {
        try {
            tablero.getJugador().teletransportarseSeguro(fila, columna, tablero);
            verificarDiamanteRecogido();
            moverRobots();
            verificarEstadoDelJuego();
        } catch (IllegalStateException e) {
            System.out.println("No tiene teletransportes seguros.");
        }
    }

    public void moverRobots() {
        Map<Ubicacion, ArrayList<Robot>> movimientosPorUbicacion = new HashMap<>();
        ArrayList<Robot> robotsOriginales = new ArrayList<>(tablero.getRobots());

        for (Robot robot : robotsOriginales) {
            Ubicacion actual = robot.getUbicacion();
            Ubicacion destino = obtenerDestinoSeguro(robot, actual);

            if (verificarCapturaJugador(destino)) return;

            if (esColision(destino, movimientosPorUbicacion)) {
                resolverColision(destino, robot, movimientosPorUbicacion);
                continue;
            }

            if (esCeldaIncendiada(destino)) {
                eliminarRobotPorFuego(robot);
                continue;
            }

            registrarMovimiento(destino, robot, movimientosPorUbicacion);
        }

        for (Map.Entry<Ubicacion, ArrayList<Robot>> entry : movimientosPorUbicacion.entrySet()) {
            Ubicacion destino = entry.getKey();
            ArrayList<Robot> robotsEnDestino = entry.getValue();

            for (Robot robot : robotsEnDestino) {
                Ubicacion actual = robot.getUbicacion();
                ejecutarMovimiento(robot, actual, destino);
            }
        }
    }

    private Ubicacion obtenerDestinoSeguro(Robot robot, Ubicacion actual) {
        Ubicacion destino = robot.mover(tablero);
        if (!tablero.esCeldaValida(destino)) {
            return actual;
        }
        return destino;
    }

    private boolean verificarCapturaJugador(Ubicacion destino) {
        if (jugador.getUbicacion().equals(destino)) {
            estadoJuego = EstadosJuego.NO_JUGANDO;
            System.out.println("El robot ha atrapado al jugador en: " + destino.getFila() + ", " + destino.getColumna());
            return true;
        }
        return false;
    }

    private boolean esColision(Ubicacion destino, Map<Ubicacion, ArrayList<Robot>> movimientosPorUbicacion) {
        return movimientosPorUbicacion.containsKey(destino);
    }

    private void resolverColision(Ubicacion destino, Robot robot, Map<Ubicacion, ArrayList<Robot>> movimientosPorUbicacion) {
        System.out.println("Colisión detectada en: " + destino.getFila() + ", " + destino.getColumna());

        ArrayList<Robot> robotsColisionados = movimientosPorUbicacion.get(destino);
        for (Robot r : robotsColisionados) {
            tablero.eliminarRobot(r);
        }
        tablero.eliminarRobot(robot);
        tablero.agregarCeldaIncendiada(destino);
        movimientosPorUbicacion.remove(destino);
        puntaje += 2;
    }

    private boolean esCeldaIncendiada(Ubicacion destino) {
        return tablero.getEstadoCeldaPorUbicacion(destino) == EstadosCeldas.INCENDIADA;
    }

    private void eliminarRobotPorFuego(Robot robot) {
        System.out.println("El robot se ha movido a una celda incendiada. Eliminar robot.");
        tablero.eliminarRobot(robot);
        puntaje += 1;
    }

    private void registrarMovimiento(Ubicacion destino, Robot robot, Map<Ubicacion, ArrayList<Robot>> movimientosPorUbicacion) {
        movimientosPorUbicacion.put(destino, new ArrayList<>(List.of(robot)));
    }

    private void ejecutarMovimiento(Robot robot, Ubicacion actual, Ubicacion destino) {
        tablero.moverEntidad(actual, destino, robot.getSimbolo());
        robot.setUbicacion(destino);
        System.out.println("Robot movido de: " + actual.getFila() + ", " + actual.getColumna() + " a: " + destino.getFila() + ", " + destino.getColumna());
    }

    private void verificarDiamanteRecogido() {
        Ubicacion ubicacionJugador = jugador.getUbicacion();

        Iterator<Diamante> iterDiamantes = tablero.getDiamantes().iterator();
        while (iterDiamantes.hasNext()) {
            Diamante diamante = iterDiamantes.next();
            Ubicacion ubicacionDiamante = diamante.getUbicacion();
            if (ubicacionDiamante.getFila() == ubicacionJugador.getFila() && ubicacionDiamante.getColumna() == ubicacionJugador.getColumna()) {
                puntaje++;
                iterDiamantes.remove();
            }
        }
    }

    private void verificarEstadoDelJuego() {
        if (estadoJuego == EstadosJuego.NO_JUGANDO) {
            return;
        }

        if (tablero.getDiamantes().isEmpty() || tablero.getRobots().isEmpty()) {
            nivel++;
            jugador.setTpSeguros(jugador.getTpSeguros() + 1);
            tablero.inicializar(nivel);
        }
    }

    public Tablero getTablero() {
        return tablero;
    }

    public EstadosJuego getEstadoJuego() {
        return estadoJuego;
    }

    public int getNivelActual() {
        return nivel;
    }

    public int getTpsSeguros() {
        return  jugador.getTpSeguros();
    }

    public String getStrNivelActual(){
        return Integer.toString(nivel);
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getStrPuntaje(){
        return Integer.toString(puntaje);
    }

    public String getStrTpsSeguros(){
        return Integer.toString(jugador.getTpSeguros());
    }

    public String getStrDiamantesRestantes() {
        return Integer.toString(tablero.getDiamantes().size());
    }
}