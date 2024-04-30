import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public ArrayList<BufferedImage> loadImage(String path) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        int salto=1;
        try {
            for (int i = 1 ; i <= 1066; i=i+salto) {
                try {
                   if(i>1010){
                    salto=2;
                   }
                    BufferedImage image = ImageIO.read(new File(path + "(" + i + ")" + ".png"));
                    images.add(image);
                } catch (IOException e) {
                    System.out.println("No se pudo cargar la imagen: " + path + "(" + i + ")" + ".png");
                    continue;
                }
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
    
}
