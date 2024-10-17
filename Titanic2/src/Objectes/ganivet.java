package Objectes;
import java.util.Scanner;

import Personatges.jugador;

public class ganivet extends objectesMobils{
    public ganivet(){
        super("ganivet");
    }

    public void utilitzar(jugador jugador){
        Scanner sc = new Scanner(System.in);
        String text;
        System.out.println("Spam tecla 'a' per atacar " /* + variable nom enemic */ + (". Tens 4s!"));
        for(int i = 1; i <= 5; i++){
            System.out.println("Progrés: (" + i + "/5)");
            text = sc.next();
            if(!text.equalsIgnoreCase("a")){
                jugador.setOxigen(0);   //El jugador mor.
                i = 6;
            }
        }
    }
}