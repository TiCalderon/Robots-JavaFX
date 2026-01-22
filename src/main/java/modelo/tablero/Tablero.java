package modelo.tablero;

import java.util.*;
import modelo.utiles.*;
import modelo.entidades.*;

public class Tablero {
    private Celda[][] tablero;
    private Jugador jugador;
    private List<Robot> robots;
    private List<Diamante> diamantes;

    public Tablero(int filas, int columnas, Jugador jugador) {
        this.tablero = new Celda[filas][columnas];
        this.robots = new ArrayList<>();
        this.diamantes = new ArrayList<>();
        this.jugador = jugador;
    }

    public void inicializar(int nivelActual) {
        robots.clear();
        diamantes.clear();

        for (int fila = 0; fila <tablero.length; fila++) {
            for (int columna = 0; columna <tablero[fila].length; columna++) {
                tablero[fila][columna] = new CeldaVacia(new Ubicacion(fila, columna));
            }
        }

        colocarJugador();
        colocarDiamantes();
        colocarRobots(nivelActual);
    }

    private void colocarJugador() {
        int filaJugador = tablero.length / 2;
        int columnaJugador = tablero[1].length / 2;
        Ubicacion ubicacionJugador = new Ubicacion(filaJugador, columnaJugador);

        jugador.setUbicacion(ubicacionJugador);
        tablero[filaJugador][columnaJugador] = new CeldaOcupada(ubicacionJugador, EstadosCeldas.JUGADOR);
    }

    private void colocarDiamantes() {
        Random random = new Random();

        while (diamantes.size() < 3) {
            int filaDiamante = random.nextInt(tablero.length);
            int columnaDiamante = random.nextInt(tablero[0].length);
            Ubicacion ubicacionDiamante = new Ubicacion(filaDiamante, columnaDiamante);

            if (!tablero[filaDiamante][columnaDiamante].estaVacia(ubicacionDiamante)) {
                continue;
            }

            boolean cercano = false;
            for (Diamante diamante : diamantes) {
                if (distanciaMinima(ubicacionDiamante, diamante.getUbicacion()) < 3) {
                    cercano = true;
                    break;
                }
            }

            if (cercano) {
                continue;
            }

            Diamante nuevoDiamante = new Diamante(ubicacionDiamante);
            diamantes.add(nuevoDiamante);
            tablero[filaDiamante][columnaDiamante] = new CeldaOcupada(ubicacionDiamante, EstadosCeldas.DIAMANTE);
        }
    }

    private void colocarRobots(int nivelActual) {
        int cantidadRobots;

        if(nivelActual == 1) {
            cantidadRobots = 3;
        } else {
          cantidadRobots = nivelActual * 2;
        }
        Random random = new Random();

        while (robots.size() < cantidadRobots) {
            int filaRobots = random.nextInt(tablero.length);
            int columnaRobots = random.nextInt(tablero[0].length);

            Ubicacion ubicacionRobots = new Ubicacion(filaRobots, columnaRobots);

            if (!tablero[filaRobots][columnaRobots].estaVacia(ubicacionRobots)) {
                continue;
            }

            if (distanciaMinima(ubicacionRobots, jugador.getUbicacion()) < 3) {
                continue;
            }

            Robot nuevoRobot;
            EstadosCeldas tipoRobot;
            if (random.nextDouble() < 0.7) {
                tipoRobot = EstadosCeldas.ROBOTX1;
                nuevoRobot = new Robotx1(ubicacionRobots);
            } else {
                tipoRobot = EstadosCeldas.ROBOTX2;
                nuevoRobot = new Robotx2(ubicacionRobots);
            }

            robots.add(nuevoRobot);
            tablero[filaRobots][columnaRobots] = new CeldaOcupada(ubicacionRobots, tipoRobot);
        }
    }

    private int distanciaMinima(Ubicacion ubicacion1, Ubicacion ubicacion2) {
        return Math.abs(ubicacion1.getFila() - ubicacion2.getFila()) +  Math.abs(ubicacion1.getColumna() - ubicacion2.getColumna());
    }

    public int getFilas (){
        return tablero.length;
    }

    public int getColumnas (){
        return tablero[0].length;
    }

    public Celda getCelda(Ubicacion ubicacion) {
        return  tablero[ubicacion.getFila()][ubicacion.getColumna()];
    }

    public EstadosCeldas getEstadoCeldaPorUbicacion(Ubicacion ubicacion) {
        return tablero[ubicacion.getFila()][ubicacion.getColumna()].getEstadoCelda();
    }

    public boolean esCeldaValida(Ubicacion ubicacion) {
        int fila = ubicacion.getFila();
        int columna = ubicacion.getColumna();

        return fila >= 0 && fila < tablero.length &&
               columna >= 0 && columna < tablero[0].length;
    }

    public void moverEntidad(Ubicacion origen, Ubicacion destino, EstadosCeldas tipo) {
        tablero[origen.getFila()][origen.getColumna()] = new CeldaVacia(origen);

        tablero[destino.getFila()][destino.getColumna()] = new CeldaOcupada(destino, tipo);
    }

    public void agregarCeldaIncendiada(Ubicacion ubicacion) {
        tablero[ubicacion.getFila()][ubicacion.getColumna()] = new CeldaIncendiada(ubicacion);
        System.out.println(tablero[ubicacion.getFila()][ubicacion.getColumna()].getEstadoCelda() + "\n");

        List<Robot> robotsAEliminar = new ArrayList<>();
        for (Robot robot : robots) {
            if (robot.getUbicacion().getFila() == ubicacion.getFila() &&
                    robot.getUbicacion().getColumna() == ubicacion.getColumna()) {
                System.out.println("Robot eliminado por incendio en fila: " + ubicacion.getFila() + ", columna: " + ubicacion.getColumna());
                robotsAEliminar.add(robot);
            }
        }

        robots.removeAll(robotsAEliminar);
    }

    public void eliminarRobot(Robot robot) {
        robots.remove(robot);

        Ubicacion ubicacion = robot.getUbicacion();

        if (tablero[ubicacion.getFila()][ubicacion.getColumna()].getEstadoCelda() != EstadosCeldas.INCENDIADA) {
            tablero[ubicacion.getFila()][ubicacion.getColumna()] = new CeldaVacia(ubicacion);
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public List<Diamante> getDiamantes() {
        return diamantes;
    }
}