package tateti;

import java.io.*;
import java.net.Socket;

public class ClienteHandler extends Thread {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String simbolo;
    private ClienteHandler oponente;
    private Tablero tablero;
    private String nombre;
    private int puntos = 0;
    private boolean miTurno;
    private boolean empiezaSiguiente = false;
    private static volatile boolean partidaTerminada = false;


    public ClienteHandler(Socket socket, String simbolo, Tablero tablero) throws IOException {
        this.socket = socket;
        this.simbolo = simbolo;
        this.tablero = tablero;
        this.miTurno = simbolo.equals("X");

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public void setOponente(ClienteHandler oponente) {
        this.oponente = oponente;
    }

    public void run() {
        try {
            out.println("INGRESE_NOMBRE");
            nombre = in.readLine();

            out.println("Bienvenido " + nombre + ". Tu símbolo es " + simbolo);

            if (simbolo.equals("X")) {
                miTurno = true;
                out.println("TU TURNO");
            }

            while (true) {

                jugarPartida();

                if (!partidaTerminada) {
                    continue;
                }

                
                if (simbolo.equals("X")) {
                    mostrarMarcador();
                }

                
                if (simbolo.equals("X")) {

                    out.println("¿Desea jugar otra partida? (S/N)");
                    oponente.out.println("¿Desea jugar otra partida? (S/N)");

                    String r1 = in.readLine();
                    String r2 = oponente.in.readLine();

                    if (r1 == null || r2 == null ||
                        !r1.trim().equalsIgnoreCase("S") ||
                        !r2.trim().equalsIgnoreCase("S")) {

                        out.println("PARTIDA_TERMINADA");
                        oponente.out.println("PARTIDA_TERMINADA");

                        socket.close();
                        oponente.socket.close();
                        break;
                    }

                 
                    tablero.reiniciar();
                    partidaTerminada = false;

                    miTurno = empiezaSiguiente;
                    oponente.miTurno = !empiezaSiguiente;

                    if (miTurno) {
                        out.println("TU TURNO");
                    } else {
                        oponente.out.println("TU TURNO");
                    }

                    out.println("NUEVA_PARTIDA");
                    oponente.out.println("NUEVA_PARTIDA");
                    out.println("TU TURNO");
                }
            }


        } catch (Exception e) {
            System.out.println("Jugador desconectado");
        }
    }


    private void jugarPartida() throws IOException {

    	while (!partidaTerminada) {


            if (!miTurno) {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                continue;
            }

            out.println("Ingrese fila,columna (0 a 2):");

            String entrada = in.readLine();
            if (entrada == null) return;

            int f, c;

            try {
                String[] mov = entrada.split(",");

                if (mov.length != 2) {
                    out.println("Formato inválido. Use fila,columna (ej: 1,2)");
                    continue;
                }

                f = Integer.parseInt(mov[0].trim());
                c = Integer.parseInt(mov[1].trim());

            } catch (NumberFormatException e) {
                out.println("Debe ingresar números. Ejemplo: 0,2");
                continue;
            }

            if (f < 0 || f > 2 || c < 0 || c > 2) {
                out.println("Valores fuera de rango. Use números entre 0 y 2.");
                continue;
            }

            if (!tablero.colocarSimbolo(f, c, simbolo)) {
                out.println("Casilla ocupada. Pruebe otra casilla.");
                continue;
            }

            String estado = tablero.mostrarTablero();
            out.println(estado);
            oponente.out.println(estado);

            if (tablero.esGanador()) {
                out.println("GANASTE");
                oponente.out.println("PERDISTE");
                puntos++;
                empiezaSiguiente = false;
                oponente.empiezaSiguiente = true;

                partidaTerminada = true;
                return;
            }

            if (tablero.esEmpate()) {
                out.println("EMPATE");
                oponente.out.println("EMPATE");

                empiezaSiguiente = simbolo.equals("X");
                oponente.empiezaSiguiente = !empiezaSiguiente;

                partidaTerminada = true;
                return;
            }

            miTurno = false;
            oponente.miTurno = true;

            out.println("Movimiento aceptado. Esperando al rival...");
            oponente.out.println("TU TURNO");
        }

        
        return;
    }
    private void mostrarMarcador() {
        String marcador = "\nMARCADOR\n"
            + nombre + " (" + simbolo + "): " + puntos + "\n"
            + oponente.nombre + " (" + oponente.simbolo + "): " + oponente.puntos + "\n";

        out.println(marcador);
        oponente.out.println(marcador);
    }


}
