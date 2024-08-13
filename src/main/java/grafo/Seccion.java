package grafo;

public class Seccion {
    Bobina bobinaDefault;
    Bobina bobinaExtra=null;
    Semaforo semaforo;
    String nombre="";
    
    public Seccion(Bobina d,Semaforo s){
        this.bobinaDefault=d;
        this.semaforo=s;
        this.nombre=bobinaDefault.getNombre();
    }

    public Bobina getBobinaDefault() {
        return bobinaDefault;
    }

    public void setBobinaDefault(Bobina bobinaDefault) {
        this.bobinaDefault = bobinaDefault;
    }

    public Bobina getBobinaExtra() {
        return bobinaExtra;
    }

    public void setBobinaExtra(Bobina bobinaExtra) {
        this.bobinaExtra = bobinaExtra;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}


