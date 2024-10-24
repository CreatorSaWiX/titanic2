package temps;

import Personatges.jugador;
import java.util.Scanner;

public class InteraccioUsuari implements Runnable{
    jugador j;
    Boolean finalitzat=false;
    public InteraccioUsuari(jugador jugador){
        j= jugador;
    }
    
    public void run(){
        Scanner e = new Scanner(System.in);
        String text;
        for(int i = 0; i < 10; i++){
            System.out.println("Progrés " + (i+1));
            text = e.next();
            if(!text.equalsIgnoreCase("a")){
                System.out.println("Lletra incorrecte. -10 oxigents.");
                j.restarOxigen();
            }
        }    
        System.out.println("T'has alliberat");
        finalitzat=true;    
    }

    public Boolean finalitzat(){
        return finalitzat;
    }
}