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

                portesInteractuables= new porta[6];
                habitacionsDisp=new sala[6];

                for (int i = 0; i < portesTitanic.length; i++) {
                    //Veure les portes que tenen relació amb l'habitació en la que estàs
                    //checkId Hab retorna el id de l'habitacio que esta al altra costat de la porta, i en cas de no tenir relació amb la porta retorna -1
                    if(portesTitanic[i].checkIdHab(idSalaActual)!=-1){
                        portesInteractuables[contOpcions]=portesInteractuables[i];
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

            System.out.println("-----------------------------------------------------------");
            
            System.out.println("Aquestes son les opcions que tens: ");
            for (int i = 0; i < habitacionsDisp.length; i++) {
                if(habitacionsDisp[i]!=null){
                    System.out.println((i+1) +": Anara a "+habitacionsDisp[i].getNomSala());
                }
            }
            resposta= e.nextInt();
            
            jugador.moure(habitacionsDisp[resposta-1].getIdHab());
 
        }
    }

    //Opcions:
    public sala[] crearTitanic(){
        sala[] habitacions = new sala[32];

        //En cas de les escales i passadis afegir-los algun nom diferent (Al igual que a les habitacions amb noms repetits)
        String [] noms={"Sala","Menjador","Cuina","Neteja","W.C.","Habitació VIP","WC habitació VIP","Habitació capità","Habitació normal","Passadis","Sala de motors","Capella","Biblioteca", "Sala Capità","Teatre","Escales"};
        String[] descripcions={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
        int cont=0;
        int id=1;
        int j=-1;

        //Creació de sales
        for (int i = 0; i < noms.length; i++) {
            j++;
            //Per sales duplicades com la sala de neteja
            if(i>=4 && i<=7 && cont<1){
                cont++;
                i--;
                //Reiniciar el contador per poder-lo utilitzar per crear les habitacions normals
            }else if(i>=4 && i<=8 && cont==1){
                cont=0;

            }else if(i==9 && cont!=7){
                cont++;
                i--;
            }else if(i==9 && cont==7){
                cont=0;
            }else if(i==10 && cont!=3){
                cont++;
                i--;
            }else if(i==10 && cont==3){
                cont=0;
            }else if(i==15 && cont!=3){
                cont++;
            }

            habitacions[j]=new sala(noms[i],id,descripcions[j]);
            if(i==15 && cont!=3){
                i--;
            }

            id++;
        }       

        //Borrar al tenir lo de les portes creat!
        for (sala habitacio: habitacions) {
            System.out.println(habitacio.getIdHab() + "   "+ habitacio.getNomSala());
        }

        return habitacions;
    }

    public porta[] crearPortesTitanic(porta porta){
        porta[] portes= new porta[5];
        portes[0]=porta; //Conexio sala amb submari

        //TODO (ns si hi ha alguna forma menys pesada xD)
        portes[1]= new porta(1, 1,2);   //Conexio sala amb menjador
        portes[2]= new porta(2, 2,3);   //Conexio menjador amb cuina
        portes[3]= new porta(3, 1,30);   //Conexio sala amb escales
        portes[4]= new porta(4, 1,25);   //Conexio sala amb sala de motors
        return portes;
    }
}