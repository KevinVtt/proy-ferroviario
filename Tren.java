import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.ArrayList;
import javax.swing.Timer;



public class Tren{
    private int velocidad;
    private BufferedImage cabina,cabina2;
    private ArrayList<BufferedImage> images;
    private int currentImage = 0;
    private Timer timer;
    private int delay = 1000;

    private Dimension screenSize;
    private boolean acelerando=false;

    
    public Tren(String cabina,String cabina2, ArrayList<BufferedImage> images) {
        screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        velocidad = 0;
        try {
            this.cabina = ImageIO.read(new File(cabina));
            this.cabina2 = ImageIO.read(new File(cabina2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.images=images;

        timer = new Timer(delay, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(velocidad>0){nextImage();}
            }
        });
        startTimer();
      
    }
    public void startTimer() {
        timer.start();
    }

    // Método para detener el timer
    public void stopTimer() {
        timer.stop();
    }

    public void setTimerSpeed() {
        if(velocidad > 0){
            delay = Math.max(9000 / velocidad, 90);
            timer.setDelay(delay);
        }else {
            stopTimer();
        }
        
        }
    public void paint(Graphics g, int w, int h) {
        g.drawImage(images.get(currentImage), 0, 0, w, h, null);
        drawCabina(g,w,h);
        int textX=screenSize.width/10+200;
        int textY=screenSize.height/2+100;
        g.setColor(Color.GREEN); // Color del texto
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente y tamaño del texto
        g.drawString("     "+velocidad, textX, textY);
        drawVelocity(g,textX,textY);
    }

    public void nextImage() {
        
            currentImage++;
            if (currentImage >= images.size()) {
                currentImage = 0;
            }
        
    }

    public void aumentarVelocidad() {
        if(velocidad<90){
            velocidad++;
            setTimerSpeed();
        }
        if (velocidad > 0 && timer.isRunning() == false) {
            startTimer();
            nextImage();
        }
    }

    public void disminuirVelocidad() {
        if (velocidad > 0) {
            velocidad--;
            setTimerSpeed(); 
        }
    }
   
    public void drawVelocity(Graphics g,int x,int y){
        g.drawRect(x, y, 10, -velocidad);
    }
    public void drawCabina(Graphics g,int w,int h) {
        if(acelerando){
            g.drawImage(cabina2, 0, 0, w, h, null);
        }
        else{g.drawImage(cabina, 0, 0, w, h, null);}
    }


    public boolean isAcelerando() {
        return acelerando;
    }
    public void setAcelerando(boolean acelerando) {
        this.acelerando = acelerando;
    }
}