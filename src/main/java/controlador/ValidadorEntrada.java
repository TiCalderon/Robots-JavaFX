package controlador;

public class ValidadorEntrada {

    public static boolean validarEntrada(String filStr, String colStr, int minFilYCol, int maxFilas, int maxColumnas){
        try {
            int fila = Integer.parseInt(filStr);
            int col = Integer.parseInt(colStr);
            return fila >= minFilYCol && fila <= maxFilas && col >= minFilYCol && col <= maxColumnas;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
