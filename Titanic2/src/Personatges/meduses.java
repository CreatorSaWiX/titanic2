package Personatges;

public class meduses extends enemics{
    /*
    *   Només pot estar en una sala. No ataca, només tapa el camí del jugador. Per esquivar, 2 spams a la 'A'.
    */

    public meduses(String nomSala){
        super(nomSala);
    }

    @Override
    public void atacarJugador(){
        
    }
}