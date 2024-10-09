package ObjectesInmobils;

public class porta {
    private boolean obert=true;
    private int idHab1;     //ID de l'habitació 1
    private int idHab2;     //ID de l'habitació 2

    public porta( int idHab1, int idHab2){
        this.idHab1=idHab1;
        this.idHab2=idHab2;
    }

    public porta( int idHab1, int idHab2, boolean obert){
        this.idHab1=idHab1;
        this.idHab2=idHab2;
        this.obert=obert;
    }
 
    public int checkIdHab(int id){
        //En cas de que l'habitació en la que estàs ara tingui un ID igual al de una de les 2 habitacions d'una porta, es retornarà l'id de l'altra habitació
        if(id==idHab1) {
            return idHab2;
        } else if(id==idHab2) {
            return idHab1;
        } else {
            return -1;  //En cas que no existeix la porta
        }
    }

    public void setObert(boolean obert){
        this.obert = obert;
    }

    // Una funció que et retrni les 2 IDS
    public int getIdHab1(){
        return this.idHab1;
    }

    public int getIdHab2(){
        return this.idHab2;
    }

    public boolean getObert(){
        return this.obert;
    }    
}