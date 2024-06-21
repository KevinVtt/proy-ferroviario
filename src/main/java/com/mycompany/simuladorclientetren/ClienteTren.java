package com.mycompany.simuladorclientetren;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
public class ClienteTren implements Runnable {

    final String HOST = "localhost";
    final int PUERTO = 5000;
    DataInputStream in;
    DataOutputStream out;
    Socket sc;
    Main main;

    public ClienteTren(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        try {
            sc = new Socket(HOST, PUERTO);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            if (sc.isConnected()) {
                out.writeUTF("tren");
            }

            while (!sc.isClosed()) {
                // out.writeUTF("tren");

                String mensaje = in.readUTF();
                System.out.println("Respuesta del servidor: " + mensaje);
                if(mensaje.contains("Bobinas del recorrido")){
                     procesarRespuesta(mensaje);
                }
               

            }
        } catch (SocketException se) {
            System.out.println("Socket cerrado");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void cerrarConexion() {
        try {
            if (sc != null && !sc.isClosed()) {
                out.writeUTF("DESCONEXION");
                sc.close();
            }

            System.out.println("conexion cerrada exitosamente");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void cambioBobina(String recorrido, String identificadorBobina, String numTren) {
        System.out.println("recorrido --> " + recorrido);
        System.out.println("cambiamos de bobina a la numero --> " + identificadorBobina);
        System.out.println("modelo del tren --> " + numTren);
//recorrido-identificador-NumTren <--- formato de mensaje
        try {
            if (sc != null && !sc.isClosed()) {
                out.writeUTF("recorrido-" + recorrido + "/identificador-" + identificadorBobina + "/tren-" + numTren);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void procesarRespuesta(String mensaje) {
     
        //Respuesta del servidor: Bobinas del recorrido constitucion-ezeiza: aux1, aux2, PT7, PT8, 233T, 223AT, 219AT
        String bobinasPart = mensaje.split(":")[1].trim();
        // Dividir las bobinas en un array
        String[] bobinas = bobinasPart.split(",");
        //quitamos espacios
        for (String bobina : bobinas) {
            System.out.println("Bobina: " + bobina.trim());

        }
        if (mensaje.contains("constitucion-ezeiza")) {
            main.initJuego(bobinas, "C:/Users/sebas/Documents/recorrido/Recorrido Lugano - Villa Madero");
        } else if (mensaje.contains("constitucion-korn")) {
            main.initJuego(bobinas, "D:/Proyectos/simuladorFerroviario2D/src/main/resources/images/recorrido/A. Korn - Guernica");
        }
    }

    public void consultarRecorrido(String texto) {
        try {
            if (sc != null && !sc.isClosed()) {
                out.writeUTF(texto);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
