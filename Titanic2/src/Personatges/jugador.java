package Personatges;

import java.util.ArrayList;

import Objectes.clau;
import Objectes.objectesMobils;
import Sales.ubicacions;

public class jugador {
    private int idHabActual;
    private int oxigen;
    private int gana = 10;
    private int maxOxigen=100;
    private ArrayList<objectesMobils> clauer=new ArrayList<>();
    private objectesMobils [] inventari = new objectesMobils[3] ;
    
    
    public jugador(){
        idHabActual = 0;
        oxigen=maxOxigen;
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

    public void setOxigen(int quantitat){
        this.oxigen = quantitat;
    }

    public void restarOxigen(){
        oxigen-=10;
    }

    public void canviarOxigen(){
        oxigen=maxOxigen;
    }

    public void actualitzarMaxOxigen(){
        maxOxigen+=50;
    }

    public void afegirClau(objectesMobils novaClau){
        clauer.add(novaClau);
    }

    public ArrayList<objectesMobils> getClauer(){
        return clauer;
    }

    public void utilitzarClau(objectesMobils clauUtilitzada){
        try{
          clauer.remove(clauUtilitzada);  
        }catch(Exception err){
            System.out.println("No s'ha pogut eliminar la clau del clauer.");
        }
        
    }

    public void setGana(){
        this.gana--;
        if(this.gana == 0){
            this.oxigen = oxigen/2;
            if(oxigen%2==0){
                oxigen-=5;
            }
        }
    }

    public objectesMobils [] getInventari(){
        return inventari;
    }

    public void deixarObjecte(String nom,ubicacions submari, ArrayList<ubicacions>titanic){
        boolean accioComplerta=false;
        for (int i = 0; i < inventari.length; i++) {
            if(inventari[i]!=null){
                if(inventari[i].getNom()=="motxilla"){    
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j]!=null){
                            if(inventari[i].getEspai()[j].getNom().equals(nom)){
                                if(idHabActual==0){
                                    submari.deixarObjecte(inventari[i].getEspai()[j]);
                                }else{
                                    accioComplerta=titanic.get(idHabActual-1).deixarObjecte(inventari[i].getEspai()[j]);
                                }
                                if(accioComplerta){
                                    inventari[i].getEspai()[j]=null;
                                }
                                i=inventari.length;
                                j=inventari[i].getEspai().length;
                            }
                        }
                    }
                }else{
                    if(inventari[i].getNom().equals(nom)){
                        if(idHabActual==0){
                            accioComplerta=submari.deixarObjecte(inventari[i]);
                        }else{
                            accioComplerta=titanic.get(idHabActual-1).deixarObjecte(inventari[i]);
                        }
                        if(accioComplerta){
                            inventari[i]=null;
                        }
                        i=inventari.length;
                    }
                }
            }
        }
        if(!accioComplerta){
            System.out.println("No pots deixar aquest objecte");
        }
    }

    public int getOxigen(){
        return oxigen;
    }

    public void mostrarInventari(){
        String frase="Oxigen: "+oxigen+"      Quantita màxima d'oxigen:"+maxOxigen+"        Gana: "+gana;
        if(clauer.size()>0){        
            frase+="\nClaus: ";
            for (objectesMobils clau : clauer) {
                frase+="\n "+clau.getNom();
            }
        }
        if(inventari.length>0){
            frase+="\nObjectes en el inventari: (Capacitat: " + inventari.length + ")";
        }    
        for (int i = 0; i < inventari.length; i++) {
            if(inventari[i]!=null){
                frase+="\n "+inventari[i].getNom();
                if(inventari[i].getNom()=="motxilla"){    
                    System.out.println("Objectes dins de la motxilla (Capacitat: 3)");
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j]!=null){
                            frase+="\n   "+inventari[i].getEspai()[j].getNom();
                        }
                    }
                }
            }
        }
        System.out.println(frase);
    }

    public boolean agafarObjecte(objectesMobils objecte){
        boolean afegit=false;
        for (int i = 0; i < inventari.length; i++) {
            if(inventari[i]==null){
                inventari[i]=objecte;
                i=inventari.length;
                afegit=true;
            }else{
                if(inventari[i].getNom()=="motxilla"){    
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j]==null){
                            inventari[i].getEspai()[j]=objecte;
                            j=inventari[i].getEspai().length;
                            i=inventari.length;
                            afegit=true;
                        }
                    }
                }
            }
        }
        if(afegit==false){
            System.out.println("No tens espai en el inventari!");
        }else{
            System.out.println("Has agafat el objecte: "+objecte.getNom());
        }
        return afegit;
    }

    public objectesMobils agafarObjectePerID(int id){
        return inventari[id-1];
    }

    public boolean llenternaUtilitzable(){
        boolean utilitzable=false;
        for (int i = 0; i < inventari.length; i++) {
            if(inventari[i]!=null){
                if(inventari[i].getNom()=="motxilla"){    
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j].getbateria()>0){
                            utilitzable=true;
                        }
                    }
                }else{
                    if(inventari[i].getbateria()>0){
                        utilitzable=true;
                    }
                }
            }
        }
        
        return utilitzable;
    }
}
