package Personatges;

public class peixosLlanterna extends enemics{
    /*
    *   Están en les sales fosques. No pot moure entre habitacions.
    */

    public peixosLlanterna(String nomSala){
        super(nomSala);
    }

    @Override
    public void atacarJugador(){
        System.out.println("AAAAAAAA, el peixLlanterna m'ataca!!! AAAAAA");
    }
}