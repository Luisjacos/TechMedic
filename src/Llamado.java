import java.util.Observable;

public class Llamado extends Observable {
    
    private String numCama;
    private String tipoLlamado;
    
    public Llamado () {
        
    }
    
    public void nuevoLlamado () {        
        setChanged();
        notifyObservers();
    }
    
    public Llamado (String numCama, String tipoLlamado) {
        this.numCama = numCama;
        this.tipoLlamado = tipoLlamado;
    }
    
    public String getNumCama() {
        return numCama;
    }
    
    public String getTipoLlamado() {
        return tipoLlamado;
    }
    
    public void setNumCama (String numCama) {
        this.numCama = numCama;
    }
    
    public void setTipoLlamado (String tipoLlamado) {
        this.tipoLlamado = tipoLlamado;
    }
}