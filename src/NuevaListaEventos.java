
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;
import java.sql.SQLException;

public class NuevaListaEventos extends Observable implements Observer {
    ArrayList <Evento> listaVerdes;
    ArrayList <Evento> listaAzules;
    ArrayList <Evento> listaAmarillos;
    Observable observable;
    Evento eventoMostrar;
    Timer cronometro;
    ConexionH2 dataBase;
    int verdeActual;
    int azulActual;
    int amarilloActual;
    
    
    public NuevaListaEventos (Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
        listaVerdes = new ArrayList();
        listaAmarillos = new ArrayList();
        listaAzules = new ArrayList();        
        eventoMostrar = new Evento();
        cronometro = new Timer(10000, new ActionListener() {
           public void actionPerformed(ActionEvent even) {
               if(!listaAzules.isEmpty()) {                    
                       azulActual = azulActual+1;
                       if(listaAzules.size()-1<azulActual)
                           azulActual = 0;;
                       eventoMostrar = listaAzules.get(azulActual);
                       eventoMostrar.setTipoLlamado("AZULTIEMPO");
                       eventoMostrar.actReloj();                       
                       nuevoEvento();
                }                                                                    
               else if(!listaAmarillos.isEmpty()) {
                  amarilloActual = amarilloActual+1;
                       if(listaAmarillos.size()-1<amarilloActual)
                           amarilloActual = 0;
                       eventoMostrar = listaAmarillos.get(amarilloActual);
                       eventoMostrar.setTipoLlamado("AMARILLOTIEMPO");
                       eventoMostrar.actReloj();                       
                       nuevoEvento();
                }                               
               else if(!listaVerdes.isEmpty()) {                    
                       verdeActual = verdeActual+1;
                       if(listaVerdes.size()-1<verdeActual)
                           verdeActual=0;
                       eventoMostrar = listaVerdes.get(verdeActual);
                       eventoMostrar.setTipoLlamado("VERDETIEMPO");
                       eventoMostrar.actReloj();
                       nuevoEvento();
                   }                                           
           } 
        });
        dataBase = new ConexionH2();
        cronometro.start();
    }
    
    @Override
    public void update(Observable obs, Object arg) {
        if(obs instanceof Llamado) {
            Llamado llamado = (Llamado) obs;
            Evento actual = new Evento();
            actual.setNumCama(llamado.getNumCama());
            actual.setNumIntentos(1);
            actual.setTiempoInicio(LocalDateTime.now());
            actual.setTipoLlamado(llamado.getTipoLlamado());
            actual.setReloj(0, 0, 0);
            if(actual.getTipoLlamado().equals("CANCELAR")) {
                int v = encontrarLlamado(actual.getNumCama(),listaVerdes);                                                         
                int az = encontrarLlamado(actual.getNumCama(),listaAzules);                               
                //int am = encontrarLlamado(actual.getNumCama(),listaAmarillos);                                                 
                if(listaAzules.isEmpty()&&listaAmarillos.isEmpty()&&listaVerdes.isEmpty()) {
                    System.out.println("VACIO");
                    actual.setTipoLlamado("VACIO");
                    setultimoEvento(actual);
                }
                else 
                    reordenar();
            }
                else if(actual.getTipoLlamado().equals("CANCELARAMARILLO")) {
                    int am = encontrarLlamado(actual.getNumCama(),listaAmarillos);
                    if(listaAzules.isEmpty()&&listaAmarillos.isEmpty()&&listaVerdes.isEmpty()) {
                        System.out.println("VACIO");
                        actual.setTipoLlamado("VACIO");
                        setultimoEvento(actual);
                    }
                
                else {
                    reordenar();
                    }
                }               
            
            else {
                
                if(actual.getTipoLlamado().equals("VERDE")) {   
                                    
                    if(!encontrar(actual.getNumCama(),listaVerdes)) {
                        listaVerdes.add(actual);
			                                    
                        if(listaVerdes.size()==1)
                            verdeActual = 0;
                        else
                            verdeActual += 1;
                    
                        if(listaAzules.isEmpty()) {
                            if(listaAmarillos.isEmpty()) {
                                setultimoEvento(actual);
                            }
                        }
                    }
                }
                if(actual.getTipoLlamado().equals("AZUL")) { 
                    if(!encontrar(actual.getNumCama(),listaAzules)) {
                        listaAzules.add(actual);
                        if(listaVerdes.size()==1)
                            azulActual = 0;
                        else
                            azulActual +=1;
                            setultimoEvento(actual);
                    }
                }
                if(actual.getTipoLlamado().equals("AMARILLO")) {                    
                    if(!encontrar(actual.getNumCama(),listaAmarillos)) {
                        listaAmarillos.add(actual);
                        if(listaAmarillos.size()==1)
                            amarilloActual = 0;
                        else
                            amarilloActual +=1;
                        if(listaAzules.isEmpty()) {
                            setultimoEvento(actual);
                        }
                    }
                }                                
            }
        }
	System.out.println(listaVerdes);
	System.out.println(listaAzules);
	System.out.println(listaAmarillos);
    }

    public int encontrarLlamado(String numCama, ArrayList<Evento> lista) {
        for(Evento este: lista) {            
            if(este.getNumCama().equals(numCama)) {   
		System.out.println("funcion encontrarLlamado");             
                este.setTiempoFinal(LocalDateTime.now());
              
                int index = lista.indexOf(este);
                lista.remove(este);
		System.out.println(este.getNumCama());
 
		dataBase.ingresarEvento(este);
                return index;
            }            
        }        
        return -1;
    }
    
    public boolean encontrar(String numCama, ArrayList<Evento> lista) {
        for(Evento este:lista) {
            if(este.getNumCama().equals(numCama))
                return true;
        }
        return false;
    }

    public void nuevoEvento() {
        setChanged();
        notifyObservers();
    }    
    
    public void reordenar() {
        if(!listaAzules.isEmpty()) {
            if(listaAzules.size()-1<azulActual)
                azulActual=0;
                eventoMostrar = listaAzules.get(azulActual);
                eventoMostrar.actReloj();
                eventoMostrar.setTipoLlamado("AZULTIEMPO");
                nuevoEvento();
        }
        else if(!listaAmarillos.isEmpty()) {
            if(listaAmarillos.size()-1<amarilloActual)
                amarilloActual=0;
                eventoMostrar = listaAmarillos.get(amarilloActual);
                eventoMostrar.setTipoLlamado("AMARILLOTIEMPO");
                eventoMostrar.actReloj();
                nuevoEvento();
        }
        else if(!listaVerdes.isEmpty()) {            
                if(listaVerdes.size()-1<verdeActual)
                verdeActual=0;
                eventoMostrar = listaVerdes.get(verdeActual);
                eventoMostrar.setTipoLlamado("VERDETIEMPO");
                eventoMostrar.actReloj();
                nuevoEvento();           
        }          
    }    
    
    public Evento getultimoEvento () {
        return eventoMostrar;
    }
    
    public void setultimoEvento(Evento evento) {
        eventoMostrar = evento;
        nuevoEvento();
    }
    
    public ArrayList<Evento> getListaVerdes() {
        return listaVerdes;
    }
    
    public ArrayList<Evento> getListaAzules() {
        return listaAzules;
    }
    
    public ArrayList<Evento> getListaAmarillos() {
        return listaAmarillos;
    }
}
