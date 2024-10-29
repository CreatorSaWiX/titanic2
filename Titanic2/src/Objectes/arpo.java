package Objectes;

import java.util.ArrayList;

import Personatges.enemics;
import Personatges.jugador;
import Sales.ubicacions;

public class arpo extends objectesMobils{
    public arpo(){
        super("arpo");
    }

    public void utilitzar(jugador jugador, ArrayList<enemics>enemics,ArrayList<ubicacions> titanic){
        jugador.utilitzarObjecte("arpo");
                int id;
                for (int index = 0; index < enemics.size(); index++) {
                    id=obtenirIdHabitacio(titanic,enemics.get(index).getSalaEnemic());
                    if(id==jugador.getSalaActual()){
                        System.out.println("Mates a "+enemics.get(index).getNomEnemic()+" amb l'arpó.");
                        enemics.remove(index);
                        break;
                    }
                }
    }
}