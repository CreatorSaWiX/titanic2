import java.util.Scanner;
import ObjectesInmobils.porta;
import Personatges.jugador;

public class App {
    public static void main(String[] args) throws Exception {
        App start = new App();
        start.start();
    }

    public void start(){
        //Crear sala submari
        sala submari = new sala("submari",0,"");
        System.out.println("Sala titan creada");

        //Crear les sales del titanic
        sala[] titanic = crearTitanic();
        System.out.println("Sales titanic creades");

        //Crear la porta del submari
        porta portaSubmari = new porta(0, 0, 1);
        System.out.println("Porta titan creada");

        porta[] portesTitanic = crearPortesTitanic(portaSubmari);
        System.out.println("Portes titanic creades");


        System.out.println("tot creat correctement");

        
        iniciarJuagabilitat(submari,titanic,portaSubmari,portesTitanic);


        

    }

    public void iniciarJuagabilitat(sala submari, sala[] titanic, porta portaSubmari, porta[] portesTitanic){

        jugador jugador = new jugador();

        Scanner e= new Scanner(System.in);
        int resposta=0;
        boolean fi=false;        
        int contOpcions=0;
        sala actual=null;
        porta[] portesInteractuables= null;
        sala[] habitacionsDisp=null;
        String opcional="";
        String rString;

        while (!fi) {
            int idSalaActual=jugador.getSalaActual();

            //En cas d'estar en el submari
            if(idSalaActual==0){
                //Al ser el submari només tindrà una porta
                portesInteractuables= new porta[1];
                portesInteractuables[0]=portaSubmari;

                //Agafant el nom de l'habtiació a la que esta enllaçada
                habitacionsDisp = new sala[1];
                habitacionsDisp[0]=titanic[0];

                //En cas d'estar a la sala o al submari se li afagira aquest text:
                // opcional=" del Titainc";
                // habitacionsDisp[0]+=opcional;

            //EN cas de que no estigui en el submari    
            }else{
                //El maxim de portes que té una habitacio (en aquest cas el passadis) és de 6

                portesInteractuables= new porta[9];
                habitacionsDisp=new sala[9];

                for (int i = 0; i < portesTitanic.length; i++) {
                    //Veure les portes que tenen relació amb l'habitació en la que estàs
                    //checkId Hab retorna el id de l'habitacio que esta al altra costat de la porta, i en cas de no tenir relació amb la porta retorna -1
                    if(portesTitanic[i].checkIdHab(idSalaActual)!=-1){
                        portesInteractuables[contOpcions]=portesTitanic[i];
                        for (int j = 0; j < titanic.length; j++) {
                            if(titanic[j].getIdHab()== portesTitanic[i].checkIdHab(idSalaActual) ){
                                habitacionsDisp[contOpcions]=titanic[j]; 
                            }else if(portesTitanic[i].checkIdHab(idSalaActual)==0){
                                habitacionsDisp[contOpcions]=submari;
                            }
                        }
                        contOpcions++;
                    }
                }
                contOpcions=0;
            }
            try{
                System.out.println("-----------------------------------------------------------");
                
                System.out.println("Aquestes son les opcions que tens: ");
                for (int i = 0; i < habitacionsDisp.length; i++) {
                    if(habitacionsDisp[i]!=null){
                        System.out.println((i+1) +": Anar a "+habitacionsDisp[i].getNomSala());
                    }
                }
                rString= e.nextLine();
                resposta= Integer.parseInt(rString);
                jugador.moure(habitacionsDisp[resposta-1].getIdHab());

            }catch(Exception err){
                System.out.println("Escriu una opció vàlida");
                resposta=0;
            }
        }
    }

