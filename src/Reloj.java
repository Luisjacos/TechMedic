
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Esteban
 */
public class Reloj extends Observable {
    private int segundos;
    private int minutos;
    private int horas;
    
    public Reloj () {
        System.out.println("Reloj Creado");
        
    }
    
    public void cambioReloj () {
        setChanged();
        notifyObservers();
    }
    
    public int getSeconds() {
        return segundos;
    }
    
    public int getMinutes() {
        return minutos;
    }
    
    public int getHoras() {
        return horas;
    }
    
    public void setSeconds(int s) {
        segundos = s;
    }
    
    public void setMinutes(int m) {
        minutos = m;
    }
    
    public void setHoras(int h) {
        horas = h;
    }
                
}
