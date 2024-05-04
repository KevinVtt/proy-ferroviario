import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    int salto=1;
    int carpetasTotales;
    int[] cantImagenesCarpeta;
    ArrayList<BufferedImage> images;
    File carpeta;
    File[] subDirectorios;
    
    public ArrayList<BufferedImage> loadPath(String path) {

        images= new ArrayList<>();
        carpeta=new File(path);
        subDirectorios=carpeta.listFiles();
        carpetasTotales=subDirectorios.length;
        
       
        cantImagenesCarpeta=new int[carpetasTotales];
        for(int i=1;i<=carpetasTotales;i++){
            cantImagenesCarpeta[i-1]=subDirectorios[i-1].listFiles().length;
        }
        
        try {
           for(int i=1;i<=carpetasTotales;i++){
            //System.out.println(cantImagenesCarpeta[i-1]);
            for(int j=1;j<=cantImagenesCarpeta[i-1];j++){

                //System.out.println("\n"+path+"/"+i+"/"+"("+j+").png");

                BufferedImage image= ImageIO.read(new File(path+"/"+i+"/"+"("+j+").png"));
                images.add(image);
              
            }
            System.out.println("carpeta"+i);
           }
                 
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return images;
    }

    public int getCarpetasTotales(){
        return carpetasTotales;
    }
    public int[] getCantImagenesCarpeta(){
        return cantImagenesCarpeta;
    }
    
}
