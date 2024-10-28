package Objectes;
import java.util.ArrayList;

import Personatges.*;
import Sales.ubicacions;

public class objectesMobils {
    String nom;
    
    public objectesMobils(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }

    public objectesMobils[] getEspai(){
        System.out.println("No pots guardar objectes en " + nom);
        return null;
    }

    public int getIdClau(){
        return -1;
    }

    public int getbateria(){
        return -1;
    }
    
    public void utilitzarLlanterna(){
        System.out.println("Aquest objecte no és una llanterna");
    }

    public void utilitzarPala(){
        //Instanciara del metode fill 
        System.out.print("");
    }

    public void utilitzar(jugador jugador, ArrayList<enemics>enemics,ArrayList<ubicacions> titanic){
        //Instanciara del metode fill 
        System.out.print("");
    }

    public void llegir(){
        //Instanciara del metode fill
        System.out.println("");
    }

    public void llegirPagines(){
        //Instanciara del metode fill
        System.out.println("");
    }

    public void afegirPaper(objectesMobils paper){
        //Instanciara del metode fill
        System.out.println("");
    }

    public int obtenirIdHabitacio(ArrayList<ubicacions>zones,String nom){
        int id=-1;
        for (ubicacions ubicacio : zones) {
            if(nom.equals(ubicacio.getNomSala())){
                id=ubicacio.getIdHab();
            }
        }
        return id;
    }
}
