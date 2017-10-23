
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

public class Conexion extends JFrame implements Observer {
    
    JLabel aux;
    Panel panel;
    JPanel espacio;
    
    Observable observable;
    static final String DATABASE_URL = "jdbc:oracle:thin:@172.28.200.4:1521:SIOFHSP";
    Connection connection;
    PreparedStatement Censo;
    ResultSet rs;
    String cad1="";
    String cad2="";
    String cad3="";
    String cad4="";
    private Image background;
    private Image grande;

    private PanelListaEventos panelListaEventos;
    private NuevaListaEventos listaEventos;
    
    private JPanel panelVideo;
    private JPanel panelNombres;
    private Panel[] panelesArray;
    private Timer cronometro;
    
    public Conexion (Observable observable) {
        ((JPanel)getContentPane()).setOpaque(false); 
        try {
            //File file= new File("src/Imagenes/GF11.jpg");
            //System.out.println(file.getPath());
            //System.out.println(file.getAbsolutePath());
            background=ImageIO.read(Conexion.class.getResource("/Imagenes/FHSP.jpg"));
            //background = ImageIO.read(new File("src/Imagenes/GF11.jpg"));
            grande = background.getScaledInstance(1924, 1085, Image.SCALE_DEFAULT);
		//grande = background;
        }
        catch(IOException ex) {
            
            
            
        }
        espacio = new JPanel();
        espacio.setPreferredSize(new Dimension(1924,140));
        ImageIcon uno=new ImageIcon(grande);
        JLabel fondo= new JLabel(); 
        fondo.setIcon(uno); 
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER); 
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
        //FlowLayout layout = new FlowLayout();
        

        this.observable = observable;
        observable.addObserver(this);
        
        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT,20,5);
        setLayout(null);
        //espacio.setOpaque(false);
        //add(espacio);
	
	panelNombres=new JPanel();
	panelNombres.setBounds(400,140,1490,845);
	panelNombres.setOpaque(false);
	
        listaEventos = new NuevaListaEventos(observable);
        panelListaEventos = new PanelListaEventos(listaEventos);	
	panelListaEventos.setBounds(80, 200, 400,700);	 
        add(panelListaEventos);

	SwingUtilities.invokeLater(new Runnable() 
 {
   
	public void run(){
      
       
	actualizarBase();
	panelesArray=new Panel[39];
        try {
            while(rs.next()){                
                if(rs.getString("PNOMBRE")!=null){
                    cad1=rs.getString("PNOMBRE");		    
                }
                else
                    cad1=" ";
                /*if(rs.getString("SNOMBRE")!=null){
                    cad2=rs.getString("SNOMBRE");
                }
                else
                    cad2=" ";*/
                if(rs.getString("PAPELLIDO")!=null){
                    cad3=rs.getString("PAPELLIDO");
                }
                else
                    cad3=" ";
                if(rs.getString("SAPELLIDO")!=null){
                    cad4=rs.getString("SAPELLIDO");
                }
                else
                    cad4=" ";
                String nombreCompleto =cad1+" "+cad3+" "+cad4;
                
                panelesArray[rs.getRow()] =  new Panel(rs.getString("CODCAMA"),nombreCompleto, rs.getString("NOMEMPRESA"), observable);
                panelNombres.add(panelesArray[rs.getRow()]);
            }
        }
        catch(SQLException ex) {}
      }
    });
	
        
        cronometro = new Timer (60000, new ActionListener() {
                public void actionPerformed(ActionEvent even) {
                    actualizarBase();                    
                    try {
                        while(rs.next()){                
                        if(rs.getString("PNOMBRE")!=null){
                            cad1=rs.getString("PNOMBRE");		    
                        }
                        else
                            cad1=" ";
                /*if(rs.getString("SNOMBRE")!=null){
                    cad2=rs.getString("SNOMBRE");
                }
                else
                    cad2=" ";*/
                        if(rs.getString("PAPELLIDO")!=null){
                            cad3=rs.getString("PAPELLIDO");
                        }
                        else
                            cad3=" ";
                        if(rs.getString("SAPELLIDO")!=null){
                            cad4=rs.getString("SAPELLIDO");
                        }
                        else
                        cad4=" ";
                        String nombreCompleto =cad1+" "+cad3+" "+cad4;
                
                panelesArray[rs.getRow()].setNombre(nombreCompleto);
                panelesArray[rs.getRow()].setEmpresa(rs.getString("NOMEMPRESA"));
                panelesArray[rs.getRow()].setCama(rs.getString("CODCAMA"));
                
               }
            }
            catch(SQLException ex) {}        
                }
            });
        
        cronometro.start();
	add(panelNombres);        
        setSize(1924,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setUndecorated(true);
        setVisible(true);        
    }
    
    public void actualizarBase(){
        try {
            connection = DriverManager.getConnection(DATABASE_URL, "CONSULTA", "CONSULT4");
            Censo = connection.prepareStatement("SELECT  SALUDSP.PCAMAS.CODPABELLON,SALUDSP.PCAMAS.CODCAMA, SALUDSP.RREFERENCIAS.NOMREFERENCIA, SALUDSP.MADMISIONES.NOHISTORIA,\n" +
            "        SALUDSP.MPACIENTES.PNOMBRE, SALUDSP.MPACIENTES.SNOMBRE, SALUDSP.MPACIENTES.PAPELLIDO, SALUDSP.MPACIENTES.SAPELLIDO,\n" +
            "        SALUDSP.MADMISIONES.FECHAINGRESO, SALUDSP.MEMPRESAS.NOMEMPRESA\n" +
            "FROM    SALUDSP.PCAMAS\n" +
            "        INNER JOIN SALUDSP.RREFERENCIAS ON (SALUDSP.PCAMAS.CODPABELLON = SALUDSP.RREFERENCIAS.CODREFERENCIA)\n" +
            "        LEFT JOIN SALUDSP.MADMISIONES ON (SALUDSP.PCAMAS.IDADMISION = SALUDSP.MADMISIONES.IDADMISION)\n" +
            "        LEFT JOIN SALUDSP.MPACIENTES ON (SALUDSP.MADMISIONES.NOHISTORIA = SALUDSP.MPACIENTES.NOHISTORIA)\n" +
            "        LEFT JOIN SALUDSP.MEMPRESAS ON (SALUDSP.MADMISIONES.IDEMPRESA = SALUDSP.MEMPRESAS.IDEMPRESA)\n" +
            "WHERE   SALUDSP.RREFERENCIAS.CODCLASEREFERENCIA = 18 AND SALUDSP.PCAMAS.CODPABELLON =14\n" +
            "        AND SALUDSP.PCAMAS.ACTIVO = 1\n" +
            "        AND SALUDSP.PCAMAS.IDSEDE <> 99\n" +
            "ORDER BY SALUDSP.PCAMAS.CODPABELLON, SALUDSP.PCAMAS.CODCAMA");
            rs = Censo.executeQuery();
            
        }
        
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Override
    public void update(Observable obs, Object arg) {        
        if(obs instanceof Llamado) {
            Llamado llamado = (Llamado) obs;           
        }
    }
    
    public static void main (String args[]) {
                
        Llamado llamado = new Llamado();
        Conexion aplicacion = new Conexion(llamado);
        Thread t = new Thread(new SerialServer(llamado));
        t.start();
        
 
    }   
    
}
