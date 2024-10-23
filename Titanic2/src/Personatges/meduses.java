package Personatges;
import java.util.Scanner;

public class meduses extends enemics{
    /*
    *   Només pot estar en una sala. No ataca, només tapa el camí del jugador. Per esquivar, 2 spams a la 'A'.
    */

    public meduses(String nomSala){
        super(nomSala, "medusa");
    }

    public static void esquivar(Scanner e, jugador jugador){
        String text;
        System.out.println("Introdueix 'a' 3 vegades per esquivar-los!");
        for(int i = 0; i < 3; i++){
            System.out.println("Progrés " + 1);
            text = e.next();
            if(!text.equalsIgnoreCase("a")){
                System.out.println("T'ha picat, -10 oxigen");
                jugador.restarOxigen();
                i--;
            }
        }
    }
}