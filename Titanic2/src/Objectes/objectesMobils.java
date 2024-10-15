package Objectes;

public class objectesMobils {
    String nom;

    public objectesMobils(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return nom;
    }

    public objectesMobils[] getEspai(){
        System.out.println("No pots guardar objectes en "+nom);
        return null;
    }

    public int getIdClau(){
        return -1;
    }

    public int getbateria(){
        return -1;
    }
}
