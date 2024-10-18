package Personatges;

public class enemics{
    private String nomSala;

    protected enemics(String nomSala){
        this.nomSala = nomSala;
    }

    protected void atacarJugador(){
        //Sobreescriu els fills
    }

    public String getSalaEnemic(){
        return this.nomSala;
    }
}