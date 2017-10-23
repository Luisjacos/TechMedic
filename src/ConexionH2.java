import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ConexionH2 {
    
    static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/NurseCall";
    Connection connection;
    PreparedStatement insertarEvento;  
    
    public ConexionH2() {

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "techmedic", "");
            insertarEvento = connection.prepareStatement("INSERT INTO Events (tipoLlamado, habitacion, fechaInicio,fechaFinal,tiempoInicio,tiempoFinal) VALUES (?,?,?,?,?,?)");
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
    }
    
    public void ingresarEvento(Evento este) {
	System.out.println("DATABASE");
        String st1 = este.getTipoLlamado();
        String st2 = String.valueOf(este.getNumCama());
        String fe1 = String.valueOf(este.getTiempoInicio().getYear());
        String fe2 = String.valueOf(este.getTiempoInicio().getMonthValue());
        String fe3 = String.valueOf(este.getTiempoInicio().getDayOfMonth());
        String st3 = fe1 + "-" + fe2 + "-" + fe3;
        String ti1 = String.valueOf(este.getTiempoInicio().getHour());
        String ti2 = String.valueOf(este.getTiempoInicio().getMinute());
        String ti3 = String.valueOf(este.getTiempoInicio().getSecond());
        String st5 = ti1 + ":" + ti2 + ":" + ti3;
        String ff1 = String.valueOf(este.getTiempoFinal().getYear());
        String ff2 = String.valueOf(este.getTiempoFinal().getMonthValue());
        String ff3 = String.valueOf(este.getTiempoFinal().getDayOfMonth());
        String st4 = fe1 + "-" + fe2 + "-" + fe3;
        String tf1 = String.valueOf(este.getTiempoFinal().getHour());
        String tf2 = String.valueOf(este.getTiempoFinal().getMinute());
        String tf3 = String.valueOf(este.getTiempoFinal().getSecond());
        String st6 = tf1 + ":" + tf2 + ":" + tf3;
        try {
            insertarEvento.setString(1, st1);
            insertarEvento.setString(2, st2);
            insertarEvento.setString(3, st3);
            insertarEvento.setString(4, st4);
            insertarEvento.setString(5, st5);
            insertarEvento.setString(6, st6);
            insertarEvento.executeUpdate();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
