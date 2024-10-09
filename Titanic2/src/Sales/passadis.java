package Sales;
public class passadis extends ubicacions{
    boolean tapat;

    public passadis(String nom,int idHab, String descripcio){
        super("Passadis"+nom, idHab, descripcio);
        this.tapat = false;
    }

    public void setTapat(boolean tapat){
        this.tapat = tapat;
    }
} 