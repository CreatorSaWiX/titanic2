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

    public void utilitzarClau(int i){
        try{
            clauer.remove(i);  
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
                    frase+="\nObjectes dins de la motxilla (Capacitat: 3)";
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j]!=null){
                            frase+="\n   "+inventari[i].getEspai()[j].getNom();
                        }
                    }
                }
            }
        }
        if(objecteEnInventari("llibreta")){
            frase+="\nEscriu 'o' per obrir la llibreta";
        }else if(objecteEnInventari("paper")){
            if(getObjecteInventari("paper", 2)!=null){
                frase+="\nEscriu 'l_num', substitueix num per el paper que vols llegir\nExemple : 'l_2'";
            }else{
                frase+="\nEscriu 'l' per llegir el paper";
            }
        }
        System.out.println(frase);
    }

    public boolean agafarObjecte(objectesMobils objecte){
        boolean afegit=false;
        if(objecte.getNom().equals("paper") && objecteEnInventari("llibreta")){
            getObjecteInventari("llibreta").afegirPaper(objecte);
            afegit=true;
        }else{
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

    public void utilitzarLlenterna(){
        for (objectesMobils objecte : inventari) {
            if(objecte!= null){
                if(objecte.getNom()=="motxilla"){    
                    for (int j = 0; j < objecte.getEspai().length; j++) {
                        if(objecte.getEspai()[j].getbateria()>0){
                            objecte.utilitzarLlanterna();
                        }
                    }
                }else{
                    if(objecte.getbateria()>0){
                            objecte.utilitzarLlanterna();
                        
                    }
                }
            }    
        }
    }

    public boolean tePala(){
        return objecteEnInventari("pala");
    }

    public void utilitzarPala(){
        utilitzarObjecte("pala");
    }

    public boolean objecteEnInventari(String nom){
        boolean enInventari=false;
        for (int i =0; i<inventari.length;i++) {
            if(inventari[i]!= null){
                if(inventari[i].getNom()=="motxilla"){    
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j].getNom().equals(nom)){
                            enInventari=true;
                        }
                    }
                }else{
                    if(inventari[i].getNom().equals(nom)){
                        enInventari=true;
                    }
                }
            }    
        }
        return enInventari;
    }

    public void utilitzarObjecte(String nom){
        boolean eliminat=false;
        for (int i =0; i<inventari.length;i++) {
            if(!eliminat){
                if(inventari[i]!= null){
                    if(inventari[i].getNom()=="motxilla"){    
                        for (int j = 0; j < inventari[i].getEspai().length; j++) {
                            if(inventari[i].getEspai()[j].getNom().equals(nom)){
                                inventari[i].getEspai()[j]=null;
                                eliminat=true;
                                System.out.println(nom+" queda inutilitzable.");
                                break;
                            }
                        }
                    }else{
                        if(inventari[i].getNom().equals(nom)){
                            eliminat=true;
                            inventari[i]=null;
                            System.out.println(nom+" queda inutilitzable.");

                        }
                    }
                }    
            }
        }
    }

    //Retornar l'objecte que s'esta buscant
    public objectesMobils getObjecteInventari(String nom){
        objectesMobils obj = null;
        for (int i =0; i<inventari.length;i++) {
            if(inventari[i]!= null){
                //En cas de ser una motxilla es mirarà els objectes del seu interior
                if(inventari[i].getNom()=="motxilla"){    
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j].getNom().equals(nom)){
                            obj=inventari[i].getEspai()[j];
                            break;
                        }
                    }
                }else{
                    if(inventari[i].getNom().equals(nom)){               
                        obj=inventari[i];
                    }
                }
            }    
        }
        return obj;
    }

    public objectesMobils getObjecteInventari(String nom, int N){
        objectesMobils obj = null;
        int cont=1;
        for (int i =0; i<inventari.length;i++) {
            if(inventari[i]!= null){
                //En cas de ser una motxilla es mirarà els objectes del seu interior
                if(inventari[i].getNom()=="motxilla"){    
                    for (int j = 0; j < inventari[i].getEspai().length; j++) {
                        if(inventari[i].getEspai()[j].getNom().equals(nom)){
                            if(cont==N){
                                obj=inventari[i].getEspai()[j];
                                cont++;
                                break;
                            }else{
                                cont++;
                            }
                        }
                    }
                }else{
                    if(inventari[i].getNom().equals(nom)){
                        if(cont==N){               
                            obj=inventari[i];
                            cont++;
                        }else{
                            cont++;
                        }

                    }
                }
            }    
        }
        return obj;
    }

}
