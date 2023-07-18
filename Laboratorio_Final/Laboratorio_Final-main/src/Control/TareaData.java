/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TareaData {
    
    //Array con tareas.
    
    private Connection con;
    
    public TareaData() {
        con = Conexion.getConexion();
    }
    
    public void crearTarea(Tarea tarea) {//Crea una tarea sin id_Miembro, para luego asignarlo en asignarTarea().
        
        String sql = "INSERT INTO tarea(Nombre, Fecha_Creacion, Fecha_Cierre, Estado) VALUES (?,?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1,tarea.getNombre());
            ps.setDate(2,Date.valueOf(tarea.getFechaCreacion()));
            ps.setDate(3,Date.valueOf(tarea.getFechaCierre()));
            ps.setBoolean(4,tarea.getEstado());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                tarea.setId_Tarea(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Tarea creada correctamente.");
            }
            else {
                JOptionPane.showMessageDialog(null, "La tarea no se pudo crear.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        
    }
    
    //Asignar tareas: Los usuarios podrán asignar tareas a diferentes miembros del equipo para
    //cada proyecto. Cada tarea debe estar asociada a un proyecto y a un miembro del equipo.
    public void asignarTarea(int id_Tarea, int id_em) {
        
        String sql = "UPDATE tarea SET Id_MiembroEq=? WHERE Id_Tarea=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1,id_em);
            ps.setInt(2,id_Tarea);
            
            ps.executeUpdate();
            
            if (ps.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Tarea asignada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar la tarea.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        
    }
    
    //Actualizar estado de las tareas: Los usuarios podrán marcar las tareas como completadas o en progreso,
    //asi como cambiar su estado a pendiente.
    public void actualizarEstado(int estado, int id_Tarea) {
        
        String sql = "UPDATE tarea SET Estado=? WHERE Id_Tarea=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1,estado);
            ps.setInt(2,id_Tarea);
            
            ps.executeUpdate();
            
            if (ps.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Estado de Tarea actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el Estado de la tarea.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        
    }
    
    //Registrar avances en las tareas: Los usuarios podrán registrar el progreso de las tareas,
    //incluyendo comentarios y fechas de avance.
    public Tarea registrarAvance(int id_tarea) {
        
        Tarea tarea = null;
        String sql = "SELECT * FROM tarea WHERE Id_Tarea=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1,id_tarea);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tarea = new Tarea();
                tarea.setId_Tarea(rs.getInt("Id_Tarea"));
                tarea.setNombre(rs.getString("Nombre"));
                tarea.setFechaCreacion(rs.getDate("Fecha_Creacion").toLocalDate());
                tarea.setFechaCierre(rs.getDate("Fecha_Cierre").toLocalDate());
                tarea.setEstado(rs.getBoolean("Estado"));
                tarea.setId_MiembroEq(rs.getInt("Id_MiembroEq"));
            }
            else {
                System.out.println("Tarea no encontrada.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        return tarea;
    }
    
    public ArrayList<Tarea> consultarTareas(String nombre) {
        
        String sql = "SELECT tarea.Id_Tarea,tarea.nombre,tarea.Fecha_Creacion,tarea.Fecha_Cierre,tarea.Estado,tarea.Id_MiembroEq \n" +
                        "FROM proyecto, equipo, equipo_miembros, tarea\n" +
                        "WHERE proyecto.Nombre = ? AND proyecto.Id_Proyecto = equipo.Id_Proyecto\n" +
                        "AND equipo.Id_Equipo = equipo_miembros.Id_Equipo\n" +
                        "AND equipo_miembros.Id_MiembroEq = tarea.Id_MiembroEq";
        
        ArrayList<Tarea> tareas = new ArrayList<>();
        Tarea tarea = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, nombre);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tarea = new Tarea();
                tarea.setId_Tarea(rs.getInt("Id_Tarea"));
                tarea.setNombre(rs.getString("Nombre"));
                tarea.setFechaCreacion(rs.getDate("Fecha_Creacion").toLocalDate());
                tarea.setFechaCierre(rs.getDate("Fecha_Cierre").toLocalDate());
                tarea.setEstado(rs.getBoolean("Estado"));
                tarea.setId_MiembroEq(rs.getInt("Id_MiembroEq"));
                tareas.add(tarea);
            }
            else {
                System.out.println("Tarea no encontrada.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        return tareas;
        
    }
    
    public ArrayList<Tarea> BuscarTareas(boolean estado) {//Nuevo
        ArrayList<Tarea> tarea1 = new ArrayList();
        Tarea tarea = null;

        String sql = "SELECT * FROM `tarea` WHERE estado=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1, estado);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    tarea = new Tarea();
                    tarea.setId_Tarea(rs.getInt("Id_Tarea"));
                    tarea.setNombre(rs.getString("Nombre"));
                    tarea.setFechaCreacion(rs.getDate("Fecha_Creacion").toLocalDate());
                    tarea.setFechaCierre(rs.getDate("Fecha_Cierre").toLocalDate());
                    tarea.setEstado(rs.getBoolean("Estado"));
                    tarea.setId_MiembroEq(rs.getInt("Id_MiembroEq"));
                    tarea1.add(tarea);
                } while (rs.next());

            } else {
                System.out.println("Tarea no encontrada.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        return tarea1;
    }
    
    public ArrayList<Tarea> mostrarTODASTareas() {
        
        ArrayList<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM `tarea` WHERE 1";
        Tarea tarea = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    tarea = new Tarea();
                    tarea.setId_Tarea(rs.getInt("Id_Tarea"));
                    tarea.setNombre(rs.getString("Nombre"));
                    tarea.setFechaCreacion(rs.getDate("Fecha_Creacion").toLocalDate());
                    tarea.setFechaCierre(rs.getDate("Fecha_Cierre").toLocalDate());
                    tarea.setEstado(rs.getBoolean("Estado"));
                    tarea.setId_MiembroEq(rs.getInt("Id_MiembroEq"));
                    tareas.add(tarea);
                } while (rs.next());
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Tarea " + ex.getMessage());
        }
        
        return tareas;
    }
    
    public ArrayList<Tarea> consultarTareasD() {

        ArrayList<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM `tarea` WHERE ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {//Nombre, Descripcion, Fecha_Inicio, Estado.
                do {
                    Tarea tarea = new Tarea();

                    
                    tarea.setId_Tarea(rs.getInt("Id_Tarea"));
                    tarea.setNombre(rs.getString("Nombre"));
                    tarea.setFechaCreacion(rs.getDate("Fecha_Creacion").toLocalDate());
                    tarea.setFechaCierre(rs.getDate("Fecha_Cierre").toLocalDate());
                    tarea.setEstado(rs.getBoolean("Estado"));
                    tareas.add(tarea);
                } while (rs.next());
            } else {
                System.out.println("Proyecto no encontrado.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Proyecto " + ex.getMessage());
        }
        return tareas;

    }
    
}
