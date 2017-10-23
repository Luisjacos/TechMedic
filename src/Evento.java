
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.util.Calendar.MONTH;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Esteban
 */
public class Evento {
    
    private String numCama;
    private int numIntentos;
    private LocalDateTime tiempoInicio;
    private LocalDateTime tiempoFinal;
    private Temporizador reloj;
    private String tipoLlamado;
    private long seg;
    
    public Evento() {
        reloj = new Temporizador();
    }

    public long getSeg() {
        return seg;
    }
    
    public String getNumCama() {
        return numCama;
    }

    public void setNumCama(String numCama) {
        this.numCama = numCama;
    }

    public int getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        this.numIntentos = numIntentos;
    }

    public LocalDateTime getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(LocalDateTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public LocalDateTime getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(LocalDateTime tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }
    
    public void setTipoLlamado(String tipo) {
        this.tipoLlamado = tipo;
    }
    
    public String getTipoLlamado() {
        return tipoLlamado;
    }
    
    public void setReloj(int h, int m, int s) {
        reloj.setHoras(h);
        reloj.setMinutes(m);
        reloj.setSeconds(s);
    }
    
    public Temporizador getReloj() {
        return reloj;
    }
    
    public void actReloj() {
        LocalDateTime ahora = LocalDateTime.now();
        seg = ahora.toEpochSecond(ZoneOffset.UTC)-getTiempoInicio().toEpochSecond(ZoneOffset.UTC);        
        int h=(int)seg/3600;
        int m=((int)seg%3600)/60;
        int s=((int)seg%3600)%60;
        setReloj(h,m,s);
    }

    public String toString() {

	return numCama + tipoLlamado;
}
    
    
}
