package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro {

    private int horas;
    private int minutos;
    private int segundos;
    private Timer timer;
    private int velocidad;
    private int width;
    private String pEstacion;
    
    public Cronometro(int velocidad,int width,String pEstacion) {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.timer = new Timer();
        this.velocidad=velocidad;
        this.width=width;
        this.pEstacion=pEstacion;
    }

    public void paint(Graphics g) {
        mostrarCronometro(g);
        mostrarProximaEstacion(g);
        mostrarVelocidadMaxima(g);
        mostrarVelocidad(g);
    }

    public void iniciar() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                incrementarTiempo();
                // System.out.println(getTiempo());
            }
        }, 0, 1000);
    }

    public void detener() {
        timer.cancel();
    }

    public void reiniciar() {
        detener();
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.timer = new Timer();
    }

    private void incrementarTiempo() {
        segundos++;
        if (segundos == 60) {
            segundos = 0;
            minutos++;
            if (minutos == 60) {
                minutos = 0;
                horas++;
            }
        }
    }

    public String getTiempo() {
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    private void mostrarCronometro(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Tiempo:", (width - 240), 70);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(getTiempo(), (width - 240), 90);
    }

    private void mostrarProximaEstacion(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Próxima estación: " + pEstacion, (width - 240), 50);
    }

    private void mostrarVelocidadMaxima(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Velocidad máxima: 90", (width - 240), 35);
    }

    private void mostrarVelocidad(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Velocidad actual: " + velocidad, (width - 240), 20);
    }
}
