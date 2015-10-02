package cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    @SuppressWarnings({"unused", "null"})
    public static void main(String[] args) {

        final int TOTAL_NUMEROS = 6;
        final int MAXIMO_SERVIDORES = 6;
        int cuentaServidores = 0;

        // GENERACIÓN DE NÚMEROS RANDOM
        Random randNumGenerator = new Random();
        int[] x = new int[TOTAL_NUMEROS];
        for (int i = 0; i < x.length; i++) {
            x[i] = (randNumGenerator.nextInt(100) + 1);
        }

        int[] part5 = new int[TOTAL_NUMEROS];
        System.arraycopy(x, 0, part5, 0, part5.length);

        // COMIENZO DEL PROCESO PRINCIPAL
        System.out.println("Comienzo del proceso");
        Cliente escucha = new Cliente();

        // Lee el archivo de ips (Arreglo: ips)
        List<String> lines = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader("C:\\desa\\tbl_ip_dist_servidores.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
                cuentaServidores++;
                if (cuentaServidores == MAXIMO_SERVIDORES) {
                    break;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
        };

        // Verifica si los servidores están activos
        String[] ips_in = new String[lines.size()];
        String[] ips_ok = new String[lines.size()];
        lines.toArray(ips_in);
        int numips_in = ips_in.length;

        int numips_ok = 0;
        for (int p = 0; p < numips_in; p++) {
            try {
                SocketAddress sockaddr = new InetSocketAddress(ips_in[p], 8000);
                Socket socket = new Socket();
                socket.connect(sockaddr, 1000);
                System.out.println("Verificación de servidor (IP): " + ips_in[p] + " = OK");
                ips_ok[numips_ok] = ips_in[p];
                numips_ok++;
                socket.close();
            } catch (IOException e) {
                System.out.println("Verificación de servidor (IP): " + ips_in[p] + " = FALLÓ");
            }
        }
        // Separa bloques de números según cantidad de servidores activos (Arreglo: arrBloques)
        int indice = 0;
        int tope = 0;

        System.out.println("\nNúmero de servidores activos      : " + numips_ok);
        if (numips_ok > 0) {
            tope = TOTAL_NUMEROS / numips_ok;
            System.out.println("Número total de peticiones        : " + TOTAL_NUMEROS);
            System.out.println("Número de peticiones por servidor : " + tope + "\n");

        } else {
            System.out.println("NO SE ENCONTRARON SERVIDORES ACTIVOS");
            System.out.println("       EL PROCESO SE DETENDRÁ       ");
        }
        String bloque = "";
        String[] arrBloques = new String[numips_ok];

        for (int y = 0; y < numips_ok; y++) {
            for (int z = 0; z < tope; z++) {
                bloque = bloque + Integer.toString(part5[indice]);
                indice++;
                if (z < (tope - 1)) {
                    bloque = bloque + ",";
                }
            }
            arrBloques[y] = bloque;
            bloque = "";
        }

        // Envía solicitudes a los servidores
        for (int a = 0; a < numips_ok; a++) {
            escucha.pideSuma(ips_ok[a], arrBloques[a]);
        }

        // Define contenedor de respuestas
        String[] respuesta = new String[numips_ok];
        Arrays.fill(respuesta, null);

        // Procesa las respuestas de los servidores
        while (numips_ok > 0 && Arrays.asList(respuesta).contains(null)) {
            for (int b = 0; b < numips_ok; b++) {
                if (respuesta[b] == null || respuesta[b].equals("")) {
                    respuesta[b] = escucha.pideRespuesta(ips_ok[b]);
                }
            }
        }

        if (numips_ok > 0) {
            String respuestaFinal = null;
            // Prepara último llamado para suma final
            String bloqueFinal = "";
            for (int c = 0; c < numips_ok; c++) {
                bloqueFinal = bloqueFinal + respuesta[c] + ",";
            }
            // Realiza llamado para procesar respuesta final
            escucha.pideSuma(ips_ok[0], bloqueFinal);
            while (respuestaFinal == null) {
                respuestaFinal = escucha.pideRespuesta(ips_ok[0]);
            }

        }
    }
}
