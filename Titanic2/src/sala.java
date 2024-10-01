public class sala {
    private String nomSala;
    private int idHab;
    private String descripco;
    

 public sala(String nomSala,int idHab,String descripco){
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
