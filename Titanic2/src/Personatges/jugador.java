package Personatges;
public class jugador {
    private int idHabActual;
    private int oxigen;
    private int gana;
    
    public jugador(){
        idHabActual = 0;
    }

    public int getSalaActual(){
        return idHabActual;
    }

    public void putSalaActual(int idHab){
        this.idHabActual=idHab;
    }

    public void moure(int id){
        idHabActual=id;
    }

}
