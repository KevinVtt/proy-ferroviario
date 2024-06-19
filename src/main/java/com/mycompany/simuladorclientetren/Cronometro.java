package com.mycompany.simuladorclientetren;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro {
    private int horas;
    private int minutos;
    private int segundos;
    private Timer timer;

    public Cronometro() {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.timer = new Timer();
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
}
