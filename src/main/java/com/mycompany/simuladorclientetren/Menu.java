package com.mycompany.simuladorclientetren;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    ClienteTren ct;
    Font font = new Font("Arial", Font.BOLD, 16);

    public Menu(int w, int h, Main main, ClienteTren ct) {
        this.w = w;
        this.h = h;
        this.main = main;
        this.ct = ct;
        play = new JButton("constitucion-ezeiza");
        play.setBackground(new Color(60, 60, 60));
        play.setForeground(Color.WHITE);
        play.setFont(font);
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        play.setContentAreaFilled(true);

        flor = new JButton("constitucion-korn");
        flor.setBackground(new Color(60, 60, 60));  // Cambia el color de fondo
        flor.setForeground(Color.WHITE);
        flor.setFont(font);
        flor.setBorderPainted(false);    // Quita el borde pintado
        flor.setFocusPainted(false);     // Quita el efecto de foco
        flor.setContentAreaFilled(true);

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
        play.setBounds((int) w / 10, (int) h / 4, 200, 50);
        flor.setBounds((int) w / 10, (int) h / 4 + 70, 200, 50);

        initListener();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, w, h, this);
        g.setColor(new Color(100, 100, 100, 127));
        g.fillRect(20, 20, w - 60, h - 60);
    }

    public void initListener() {
        listenerAction(play);
        listenerMouse(play);
        listenerAction(flor);
        listenerMouse(flor);
    }
//yamil        
//D:/Proyectos/simuladorFerroviario2D/src/main/resources/images/recorrido/A. Korn - Guernica

    

    public void listenerAction(JButton boton) {
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = boton.getText();
                System.out.println(texto);
                ct.consultarRecorrido("menu/" + texto);
            }
        });
    }

    public void listenerMouse(JButton boton) {
        // Listener de hover para el botón 'play'
        boton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setFont(new Font("Arial", Font.BOLD, 14));
                boton.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setFont(font); // Restaurar la fuente original
                boton.setForeground(Color.WHITE); // Restaurar el color original
            }
        });
    }
}
