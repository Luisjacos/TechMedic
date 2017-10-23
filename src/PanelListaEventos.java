
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class PanelListaEventos extends JPanel implements Observer {
    
    Observable listaEventos;
    JLabel cama;
    JLabel tiempo;
    Evento evento;
    Timer cronometro;
    int hor = 0;
    int min = 0;
    int seg = 0;    
    
    public PanelListaEventos (Observable listaEventos) {
        super();
        this.listaEventos = listaEventos;
        listaEventos.addObserver(this);
        evento = new Evento();
        //FlowLayout layoout = new FlowLayout();
        //BorderLayout layout = new BorderLayout();
        cama = new JLabel(" ");
        setBackground(new Color(255,255,255,255));
        setOpaque(false);
        setLayout(null);
        cama.setFont(new Font("serif", Font.BOLD, 120));
        cama.setBounds(15, 15, 300, 300);        
        add(cama);

        tiempo = new JLabel("");
        tiempo.setFont(new Font("serif", Font.PLAIN, 40));
        tiempo.setText("00:00:00");        
        tiempo.setBounds(70,500,200,100);
	add(tiempo);
        
        cronometro = new Timer (1000, new ActionListener() {
                public void actionPerformed(ActionEvent even) {                                        
                    seg = seg + 1;
                    if (seg==60) {
                        min = min + 1;
                        seg = 0;
                        if(min==60) {
                            hor = hor + 1;
                            min = 0;
                        }
                    }
                    //System.out.println(hor + ":" + min + ":" + seg);
                    if(evento.getTipoLlamado().equals("VERDE"))
                        cama.setForeground(new Color(27,201,14));
                    else if (evento.getTipoLlamado().equals("AZUL"))
                        cama.setForeground(Color.blue);
                    else if (evento.getTipoLlamado().equals("AMARILLO"))
                        cama.setForeground(Color.yellow);
                    else
                        cama.setForeground(Color.black);
		    if(evento.getNumCama().length()==3)
			cama.setFont(new Font("serif", Font.BOLD, 120));
		    else if(evento.getNumCama().length()==4)
			cama.setFont(new Font("serif", Font.BOLD, 90));
                    cama.setText(evento.getNumCama());
                    tiempo.setText(String.format("%02d:%02d:%02d", hor, min, seg));                   
                }
            });
         
         
    }
    
    public void update(Observable obs, Object arg) {
        if(obs instanceof NuevaListaEventos) {
            NuevaListaEventos listaEventos = (NuevaListaEventos) obs;            
            evento = listaEventos.getultimoEvento();                        
                hor = evento.getReloj().getHoras();
                min = evento.getReloj().getMinutes();
                seg = evento.getReloj().getSeconds();               
                cronometro.start();                                 
            if(evento.getTipoLlamado().equals("VACIO")) {
                cama.setForeground(Color.BLACK);
                cama.setText(" ");
                tiempo.setText("00:00:00");
                cronometro.stop();
            }
            else if(evento.getTipoLlamado().equals("VERDETIEMPO"))
                evento.setTipoLlamado("VERDE");
            else if(evento.getTipoLlamado().equals("AMARILLOTIEMPO"))
                evento.setTipoLlamado("AMARILLO");
            else if(evento.getTipoLlamado().equals("AZULTIEMPO"))
                evento.setTipoLlamado("AZUL");
            else if(evento.getTipoLlamado().equals("CANCELAR")){}                      
        }
    }
    
 }
    

