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
    private static final int IMAGES_BATCH_SIZE = 10; // Número de imágenes a cargar por vez

    private BlockingQueue<BufferedImage> imageQueue; // Cola para manejar imágenes
    private Iterator<File> folderIterator;
    private Queue<File> currentFolderImages;
    private File currentFolder;
    private boolean loadingDone;

    private Grafo mapaGrafo;
    private Map<String, Integer> imagenesPorCarpeta;

    public ImageLoader() {
        mapaGrafo = new Grafo();
        imagenesPorCarpeta = new HashMap<>();
        imageQueue = new LinkedBlockingQueue<>();
        loadingDone = false;
    }

    public void loadPath(String path) {
        List<String> nombresSecciones = obtenerNombresSecciones();
        File carpeta = new File(path);
        File[] subDirectorios = carpeta.listFiles(File::isDirectory);

        if (subDirectorios != null) {
            // Filtrar directorios basados en los nombres de las secciones
            List<File> filteredDirectories = new ArrayList<>();
            for (File dir : subDirectorios) {
                if (nombresSecciones.contains(dir.getName())) {
                    filteredDirectories.add(dir);
                }
            }

            folderIterator = filteredDirectories.iterator();
            loadNextFolderImages(); // Cargar imágenes iniciales
        }
    }

    private void loadNextFolderImages() {
        if (folderIterator == null || !folderIterator.hasNext()) {
            loadingDone = true;
            return;
        }

        currentFolder = folderIterator.next();
        File[] imagenesEnCarpeta = currentFolder.listFiles((dir, name) -> name.endsWith(".png"));
        if (imagenesEnCarpeta != null) {
            Arrays.sort(imagenesEnCarpeta, (f1, f2) -> extractNumber(f1.getName()) - extractNumber(f2.getName()));
            imagenesPorCarpeta.put(currentFolder.getName(), imagenesEnCarpeta.length);
            
            currentFolderImages = new LinkedList<>(Arrays.asList(imagenesEnCarpeta));
            loadImagesBatch();
        }
    }

    private void loadImagesBatch() {
        int count = 0;
        while (count < IMAGES_BATCH_SIZE && !currentFolderImages.isEmpty()) {
            File archivoImagen = currentFolderImages.poll();
            try {
                BufferedImage image = ImageIO.read(archivoImagen);
                if (image != null) {
                    imageQueue.offer(image);
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
        if (imageQueue.isEmpty() && !loadingDone) {
            loadImagesBatch();
        }
        return imageQueue.poll();
    }

    private int extractNumber(String filename) {
        String numberPart = filename.replaceAll("\\D+", "");
        return Integer.parseInt(numberPart);
    }

    private List<String> obtenerNombresSecciones() {
        List<String> nombres = new ArrayList<>();
        for (Seccion s : mapaGrafo.getAllSeccion()) {
            nombres.add(s.getNombre());
        }
        return nombres;
    }

    public int getCarpetasTotales() {
        return folderIterator != null ? folderIterator.hasNext() ? imagenesPorCarpeta.size() : 0 : 0;
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
