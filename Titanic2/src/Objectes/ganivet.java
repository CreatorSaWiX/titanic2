package Objectes;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.*;
import Personatges.jugador;
import temps.*;

public class ganivet extends objectesMobils{
    public ganivet(){
        super("ganivet");
    }

    public void utilitzar(jugador jugador){
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
        comptador.setFinalitzat();
        if(!comptador.getFinalitzat()){
                System.out.println("Has aconseguit matar a");
        }
        scheduler.shutdown();
    }
}


