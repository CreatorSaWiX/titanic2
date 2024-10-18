package Personatges;

public class taurons extends enemics{

    public taurons(String nomSala){
        super(nomSala);
    }
    
    @Override
    public void atacarJugador(){
        System.out.println("AAAAAAAA, el tiburon me ataca!!! AAAAAA");
    }
}