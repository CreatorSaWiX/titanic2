package Sales;
import ObjectesInmobils.mobles;
import ObjectesInmobils.porta;
import java.util.ArrayList;

import Objectes.objectesMobils;
public class ubicacions {
   private String nomSala; //Com es dirà la sala
   private objectesMobils objecteTerre=null;  //Quin objecte hi ha a terre (Només es pot deixar 1 objecte a terre)
   private int idHab;   //El id de l'habitació
   private String descripco;  //La descripció de l'habitació
   private mobles[] mobles;   //La llista de mobles interactuables que tindrà l'habitació
   static public ArrayList<porta> portes= new ArrayList<>(); //Les portes que tindrà l'habitació

   public ubicacions(String nomSala,int idHab,String descripco){
      //Assignar les variables passades a les variables
      this.nomSala=nomSala;   
      this.idHab=idHab;
      this.descripco=descripco;
   }

   //Funció per veure el nom
   public String getNomSala(){
      return nomSala;
   }

   //Funció per veure el ID de l'habitació
   public int getIdHab(){
      return idHab;
   }

   //Funció per veure la descripció de l'habitació
   public String getDescripcio(){
      if(objecteTerre.equals("")){ //En cas de que no hi hagi cap objecte al terre es retornarà la descripció normal
         return descripco;
      }else{ //Si hi ha un objecte a terre es retorna la descripció i el nom del objecte del terre
         return descripco+"\nEs pot veure un objecte a terre: "+objecteTerre;
      }
   }

   public void afegirPorta(porta novaPorta){
      portes.add(novaPorta);
   }
}
