




import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.Codec;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;
import javax.media.format.VideoFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Video extends JFrame{
    //INICIALIZAMOS VARIABLES NECESARIAS DEL REPRODUCTOR
    //PANEL
    //JPanel panel;
    //REPRODUCTOR
    Player mediaPlayer;
    //COMPONENTE DE VIDEO
    Component video;
    //COMPONENTE DE CONTROLES
    Component controles;
    //BOOLEANO
    public boolean play = true;
    
   
    public Video(){
        
        setSize(1490,845);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        reproducir();
        
    }    
     
    public void reproducir(){
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());;
        //AGREGAMOS EL CONTENEDOR A NUESTRA VENTANA
        add(panel); 
         if(play == true){
            try {
                //COMPROBAMOS SI NO HABIA INSTANCIAS DE PLAYER REPRODUCIENDO
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                }
                // OBTENEMOS LA RUTA DE LA LISTA DE ARCHIVOS EN BASE A LA POSICION ACTUAL
                URL url = new URL("file://C:\\Users\\ThinkPad X240\\Documents\\NetBeansProjects\\video3\\src\\Video\\AFF.mpg");
                //CODECS DE VIDEO
                String jffmpegVideoDecoder = "net.sourceforge.jffmpeg.VideoDecoder";
            
            
            Codec codecVideo = (Codec) Class.forName(jffmpegVideoDecoder).newInstance();
            
            
            // Agregamos los codec al PlugInManager 
            PlugInManager.addPlugIn(jffmpegVideoDecoder,
                    codecVideo.getSupportedInputFormats(),
                    new Format[]{new VideoFormat("net.sourceforge.jffmpeg.VideoDecoder")},
                    PlugInManager.CODEC);
                // CONVERTIMOS LA RUTA A RECURSO MEDIALOCATOR
                MediaLocator ml = new MediaLocator(url);
                //CREAMOS EL PLAYER Y LE ASIGNAMOS EL MEDIA LOCATOR
                mediaPlayer = Manager.createRealizedPlayer( ml );
                // OBTENEMOS LOS COMPONENTES DE VIDEO Y CONTROLES DEL REPRODUCTOR
                video = mediaPlayer.getVisualComponent();
                controles = mediaPlayer.getControlPanelComponent();
                //ELIMINAMOS CUALQUIER COMPONENTE QUE EXISTA EN EL CONTENDOR
                panel.removeAll();
                //AGREGAMOS LOS COMPONENTES DE VIDEO Y CONTROLES AL CONTENEDOR
                if ( video != null )
                    panel.add("Center", video );
                if ( controles != null )
                    panel.add( "South", controles );
                // DAMOS PLAY AL REPRODUCTOR
                mediaPlayer.start();
                // ACTUALIZAMOS LA INTERFAZ DEL CONTENEDOR PARA ACTUALIZAR CAMBIOS
                panel.updateUI();
                
                // EVENTOS DEL REPRODUCTOR
               
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException | NoPlayerException | CannotRealizeException ex) { 
            Logger.getLogger(Video.class.getName()).log(Level.SEVERE, null, ex);
        }
         }else
             play=false;
          mediaPlayer.addControllerListener(new ControllerListener(){
                    @Override
                    public void controllerUpdate(ControllerEvent ce) {
                        // SI EL EVENTO ES UNA INSTANCIA DE ENDOFMEDIA ES QUE TERMINO DE REPRODUCIR
                        if(ce instanceof EndOfMediaEvent) {
                            //PARAMOS EL VIDEO QUE ESTA EN REPRODUCCION
                            //mediaPlayer.setMediaTime(new Time(0));
                            mediaPlayer.stop();
                            mediaPlayer.deallocate();
                            panel.removeAll();
                            System.exit(0);
                            
                                                      
                        }
                    }
                });       
        }
    
     public static void main(String[] args) {
        
        
        Video V = new Video();
    }
    
   
}