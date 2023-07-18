/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.*;
import java.sql.Connection;
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
public class MiembroData {

    private Connection con;

    public MiembroData() {
        con = Conexion.getConexion();
    }

    public void guardarMiembro(Miembro miembro) {
        
        String sql = "INSERT INTO miembro(DNI, Apellido, Nombre, Estado) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, miembro.getDNI());
            ps.setString(2, miembro.getApellido());
            ps.setString(3, miembro.getNombre());
            ps.setBoolean(4, miembro.getEstado());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                miembro.setId_Miembro(1);
                JOptionPane.showMessageDialog(null, "El Miembro se guardo correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El Miembro no se pudo guardar.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar Miembro " + ex.getMessage());
        }
        
    }

    public void actualizarMiembro(Miembro miembro) {
        String sql = "UPDATE miembro(DNI, Apellido, Nombre, Estado) WHERE Id_Miembro=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, miembro.getDNI());
            ps.setString(2, miembro.getApellido());
            ps.setString(3, miembro.getNombre());
            ps.setBoolean(4, miembro.getEstado());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                miembro.setId_Miembro(1);
                JOptionPane.showMessageDialog(null, "Miembro actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El Miembro no se pudo actualizar.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar Miembro " + ex.getMessage());
        }

    }

    public void desactivarMiembro(int Id_Miembro) {//Funciona
        
        String sql = "UPDATE miembro SET estado=0 WHERE Id_Miembro=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Id_Miembro);
            
            if (ps.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "El Miembro se dio de baja.");
            } else {
                JOptionPane.showMessageDialog(null, "El Miembro no se encontro.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        
    }
    
    public ArrayList<Miembro> mostrarMiembros() {//Falta terminar.
        
        ArrayList<Miembro> miembros = new ArrayList<>();
        String sql = "SELECT Apellido, Nombre FROM miembro WHERE ?";
        Miembro miembro = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, 1);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {//
                do {
                    miembro = new Miembro();
                    
                    //miembro.setId_Miembro(rs.getInt("Id_Miembro"));
                    //miembro.setDNI(rs.getInt("DNI"));
                    miembro.setApellido(rs.getString("Apellido"));
                    miembro.setNombre(rs.getString("Nombre"));
                    //miembro.setEstado(rs.getBoolean("Estado"));
                    miembros.add(miembro);
                } while (rs.next());
            } else {
                System.out.println("Miembro no encontrado.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        
        return miembros;
    }
    
    // Metodo Datos miembros
    public ArrayList<Miembro> ConsultarTareasMiembro(int id_equipo) {
        ArrayList<Miembro> miembros = new ArrayList<>();
        String sql = "SELECT miembro.Nombre,miembro.Apellido,miembro.Estado,tarea.Nombre FROM equipo,miembro,tarea,equipo_miembros WHERE equipo.Id_Equipo=? and equipo.Id_Equipo=equipo_miembros.Id_Equipo and equipo_miembros.Id_Miembro=miembro.Id_Miembro and equipo_miembros.Id_MiembroEq=tarea.Id_MiembroEq";
         try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id_equipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Miembro miembro = new Miembro();
                Tarea tarea= new Tarea();
                miembro.setApellido(rs.getString("Apellido"));
                miembro.setNombre(rs.getString("Nombre"));
                miembro.setEstado(rs.getBoolean("Estado"));
                tarea.setNombre(rs.getString(4));
                
                miembros.add(miembro);
            }
            
            if (miembros.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No existe el Miembro en este equipo.");
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        return miembros;
    }
    //Datos Tarea 
    public ArrayList<Tarea> ConsultaGeneral(int id_equipo){
        
        ArrayList<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT miembro.Nombre,miembro.Apellido,miembro.Estado,tarea.Nombre FROM equipo,miembro,tarea,equipo_miembros WHERE equipo.Id_Equipo=? and equipo.Id_Equipo=equipo_miembros.Id_Equipo and equipo_miembros.Id_Miembro=miembro.Id_Miembro and equipo_miembros.Id_MiembroEq=tarea.Id_MiembroEq";
         try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id_equipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tarea tarea= new Tarea();
                
                tarea.setNombre(rs.getString(4));
                
                tareas.add(tarea);
            }
            
            if (tareas.isEmpty()) {
                System.out.println("No existe un Miembro en este equipo.");
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        return tareas;
    }
    
    public ArrayList<Miembro> miembrosSinEquipo() {
        
        //SELECT miembro.id_Miembro, miembro.DNI, miembro.nombre, miembro.apellido, miembro.Estado 
        //FROM miembro 
        //WHERE NOT EXISTS (
            //SELECT 1 FROM equipo_miembros WHERE equipo_miembros.id_Miembro = miembro.id_Miembro
        //)
        
        ArrayList<Miembro> miembros = new ArrayList<>();
        Miembro miembro = null;
        
        String sql = "SELECT miembro.id_Miembro, miembro.DNI, miembro.nombre, miembro.apellido, miembro.Estado\n" +
                        "FROM miembro\n" +
                        "WHERE NOT EXISTS (\n" +
                        "    SELECT 1\n" +
                        "    FROM equipo_miembros\n" +
                        "    WHERE equipo_miembros.id_Miembro = miembro.id_Miembro\n" +
                        ")";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                miembro = new Miembro();
                miembro.setId_Miembro(rs.getInt("Id_Miembro"));
                miembro.setDNI(rs.getInt("DNI"));
                miembro.setApellido(rs.getString("Apellido"));
                miembro.setNombre(rs.getString("Nombre"));
                miembro.setEstado(rs.getBoolean("Estado"));
                
                miembros.add(miembro);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        
        return miembros;
    }
    
    public ArrayList<Miembro> consultarMiembrosD() {

        ArrayList<Miembro> miembros = new ArrayList<>();
        String sql = "SELECT * FROM `miembro` WHERE ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {//Nombre, Descripcion, Fecha_Inicio, Estado.
                do {
                    Miembro miembro = new Miembro();

                    
                    
                    miembro.setId_Miembro(rs.getInt("Id_Miembro"));
                    miembro.setDNI(rs.getInt("DNI"));
                    miembro.setApellido(rs.getString("Apellido"));
                    miembro.setNombre(rs.getString("Nombre"));
                    miembro.setEstado(rs.getBoolean("Estado"));
                    miembros.add(miembro);
                } while (rs.next());
            } else {
                System.out.println("Proyecto no encontrado.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Proyecto " + ex.getMessage());
        }
        return miembros;

    }
    
    public void asignarMiembroaEquipo(int id_miembro,int id_equipo, int id_em) {//Falta
        
        String sql = "UPDATE miembro SET Id_Miembro=? WHERE Id_miembro=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1,id_em);
            ps.setInt(2,id_miembro);
            
            ps.executeUpdate();
            
            if (ps.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "miembro asignada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar el miembro.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla miembro " + ex.getMessage());
        }
    }
    
    public void asignarMiembroaEquipoMiembros(int id_Miembro, int Id_Miembro) {
                                              
        String sql = "UPDATE miembro SET Id_Miembro=? WHERE Id_Miembro=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1,Id_Miembro);
            ps.setInt(2,id_Miembro);
            
            ps.executeUpdate();
            
            if (ps.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Miembro asignada correctamente a EquipoMiembros.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar el Miembro.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        
    }
    
    public ArrayList<Miembro> mostrarMiembrosxEquipo(int idEquipo) {
        
        ArrayList<Miembro> miembros = new ArrayList<>();
        
        String sql = "SELECT miembro.Nombre, miembro.Apellido, miembro.DNI, miembro.Id_Miembro FROM equipo, equipo_miembros, miembro WHERE equipo.id_Equipo = ? AND equipo.Id_Equipo = equipo_miembros.Id_Equipo AND miembro.Id_Miembro = equipo_miembros.Id_Miembro";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, idEquipo);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                do{
                    Miembro miembro = new Miembro();
                    miembro.setApellido(rs.getString("Apellido"));
                    miembro.setNombre(rs.getString("Nombre"));
                    miembro.setDNI(rs.getInt("DNI"));
                    miembro.setId_Miembro(rs.getInt("Id_Miembro"));
                    miembros.add(miembro);
                    
                }while(rs.next());
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        return miembros;
    }

}
