package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

    private List<Seccion> secciones;
    //utiliza list porque pueden ser varias conexiones
    private Map<String, List<Seccion>> conexiones;

    public Grafo() {
        secciones = new ArrayList<>();
        conexiones = new HashMap<>();
        
        Bobina bobina1 = new Bobina("ab-2");
        Bobina bobina2 = new Bobina("c5");
        Bobina bobina3 = new Bobina("v4-2");
        Bobina bobina4 = new Bobina("z");
        
        Semaforo semaforo1 = new Semaforo();
        Semaforo semaforo2 = new Semaforo();
        Semaforo semaforo3 = new Semaforo();
        Semaforo semaforo4 = new Semaforo();
        
        Seccion seccion1 = new Seccion(bobina1,  semaforo1);
        Seccion seccion2 = new Seccion(bobina2, semaforo2);
        Seccion seccion3 = new Seccion(bobina3, semaforo3);
        Seccion seccion4 = new Seccion(bobina4, semaforo4);
        
        agregarSeccion(seccion1);
        agregarSeccion(seccion2);
        agregarSeccion(seccion3);
        //se agrega lo que quieras
        //agregarSeccion(seccion4);
        
        setSiguiente(seccion1, seccion2);
        setSiguiente(seccion2, seccion3);
        //modo loop
        setSiguiente(seccion3, seccion1);
        
        imprimiVecinos(seccion1);
        imprimiVecinos(seccion2);
        imprimiVecinos(seccion3);
        
    }

    public void agregarSeccion(Seccion seccion) {
        secciones.add(seccion);
        conexiones.put(seccion.getNombre(), new ArrayList<>());
    }

    public void setSiguiente(Seccion s1, Seccion s2) {
        if (secciones.contains(s1) && secciones.contains(s2)) {

            conexiones.get(s1.getNombre()).add(s2);

        } else {
            throw new IllegalArgumentException("al menos una de las secciones no esta presente en el grafo");
        }
    }

    public List<Seccion> obtenerVecinos(Seccion s) {
        return conexiones.get(s.getNombre());
    }

    public void imprimiVecinos(Seccion s) {
        List<Seccion> vecinosSeccion = obtenerVecinos(s);
        System.out.println("Vecinos de " + s.getNombre() + ":");

        if (!vecinosSeccion.isEmpty()) {
            if (s.getSemaforo().getEstado() && vecinosSeccion.size() > 0) {
                // Semáforo está en estado true y hay al menos un vecino
                Seccion vecinoSinCambios = vecinosSeccion.get(0);
                System.out.println("sin cambios " + vecinoSinCambios.getNombre());
            } else if (!s.getSemaforo().getEstado() && vecinosSeccion.size() > 1) {
                // Semáforo está en estado false y hay al menos dos vecinos
                Seccion vecinoConCambio = vecinosSeccion.get(1);
                System.out.println("cambio realizado a... " + vecinoConCambio.getNombre());
            } else {
                System.out.println("No se puede determinar el vecino adecuado según el estado del semáforo.");
            }
        } else {
            System.out.println("No hay vecinos para la sección " + s.getNombre());
        }
    }

    public void imprimiTodos() {
        for(Seccion s: secciones){
            imprimiVecinos(s);
        }
    }
    public List<Seccion> getAllSeccion(){
        return secciones;
    }
    public Seccion getFirstSeccion(){
        return secciones.get(0);
    }
    public Seccion getSeccion(String name) {
        for (int i = 0; i < secciones.size(); i++) {
            Seccion current = secciones.get(i);
            if (current.getNombre().equals(name)) {
                return current;
            }
        }
        return null;
    }
}


