
public class Rutina {
    private int idRutina;
    private int IdEntrenador;
    private String entrenador;
    private int idMaquina;
    private String Maquina;
    private int duracion;
    private String descripcion;

    public Rutina() {
    }

    public Rutina(int idRutina, int IdEntrenador, String entrenador, int idMaquina, String Maquina, int duracion, String descripcion) {
        this.idRutina = idRutina;
        this.IdEntrenador = IdEntrenador;
        this.entrenador = entrenador;
        this.idMaquina = idMaquina;
        this.Maquina = Maquina;
        this.duracion = duracion;
        this.descripcion = descripcion;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdEntrenador() {
        return IdEntrenador;
    }

    public void setIdEntrenador(int IdEntrenador) {
        this.IdEntrenador = IdEntrenador;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getMaquina() {
        return Maquina;
    }

    public void setMaquina(String Maquina) {
        this.Maquina = Maquina;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
