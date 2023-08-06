package Desarrollo;
import Bdd.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Autor {
    private String Nom;
    private int Pais;
    private int TipoDeAutor;
    
    private final Conexion con = new Conexion(); //La estoy usando de forma global
    private PreparedStatement Ps; //La estoy usando de forma global
    ResultSet Rs; //hago un import a la clase ResultSet, la llamo y creo un obj 'Rs'
    DefaultTableModel contenido;
    
    public Autor(String Nom, int Pais, int TipoDeAutor) {
        this.Nom = Nom;
        this.Pais = Pais;
        this.TipoDeAutor = TipoDeAutor;
    }
    public Autor(){        
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public int getPais() {
        return Pais;
    }

    public void setPais(int Pais) {
        this.Pais = Pais;
    }

    public int getTipoDeAutor() {
        return TipoDeAutor;
    }

    public void setTipoDeAutor(int TipoDeAutor) {
        this.TipoDeAutor = TipoDeAutor;
    }
    
    public void Alta(){
        String carga = "INSERT INTO autor(Nombre,Pais,TipoDeAutor)VALUES(?,?,?)";
        try {
            Ps = con.conectar().prepareStatement(carga);//conecto a la Bdd e inserto los valores
            Ps.setString(1, this.Nom);
            Ps.setInt(2, this.Pais);
            Ps.setInt(3, this.TipoDeAutor);
            int rayito = Ps.executeUpdate();//ejecuto el rayito
            if (rayito > 0) {
                JOptionPane.showMessageDialog(null, "cargado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR !!, no pudo ingresar el autor" + "\n" + e);
        }
    }
    public void Modificar(int idAutor, String NuevoNombre){
        String cambio = "UPDATE autor SET Nombre = ? WHERE idAutor = ?";
        try {
            Ps = con.conectar().prepareStatement(cambio);
            Ps.setString(1, NuevoNombre);
            Ps.setInt(2, idAutor);
            int rayo = Ps.executeUpdate();
            if (rayo > 0) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error!!, el registro no pudo ser modificado" + e);
        }
    }
 /*   public DefaultListModel Mostrar(){
        DefaultListModel mostrarLista = new DefaultListModel();
        String mostrar = "select Nombre from autor order by Nombre"; //traigo de mysql esta consulta y la almaceno en una variable
        try {
            Ps = con.conectar().prepareStatement(mostrar);
            Rs = Ps.executeQuery(); //ejecuto el objeto Rs 
            while (Rs.next()) {// y hago un bucle para recorrer la lista
                mostrarLista.addElement(Rs.getString(1));//el dato cargado necesito que me lo muestre en la lista de la columna (1)
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la muestra de los nombres de autores" + e);
        }
        return mostrarLista;
    }   */
    public DefaultComboBoxModel LlenarCombo(){
        DefaultComboBoxModel conten = new DefaultComboBoxModel();
        String muestro = "select Nombre from autor order by Nombre";
        try {
            Ps = con.conectar().prepareStatement(muestro);
            Rs = Ps.executeQuery();
            while (Rs.next()) {
                conten.addElement(Rs.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no puede mostrar los autores" + "\n" + e);
        }
        return conten;
    }
    public void Baja(int autorAEliminar){
        String Borrar = "DELETE FROM autor WHERE idAutor = ?"; //Almaceno el DELETE del worbench en una variable
        try {
            Ps = con.conectar().prepareStatement(Borrar); //llamo a mi metodo conectar de la clase Conexion, tambien llamo al metodo prepareStatement, le asigno la variable creada y todo esto lo almaceno en mi objeto Ps
            Ps.setInt(1, autorAEliminar);
            int rayo = Ps.executeUpdate(); //ejecuto el rayo y como me devuelve un int lo almaceno en una variable
            if (rayo > 0) { //hago un if para preguntar si encuentra el dato, si lo encuentra me devuelve un 1 por lo tanto entra y lo elimina
                JOptionPane.showMessageDialog(null, "Dato eliminado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error, no pudo borrarlo, error en la sentencia, no tiene permiso para borrar" + "\n" + e);
        }
    }    
    public void BuscarPorIdAutor(int AutorEleg){
        String AutorABuscar = "select Nombre, Pais, TipoDeAutor from autor where idAutor = ?";
        try {
            Ps = con.conectar().prepareStatement(AutorABuscar);
            Ps.setInt(1, AutorEleg);
            Rs = Ps.executeQuery();
            if (Rs.next()) {
                this.setNom(Rs.getString(1));
                this.setPais(Rs.getInt(2));
                this.setTipoDeAutor(Rs.getInt(3));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR!!, no pudo encontrar el autor" + "\n" + e);
        }
    }
    
    public DefaultTableModel MostrarTitulos(){
        String [] titulos = {"Id de Autor", "Nombre"};
        String [][] registros ={};
        contenido = new DefaultTableModel(registros, titulos);
        return contenido;
    }
    public DefaultTableModel MostrarContenido(){
        String mostrarSql= "select IdAutor, Nombre from autor order by Nombre";
        try {
            Ps = con.conectar().prepareStatement(mostrarSql);
            Rs = Ps.executeQuery();
            if (contenido.getRowCount() > 0) {
                MostrarTitulos();
            }
            while (Rs.next()) {
                Object[] vector = {Rs.getInt(1), Rs.getString(2)};
                contenido.addRow(vector);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta de Socio" + "\n" + e);
        }
        return contenido;
    }
}
