package grafo;

import com.google.gson.annotations.SerializedName;

public class Bobina {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("anterior")
    private Bobina anterior;

    @SerializedName("siguiente1")
    private Bobina siguiente1;

    @SerializedName("siguiente2")
    private Bobina siguiente2;

    // Constructor sin argumentos requerido por Gson
    public Bobina() {
    }

    // Constructor con nombre, opcional
    public Bobina(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bobina getAnterior() {
        return anterior;
    }

    public void setAnterior(Bobina anterior) {
        this.anterior = anterior;
    }

    public Bobina getSiguiente1() {
        return siguiente1;
    }

    public void setSiguiente1(Bobina siguiente1) {
        this.siguiente1 = siguiente1;
    }

    public Bobina getSiguiente2() {
        return siguiente2;
    }

    public void setSiguiente2(Bobina siguiente2) {
        this.siguiente2 = siguiente2;
    }
}
