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
        //cargaRecorridoGuernica();
        cargaRecorridoLugano();
    }

    public void agregarSeccion(Seccion seccion) {
        secciones.add(seccion);
        conexiones.put(seccion.getNombre(), new ArrayList<>());
    }

    public void setSiguiente(Seccion s1, Seccion s2) {
        if (secciones.contains(s1) && secciones.contains(s2)) {
        //dirigido
            conexiones.get(s1.getNombre()).add(s2);

        } else {
            throw new IllegalArgumentException("al menos una de las secciones no esta presente en el grafo");
        }
    }
//devuelve la lista de conexiones de la seccion
    public List<Seccion> obtenerVecinos(Seccion s) {
        return conexiones.get(s.getNombre());
    }
//a modo de debug no se utiliza
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
        for (Seccion s : secciones) {
            imprimiVecinos(s);
        }
    }

    public List<Seccion> getAllSeccion() {
        return secciones;
    }

    public Seccion getFirstSeccion() {
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

    public void cargaRecorridoGuernica() {
        Bobina bobina1 = new Bobina("a1");
        Bobina bobina2 = new Bobina("b2");
        Bobina bobina3 = new Bobina("c3");
        Bobina bobina4 = new Bobina("d4");
        Bobina bobina5 = new Bobina("e5");
        Bobina bobina6 = new Bobina("f6");
        Bobina bobina7 = new Bobina("g7");
        Bobina bobina8 = new Bobina("h8");
        Bobina bobina9 = new Bobina("i9");

        Semaforo semaforo1 = new Semaforo();
        Semaforo semaforo2 = new Semaforo();
        Semaforo semaforo3 = new Semaforo();
        Semaforo semaforo4 = new Semaforo();
        Semaforo semaforo5 = new Semaforo();
        Semaforo semaforo6 = new Semaforo();
        Semaforo semaforo7 = new Semaforo();
        Semaforo semaforo8 = new Semaforo();
        Semaforo semaforo9 = new Semaforo();

        Seccion seccion1 = new Seccion(bobina1, semaforo1);
        Seccion seccion2 = new Seccion(bobina2, semaforo2);
        Seccion seccion3 = new Seccion(bobina3, semaforo3);
        Seccion seccion4 = new Seccion(bobina4, semaforo4);
        Seccion seccion5 = new Seccion(bobina5, semaforo5);
        Seccion seccion6 = new Seccion(bobina6, semaforo6);
        Seccion seccion7 = new Seccion(bobina7, semaforo7);
        Seccion seccion8 = new Seccion(bobina8, semaforo8);
        Seccion seccion9 = new Seccion(bobina9, semaforo9);

        agregarSeccion(seccion1);
        agregarSeccion(seccion2);
        agregarSeccion(seccion3);
        agregarSeccion(seccion4);
        agregarSeccion(seccion5);
        agregarSeccion(seccion6);
        agregarSeccion(seccion7);
        agregarSeccion(seccion8);
        agregarSeccion(seccion9);
        //se agrega lo que quieras
        //agregarSeccion(seccion4);

        setSiguiente(seccion1, seccion2);
        setSiguiente(seccion2, seccion3);
        //modo loop
        setSiguiente(seccion3, seccion4);
        setSiguiente(seccion4, seccion5);
        setSiguiente(seccion5, seccion6);
        setSiguiente(seccion6, seccion7);
        setSiguiente(seccion7, seccion8);
        setSiguiente(seccion8, seccion9);

    }

    public void cargaRecorridoLugano() {
        Bobina bobina1 = new Bobina("ab-2");
        Bobina bobina3 = new Bobina("c5");
        Bobina bobina2 = new Bobina("v4-2");
        Bobina bobina4 = new Bobina("z");

        Semaforo semaforo1 = new Semaforo();
        Semaforo semaforo2 = new Semaforo();
        Semaforo semaforo3 = new Semaforo();
        Semaforo semaforo4 = new Semaforo();

        Seccion seccion1 = new Seccion(bobina1, semaforo1);
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
        //modo loop si haces esto se crashea por memoria
       // setSiguiente(seccion3, seccion1);

        imprimiVecinos(seccion1);
        imprimiVecinos(seccion2);
        imprimiVecinos(seccion3);
    }
    
    public List<Seccion> getRecorridoGrafo(Seccion current) {
        // Obtener el recorrido de las secciones basado en el grafo
        List<Seccion> secciones = getAllSeccion();
        List<Seccion> recorrido = new ArrayList<>();
        
        if (!secciones.isEmpty()) {
            while (current != null) {
                
                recorrido.add(current);
                List<Seccion> vecinos = obtenerVecinos(current);
                
                if (!vecinos.isEmpty()) {
                    // Avanzar al siguiente vecino en este caso el primero
                    //(se puede ajustar si se desea segun estado de semaforo)
                    current = vecinos.get(0); 
                } else {
                    break;
                }
            }
        }
        return recorrido;
    }
}
