package temps;
public class Comptador implements Runnable{
    Boolean finalitzat;
    public Comptador(Boolean finalitzat){
        this.finalitzat=finalitzat;
    }
    public void run(){
        if(!finalitzat){
            System.out.println("Fi del temps");
            setFinalitzat();

        }
    }
    public void setFinalitzat(){
        finalitzat=true;
    }

    public Boolean getFinalitzat(){
        return finalitzat;
    }
    
}