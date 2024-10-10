package ObjectesInmobils;

import java.util.Scanner;

import Objectes.objectesMobils;
import Personatges.jugador;

public class mobles {
 String nom;
 objectesMobils [] objectes = null;
 
 public mobles(String nom, int maxObjectes){
  this.nom=nom;
  objectes=new objectesMobils[maxObjectes];
 }

 public mobles(String nom){
  this.nom=nom;
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
 public objectesMobils interactuarAmbMoble(jugador jugador){
  Scanner e= new Scanner(System.in);
  String str="";
  int cont=0;
  int con2=0;
  String r="";
  int rInt=0;
  objectesMobils objecteAgafat=null;
  boolean accedit=false;

  System.out.println("Què vols fer?\n1: Agafar objecte del moble\n2: Deixar objecte en el moble\nEscriu 'e' per tornar enrere");
  r=e.nextLine();
  switch (r) {
   case "1":
   for (objectesMobils objectesMobils : objectes) {
    if(objectesMobils!=null){
     cont++;
     str+="\n "+cont+": "+objectesMobils.getNom();
    }
   }
   System.out.println("Quin d'aquests objectes vols treure?\n"+str+"\n Escriu 'e' si vols tornar enrere");
   while (r.equals("e")==false && objecteAgafat==null) {
    try {
     r=e.next();
     if(r.equals("e")==false && Integer.parseInt(r)!=0){
      for (objectesMobils objectesMobils : objectes) {
       if(objectesMobils!=null){
        con2++;
        if(con2==Integer.parseInt(r)){
         objecteAgafat=objectesMobils;
         System.out.println("Has agafat el objecte");
         objectesMobils=null;
         return objecteAgafat;  
        }
       }
      }
     }
    } catch (Exception err) {
     System.out.println("Escriu un número vàlid o 'e' per sortir");
    }
   }
    break;
   case "2":
    System.out.println("Quin d'aquests objectes vols deixar?");
    jugador.mostrarInventari();
    System.out.println("Escriu 'e' per sortir");
    while (r.equals("e")==false) {
       r = e.nextLine();
       try {
            if(r.equals("e")==false){
                rInt=Integer.parseInt(r);
                for (objectesMobils objectesMobil : objectes) {
                if(objectesMobil==null && accedit==false)
                    objectesMobil=jugador.getInventari()[rInt];
                    for (int i = 0; i < jugador.getInventari().length; i++) {
                        if(jugador.getInventari()!=null){
                            con2++;
                            if(con2==rInt){
                            afegirObecte(jugador.getInventari()[i]);
                            jugador.getInventari()[i]=null;
                            System.out.println("El objecte indicat s'ha deixat en el moble.");
                            r="e";
                            accedit=true;
                            i=jugador.getInventari().length;
                            }
                        }
                    }
                }
            }
            if(accedit==false){
                System.out.println("No pots deixar més objectes en aquest moble!");
                r="e";
            }
       } catch (Exception err) {
              System.out.println("Escriu una resposta vàlida.");
       }  
    }
    
    break;
   case "e":
    break;
   default:
    System.out.println("Escriu un valor vàlid.");
    break;
  }

  
  return objecteAgafat;
 }

 
}
