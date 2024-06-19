package com.mycompany.simuladorclientetren;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {

    private int w, h;

    JButton play;
    JButton flor;
    BufferedImage background;

    Main main;

    public Menu(int w, int h, Main main) {
    this.w = w;
    this.h = h;
    this.main = main;
    play = new JButton("Lugano - Villa Madero");
    flor = new JButton("A.Korn-a-guernica");

    try {
        background = ImageIO.read(new File("./src/main/resources/images/menu/background.png"));
    } catch (IOException ex) {
        ex.printStackTrace(); // Manejo de errores en caso de que no se pueda cargar la imagen
    }
    this.setBounds(0, 0, this.w, this.h);

    //para poder posicionar manualmente los componentes
    setLayout(null);

    add(play);
    add(flor);
    play.setBounds(20, 20, 200, 50); 
    flor.setBounds(20, 80, 200, 50); 

    initListener();
}

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, w, h, this);
}

public void initListener() {
    play.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            main.initJuego("D:/Proyectos/simuladorFerroviario2D/src/main/resources/images/recorrido/A. Korn - Guernica");
        }
    });
    flor.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            main.initJuego("D:/Proyectos/simuladorFerroviario2D/src/main/resources/images/recorrido/A. Korn - Guernica");
        }
    });
}}