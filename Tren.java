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
    private int currentImageFolder=0;
    private Timer timer;
    private int delay = 1000;

    private Dimension screenSize;
    private boolean acelerando=false;
    private String pathRecorrido="./resources/images/recorrido/Recorrido Lugano - Villa Madero";
    private boolean cargado=false;
    private ImageLoader loader;
    private int nombreBobina=0;
    int[] imagesFolder;
    int currentFolder=0;

    public Tren() {
        this.loader = new ImageLoader();
        images = loader.loadPath(pathRecorrido);
        if (images.isEmpty()) {
            System.out.println("No se han cargado las imágenes.");
            System.exit(1);
        }
        cargado=true;
       if(cargado){
        imagesFolder=loader.getCantImagenesCarpeta();
       }
        screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        velocidad = 0;
        try {
            this.cabina = ImageIO.read(new File("./resources/images/cabina/cabinaRender3D.png"));
            this.cabina2 = ImageIO.read(new File("./resources/images/cabina/cabina2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        g.setColor(Color.red); // Color del texto
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("currentImg "+ currentImage,10,10);
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
           
            if(currentImageFolder > imagesFolder[currentFolder]){
                nombreBobina++;
                currentFolder++;
                currentImageFolder=0;
            }
           
            currentImageFolder++;
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
    public boolean recorridoCargado(){
        return cargado;
    }
    public ImageLoader getImageLoader(){
        return loader;
    }
    public int getNombreBonina(){
       return nombreBobina;
    }
}