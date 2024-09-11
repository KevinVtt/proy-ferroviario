package ui;

import grafo.Seccion;
import grafo.Semaforo;
import java.awt.*;

import javax.swing.*;

public class Ui extends JPanel {

    private static Ui instancia;
    private int velocidad;
    private int width;
    private int height;
    Components crono;
    private Semaforo semaforo = null;
    private Seccion seccion = null;

    public static Ui getInstancia() {
        if (instancia == null) {
            return instancia = new Ui();
        }
        return instancia;
    }

    public Ui() {
    }

    public Ui(int velocidad, int width, int height) {
        this.width = width;
        this.height = height;
        setOpaque(false);
        crono = new Components(this.width, this.height, velocidad);

    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        if (semaforo != null && semaforo.getEstado()) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 50, 50);
        } else if (semaforo != null && !semaforo.getEstado()) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 50, 50);
        }
        crono.paint(g);
    }

    public void actualizaSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public void actualizaSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

}
