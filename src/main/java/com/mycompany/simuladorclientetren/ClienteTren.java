package com.mycompany.simuladorclientetren;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import grafo.Bobina;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import utils.CambioBobinaDTO;
import utils.RespuestaBobinas;

public class ClienteTren implements Runnable {

    final String HOST = "localhost";
    final int PUERTO = 5000;
    DataInputStream in;
    DataOutputStream out;
    Socket sc;
    Main main;
    Gson gson;
    Properties properties;

    public ClienteTren(Main main) {
        this.main = main;
        leerConfig();
        gson = new GsonBuilder().create();
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
                String mensaje = in.readUTF();
                System.out.println("Respuesta del servidor: " + mensaje);

                // Procesar la respuesta según el formato del mensaje
                procesarRespuesta(mensaje);
            }

        } catch (SocketException se) {
            System.out.println("Socket cerrado");
            iniciarSinConexion();
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
        System.out.println("estado del sokect para cambiar bobina --> " + sc);

        //creamos el dto
        CambioBobinaDTO dto = new CambioBobinaDTO("cambio-bobina",recorrido, identificadorBobina, numTren);

        //dto --> json
        String mensaje = gson.toJson(dto);

        //recorrido-identificador-NumTren <--- formato de mensaje
        try {
            if (sc != null && !sc.isClosed()) {
                out.writeUTF(mensaje);
                //out.writeUTF("recorrido-" + recorrido + "/identificador-" + identificadorBobina + "/tren-" + numTren);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void procesarRespuesta(String mensaje) {

        String numeroSerie = "";
        // Verifica si el mensaje empieza con "/tren"
        if (mensaje.startsWith("/tren")) {
            // Maneja el mensaje del tren (número de serie)
            numeroSerie = mensaje.split(" ")[1];
            System.out.println("Número de serie del tren: " + numeroSerie);
            return;
        }

        // Verifica si el mensaje parece ser un JSON
        if (mensaje.startsWith("{") && mensaje.endsWith("}")) {
            // Intenta deserializar el JSON
            Type mapType = new TypeToken<RespuestaBobinas>() {
            }.getType();
            RespuestaBobinas respuesta = gson.fromJson(mensaje, mapType);

            if (respuesta != null) {
                String recorrido = respuesta.getRecorrido();
                Map<String, Bobina> bobinasMap = respuesta.getBobinas();

                // Imprimir el tamaño del mapa
                System.out.println("Número de bobinas deserializadas: " + bobinasMap.size());

                // Procesar la respuesta
                List<Bobina> bobinas = new ArrayList<>(bobinasMap.values());
                for (Bobina b : bobinas) {
                    System.out.println("Nombre bobina: " + b.getNombre());
                    System.out.println("Anterior: " + b.getAnterior());
                    System.out.println("Siguiente1: " + b.getSiguiente1());
                    System.out.println("Siguiente2: " + b.getSiguiente2());
                }

                // Inicializa el juego (comentado, ajustar según sea necesario)
                if (mensaje.contains("constitucion-ezeiza")) {
                    main.initJuego(numeroSerie, bobinas, properties.getProperty("ruta.recorrido1"));
                } else if (mensaje.contains("A.Korn-a-Guernica")) {
                    main.initJuego(numeroSerie, bobinas, properties.getProperty("ruta.recorrido2"));
                }
            } else {
                System.out.println("No se deserializó correctamente el JSON.");
            }
        } else {
            System.out.println("Mensaje no es del formato esperado para procesamiento: " + mensaje);
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

    public void iniciarSinConexion() {
        String[] bobinaString = {"aux1", "aux2","PT7"};
        List<Bobina> bobinasPrueba = new ArrayList();
        for (String s : bobinaString) {
            bobinasPrueba.add(new Bobina(s));
        }
           
        //pasarlas ya con sus siguientes asi arma las conexiones
        bobinasPrueba.get(0).setSiguiente1(bobinasPrueba.get(1));
        bobinasPrueba.get(1).setSiguiente1(bobinasPrueba.get(2));
//        bobinasPrueba.get(1).setSiguiente2(bobinasPrueba.get(3));

        String nSeriePrueba = "tren sin conexcion";
        main.initJuego(nSeriePrueba, bobinasPrueba, properties.getProperty("ruta.recorrido1"));

    }

    public void leerConfig() {
        this.properties = new Properties();

        try {
            FileInputStream fis = new FileInputStream("./src/main/resources/config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
