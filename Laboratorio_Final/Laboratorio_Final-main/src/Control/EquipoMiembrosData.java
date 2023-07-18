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
public class EquipoMiembrosData {
    
    private Connection con;

    public EquipoMiembrosData() {
        con = Conexion.getConexion();
    }
    
    public void crearEquipoMiembros(EquipoMiembros equipomiembros) {
        
        String sql = "INSERT INTO equipo_miembros(Fecha_Incorporacion, Id_Equipo, Id_Miembro) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setDate(1,Date.valueOf(equipomiembros.getFechaIncorporacion()));
            ps.setInt(2,equipomiembros.getId_Equipo());
            ps.setInt(3,equipomiembros.getId_Miembro());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                equipomiembros.setId_MiembroEq(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Miembro asignado Correctamente al equipo.");
            }
            else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar el miembro.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo_Miembros " + ex.getMessage());
        }
        
    }
    
    public ArrayList<EquipoMiembros> consultarEquipoMiembros() {

        ArrayList<EquipoMiembros> equipomiembro = new ArrayList<>();
        String sql = "SELECT * FROM `equipo_miembros` WHERE ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    EquipoMiembros equipom = new EquipoMiembros();


                      equipom.setId_MiembroEq(rs.getInt("Id_MiembroEq"));
                      equipom.setFechaIncorporacion(rs.getDate("Fecha_Incorporacion").toLocalDate());
                      equipom.setId_Equipo(rs.getInt("Id_Equipo"));
                      equipom.setId_Miembro(rs.getInt("Id_Miembro"));
                      equipomiembro.add(equipom);

                } while (rs.next());
            } else {
                System.out.println("EquipoMiembro no encontrado.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla EquipoMiembro " + ex.getMessage());
        }
        return equipomiembro;

    }
    
    public int obtenerID_EM(Proyecto p, Equipo e, Miembro m) {
        
        int id = 0;
        
        String sql = "SELECT equipo_miembros.Id_MiembroEq FROM proyecto, equipo, miembro, equipo_miembros WHERE proyecto.Nombre = ? AND equipo.nombre = ? AND miembro.Id_Miembro = ? AND proyecto.Id_Proyecto = equipo.Id_Proyecto AND miembro.Id_Miembro = equipo_miembros.Id_Miembro AND equipo.Id_Equipo = equipo_miembros.Id_Equipo";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, e.getNombre());
            ps.setInt(3, m.getId_Miembro());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla EquipoMiembro " + ex.getMessage());
        }
        return id;
    }
    
    public ArrayList<Miembro> mostrarMiembrosSinEquipos() {
        
        ArrayList<Miembro> miembros = new ArrayList();
        
        String sql = "SELECT miembro.Id_Miembro, miembro.Nombre, miembro.Apellido,miembro.DNI FROM miembro WHERE NOT EXISTS (SELECT 1 FROM equipo_miembros WHERE equipo_miembros.id_Miembro = miembro.id_Miembro)";
        
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                do {
                    Miembro miembro = new Miembro();
                    miembro.setId_Miembro(rs.getInt(1));
                    miembro.setNombre(rs.getString(2));
                    miembro.setApellido(rs.getString(3));
                    miembro.setDNI(rs.getInt(4));
                    miembros.add(miembro);

                } while (rs.next());
            } else {
                JOptionPane.showMessageDialog(null, "Todos los miembros tienen equipo.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Miembro " + ex.getMessage());
        }
        return miembros;

    }
    
}
