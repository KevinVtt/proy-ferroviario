package ui;

import grafo.Seccion;
import grafo.Semaforo;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;

import javax.swing.*;

public class Ui extends JPanel {

    private static Ui instancia;
    private int velocidad;
    private int width;
    private int height;
    Components crono;
    private Semaforo semaforo = null;
    private Seccion seccion = null;
    private Properties properties;
    private BufferedImage semaforoPng;

    public static Ui getInstancia() {
        if (instancia == null) {
            return instancia = new Ui();
        }
        return instancia;
    }

    public Ui() {
    }

    public Ui(int velocidad, int width, int height) {
        leerConfig();
        this.width = width;
        this.height = height;
        setOpaque(false);
        crono = new Components(this.width, this.height, velocidad);

        try {
            this.semaforoPng = ImageIO.read(new File(properties.getProperty("semaforo")));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        if (semaforo != null && semaforo.getEstado()) {
            g.setColor(Color.GREEN);
            //g.fillRect(0, 0, 50, 50);
            g.fillOval(5, 10, 15, 15);
            g.setColor(Color.gray);
            g.fillOval(5, 25, 15, 15);
        } else if (semaforo != null && !semaforo.getEstado()) {
            g.setColor(Color.gray);
            g.fillOval(5, 10, 15, 15);
            g.setColor(Color.RED);
            g.fillOval(5, 25, 15, 15);

        }
        //semaforo png
        g.drawImage(semaforoPng, -30, -10, 80, 100, null);
        crono.paint(g);
    }

    public void actualizaSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public void actualizaSeccion(Seccion seccion) {
        this.seccion = seccion;
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
