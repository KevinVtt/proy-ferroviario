package utils;

import com.google.gson.annotations.SerializedName;
import grafo.Bobina;
import java.util.Map;

public class RespuestaBobinas {

    @SerializedName("tipoMensaje")
    private String tipoMensaje;
    
    @SerializedName("recorrido")
    private String recorrido;

    @SerializedName("bobinas")
    private Map<String, Bobina> bobinas;
    
    

    // Constructor
    public RespuestaBobinas(String tipoMensaje,String recorrido, Map<String, Bobina> bobinas) {
        this.tipoMensaje=tipoMensaje;
        this.recorrido = recorrido;
        this.bobinas = bobinas;
    }

    // Getters and Setters

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
    
    
    
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

