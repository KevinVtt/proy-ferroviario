import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.ArrayList;
import javax.swing.Timer;



public class Tren{
    private int velocidad;
    private BufferedImage cabina;
    private ArrayList<BufferedImage> images;
    private int currentImage = 0;
    private Timer timer;
    private int delay = 1000;

    public Tren(String cabina, ArrayList<BufferedImage> images) {
        velocidad = 0;
        try {
            this.cabina = ImageIO.read(new File(cabina));
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
            delay = Math.max(1000 / velocidad, 5);
            timer.setDelay(delay);
        }else {
            stopTimer();
        }
        
        }
    public void paint(Graphics g, int w, int h) {
        g.drawImage(images.get(currentImage), 0, 0, w, h, null);
        g.drawImage(cabina, 0, 0, w, h, null);
        g.setColor(Color.GREEN); // Color del texto
        g.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente y tamaño del texto
        g.drawString("Velocidad:" + velocidad, 10, 20);
    }

    public void nextImage() {
        
            currentImage++;
            if (currentImage >= images.size()) {
                currentImage = 0;
            }
        
    }

    public void aumentarVelocidad() {
        velocidad++;
        setTimerSpeed();
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
   
}