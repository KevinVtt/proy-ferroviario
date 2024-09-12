package utils;

import com.google.gson.annotations.SerializedName;
import grafo.Bobina;
import java.util.Map;

public class RespuestaBobinas {

    @SerializedName("recorrido")
    private String recorrido;

    @SerializedName("bobinas")
    private Map<String, Bobina> bobinas;

    // Constructor
    public RespuestaBobinas(String recorrido, Map<String, Bobina> bobinas) {
        this.recorrido = recorrido;
        this.bobinas = bobinas;
    }

    // Getters and Setters
    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }

    public Map<String, Bobina> getBobinas() {
        return bobinas;
    }

    public void setBobinas(Map<String, Bobina> bobinas) {
        this.bobinas = bobinas;
    }
}
