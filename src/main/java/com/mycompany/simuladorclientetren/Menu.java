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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {

    private int w, h;

    JButton constitucionEzeiza;
    JButton kornGuernica;
    BufferedImage background;
    Properties properties;
    Main main;
    ClienteTren ct;
    Font font = new Font("Arial", Font.BOLD, 16);

    public Menu(int w, int h, Main main, ClienteTren ct) {
        leerConfig();
        this.w = w;
        this.h = h;
        this.main = main;
        this.ct = ct;
        constitucionEzeiza = new JButton("constitucion-ezeiza");
        constitucionEzeiza.setBackground(new Color(60, 60, 60));
        constitucionEzeiza.setForeground(Color.WHITE);
        constitucionEzeiza.setFont(font);
        constitucionEzeiza.setBorderPainted(false);
        constitucionEzeiza.setFocusPainted(false);
        constitucionEzeiza.setContentAreaFilled(true);

        kornGuernica = new JButton("A.Korn-a-Guernica");
        kornGuernica.setBackground(new Color(60, 60, 60));  // Cambia el color de fondo
        kornGuernica.setForeground(Color.WHITE);
        kornGuernica.setFont(font);
        kornGuernica.setBorderPainted(false);    // Quita el borde pintado
        kornGuernica.setFocusPainted(false);     // Quita el efecto de foco
        kornGuernica.setContentAreaFilled(true);

        try {
            background = ImageIO.read(new File(properties.getProperty("cabinaDefault")));
        } catch (IOException ex) {
            ex.printStackTrace(); // Manejo de errores en caso de que no se pueda cargar la imagen
        }
        this.setBounds(0, 0, this.w, this.h);

        //para poder posicionar manualmente los componentes
        setLayout(null);

        add(constitucionEzeiza);
        add(kornGuernica);
        constitucionEzeiza.setBounds((int) w / 10, (int) h / 4, 200, 50);
        kornGuernica.setBounds((int) w / 10, (int) h / 4 + 70, 200, 50);

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
        listenerAction(constitucionEzeiza);
        listenerMouse(constitucionEzeiza);
        listenerAction(kornGuernica);
        listenerMouse(kornGuernica);
    }
    
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
}
