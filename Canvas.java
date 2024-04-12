import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Canvas extends JPanel implements Runnable {
    private ArrayList<BufferedImage> images;
    private static final int TARGET_FPS = 30;
    private static final long FRAME_TIME = 1000 / TARGET_FPS;
    private Tren tren;

    public Canvas(int w, int h) {
        ImageLoader loader = new ImageLoader();
        images = loader.loadImage("./resources/images/1 a 1664 de 9147/");
        tren = new Tren("./resources/images/cabina/cabinatest.png",images);
    
         // Agregar KeyListener para las teclas numéricas 1 y 2
         this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Acelerar con la tecla numérica 1
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD1 || e.getKeyCode() == KeyEvent.VK_1) {
                    tren.aumentarVelocidad();
                }
                // Disminuir velocidad con la tecla numérica 2
                else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_2) {
                    tren.disminuirVelocidad();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                // Método no utilizado en este caso
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
        tren.paint(g, getWidth(), getHeight());
        renderFPS(g);
    }

    private void renderFPS(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("FPS: " + TARGET_FPS, 100, 20);
    }

    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();
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
