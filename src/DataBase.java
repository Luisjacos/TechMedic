
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DataBase {
    
    static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/NurseCall";
    Connection connection;
    PreparedStatement statement;
    
    public DataBase() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, "techmedic", "");
            statement = connection.prepareStatement("INSERT INTO Events (tipoLlamado, habitacion, fechaInicio,fechaFinal,tiempoInicio,tiempoFinal) VALUES (?,?,?,?,?,?)");
            
            String fe1 = String.valueOf(LocalDateTime.now().getYear());
            String fe2 = String.valueOf(LocalDateTime.now().getMonthValue());
            String fe3 = String.valueOf(LocalDateTime.now().getDayOfMonth());
            String st3 = fe1 + "-" + fe2 + "-" + fe3;            
            String ti1 = String.valueOf(LocalDateTime.now().getHour());
            String ti2 = String.valueOf(LocalDateTime.now().getMinute());
            String ti3 = String.valueOf(LocalDateTime.now().getSecond());
            String st5 = ti1 + ":" + ti2 + ":" + ti3;
            String ff1 = String.valueOf(LocalDateTime.now().getYear());
            String ff2 = String.valueOf(LocalDateTime.now().getMonthValue());
            String ff3 = String.valueOf(LocalDateTime.now().getDayOfMonth());
            String st4 = fe1 + "-" + fe2 + "-" + fe3;
            String tf1 = String.valueOf(LocalDateTime.now().getHour());
            String tf2 = String.valueOf(LocalDateTime.now().getMinute());
            String tf3 = String.valueOf(LocalDateTime.now().getSecond());
            String st6 = tf1 + ":" + tf2 + ":" + tf3;

            statement.setString(1, "AZUL");
            statement.setString(2, "315");
            statement.setString(3, st3);
            statement.setString(4, st4);
            statement.setString(5, st5);
            statement.setString(6, st6);
            statement.executeUpdate();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    
    
    public static void main (String[] args) {
        DataBase application = new DataBase();
    }
    
}
