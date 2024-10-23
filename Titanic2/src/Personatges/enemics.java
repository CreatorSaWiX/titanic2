package Personatges;

public class enemics{
    private String nomSala;
    private String nomEnemic;

    public enemics(String nomSala, String nomEnemic){
        this.nomSala = nomSala;
        this.nomEnemic = nomEnemic;
    }

    public String getSalaEnemic(){
        return this.nomSala;
    }

    public String getNomEnemic(){
        return nomEnemic;
    }
}