package com.mycompany.simuladorclientetren;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main extends JFrame {
    Canvas panel;
    Menu menu;
    ClienteTren ct;
    int width=1024;
    int height=768;
    
    public Main(){
        setTitle("Simulador Ferroviario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        
        ct=new ClienteTren();
        //panel=new Canvas(width,height,ct);
        menu=new Menu(width,height,this);
        //add(panel);
        add(menu);
       
        setVisible(true);
        
        //Thread renderingThread = new Thread(panel);
        Thread hiloct = new Thread(ct);
        
        //renderingThread.start();
        hiloct.start();

        initListener(this);
        
      
    
       }

    public void initListener(JFrame ventana){
        ventana.addWindowListener(new WindowAdapter() {
        @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cerrando la ventana");
                ct.cerrarConexion();
                
            }
       });
    }

    public static void main(String[] args) {
        
            new Main();
       
    }

    void initJuego(String pathRecorrido) {
        panel=new Canvas(width,height,ct,pathRecorrido);
        this.add(panel);
        this.remove(menu);
        revalidate();
        Thread renderingThread = new Thread(panel);
        renderingThread.start();
    }
}
