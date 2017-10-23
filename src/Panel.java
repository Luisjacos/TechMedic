
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Panel extends JPanel implements Observer {

     public enum Tipo {
        BLANCO, AZUL, AMARILLO, VERDE, NEGRO
    }
    
    Observable llamado;
    Tipo tipoLlamado = Tipo.BLANCO;
    Tipo tipoAux = Tipo.BLANCO;
    String nomCama;
    JLabel tipo;
    JLabel cama;
    JLabel nombre;
    JLabel empresa;
    boolean activoAzul = false;
    boolean activoAmarillo = false;
    boolean activoVerde = false;
    boolean activoNegro = false;
    static boolean play=false;
    
    Timer cronometro;
   
    
    public Panel (String cama, String nombre, String empresa, Observable llamado) {
        super();    
        this.llamado = llamado;
        llamado.addObserver(this);
        tipo = new JLabel("NINGUNO");
        this.cama = new JLabel(cama,SwingConstants.LEFT);
        this.nombre = new JLabel(nombre,SwingConstants.LEFT);
        this.empresa = new JLabel(empresa,SwingConstants.LEFT);
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        setLayout(layout);
        this.cama.setPreferredSize(new Dimension(85,60));
        this.cama.setFont( new Font("Haettenschweiler",Font.PLAIN,57));
        this.nombre.setPreferredSize(new Dimension(390,60));
        this.nombre.setFont( new Font("Haettenschweiler",Font.ITALIC,40));
        this.empresa.setPreferredSize(new Dimension(185,60));
        this.empresa.setFont( new Font("Haettenschweiler",Font.ITALIC,40));
        add(this.cama);
        add(this.nombre);
        add(this.empresa);
        //this.setOpacity(0.55f);
        cronometro = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent even) {                        
                        if(tipoAux==Tipo.BLANCO) {
                            tipoAux = tipoLlamado;                           
                        }
                        else {
                            tipoAux = Tipo.BLANCO;                            
                        }
                        repaint();
                    }
                });
        setOpaque(false); 
        setPreferredSize(new Dimension(740,65));
        //setBorder(BorderFactory.createLineBorder(Color.BLUE));
        setBorder( BorderFactory.createRaisedBevelBorder());        
    }
    
    public void paintComponent(Graphics g) {       
        if (g instanceof Graphics2D) {                    
            int R =255;
            int G=255;
            int B=255;

            if(tipoAux==Tipo.AZUL){
                R = 0;
                G = 128;
                B = 255;
            }
            else if(tipoAux==Tipo.VERDE){
                R = 0;
                G = 255;
                B = 128;
		}
            else if(tipoAux==Tipo.AMARILLO){
		R=255;
	    	G=255;
	    	B=51;
            }
            else if(tipoAux==Tipo.NEGRO) {
                R=0;
	    	G=0;
	    	B=0;
            }                        
                    Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0), 0.0f, getHeight(), new Color(R, G, B, 255), true);
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
            
            
    }
}
    
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Llamado) {
            Llamado llamado = (Llamado) o;
  //          tipoLlamado = llamado.getTipoLlamado();
            nomCama = llamado.getNumCama();            
                        
            if(this.cama.getText().equals(nomCama)){
                if(llamado.getTipoLlamado().equals("AZUL")){
                    activoAzul = true;	
		    SoundPlayer2.playSound();                   
                }
                else if(llamado.getTipoLlamado().equals("AMARILLO")) {
                    activoAmarillo = true;
			SoundPlayer.playSound();
                }
                else if(llamado.getTipoLlamado().equals("VERDE")) {
                    activoVerde = true;
                    SoundPlayer.playSound();
                }
                else if(llamado.getTipoLlamado().equals("NEGRO")) {
                    activoNegro = true;
                }
                else if(llamado.getTipoLlamado().equals("CANCELAR")) {
                    activoAzul = false;
                    activoVerde = false;
		    SoundPlayer2.stopSound();
                }
                else if(llamado.getTipoLlamado().equals("CANCELARAMARILLO")) {
                    activoAmarillo = false;
                }
                
                if(activoAzul) {
                    tipoLlamado = Tipo.AZUL;
                }
                else if(activoAmarillo) {
                    tipoLlamado = Tipo.AMARILLO;
                }
                else if(activoVerde) {
                    tipoLlamado = Tipo.VERDE;
                }                
                else if(activoNegro) {                    
                        tipoLlamado = Tipo.NEGRO;         
                        tipoAux = Tipo.NEGRO;
                }
                else 
                    tipoAux = Tipo.BLANCO;
                    repaint();
                    
                }
                if(activoAzul||activoVerde||activoAmarillo)  
                    cronometro.start();
                else
                    cronometro.stop();
            }
            
    }

    public void setNombre(String nombre){
        this.nombre.setText(nombre);        
    }
    public void setEmpresa(String empresa){        
        this.empresa.setText(empresa);
    }
    public void setCama(String cama){        
        this.cama.setText(cama);
    }
}
