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

    public ClienteTren() {
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

    public void cambioBobina(String bobina) {
        System.out.println("cambiamos de bobina a la numero " + bobina);
        try {
            if (sc != null && !sc.isClosed()) {
                out.writeUTF("bobina-" + bobina);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
