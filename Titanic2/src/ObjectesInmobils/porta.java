package ObjectesInmobils;

public class porta {

 private int idPorta; //Especial
 private int idHab1; 
 private int idHab2;

 public porta(int idPorta, int idHab1, int idHab2){
  this.idPorta=idPorta;
  this.idHab1=idHab1;
  this.idHab2=idHab2;
 }
 
 public int checkIdHab(int id){
  //En cas de que l'habitació en la que estàs ara tingui un ID igual al de una de les 2 habitacions d'una porta, es retornarà l'id de l'altra habitació
  if(id==idHab1){
   return idHab2;
  }
  else if(id==idHab2){
   return idHab1;
  }else{
   return -1;
  }
 }

 public int getIdPorta(){

  return idPorta;
 }
}




