package Modelo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proyecto {

    private int id_Proyecto;

    private String nombre;

    private String descripcion;

    private LocalDate fecha_Inicio;
    
    private Boolean estado;

    public Proyecto() {
    }

    public Proyecto(int id_Proyecto, String nombre, String descripcion, LocalDate fecha_Inicio, Boolean estado) {
        this.id_Proyecto = id_Proyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_Inicio = fecha_Inicio;
        this.estado = estado;
    }

    public Proyecto(String nombre, String descripcion, LocalDate fecha_Inicio, Boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_Inicio = fecha_Inicio;
        this.estado = estado;
    }

    public int getId_Proyecto() {
        return id_Proyecto;
    }

    public void setId_Proyecto(int id_Proyecto) {
        this.id_Proyecto = id_Proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fecha_Inicio;
    }

    public void setFechaInicio(LocalDate fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    //Los usuarios podrán crear nuevos proyectos : Crear proyecto.
    public void crearProyecto() {
    
    //System.out.println("Id del Proyecto: " + id_Proyecto);    
    //System.out.println("Proyecto creado: " + nombre);
    //System.out.println("Descripción Proyecto: " + descripcion);
    //System.out.println("Fecha de inicio: " + fecha_Inicio);
    
    //if(estado){
      //System.out.println("Proyecto Activo");  
    //}else{
      //System.out.println("Proyecto Inactivo"); 
    //}
    
    //Tarea tarea1 = new Tarea("Crear una Base de Datos",LocalDate.now(),null,false,1);
    //Tarea tarea2 = new Tarea("Proyecto en Netbeans",LocalDate.now(),null,false,2);
    //Tarea tarea3 = new Tarea("Desarrollo de todas las clases principales del dominio (ABM)",LocalDate.now(),null,true,3);
    
    //tareas.add(tarea1);
    //tareas.add(tarea2);
    //tareas.add(tarea3);
    
    //this.estado= true;
    
    //informeProyecto();
    } 
    
    //private void informeProyecto() {
        //System.out.println("\nDetalle Completo del Proyecto: " + id_Proyecto);
        //System.out.println("Nombre: " + nombre);
        //System.out.println("Descripción: " + descripcion);
        //System.out.println("Fecha de inicio: " + fecha_Inicio);
        //System.out.println("Estado: " + (estado ? "Activo" : "Inactivo"));
        
        //System.out.println("Tareas asignadas:");
        
        //for(int i=0;i<tareas.size();i++){
        
        //Tarea tarea = tareas.get(i);
        //System.out.println("- " + tarea.getNombre());
        
      //}
        
    //}

    @Override
    public String toString() {
       return nombre;
    }
    
    
}
        



