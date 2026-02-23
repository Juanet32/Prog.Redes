package tateti;

import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {

        try (
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
            );
            BufferedReader teclado = new BufferedReader(
                new InputStreamReader(System.in)
            );
        ) {

            String mensaje;

            while ((mensaje = in.readLine()) != null) {

             
                if (mensaje.equals("INGRESE_NOMBRE")) {
                    System.out.print("Ingrese su nombre: ");
                    out.println(teclado.readLine());
                    continue;
                }

                System.out.println(mensaje);

                if (mensaje.contains("Ingrese fila,columna")) {
                    String jugada = teclado.readLine();
                    out.println(jugada);
                }
                else if (mensaje.contains("¿Desea jugar otra partida")) {
                    String respuesta = teclado.readLine();
                    out.println(respuesta);
                }


                if (mensaje.equals("PARTIDA_TERMINADA")) {
                    System.out.println("Fin del juego.");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Conexión finalizada.");
        }
    }
}
