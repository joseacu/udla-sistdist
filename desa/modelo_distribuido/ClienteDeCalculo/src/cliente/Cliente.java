package cliente;

import java.net.*;
import java.io.*;

public class Cliente {

	Socket socket = null;
	Socket socket2 = null;

	
    @SuppressWarnings("unused")
	public void pideSuma(String ip, String bloque) {
        //Socket socket = null;
        String respuesta = null;
        int b = 5;
        int c = 8;
        try {
            SocketAddress sockaddr = new InetSocketAddress(ip, 8000);
            Socket socket = new Socket();
            socket.connect(sockaddr, 1000);
            //socket = new Socket(ip, 8000);
            System.out.println("Conectando a " + socket.getInetAddress().getHostAddress() + ":8000 para enviar petición");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(bloque);
            dos.close();
            socket = null;
        } catch (IOException e) {
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }
    }

    public String pideRespuesta(String ip) {
        //Socket socket = null;
        String respuesta = null;
        try {
            System.out.println("Conectando a " + ip + ":9000 para solicitar la respuesta");
            SocketAddress sockaddr = new InetSocketAddress(ip, 9000);
            Socket socket = new Socket();
            socket.connect(sockaddr);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            respuesta = dis.readUTF();
            System.out.println("Suma del servidor " + ip + " es: " + respuesta);
            dis.close();
            socket.close();
        } catch (IOException e) {
            //socket.close();
            //System.out.println("java.io.IOException generada");
            //e.printStackTrace();
        }
        return respuesta;
    }
}
