package Objectes;
import java.util.concurrent.*;
import java.util.*;

import Personatges.enemics;
import Personatges.jugador;
import Sales.ubicacions;
import temps.*;

public class ganivet extends objectesMobils{
    public ganivet(){
        super("ganivet");
    }

    public void utilitzar(jugador jugador, ArrayList<enemics>enemics,ArrayList<ubicacions> titanic){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20); 
        Comptador comptador= new Comptador(false);
        Scanner sc = new Scanner(System.in);
        String text;
        scheduler.schedule(comptador, 4, TimeUnit.SECONDS);
        System.out.println("Spam tecla 'a' per atacar " /* + variable nom enemic */ + (". Tens 4s!"));
        for(int i = 1; i <= 5; i++){
            System.out.println("Progrés: (" + i + "/5)");
            text = sc.next();
            if(!text.equalsIgnoreCase("a") || comptador.getFinalitzat()){
                jugador.setOxigen(0);   //El jugador mor.
                i = 6;
            }
        }
        
        if(!comptador.getFinalitzat()){
            int id;
            for (int index = 0; index < enemics.size(); index++) {
                id=obtenirIdHabitacio(titanic,enemics.get(index).getSalaEnemic());
                if(id==jugador.getSalaActual()){
                    System.out.println("Has aconseguit matar a "+enemics.get(index).getNomEnemic());
                    enemics.remove(index);
                    break;
                }
            }
        }
        comptador.setFinalitzat();
        scheduler.shutdown();
    }
}


