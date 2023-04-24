
public class Maquina {
    
    private int idMaquina;
    private String nombre;
    private int estado;
    private String desEstado;

    public Maquina() {
    }

    public Maquina(int idMaquina, String nombre, int estado, String desEstado) {
        this.idMaquina = idMaquina;
        this.nombre = nombre;
        this.estado = estado;
        this.desEstado = desEstado;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDesEstado() {
        return desEstado;
    }

    public void setDesEstado(String desEstado) {
        this.desEstado = desEstado;
    }
    
    
    
    
}
