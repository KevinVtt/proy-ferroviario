import javax.swing.JFrame;

public class Main extends JFrame {
    Canvas panel;
    int width=800;
    int height=600;
    
    public Main() {
        setTitle("Simulador Ferroviario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        panel=new Canvas(width,height);
        add(panel);
       
        setVisible(true);

        Thread renderingThread = new Thread(panel);
        renderingThread.start();
       
    }

    public static void main(String[] args) {
        new Main();
    }
}
