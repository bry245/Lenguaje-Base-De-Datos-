
public class Reservacion {
    
    private int idReservacion;
    private String cedula;
    private int idSucursal;
    private int idRutina;
    private String fecha;
    private int monto;
    private String NombreCliente;
    private String sucursal;
    private String descripcion;
    private String Maquina;
    private String coach;
    private String Puesto;
    

    public Reservacion() {
    }

    public Reservacion(int idReservacion,String cedula, int idSucursal, int idRutina, String fecha, int monto, String NombreCliente, String sucursal, String descripcion, String Maquina, String coach, String Puesto) {
       this.idReservacion=idReservacion;
        this.cedula = cedula;
        this.idSucursal = idSucursal;
        this.idRutina = idRutina;
        this.fecha = fecha;
        this.monto = monto;
        this.NombreCliente = NombreCliente;
        this.sucursal = sucursal;
        this.descripcion = descripcion;
        this.Maquina = Maquina;
        this.coach = coach;
        this.Puesto = Puesto;
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }
    
        

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMaquina() {
        return Maquina;
    }

    public void setMaquina(String Maquina) {
        this.Maquina = Maquina;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    


    
}
