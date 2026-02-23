package tateti;

public class Tablero {

    private Celda[][] tablero = new Celda[3][3];

    public Tablero() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                tablero[i][j] = new Celda();
    }

    public synchronized boolean colocarSimbolo(int fila, int col, String simbolo) {
        if (tablero[fila][col].esVacia()) {
            tablero[fila][col].marcar(simbolo);
            return true;
        }
        return false;
    }

    public boolean esGanador() {
        for (int i = 0; i < 3; i++) {
            if (!tablero[i][0].esVacia() &&
                tablero[i][0].getEstado().equals(tablero[i][1].getEstado()) &&
                tablero[i][1].getEstado().equals(tablero[i][2].getEstado()))
                return true;

            if (!tablero[0][i].esVacia() &&
                tablero[0][i].getEstado().equals(tablero[1][i].getEstado()) &&
                tablero[1][i].getEstado().equals(tablero[2][i].getEstado()))
                return true;
        }

        if (!tablero[0][0].esVacia() &&
            tablero[0][0].getEstado().equals(tablero[1][1].getEstado()) &&
            tablero[1][1].getEstado().equals(tablero[2][2].getEstado()))
            return true;

        if (!tablero[0][2].esVacia() &&
            tablero[0][2].getEstado().equals(tablero[1][1].getEstado()) &&
            tablero[1][1].getEstado().equals(tablero[2][0].getEstado()))
            return true;

        return false;
    }

    public boolean esEmpate() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (tablero[i][j].esVacia())
                    return false;
        return true;
    }

    public String mostrarTablero() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(tablero[i][j].getEstado().equals("VACIA") ? "-" : tablero[i][j].getEstado());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public void reiniciar() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j].vaciar();
            }
        }
    }

}
