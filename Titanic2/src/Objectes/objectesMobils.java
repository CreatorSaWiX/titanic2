package Objectes;
import Personatges.*;
public class objectesMobils {
    String nom;

    public objectesMobils(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }

    public objectesMobils[] getEspai(){
        System.out.println("No pots guardar objectes en "+nom);
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
        System.out.println("No és una pala!");
    }

    public void utilitzar(jugador jugador){
        System.out.println("a");
    }
}
