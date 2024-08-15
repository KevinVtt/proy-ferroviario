package com.mycompany.simuladorclientetren;

import grafo.Grafo;
import grafo.Seccion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ImageLoader {
    private final int CACHE_SIZE = 10; // Número de imágenes a cargar por vez

    private final BlockingQueue<BufferedImage> IMAGE_QUEUE; // Cola para manejar imágenes
    private Iterator<Seccion> recorridoIterator;
    private Queue<File> currentFolderImages;
    private Seccion currentSeccion;
    private boolean Cargado;
    //grafo generico a futuro sera una interfaz
    private Grafo mapaGrafo;
    private Map<String, Integer> imagenesPorCarpeta;
    private Map<String, File> carpetaPorSeccion;

    public ImageLoader() {
        mapaGrafo = Grafo.getInstancia();
        imagenesPorCarpeta = new HashMap<>();
        IMAGE_QUEUE = new LinkedBlockingQueue<>();
        Cargado = false;
        carpetaPorSeccion = new HashMap<>();
    }

    public void loadPath(String path) {
        File carpeta = new File(path);
        File[] subDirectorios = carpeta.listFiles(File::isDirectory);

        if (subDirectorios != null) {
            //por default le pasamos la primer seccion
            
            List<Seccion> seccionesOrdenadas = mapaGrafo.getRecorridoGrafo(mapaGrafo.getFirstSeccion());
            
            for (File dir : subDirectorios) {
                if (seccionesOrdenadas.stream().anyMatch(s -> s.getNombre().equals(dir.getName()))) {
                    
                    carpetaPorSeccion.put(dir.getName(), dir);
                }
            }

            // Ordenar las carpetas basadas en el recorrido del grafo
            recorridoIterator = seccionesOrdenadas.iterator();
            loadNextFolderImages();
        }
    }

    private void loadNextFolderImages() {
        if (recorridoIterator == null || !recorridoIterator.hasNext()) {
            Cargado = true;
            return;
        }

        currentSeccion = recorridoIterator.next();
        
        File folder = carpetaPorSeccion.get(currentSeccion.getNombre());
        if (folder != null) {
            File[] imagenesEnCarpeta = folder.listFiles((dir, name) -> name.endsWith(".png"));
            if (imagenesEnCarpeta != null) {
                Arrays.sort(imagenesEnCarpeta, (f1, f2) -> extractNumber(f1.getName()) - extractNumber(f2.getName()));
                imagenesPorCarpeta.put(currentSeccion.getNombre(), imagenesEnCarpeta.length);
                currentFolderImages = new LinkedList<>(Arrays.asList(imagenesEnCarpeta));
                loadImagesBatch();
            }
        } else {
            loadNextFolderImages(); // Continuar con la siguiente sección si la carpeta actual no está disponible
        }
    }

    private void loadImagesBatch() {
        int count = 0;
        while (count < CACHE_SIZE && !currentFolderImages.isEmpty()) {
            File archivoImagen = currentFolderImages.poll();
            try {
                BufferedImage image = ImageIO.read(archivoImagen);
                if (image != null) {
                    IMAGE_QUEUE.offer(image);
                } else {
                    System.out.println("Image is null: " + archivoImagen.getName());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            count++;
        }

        if (currentFolderImages.isEmpty()) {
            loadNextFolderImages();
        }
    }

    public BufferedImage getNextImage() {
        if (IMAGE_QUEUE.isEmpty() && !Cargado) {
            loadImagesBatch();
        }
        return IMAGE_QUEUE.poll();
    }

    private int extractNumber(String filename) {
        String numberPart = filename.replaceAll("\\D+", "");
        return Integer.parseInt(numberPart);
    }

    public int getCarpetasTotales() {
        return carpetaPorSeccion.size();
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
