package temps;

import Personatges.jugador;
import temps.InteraccioUsuari;
public class ComptadorCalamars implements Runnable{
    Boolean finalitzat;
    jugador jugador;
    InteraccioUsuari intUsu;

    public ComptadorCalamars(Boolean finalitzat, jugador jugador, InteraccioUsuari intUsu){
        this.finalitzat=finalitzat;
        this.jugador=jugador;
        this.intUsu = intUsu;
    }
    
    public void run(){
        if(!finalitzat && !intUsu.finalitzat()){
            jugador.restarOxigen();      
        }
    }
}