package ObjectesInmobils;

import Objectes.objectesMobils;

public class mobles {
 String nom;
 objectesMobils [] objectes = null;
 
 
 public mobles(String nom, int maxObjectes){
  this.nom=nom;
  objectes=new objectesMobils[maxObjectes];
 }

 public void afegirObecte(objectesMobils nouObjecte){
  int cont=0;
  boolean objecteAfegit=false;
  while (cont<objectes.length && objecteAfegit==false) {
   //En cas de que no s'hagi afegit el objecte i que hi hagi espai en el moble, podràs insertar l'objecte

   if(objectes[cont]==null && objecteAfegit==false){
    objectes[cont]=nouObjecte;
    objecteAfegit=true;
   }
   cont++;
  }
  //En cas de no haber afegit l'objecte
  if(objecteAfegit==false){
   System.out.println("El moble "+nom+" no té prou espai per aquest objecte, treu algún objecte si el bols afegir.");
  }
 }

 public objectesMobils treureObjecte(String resposta){
  int cont=0;
  boolean trobat=true;
  while (cont<objectes.length && objectes[cont].getNom().equals("resposta")==false) {
   cont++; 
   if(cont==objectes.length){
    trobat=false;
   }
  }

  if(trobat){
   return objectes[cont];
  }else{
   return null;
  }
 }
 public String getNom(){
  return nom;
 }
 
}
