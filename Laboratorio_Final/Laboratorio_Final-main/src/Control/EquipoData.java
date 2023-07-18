/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.sql.Connection;
import Modelo.*;
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
public class EquipoData {

    private Connection con;

    public EquipoData() {
        con = Conexion.getConexion();
    }

    //creacion de equipos.
    public void crearEquipo(Equipo equipo) {//null,5,'Dinamita','2023-06-13',1

        String sql = "INSERT INTO equipo(Id_Proyecto, Nombre, Fecha_Creacion, Estado) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, equipo.getId_Proyecto());
            ps.setString(2, equipo.getNombre());
            ps.setDate(3, Date.valueOf(equipo.getFechaCreacion()));
            ps.setBoolean(4, equipo.getEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                equipo.setId_Equipo(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "El equipo se creo correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo crear el Equipo.");
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo " + ex.getMessage());
        }

    }
    
    public void desactivarEquipo(int Id_Equipo) {//Funciona
        
        String sql = "UPDATE equipo SET estado=0 WHERE Id_Equipo=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Id_Equipo);
            
            if (ps.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "El equipo se dio de baja.");
            }
            else {
                JOptionPane.showMessageDialog(null, "El equipo no se encontro.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo " + ex.getMessage());
        }
        
    }
    
//    public ArrayList<Equipo> mostrarEquipo() {//Falta terminar.
//        
//        ArrayList<Equipo> equipos = new ArrayList<>();
//        String sql = "SELECT miembro.apellido, miembro.nombre FROM miembro, equipo, equipoMiembros WHERE ?";
//        Equipo equipo = null;
//        
//        try {
//            PreparedStatement ps = con.prepareStatement(sql);
//            
//            ps.setInt(1, 1);
//            
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {//Id_Proyecto, Nombre, Fecha_Creacion, Estado.
//                do {
//                    equipo = new Equipo();
//
//                    equipo.setId_Proyecto(rs.getInt("Id_Proyecto"));
//                    equipo.setNombre(rs.getString("Nombre"));
//                    equipo.setFechaCreacion(rs.getDate("Fecha_Creacion").toLocalDate());
//                    equipo.setEstado(rs.getBoolean("Estado"));
//                    equipos.add(equipo);
//                } while (rs.next());
//            } else {
//                System.out.println("Equipo no encontrado.");
//            }
//            ps.close();
//            
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo " + ex.getMessage());
//        }
//        
//        return equipos;
//    }
    
    public ArrayList<Equipo> mostrarEquipo() {
        
        ArrayList<Equipo> equipo = new ArrayList<>();
        String sql = "SELECT * FROM `equipo` WHERE ?;";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,1);
            ps.executeUpdate();
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                do{
                    Equipo equip = new Equipo();
                    equip.setNombre(rs.getString(3));
                    equip.setId_Equipo(rs.getInt(2));
                    equipo.add(equip);
                    
                }while(rs.next());
                
            }else{
               JOptionPane.showMessageDialog(null, "No existe el Equipo."); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo " + ex.getMessage());
        }
        
        
        
        return equipo;
    }
    //Metodo nuevo agregado para crear Vista Asignar Tarea.    
    public  ArrayList<Equipo> consultarEquipoProyecto(String nombre){
         
        ArrayList<Equipo> equipo = new ArrayList<>();
        
        String sql="SELECT equipo.nombre FROM proyecto,equipo WHERE proyecto.Nombre =? AND proyecto.Id_Proyecto = equipo.Id_Proyecto;";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();//ejecuta la consulta
            ResultSet rs = ps.executeQuery();//devuelve la consulta.
            if(rs.next()){
                do{
                    Equipo equip = new Equipo();
                    equip.setNombre(rs.getString(1));
                    equipo.add(equip);//Lo guardo en el arrayList.
                    
                }while(rs.next());
                
            }else{
               JOptionPane.showMessageDialog(null, "No existe Equipo en este Proyecto"); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo " + ex.getMessage());
        }
          return equipo;
    }

    
       public  ArrayList<Equipo> consultarEquipoPorProyectoID(int id_Proyecto){
         
        ArrayList<Equipo> equipos = new ArrayList<>();
        
       String sql="SELECT equipo.Nombre, equipo.Id_Equipo FROM proyecto,equipo WHERE proyecto.Id_Proyecto=? AND proyecto.Id_Proyecto = equipo.Id_Proyecto;;";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_Proyecto);
            ps.executeUpdate();//ejecuta la consulta
            ResultSet rs = ps.executeQuery();//devuelve la consulta.
            if(rs.next()){
                do{
                    Equipo equip = new Equipo();
                    equip.setNombre(rs.getString(1));
                    equip.setId_Equipo(rs.getInt(2));
                    equipos.add(equip);//Lo guardo en el arrayList.
                    
                }while(rs.next());
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Equipo " + ex.getMessage());
        }
          return equipos;
    }
   
}
