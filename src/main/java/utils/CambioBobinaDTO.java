package utils;

public class CambioBobinaDTO {

    private String tipoMensaje;
    private String recorrido;
    private String identificadorBobina;
    private String numTren;

    // Constructores
    public CambioBobinaDTO() {
    }

    public CambioBobinaDTO(String tipoMensaje,String recorrido, String identificadorBobina, String numTren) {
        this.tipoMensaje=tipoMensaje;
        this.recorrido = recorrido;
        this.identificadorBobina = identificadorBobina;
        this.numTren = numTren;
    }

    // Getters y Setters

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

    public String getIdentificadorBobina() {
        return identificadorBobina;
    }

    public void setIdentificadorBobina(String identificadorBobina) {
        this.identificadorBobina = identificadorBobina;
    }

    public String getNumTren() {
        return numTren;
    }

    public void setNumTren(String numTren) {
        this.numTren = numTren;
    }
}
