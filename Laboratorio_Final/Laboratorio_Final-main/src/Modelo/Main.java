package Modelo;

import Control.ComentariosData;
import Control.EquipoData;
import Control.EquipoMiembrosData;
import Control.MiembroData;
import Control.ProyectoData;
import Control.TareaData;
import java.time.LocalDate;

/**
 * @author jesim
 */
public class Main {

    public static void main(String[] args) {//Mi main
        
        
        MiembroData md = new MiembroData();
        ProyectoData pd = new ProyectoData();
        EquipoMiembrosData emd = new EquipoMiembrosData();
        EquipoData ed = new EquipoData();
        TareaData td = new TareaData();
        ComentariosData cd = new ComentariosData();
        
        Proyecto proyecto = new Proyecto("Proyecto 1","Compilar base de datos", LocalDate.of(2023, 06, 13),true);
        Miembro miembro = new Miembro(29960012,"Moll Montiveros","Jesica",true);
        Equipo equipo = new Equipo(5,"Dinamita",LocalDate.of(2023, 06, 13),true);
        EquipoMiembros em = new EquipoMiembros(LocalDate.of(2023, 06, 13), equipo.getId_Equipo(), miembro.getId_Miembro());
        Tarea tarea = new Tarea("Tarea 1",LocalDate.of(2023, 6, 17),LocalDate.of(2023, 8, 17),true,em.getId_MiembroEq());
        Comentarios comentario = new Comentarios("Primera prueba",LocalDate.of(2023, 06, 13),1);
        
        //md.guardarMiembro(miembro);//Funciona
        //pd.crearProyectos(proyecto);//Funciona
        //ed.crearEquipo(equipo);//Funciona
        //emd.crearEquipoMiembros(em);//
        //td.crearTarea(tarea);//Funciona
        //cd.crearComentario(comentario);//Funciona
        
        
        
    }
    
}
