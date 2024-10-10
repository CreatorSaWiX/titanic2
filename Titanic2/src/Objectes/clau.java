package Objectes;
public class clau extends objectesMobils {
    int idHabTancada;
    
    public clau(int id1,String nomHabitacio){
        super("Clau " +nomHabitacio);
        idHabTancada=id1;
    }   

    public int getIdClau(){
        return idHabTancada;
    }
}