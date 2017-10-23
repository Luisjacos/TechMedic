
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {
 
    static boolean sonando = false;
    static AudioInputStream audioStream;
    static AudioFormat format;
    static DataLine.Info info;
    static Clip audioClip;

    
    
    static {
        URL url=SoundPlayer.class.getResource("Imagenes/notificacion.wav");
        try {
            InputStream is = url.openStream();
            BufferedInputStream bis = new BufferedInputStream(is);                
            audioStream = AudioSystem.getAudioInputStream(bis);
            format = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioClip.addLineListener(new SoundListener());
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("ERROR1");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("ERROR3");
        }
    }

    
       
    static class SoundListener implements LineListener {

        @Override
        public void update(LineEvent event) {
            LineEvent.Type type = event.getType();
            if (type == LineEvent.Type.START) {
                System.out.println("START");
                sonando = true;
            }
            if (type == LineEvent.Type.STOP) {
                System.out.println("STOP");               
                sonando = false;
            } 
        }
        
    }
    
    public static void playSound() {
        
        if(sonando==true) {
            System.out.println("Ya estoy sonando");
            return;
        }
        else {
            System.out.println("Sonando");
            audioClip.loop(1);
        } 
    }
         
}
