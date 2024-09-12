package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

    private static Grafo instancia;
    private List<Seccion> secciones;
    //utiliza list porque pueden ser varias conexiones
    private Map<String, List<Seccion>> conexiones;

    private Grafo() {
        secciones = new ArrayList<>();
        conexiones = new HashMap<>();
    }

    public static Grafo getInstancia() {
        if (instancia == null) {
            return instancia = new Grafo();
        }
        return instancia;
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

    public List<Seccion> getRecorridoGrafo(Seccion current) {
        List<Seccion> recorrido = new ArrayList<>();

        while (current != null) {
            recorrido.add(current);
            List<Seccion> vecinos = obtenerVecinos(current);

            if (!vecinos.isEmpty()) {
                // Utiliza el estado del semáforo para determinar el siguiente vecino
                if (current.getSemaforo().getEstado() && vecinos.size() > 0) {
                    current = vecinos.get(0); // Asumimos que el primer vecino es el correcto cuando el semáforo está en verde
                } else if (!current.getSemaforo().getEstado() && vecinos.size() > 1) {
                    current = vecinos.get(1); // Asumimos que el segundo vecino es el correcto cuando el semáforo está en rojo
                } else {
                    current = null; // No hay vecinos adecuados
                }
            } else {
                break;
            }
        }
        return recorrido;
    }

    public void inicializarRecorrido(List<Bobina> bobinas) {
        // Inicializa las secciones
        for (Bobina bob : bobinas) {
            Semaforo semaforo = new Semaforo();
            Seccion seccion = new Seccion(bob, semaforo);
            agregarSeccion(seccion);
        }

        // Conecta las secciones // es lo mismo recorrer secciones o bobinas 
        for (Bobina bob : bobinas) {
            Seccion seccionActual = getSeccion(bob.getNombre());
            if (seccionActual != null) {
                Seccion siguiente1 = bob.getSiguiente1() != null ? getSeccion(bob.getSiguiente1().getNombre()) : null;
                Seccion siguiente2 = bob.getSiguiente2() != null ? getSeccion(bob.getSiguiente2().getNombre()) : null;
                
                // Conectar siempre, independientemente del estado del semáforo
                if (siguiente1 != null) {
                    setSiguiente(seccionActual, siguiente1);
                }
                
                if (siguiente2 != null) {
                    setSiguiente(seccionActual, siguiente2);
                }
            }
        }
    }
}
