import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public ArrayList<BufferedImage> loadImage(String path) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        try {
            for (int i = 200 ; i <= 300; i++) {
                BufferedImage image = ImageIO.read(new File(path + "(" + i + ")" + ".png"));
                images.add(image);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }
    
}
