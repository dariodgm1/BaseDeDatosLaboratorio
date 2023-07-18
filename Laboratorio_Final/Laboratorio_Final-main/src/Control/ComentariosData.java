/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Comentarios;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel
 */
public class ComentariosData {
    
    private Connection con;

    public ComentariosData() {
        con = Conexion.getConexion();
    }
    
    public void crearComentario(Comentarios comentario) {
        
        String sql = "INSERT INTO comentarios(Comentarios, Fecha_Avance, Id_Tarea) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, comentario.getComentario());
            ps.setDate(2, Date.valueOf(comentario.getFechaAvance()));
            ps.setInt(3, comentario.getId_Tarea());
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                comentario.setId_Comentario(1);
                JOptionPane.showMessageDialog(null, "Comentario creado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo crear el Comentario.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Comentarios " + ex.getMessage());
        }
        
        
    }
    
}
