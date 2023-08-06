package Bdd;
import java.sql.DriverManager;
import java.sql.Connection;
import javax.swing.JOptionPane;
public class Conexion {
    
    private final String bdd = "jdbc:mysql://Localhost/proyectobiblioteca";
    private final String usu = "root";
    private final String clave = "";
    
    private final String obj = "com.mysql.jdbc.Driver"; //creo un objeto para almacenar la ruta del Driver de la conexion
    Connection con = null; //llamo a la clase Connection y creo un objeto
    
    public Conexion(){
        try {
            Class.forName(obj);
            con = DriverManager.getConnection(bdd, usu, clave);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, no se encontro el driver para la conexión", "Conexión a Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Connection conectar(){
        return con;
    }
    
    public void desconectar(){
        
    }
}
