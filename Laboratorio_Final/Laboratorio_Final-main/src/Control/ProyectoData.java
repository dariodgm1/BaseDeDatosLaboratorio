package Control;

import Modelo.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class ProyectoData {

    private Connection con;

    public ProyectoData() {
        con = Conexion.getConexion();
    }

    //Crear proyectos: Los usuarios podrán crear nuevos proyectos especificando un nombre, una 
    //descripción y una fecha de inicio.
    public void crearProyectos(Proyecto proyecto) {

        String sql = "INSERT INTO proyecto(Nombre, Descripcion, Fecha_Inicio, Estado) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, proyecto.getNombre());
            ps.setString(2, proyecto.getDescripcion());
            ps.setDate(3, Date.valueOf(proyecto.getFechaInicio()));
            ps.setBoolean(4, true);//true: estado esta en proceso.

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                proyecto.setId_Proyecto(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Proyecto creado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El proyecto no se pudo guardar.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Proyecto " + ex.getMessage());
        }

    }

    //Consultar proyectos y tareas: Los usuarios podrán ver la lista de proyectos y sus respectivas
    //tareas, así como filtrar las tareas por estado y miembro del equipo.
    public ArrayList<Proyecto> consultarProyectos() {

        ArrayList<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto WHERE ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {//Nombre, Descripcion, Fecha_Inicio, Estado.
                do {
                    Proyecto proyecto = new Proyecto();

                    proyecto.setId_Proyecto(rs.getInt("Id_Proyecto"));
                    proyecto.setNombre(rs.getString("Nombre"));
                    proyecto.setDescripcion(rs.getString("Descripcion"));
                    proyecto.setFechaInicio(rs.getDate("Fecha_Inicio").toLocalDate());
                    proyecto.setEstado(rs.getBoolean("Estado"));
                    proyectos.add(proyecto);
                } while (rs.next());
            } else {
                System.out.println("Proyecto no encontrado.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Proyecto " + ex.getMessage());
        }
        return proyectos;

    }

}
