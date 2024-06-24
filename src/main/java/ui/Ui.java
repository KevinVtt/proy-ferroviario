package ui;
import java.awt.*;

import javax.swing.*;

public class Ui extends JPanel {

    private int velocidad;
    private int width;
    private int height;
    Components crono;

    public Ui(int velocidad, int width, int height) {
        this.width = width;
        this.height = height;
        setOpaque(false);
        crono=new Components(this.width,this.height,velocidad);
        
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
      crono.paint(g);
    }

   
  
}
