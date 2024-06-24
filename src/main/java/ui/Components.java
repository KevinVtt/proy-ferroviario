package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;

public class Components extends JPanel {

    private int horas;
    private int minutos;
    private int segundos;
    private int width; 
    private int height;
    int velocidad;
     private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    
    private Font font = new Font("Arial", Font.BOLD, 12);

    public Components(int width,int height,int velocidad) {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.width=width;
        this.height=height;
        this.velocidad = velocidad;
        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(true);
        iniciar();
    }

    public void iniciar() {
        new Thread(() -> {
            while (true) {
                incrementarTiempo();
                repaint();
                try {
                    Thread.sleep(1000); // Esperar 1 segundo (1000 milisegundos)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

   
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(100,100,100,127));
        g.fillRect(this.width-120,0, 100, 20);
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(toString(segundos), this.width-120, 15);
        
        g.setColor(new Color(100,100,100,127));
        g.fillRect(this.width-120,35, 100, 20);
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("velocidad: "+velocidad, this.width-120, 50);
        
        g.setColor(new Color(100,100,100,127));
        g.fillRect(this.width-120,65, 100, 20);
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("fecha: " + dateFormat.format(new Date()), this.width - 120, 80);
        
    }

    private void incrementarTiempo() {
        segundos++;
        if (segundos == 60) {
            segundos = 0;
            minutos++;
            if (minutos == 60) {
                minutos = 0;
                horas++;
            }
        }
    }

    private String toString(int segundos) {
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}

