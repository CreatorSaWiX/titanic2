package Personatges;

public class calamarsMitjans extends enemics{
    /*
    *   Están en les sales fosques. Només pot agafar el jugador i aixafar-lo.
    */

    public calamarsMitjans(String nomSala){
        super(nomSala);
    }

    @Override
    public void atacarJugador(){
        System.out.println("AAAAAAAA, el calamar m'ataca!!! AAAAAA");
    }
}