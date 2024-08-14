package com.mycompany.simuladorclientetren;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;

public class Tren {
    private static final int INITIAL_DELAY = 1000;
    
    private Properties properties;
    private int velocidad;

    
    private String nSerie;
    private String nombreRecorrido;

    private int nombreBobina;

    
    private BufferedImage cabina, cabina2;
    private BufferedImage currentImage;
    private Timer timer;
    private int delay = INITIAL_DELAY;

    private Dimension screenSize;
    private boolean acelerando = false;

    private String pathRecorrido;
    private boolean cargado = false;
    private ImageLoader loader;

    public Tren(String path) {
        //para la imagenes de la cabina por ahora
        leerConfig();
        nombreBobina=0;
        nSerie = "thoshiba234";
        int ultimoSeparador = path.lastIndexOf("/");
        nombreRecorrido = path.substring(ultimoSeparador + 1);
        nombreBobina = 0;
        pathRecorrido = path;
        this.loader = new ImageLoader();
        loader.loadPath(pathRecorrido);
        //cargamos la primer imagen para que no se vea vacio(peligrooo)
        try{
            currentImage = loader.getNextImage();
        }catch(Exception e){
            e.printStackTrace();
            currentImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        }
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        velocidad = 0;
        try {
            this.cabina = ImageIO.read(new File(properties.getProperty("cabinaDefault")));
            this.cabina2 = ImageIO.read(new File(properties.getProperty("cabinaAvanza")));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (velocidad > 0) {
                    nextImage();
                }
            }
        });

        startTimer();
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void setTimerSpeed() {
        if (velocidad > 0) {
            delay = Math.max(9000 / velocidad, 90);
            timer.setDelay(delay);
        } else {
            stopTimer();
        }
    }

    public void paint(Graphics g, int w, int h) {
        if (currentImage != null) {
            g.drawImage(currentImage, 0, 0, w, h, null);
        }
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("currentImg " + currentImage, 10, 10);
        drawCabina(g, w, h);
        int textX = screenSize.width / 10 + 200;
        int textY = screenSize.height / 2 + 100;
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("     " + velocidad, textX, textY);
        drawVelocity(g, textX, textY);
    }

    public void nextImage() {
        currentImage = loader.getNextImage();
        if (currentImage == null) {
            System.out.println("No hay más imágenes para mostrar.");
        }
    }

    public void aumentarVelocidad() {
        if (velocidad < 90) {
            velocidad++;
            setTimerSpeed();
        }
        if (velocidad > 0 && !timer.isRunning()) {
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

    public void drawVelocity(Graphics g, int x, int y) {
        g.drawRect(x, y, 10, -velocidad);
    }

    public void drawCabina(Graphics g, int w, int h) {
    if (acelerando) {
        if (cabina2 != null) {
            g.drawImage(cabina2, 0, 0, w, h, null);
        } else {
            System.out.println("cabina2 es null");
        }
    } else {
        if (cabina != null) {
            g.drawImage(cabina, 0, 0, w, h, null);
        } else {
            System.out.println("cabina es null");
        }
    }
}

    public boolean isAcelerando() {
        return acelerando;
    }

    public void setAcelerando(boolean acelerando) {
        this.acelerando = acelerando;
    }

    public boolean recorridoCargado() {
        return cargado;
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

    public String getProximaEstacion() {
        int posGuion = nombreRecorrido.indexOf("-");
        return nombreRecorrido.substring(posGuion + 2);
    }

    public ImageLoader getImageLoader() {
        return loader;
    }

    public String getNSerie() {
        return nSerie;
    }

    public void setNSerie(String nSerie) {
        this.nSerie = nSerie;
    }
    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
     public String getRecorrido() {
        return nombreRecorrido;
    }

    public void setRecorrido(String nombreRecorrido) {
        this.nombreRecorrido = nombreRecorrido;
    }
    public int getNombreBobina() {
        return nombreBobina;
    }

    public void setNombreBobina(int nombreBobina) {
        this.nombreBobina = nombreBobina;
    }
    public BufferedImage getCurrentImage() {
        return currentImage;
    }
}
