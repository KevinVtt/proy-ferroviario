package com.mycompany.simuladorclientetren;

import grafo.Bobina;
import grafo.Grafo;
import grafo.Seccion;
import grafo.Semaforo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import ui.Ui;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Canvas extends JPanel implements MediadorCanvas, Runnable {

    private static final int TARGET_FPS = 30;
    private static final long FRAME_TIME = 1000 / TARGET_FPS;
    private Tren tren;
    private ClienteTren ct;
    private Grafo grafo;
    private int currentBobinaAux = 0;
    private Ui ui;
    private List<Bobina> bobinas;

    public Canvas(int w, int h, ClienteTren ct, String pathRecorrido,List<Bobina> bobinas, String nSerie) {
        this.bobinas=bobinas;
        grafo = Grafo.getInstancia();
        grafo.inicializarRecorrido(bobinas);
        tren = new Tren(pathRecorrido,ct);
        tren.setNSerie(nSerie);

        ui = new Ui(tren.getVelocidad(), w, h);
        setLayout(new BorderLayout());
        add(ui, BorderLayout.CENTER);

        this.ct = ct;

        // Agregar KeyListener para las teclas numéricas 1 y 2
        //porque no recuerdo el keykode de las flechitas XD
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Acelerar con la tecla numérica 1
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_1) {
                    tren.aumentarVelocidad();
                    tren.setAcelerando(true);
                } // Disminuir velocidad con la tecla numérica 2
                else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_2) {
                    tren.disminuirVelocidad();
                }
                else if(e.getKeyCode()==KeyEvent.VK_NUMPAD9 || e.getKeyCode() == KeyEvent.VK_9){
                    tren.setVisibleCabina(!tren.getVisibleCabina());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_1) {
                    tren.setAcelerando(false);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Método no utilizado en este caso
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        tren.paint(g, getWidth(), getHeight());

        ui.paint(g);
        renderFPS(g);
    }

    private void renderFPS(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("FPS: " + TARGET_FPS, 100, 20);
    }

    private void verificarBobina() {
        int aux = tren.getNombreBobina();
        if (currentBobinaAux != aux) {
            currentBobinaAux = aux;
            String currentBobina = this.bobinas.get(currentBobinaAux).getNombre();
            ct.cambioBobina(tren.getRecorrido(), currentBobina, tren.getNSerie());
        }
    }

    @Override
    public void actualizarSeccion(Seccion seccion) {
        //avisar a la ui y demas
        if (ui != null) {
            ui.actualizaSeccion(seccion);
        }

    }

    @Override
    public void actualizarSemaforo(Semaforo semaforo) {
        //avisar a la ui y demas
        if (ui != null) {
            ui.actualizaSemaforo(semaforo);
        }
    }

    private void actualizarDatosTren() {
        tren.actualizaSemaforo();
        tren.actualizaCurrentSeccion();
        actualizarSemaforo(tren.getSemaforo());
        actualizarSeccion(tren.getCurrentSeccion());
    }

    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();
            //updates------------------------
            verificarBobina();
            actualizarDatosTren();
            //--------------------------------
            repaint();
            long currentTime = System.currentTimeMillis() - startTime;

            if (currentTime < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - currentTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
