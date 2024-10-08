package Personatges;

import java.util.ArrayList;

import Objectes.clau;
import Objectes.objectesMobils;

public class jugador {
    private int idHabActual;
    private int oxigen;
    private int gana;
    private int maxOxigen=100;
    private ArrayList<clau> clauer=new ArrayList<>();
    private objectesMobils [] inventari = new objectesMobils[3] ;
    
    
    public jugador(){
        idHabActual = 0;
        oxigen=maxOxigen;
    }

    public int getSalaActual(){
        return idHabActual;
    }

    public void putSalaActual(int idHab){
        this.idHabActual=idHab;
    }

    public void moure(int id){
        idHabActual=id;
    }

    public void restarOxigen(){
        oxigen-=10;
    }

    public void canviarOxigen(){
        oxigen=maxOxigen;
    }

    public void actualitzarMaxOxigen(){
        maxOxigen+=50;
    }

    public void afegirClau(clau novaClau){
        clauer.add(novaClau);
    }

    public ArrayList<clau> getClauer(){
        return clauer;
    }

    public void utilitzarClau(clau clauUtilitzada){
        clauer.remove(clauUtilitzada);
    }

}
