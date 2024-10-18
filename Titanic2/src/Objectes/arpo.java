package Objectes;

import Personatges.jugador;

public class arpo extends objectesMobils{
    public arpo(){
        super("arpo");
    }

    public void utilitzar(jugador jugador){
        System.out.println("S'ha disparat l'arpó per matar l'enemic");
    }
}