package servidor;

import java.net.*;
import java.io.*;

public class Servidor {

    @SuppressWarnings({ "unused", "resource" })
	public String conexion1() {
        ServerSocket serverSocket = null;
        ServerSocket serverSocketR = null;
        Socket socket = null;
        Socket socketR = null;
        String peticion = null;
        String respuesta = "Datos incorrectos";
        String num = "";
        String num2 = "";
        String num3 = "";
        
        try {
            System.out.println("Escuchando por el puerto 8000");
            serverSocket = new ServerSocket(8000, 300);
        } catch (IOException e) {
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }

        System.out.println("Esperando a que los clientes se conecten...");
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Se conecto un cliente: " + socket.getInetAddress().getHostAddress());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                peticion = dis.readUTF();
                System.out.println("El mensaje que me envio el cliente es: " + peticion);

                String[] strArr = peticion.replace("[", "").replace("]", "").replace(" ", "").split(",");
                int suma = 0;
                for (int i = 0; i < strArr.length; i++) {
                    suma = suma + (Integer.parseInt(strArr[i]));
                }
                /*
                try {
                    Thread.sleep(5000);                 //1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                */
                try {
                    System.out.println("Escuchando por el puerto 9000");
                    serverSocketR = new ServerSocket(9000, 300);
                } catch (IOException e) {
                    System.out.println("java.io.IOException generada");
                    e.printStackTrace();
                }
                
                //----------------
                System.out.println("El respuesta que tengo para entregar es: " + suma);
                socket = null;

                System.out.println("Escuchando por el puerto 9000 para entregar la respuesta");
                socketR = serverSocketR.accept();
                System.out.println("Se conectó el cliente para pedir su respuesta: " + socketR.getInetAddress().getHostAddress());
                DataOutputStream dos = new DataOutputStream(socketR.getOutputStream());
                dos.writeUTF(String.valueOf(suma));

                dos.close();
                dis.close();
                socketR.close();
                serverSocketR.close();
            } catch (IOException e) {
                //System.out.println("java.io.IOException generada");
                System.out.println("Verificación de puerto OK");
                //e.printStackTrace();
            }
        }

    }
    
    
    
    
}
