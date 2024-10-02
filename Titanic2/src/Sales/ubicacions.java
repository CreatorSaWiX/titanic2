package Sales;
import ObjectesInmobils.mobles;
import ObjectesInmobils.porta;

public class ubicacions {
   private String nomSala;
   private int idHab;
   private String descripco;
   private mobles[] mobles;
   private porta[] portes;

   public ubicacions(String nomSala,int idHab,String descripco){
      this.nomSala=nomSala;
      this.idHab=idHab;
      this.descripco=descripco;
   }

   public String getNomSala(){
      return nomSala;
   }

   public int getIdHab(){
      return idHab;
   }

}
