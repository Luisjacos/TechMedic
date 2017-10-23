import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This version of the TwoWaySerialComm example makes use of the 
 * SerialPortEventListener to avoid polling.
 *
 */
public class SerialServer implements Runnable
{
 private Llamado llamado;

 public SerialServer(Llamado llamado)
    {
        super();
        this.llamado = llamado;
    }
    
    public void run() {        
        try
        {
            connect("/dev/ttyUSB0");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                               
              // (new Thread(new SerialWriter(out))).start();
                
                serialPort.addEventListener(new SerialReader(in, llamado));
                serialPort.notifyOnDataAvailable(true);

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private Llamado llamado;
        private byte[] buffer = new byte[1024];
        
        public SerialReader ( InputStream in, Llamado llamado )
        {
            this.in = in;
            this.llamado = llamado;            
        }
        
        public void serialEvent(SerialPortEvent arg0) {
            int data;
            int cama;
            int piso;
            int letra;
            int tipo;
            String numCama;
            try
            {             
                
                data=in.read(); 
                
                if(data==0) {
                    data=in.read();
                
                    if(data==4) {
                        cama=in.read();                                                    
                        letra=in.read();                           
                        piso=in.read();                           
                        tipo=in.read();
                        data=in.read();
                        cama = piso *100 + cama;
			if(cama>=101 && cama<=122){                        
                        if(letra=='A')
                            numCama = Integer.toString(cama)+"A";
                        else if(letra=='B')
                            numCama = Integer.toString(cama)+"B";
                        else
                            numCama = Integer.toString(cama);
                        llamado.setNumCama(numCama);
                        if((tipo==1)||(tipo==3))
                                llamado.setTipoLlamado("VERDE");
                        else if((tipo==2)||(tipo==4))
                                llamado.setTipoLlamado("AZUL");
                        else if((tipo==5))
                                llamado.setTipoLlamado("AMARILLO");
                        else if((tipo==6)||(tipo==7))
                                llamado.setTipoLlamado("CANCELAR");
                        else if(tipo==8)
                                llamado.setTipoLlamado("CANCELARAMARILLO");
                        System.out.println(llamado.getNumCama());
                        System.out.println(llamado.getTipoLlamado());
                        llamado.nuevoLlamado();    
			}                    
                    }
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }                  
    }

    }
    
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                 //   this.out.write(119);
                 //   this.out.write(0);
                 //   this.out.write(3);
                    this.out.write(15);
                    this.out.write(0);
                    this.out.write(0);
                    this.out.write(0);
                    this.out.write(3);
                //    this.out.write(58);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }            
        }
    }         
    }
