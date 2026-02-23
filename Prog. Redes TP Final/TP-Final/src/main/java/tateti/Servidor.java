package tateti;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor iniciado...");

            Socket jugador1 = serverSocket.accept();
            Socket jugador2 = serverSocket.accept();

            Tablero tablero = new Tablero();

            ClienteHandler h1 = new ClienteHandler(jugador1, "X", tablero);
            ClienteHandler h2 = new ClienteHandler(jugador2, "O", tablero);

            h1.setOponente(h2);
            h2.setOponente(h1);

            h1.start();
            h2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
