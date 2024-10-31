package com.mycompany.simuladorclientetren;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;

public class TrenPrueba extends JPanel{
    
    private int x;
    private int y;
    private int width;
    private int height;
    private int cont = 3;
    private boolean isVisible;
    private BufferedImage imagenTren;
    private int anchoTren = 20;
    private int largoTren = 20;
    private int xTren;
    private int yTren;
    private Tren tren;
    
    
    
    public TrenPrueba(int x, int y,int width, int height, Tren tren) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.isVisible = true;
        this.setBackground(Color.red);
        this.xTren = Math.round(width/2) + 35;
        this.yTren = Math.round(height/2) + 50;
        this.tren = tren;
        
                
        
        setBounds(0,0, width, height);
        setOpaque(false);
        cargarImagen();
        
    }
    
        private void cargarImagen() {
        Properties props = new Properties();
        try {
            // Cargar el archivo config.properties
            FileInputStream in = new FileInputStream("./src/main/resources/config.properties");
            props.load(in);
            in.close();

            // Obtener la ruta de la imagen del tren
            String rutaImagen = props.getProperty("rutaImagenTren");
              
            if (rutaImagen != null) {
                imagenTren = ImageIO.read(new File(rutaImagen));
                System.out.println("SE CARGO CORRECTAMENTE");
                System.out.println(rutaImagen);
            } else {
                System.err.println("No se encontró la clave 'rutaImagenTren' en config.properties");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        
        
    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
        
        if(isVisible){
            g.drawImage(imagenTren, xTren, yTren, anchoTren,largoTren,this);
        }
        
        
    }

    
    public void actualiza(){
        
        try{
            if(800 < xTren || 600 < yTren){
                //isVisible = false;
                resetearImagen();
            }
            
            if(tren.getVelocidad() > 0){
                this.xTren += tren.getVelocidad();
                this.yTren += tren.getVelocidad();
                // coheficiente de escalado
                setLargo(cont);
                setAncho(cont);
                System.out.println("W: " + width + " H: " + height);
            }
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
       
        
    }
    
    public void resetearImagen(){
        
        anchoTren = 20;
        largoTren = 20;
        this.xTren = Math.round(width/2) + 35;
        this.yTren = Math.round(height/2) + 50;
    }
    
    public void setAncho(int width) {
        this.anchoTren += width;
    }

    public void setLargo(int height) {
        this.largoTren += height;
    }
    
    
    
    
}
