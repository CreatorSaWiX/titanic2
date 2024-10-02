import java.util.Scanner;
import java.util.ArrayList;
import ObjectesInmobils.porta;
import Personatges.jugador;
import Sales.*;


public class App {
    public static void main(String[] args) throws Exception {
        App start = new App();
        start.start();
    }

    public void start(){
        //Crear sala submari
        ubicacions submari = new ubicacions("submari",0,"");
        System.out.println("ubicacions titan creada");

        //Crear les sales del titanic
        ArrayList<ubicacions> titanic = new ArrayList<>();
        crearTitanic(titanic);
        System.out.println("Sales titanic creades");

        //Crear la porta del submari
        porta portaSubmari = new porta(0, 0, 1);
        System.out.println("Porta titan creada");

        porta[] portesTitanic = crearPortesTitanic(portaSubmari);
        System.out.println("Portes titanic creades");


        System.out.println("tot creat correctement");

        
        iniciarJuagabilitat(submari,titanic,portaSubmari,portesTitanic);


        

    }

    public void iniciarJuagabilitat(ubicacions submari, ArrayList<ubicacions> titanic, porta portaSubmari, porta[] portesTitanic){

        jugador jugador = new jugador();

        Scanner e= new Scanner(System.in);
        int resposta=0;
        boolean fi=false;        
        int contOpcions=0;
        ubicacions actual=null;
        porta[] portesInteractuables= null;
        ubicacions[] habitacionsDisp=null;
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
                habitacionsDisp = new ubicacions[1];
                habitacionsDisp[0]=titanic.get(0);

                //En cas d'estar a la sala o al submari se li afagira aquest text:
                // opcional=" del Titainc";
                // habitacionsDisp[0]+=opcional;

            //EN cas de que no estigui en el submari    
            }else{
                //El maxim de portes que té una habitacio (en aquest cas el passadis) és de 6

                portesInteractuables= new porta[9];
                habitacionsDisp=new ubicacions[9];

                for (int i = 0; i < portesTitanic.length; i++) {
                    //Veure les portes que tenen relació amb l'habitació en la que estàs
                    //checkId Hab retorna el id de l'habitacio que esta al altra costat de la porta, i en cas de no tenir relació amb la porta retorna -1
                    if(portesTitanic[i].checkIdHab(idSalaActual)!=-1){
                        portesInteractuables[contOpcions]=portesTitanic[i];
                        for (int j = 0; j < titanic.size(); j++) {
                            if(titanic.get(j).getIdHab()== portesTitanic[i].checkIdHab(idSalaActual) ){
                                habitacionsDisp[contOpcions]=titanic.get(j); 
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

    public void crearTitanic(ArrayList<ubicacions> titanic){
        String adicio;
        int id = 1;

        // Crear Sala
        salap0 sala = new salap0(id);
        titanic.add(sala);
        id++;

        // Crear Menjador
        menjador menjador = new menjador(id);
        titanic.add(menjador);
        id++;

        // Crear Cuina
        cuina cuina = new cuina(id);
        titanic.add(cuina);
        id++;

        // Crear Sales de Neteja
        for (int i = 0; i < 2; i++) {
            adicio = (i == 0) ? "est" : "oest";
            neteja salaNeteja = new neteja(adicio, id);
            titanic.add(salaNeteja);
            id++;
        }

        // Crear WC públics
        for (int i = 0; i < 2; i++) {
            adicio = (i == 0) ? "est" : "oest";
            wc wcPublic = new wc(adicio, id);
            titanic.add(wcPublic);
            id++;
        }

        // Crear Habitacions VIP i WCs VIP
        for (int i = 0; i < 2; i++) {
            adicio = (i == 0) ? "est" : "oest";
            habitacioV habitacioVip = new habitacioV(adicio, id);
            titanic.add(habitacioVip);
            id++;

            wc wcVip = new wc("VIP " + (i + 1) + ", " + adicio, id);
            titanic.add(wcVip);
            id++;
        }

        // Crear Habitació del Capità
        habitacioC habitacioCapita = new habitacioC(id);
        titanic.add(habitacioCapita);
        id++;

        // Crear Habitacions Normals
        for (int i = 0; i <= 6; i++) {
            if (i < 3) {    // 3 habitacions a est
                adicio = "est";
            } else if (i < 6) {     // 3 habitacions a oest
                adicio = "oest";
            } else {    // 2 habitacions nord
                adicio = "nord";
            }
            habitacioN habitacioNormal = new habitacioN(adicio, id);
            titanic.add(habitacioNormal);
            id++;
        }

        // Crear Passadissos
        for (int i = 1; i <= 4; i++) {
            passadis passadis = new passadis("Passadis " + i, id,"");
            titanic.add(passadis);
            id++;
        }

        // Crear altres ubicacions
        salaDeMotors salaMotors = new salaDeMotors(id);
        titanic.add(salaMotors);
        id++;

        capella capella = new capella(id);
        titanic.add(capella);
        id++;

        biblio biblioteca = new biblio(id);
        titanic.add(biblioteca);
        id++;

        salaC salaCapita = new salaC(id);
        titanic.add(salaCapita);
        id++;

        teatre teatre = new teatre(id);
        titanic.add(teatre);
        id++;

        // Crear escales
        for (int i = 0; i < 4; i++) {
            adicio = (i < 2) ? "Passadis " + (i + 1) + " - sala" : "Passadis " + (i - 1) + " - p2";
            escala escales = new escala("",id);
            titanic.add(escales);
            id++;
        }

        // Crear Sala P2
        salap2 salap2 = new salap2(id);
        titanic.add(salap2);
    }


    public porta[] crearPortesTitanic(porta porta){
        //34 sales del titanic
        int[] idHab1={1 ,2 ,1 ,1 ,1 ,21,31,22,21,21,21,21,22,22,22,23,23,23,21,21,21,22,22,22,21,22,32,33};
        int[] idHab2={2 ,3 ,25,30,31,30,22,23,23,13,14,15,16,17,18,19,20,12,4 ,6 ,8 ,5 ,7 ,9 ,32,33,34,34};

        porta[] portes= new porta[idHab1.length];
        portes[0]=porta; //Conexio sala amb submari

        for (int i = 1; i < portes.length; i++) {
            portes[i]=new porta(i, idHab1[i-1], idHab2[i-1]);
        }

        return portes;
    }

    // public 

}