    //Opcions:
    public sala[] crearTitanic(){
        sala[] habitacions = new sala[34];

        //En cas de les escales i passadis afegir-los algun nom diferent (Al igual que a les habitacions amb noms repetits)
        String[] noms={"Sala P0","Menjador","Cuina","Neteja","W.C.","Habitació VIP","WC habitació VIP","Habitació capità","Habitació normal","Passadis","Sala de motors","Capella","Biblioteca", "Sala Capità","Teatre","Escales","Sala P2"};
        String[] descripcions={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
        int cont=0;
        int id=1;
        int j=-1;
        String nom="";

        //Creació de sales
        for (int i = 0; i < noms.length; i++) {
            j++;
            nom=noms[i];
            //Per sales duplicades com la sala de neteja, W.C, Habitacions VIP i WC Habitacions VIP
            if(i>=3 && i<=6 && cont<1){
                nom+= " est";
                cont++;
                i--;
            //Reiniciar el contador per poder-lo utilitzar per crear les altres habitacions i creant la segona habotació del mateix nom
            }else if(i>=3 && i<=6 && cont==1){
                nom+= " oest";
                cont=0;
            //Creant 7 vegades l'habitació normal
            }else if(i==8 && cont!=7){
                cont++;
                i--;
            //Reiniciar el contador per poder-lo utilitzar per crear les altres habitacions i creant la segona habotació del mateix nom
            }else if(i==8 && cont==7){
                cont=0;
            //Creant 3 passadissos
            }else if(i==9 && cont!=3){
                cont++;
                i--;
            //Creant l'ultim passadis i reiniciant el contador
            }else if(i==9 && cont==3){
                cont=0;
            //Creant els 4 passadissos d'una tirada
            }else if(i==15 && cont!=4){
                cont++;
            }

            habitacions[j]=new sala(nom,id,descripcions[j]);
            //Restant 1 a i en cas de no ser l'últim passadis per tornar a crear un altra passadis
            if(i==15 && cont!=4){
                i--;
            }

            id++;
            
        }       

        //Imprimir totes les habitacions
        for (sala habitacio: habitacions) {
            System.out.println(habitacio.getIdHab() + "   "+ habitacio.getNomSala());
        }

        return habitacions;
    }

    public porta[] crearPortesTitanic(porta porta){
    

        /*
        1   Sala
        2   Menjador
        3   Cuina
        4   Neteja (Passadis1)
        5   Neteja (Passadis 2)
        6   W.C. (Passadis 1)
        7   W.C. (Passadis 2)
        8   Habitació VIP (Passadis 1)
        9   Habitació VIP (Passadis 2)
        10   WC habitació VIP (VIP 1, passadis 1)
        11   WC habitació VIP (VIP 2, passadis 2)
        12   Habitació capità (Passadis 3)
        13   Habitació normal (Passadis 1)
        14   Habitació normal (Passadis 1)
        15   Habitació normal (Passadis 1)
        16   Habitació normal (Passadis 2)
        17   Habitació normal (Passadis 2)
        18   Habitació normal (Passadis 2)
        19   Habitació normal (Passadis 3)
        20   Habitació normal (Passadis 3)
        21   Passadis (Passadis 1)
        22   Passadis (Passadis 2)
        23   Passadis (Passadis 3)
        24   Passadis (Passadis 4)
        25   Sala de motors
        26   Capella
        27   Biblioteca
        28   Sala Capità
        29   Teatre
        30   Escales (passadis1 - sala)
        31   Escales (passadis2 - sala)
        32   Escales (passadis1 - p2)
        33   Escales (passadis2 - p2)
        34   Sala P2
        */
        
        int[] idHab1={1 ,2 ,1 ,1 ,1 ,21,31,22,21,21,21,21,22,22,22,23,23,23,21,21,21,22,22,22,21,22,32,33};
        int[] idHab2={2 ,3 ,25,30,31,30,22,23,23,13,14,15,16,17,18,19,20,12,4 ,6 ,8 ,5 ,7 ,9 ,32,33,34,34};

        porta[] portes= new porta[idHab1.length];
        portes[0]=porta; //Conexio sala amb submari

        for (int i = 1; i < portes.length; i++) {
            portes[i]=new porta(i, idHab1[i-1], idHab2[i-1]);
        }

        return portes;
    }
}