package com.mycompany.simuladorclientetren;
import grafo.Grafo;
import grafo.Seccion;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class ImageLoader {
    private ArrayList<BufferedImage> images;
    private File carpeta;
    private File[] subDirectorios;
    private Grafo mapaGrafo;
    private Map<String, Integer> imagenesPorCarpeta;

    public ImageLoader() {
        mapaGrafo = new Grafo();
        imagenesPorCarpeta = new HashMap<>();
    }

    public ArrayList<BufferedImage> loadPath(String path) {
        images = new ArrayList<>();
        List<String> nombresSecciones = obtenerNombresSecciones();

        // Filtrar solo los directorios
        carpeta = new File(path);
        subDirectorios = carpeta.listFiles(File::isDirectory); 

        if (subDirectorios != null) {
            for (File subDirectorio : subDirectorios) {
                String nombreCarpeta = subDirectorio.getName();
                // Solo procesar carpetas que están en el grafo
                if (nombresSecciones.contains(nombreCarpeta)) { 
                    File[] imagenesEnCarpeta = subDirectorio.listFiles((dir, name) -> name.endsWith(".png"));
                    //se ordena porque se cargaban incorrectamente por ej-> 1 10 2 etc..
                    if (imagenesEnCarpeta != null) {
                        // Ordenar imágenes por nombre
                        Arrays.sort(imagenesEnCarpeta, new Comparator<File>() {
                            @Override
                            public int compare(File f1, File f2) {
                                return extractNumber(f1.getName()) - extractNumber(f2.getName());
                            }
                                //esto devuelve de (1).png--> return 1
                            private int extractNumber(String filename) {
                                String numberPart = filename.replaceAll("\\D+", "");
                                return Integer.parseInt(numberPart);
                            }
                        });
                        //se necesita para avisar del cambio de bobina manjeado en Tren.
                        imagenesPorCarpeta.put(nombreCarpeta, imagenesEnCarpeta.length);
                        for (File archivoImagen : imagenesEnCarpeta) {
                            try {
                                BufferedImage image = ImageIO.read(archivoImagen);
                                if (image != null) {
                                    images.add(image);
                                } else {
                                    System.out.println("Image is null: " + archivoImagen.getName());
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return images;
    }

    private List<String> obtenerNombresSecciones() {
        List<String> nombres = new ArrayList<>();
        for (Seccion s : mapaGrafo.getAllSeccion()) {
            nombres.add(s.getNombre());
        }
        return nombres;
    }

    public int getCarpetasTotales() {
        return subDirectorios != null ? subDirectorios.length : 0;
    }

    public ArrayList<BufferedImage> getImages() {
        return images;
    }

    public int[] getCantImagenesCarpeta() {
        int[] counts = new int[imagenesPorCarpeta.size()];
        int i = 0;
        for (Integer count : imagenesPorCarpeta.values()) {
            counts[i++] = count;
        }
        return counts;
    }
}
