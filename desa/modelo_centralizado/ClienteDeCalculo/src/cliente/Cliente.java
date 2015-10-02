package cliente;

import java.net.*;
import java.io.*;

public class Cliente {

    @SuppressWarnings("unused")
    public void pideSuma(String ip, String bloque) {
        String respuesta;
        Socket socket;
        
        try {
            socket = new Socket(ip, 8000);
            System.out.println("Conectando a " + socket.getInetAddress().getHostAddress() + ":8000 para enviar petición");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(bloque);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            respuesta = dis.readUTF();
            System.out.println("Suma del servidor " + ip + " es: " + respuesta);

            dos.close();
            dis.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("ERROR: NO FUE POSIBLE ESTABLECER COMUNICACIÓN CON EL SERVIDOR");
            //e.printStackTrace();
        }
    }
}
