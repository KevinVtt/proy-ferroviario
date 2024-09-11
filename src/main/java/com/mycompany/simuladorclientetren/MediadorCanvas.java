package com.mycompany.simuladorclientetren;

import grafo.Seccion;
import grafo.Semaforo;

public interface MediadorCanvas {
    
    void actualizarSeccion(Seccion seccion);
    void actualizarSemaforo(Semaforo semaforo);
}
