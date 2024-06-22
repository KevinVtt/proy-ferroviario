package com.mycompany.simuladorclientetren;
import ui.Cronometro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Canvas extends JPanel implements Runnable {
    private static final int TARGET_FPS = 30;
    private static final long FRAME_TIME = 1000 / TARGET_FPS;
    private Tren tren;
    private ClienteTren ct;
    int currentBobinaAux = 0;
    private Cronometro c;
    private String[] bobinas;
    
    public Canvas(int w, int h, ClienteTren ct, String pathRecorrido,String[] bobinas,String nSerie) {
        this.bobinas=bobinas; 
        tren = new Tren(pathRecorrido);
        tren.setNSerie(nSerie);
        
        c = new Cronometro(tren.getVelocidad(), w, tren.getProximaEstacion());
        c.iniciar();
        this.ct = ct;
         // Agregar KeyListener para las teclas numéricas 1 y 2
         this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Acelerar con la tecla numérica 1
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_1) {
                    tren.aumentarVelocidad();
                    tren.setAcelerando(true);
                }
                // Disminuir velocidad con la tecla numérica 2
                else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_2) {
                    tren.disminuirVelocidad();
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
    
    public void paint(Graphics g) {
        super.paint(g);
        if (tren.recorridoCargado()) {
            tren.paint(g, getWidth(), getHeight());
        }
        
        renderFPS(g);
        c.paint(g);
    }

    private void renderFPS(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("FPS: " + TARGET_FPS, 100, 20);
    }
    private void verificarBobina(){
        int aux = tren.getNombreBobina();
        if (currentBobinaAux != aux) {
            currentBobinaAux = aux;
           String currentBobina =this.bobinas[currentBobinaAux-1];
            ct.cambioBobina(tren.getRecorrido(), currentBobina, tren.getNSerie());
        }
    }

    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();
            verificarBobina();
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
