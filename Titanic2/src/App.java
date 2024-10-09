import java.util.Scanner;

import Objectes.clau;
import Objectes.llanterna;
import Objectes.menjarTauro;
import Objectes.motxilla;
import Objectes.objectesMobils;

import java.util.ArrayList;
import Personatges.jugador;
import Sales.*;
import ObjectesInmobils.*;


public class App {
    Scanner e = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        App start = new App();
        start.start();
    }

    public void start(){

        int opcio=0;
        do{
            //Menú del joc
            System.out.println("\n");
            System.out.println("*****************************");
            System.out.println("****   T I T A N   I I   ****");
            System.out.println("*****************************");
            System.out.println("\nOpcions:");
            System.out.println("1) Jugar \n2) Informació \n3) Sortir");
            opcio = e.nextInt();

            switch (opcio) {
                case 1: jugar(); break;
                case 2: informacio(); break;
                case 3: break;
                default: break;
            }
        } while(opcio != 3);
        
        System.out.println("Gràcies per jugar el joc.");
    }

    public void jugar(){
        //Crear sala submari
        ubicacions submari = new ubicacions("submari",0,"Estàs en el submarí OpenGate - TITAN II. No s'ha detectat cap perill.");
        System.out.println("ubicacions titan creada");

        //Crear les sales del titanic
        ArrayList<ubicacions> titanic = new ArrayList<>();
        crearTitanic(titanic);
        System.out.println("Sales titanic creades");

        //Crear les connexions entre zones del submari
        porta portaSubmari = new porta(0, 1);
        System.out.println("Porta titan creada");

        //Crear les connexions entre zones del titanic
        crearPortesTitanic(portaSubmari, titanic);
        System.out.println("Portes titanic creades");
        
        //Crear objectes en les habitacions
        crearObjectesTitanic(titanic);
        System.out.println("Objectes creades amb èxit");
        System.out.println("tot creat correctement");

        iniciarJuagabilitat(submari,titanic);
    }

    public void informacio(){
        System.out.println("*********************************");
        System.out.println("****   I N F O R M A C I O   ****");
        System.out.println("*********************************");
        System.out.println("Per mostrar descripció, caldrà introduir la lletra 'd'.");
        System.out.println("Per mostrar inventari, caldrà introduir la lletra 'i'.");
        System.out.println("PEGI 3");
        System.out.println("COPYRIGHT © 2024 NexoDynamics S.L");
        System.out.println("\n Escriu 'e' per sortir");
        String text = e.nextLine();
        while(!text.equals("e")){
            text = e.nextLine();
        }
    }

    public void iniciarJuagabilitat(ubicacions submari, ArrayList<ubicacions> titanic){

        jugador jugador = new jugador();
        int resposta=0;
        boolean fi=false;        
        ubicacions[] habitacionsDisp=null;
        String text, rString="";
        int idSegonaSala;
        int cont;
        ubicacions habitaciAntiga;
        int idHabAntiga=0;
        

        while (!fi) {
            int idSalaActual=jugador.getSalaActual();

            //En cas d'estar en el submari
            if(idSalaActual==0){                
                //Agafant el nom de l'habtiació a la que esta enllaçada
                habitacionsDisp = new ubicacions[1];
                habitacionsDisp[0]=titanic.get(0);

            //EN cas de que no estigui en el submari    
            }else{

                idSalaActual--;//El id de la sala actual equival al seu id del array -1, per no haber de fer la resta tot el rato es resta aqui
                if(idSalaActual==0){    //En cas d'estar a la primera sala
                    habitacionsDisp= new ubicacions[titanic.get(idSalaActual).getNumPortes()+1];    //Se li sumarà 1 al array pq el submari tmb ha d'apareixer
                    habitacionsDisp[habitacionsDisp.length-1]=submari; //Al final de tot se li sumarà el submari
                }else{
                    habitacionsDisp= new ubicacions[titanic.get(idSalaActual).getNumPortes()];      //Simplement es crearà l'array amb el numero de portes existents en aquella habitació
                }

                cont=0;//COntador per indicar la posició de la habitació en l'array
                for (porta portaActual : titanic.get(idSalaActual).getPortes()) { //Obtenir les portes de l'habitació
                    idSegonaSala=portaActual.checkIdHab(idSalaActual+1);    //Obtenir el id de l'altra habitació (se li suma 1 al id x tornar a tenir el id de l'habitació)
                    habitacionsDisp[cont]=titanic.get(idSegonaSala-1);    //Obtenir la segona habitació (Se li resta 1 pq la posició de l'habitació en l'array equival al id -1)
                    cont++;
                }
                
            }
            try{
                System.out.println("-----------------------------------------------------------");
                
                System.out.println("Aquestes son les opcions que tens: ");
                for (int i = 0; i < habitacionsDisp.length; i++) {
                    if(habitacionsDisp[i]!=null){
                        System.out.println((i+1) +": Anar a "+habitacionsDisp[i].getNomSala());
                    }
                }
                
                //Opció per sortir del joc
                if(jugador.getSalaActual()==0){
                    System.out.println("2: Sortir del joc");
                }
                
                //Capturar el que escriu el jugador
                rString= e.next();

                //Possibles respostes
                if(rString.equalsIgnoreCase("d")){
                    //Descripció de l'habitació en la que estàs
                    text = "";
                    objectesMobils objectes = submari.getObjecte();
                    if(jugador.getSalaActual()!=0){
                        System.out.println(titanic.get(jugador.getSalaActual()-1).getDescripcio());
                    }else{
                        System.out.println(submari.getDescripcio());
                    }
                    
                    System.out.println("\n Escriu 'e' per sortir");
                    while(!text.equalsIgnoreCase("e")){
                        text = e.next();
                    }
                    
                    
                } else if(rString.equalsIgnoreCase("i")){
                    //Inventari

                } else{
                    //Moure entre zones
                    resposta= Integer.parseInt(rString);
                    if(jugador.getSalaActual()==0 && resposta==2){ //En cas d'estar en el submari i finalitzar el joc
                        fi=true;

                    }else{  //En cas de voler-te moure entre habitacions
                        //Obtenir el id de la sala en la que estàs 
                        idHabAntiga=jugador.getSalaActual();
                        jugador.moure(habitacionsDisp[resposta-1].getIdHab()); //Canviar d'habitació
                       
                        if(idHabAntiga!=0){ //En cas de no estar en el submari
                            // int idHab = jugador.getSalaActual()-1;

                            //titanic.get(idHabAntiga-1).getPortes(); //Codi per obtenir portes
                            for (porta portaActual :  titanic.get(idHabAntiga-1).getPortes()) { //Anar passant porta per porta de l'habiatció antiga
                                if(portaActual.checkIdHab(idHabAntiga)==jugador.getSalaActual()){   //Obtenir la porta a la que acaba d'accedir
                                    if(!portaActual.getObert()){ //En cas de que la porta no estigui oberta
                                        System.out.println("Aquesta porta no està oberta");
                                        System.out.println("Vols desbloquejar la porta? (s/n)");
                                        rString=e.next().toLowerCase();
                                        if(rString.charAt(0)=='s'){
                                            //Aqui s'hauria de comprobar si el jugador té la clau de la prota
                                            
                                            //Aixó es fa en cas de tenir la clau
                                            System.out.println("Desbloqueges la porta i segueixes endavant");
                                            portaActual.setObert(true);
                                            break;
                                        }else{
                                            System.out.println("Et quedes en la mateixa habitació");
                                            jugador.moure(idHabAntiga);
                                            break;
                                        }
                                    }else{
                                        break;
                                    }
                                }
                            }
                            
                            jugador.restarOxigen();
                        }else{
                            jugador.canviarOxigen();
                        }
                        
                    } 
                }   

            }catch(Exception err){
                System.err.println(err);
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
            adicio = (i == 0) ? " est" : " oest";
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
            adicio = (i == 0) ? " est" : " oest";
            habitacioV habitacioVip = new habitacioV(adicio, id);
            titanic.add(habitacioVip);
            id++;

            wc wcVip = new wc("VIP" + adicio, id);
            titanic.add(wcVip);
            id++;
        }

        // Crear Habitació del Capità
        habitacioC habitacioCapita = new habitacioC(id);
        titanic.add(habitacioCapita);
        id++;

        // Crear Habitacions Normals
        for (int i = 0; i <= 7; i++) {
            if (i < 3) {    // 3 habitacions a est
                adicio = " est (10" + (i+1) + ")";
            } else if (i < 6) {     // 3 habitacions a oest
                adicio = " oest (10" + (i+1) + ")";
            } else {    // 2 habitacions nord
                adicio = " nord (10" + (i+1) + ")";
            }
            habitacioN habitacioNormal = new habitacioN(adicio, id);
            titanic.add(habitacioNormal);
            id++;
        }

        // Crear Passadissos
        for (int i = 1; i <= 4; i++) {
            
            switch (i) {
                case 1: passadis passadis = new passadis(" est", id,"Aquest és la descripció del passadís est"); 
                        titanic.add(passadis); break;
                case 2: passadis passadis2 = new passadis(" oest", id, "Aquest és la descripció del passadís oest");
                        titanic.add(passadis2); break;
                case 3: passadis passadis3 = new passadis(" nord", id, "Aquest és la descripció del passadís nord");
                        titanic.add(passadis3); break;
                case 4: passadis passadis4 = new passadis(" planta 2", id, "Aquest és la descripció del passadís de la segona planta");
                        titanic.add(passadis4); break;
                default: break;
            }
            
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
            switch (i) {
                case 0: escala e1 = new escala(" est - sala", id); 
                        titanic.add(e1); break;
                case 1: escala e2 = new escala(" oest - sala", id);
                        titanic.add(e2); break;
                case 2: escala e3 = new escala(" est - planta 2", id);
                        titanic.add(e3); break;
                case 3: escala e4 = new escala(" oest - planta 2", id);
                        titanic.add(e4); break;
                default: break;
            }
            id++;
        }

        // Crear Sala P2
        salap2 salap2 = new salap2(id);
        titanic.add(salap2);
    }


    public void crearPortesTitanic(porta porta, ArrayList<ubicacions> zones){
        crearPortaHabitacio("Sala planta 0", "Menjador", zones);
        crearPortaHabitacio("Menjador", "Cuina", zones);
        crearPortaHabitacio("Sala planta 0", "Sala de motors", zones);
        crearPortaHabitacio("Sala planta 0", "Escala est - sala", zones);
        crearPortaHabitacio("Sala planta 0", "Escala oest - sala", zones);
        crearPortaHabitacio("Passadis est", "Escala est - sala", zones);
        crearPortaHabitacio("Escala oest - sala", "Passadis oest", zones);
        crearPortaHabitacio("Passadis oest", "Passadis nord", zones);
        crearPortaHabitacio("Passadis est", "Passadis nord", zones);
        crearPortaHabitacio("Passadis est", "Habitació normal est (101)", zones);
        crearPortaHabitacio("Passadis est", "Habitació normal est (102)", zones);
        crearPortaHabitacio("Passadis est", "Habitació normal est (103)", zones);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest (104)", zones);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest (105)", zones);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest (106)", zones);
        crearPortaHabitacio("Passadis nord", "Habitació normal nord (107)", zones);
        crearPortaHabitacio("Passadis nord", "Habitació normal nord (108)", zones);
        crearPortaHabitacio("Passadis nord", "Habitació Capità", zones);
        crearPortaHabitacio("Passadis est", "Neteja est", zones);
        crearPortaHabitacio("Passadis est", "W.C.est", zones);
        crearPortaHabitacio("Passadis oest", "W.C.oest", zones);
        crearPortaHabitacio("Passadis oest", "Neteja oest", zones); 
        crearPortaHabitacio("Passadis est", "Habitació VIP est", zones);
        crearPortaHabitacio("Passadis oest", "Habitació VIP oest", zones);
        crearPortaHabitacio("Habitació VIP est", "W.C.VIP est", zones);
        crearPortaHabitacio("Habitació VIP oest", "W.C.VIP oest", zones);
        crearPortaHabitacio("Escala est - planta 2", "Sala planta 2", zones);
        crearPortaHabitacio("Escala oest - planta 2", "Sala planta 2", zones);
        crearPortaHabitacio("Passadis est", "Escala est - planta 2", zones);
        crearPortaHabitacio("Passadis oest", "Escala oest - planta 2", zones);
        crearPortaHabitacio("Sala planta 2", "Passadis planta 2", zones);
        crearPortaHabitacio("Passadis planta 2", "Capella", zones);
        crearPortaHabitacio("Passadis planta 2", "Biblioteca", zones);
        crearPortaHabitacio("Passadis planta 2", "Teatre", zones);
        crearPortaHabitacio("Passadis planta 2", "Sala del capità", zones);
    }

    public void crearPortaHabitacio(String n1, String n2, ArrayList<ubicacions>zones) {
        int id1 = 0;
        int id2 = 0;

        //Si el nom de l'habitació (del index) coincideix amb el que es busca, es guardarà el seu ID
        for (int index = 0; index < zones.size(); index++){
            if(n1.equals(zones.get(index).getNomSala())){
                id1=zones.get(index).getIdHab();
            } else if(n2.equals(zones.get(index).getNomSala())){
                id2=zones.get(index).getIdHab();
            }  
        }
        
        //Les habitacions tenen com a id la seva posició en el array, només cal obtenir els ids i crear la porta
        //Per crear una porta es nessesita el id de l'habitacío 
        
        porta newDoor = new porta(id1, id2);
        
        try {
            zones.get(id1-1).afegirPorta(newDoor);
            zones.get(id2-1).afegirPorta(newDoor);    
        } catch (Exception e) {
            System.out.println("Hi ha un nom d'habitació mal escrit");
        }
    }

    //Afegir mobles a habitacions
    public void crarMobles(ArrayList<ubicacions> zones){
        String[] SP0 = {"taula","sofa"};    //Sala principi
        String[] MJD = {"taula","taula","taula","taula","taula"};    //Sala menjador
        String[] CNA = {"nevera","taula"};  //Cuina
        // String[] SDM = {"taula","sofa"};    //Sala de maquines
        // String[] EES = {"taula","sofa"};    //Escales Est
        // String[] EOS = {"taula","sofa"};    //Escales oest
        // String[] PE = {"taula","sofa"};     //Passadis Est
        // String[] PO = {"taula","sofa"};     //Passadis Oest
        // String[] PN = {"taula","sofa"};     //Passadis Nord
        // String[] HNE = {"taula","llit","armari"};    //Habitacion Normal Est
        // String[] HNN = {"taula","sofa"};    //Habitacio Normal Nord
        String[] HN = {"llit","armari","escriptori"};    //Habitacio Normal 
        String[] HC = {"llit","sofa","escriptori","armari","taula"};     //Habitacio capita
        String[] NO = {"armari"};     //Neteja Oest
        String[] NE = {"armari"};     //Neteja Est
        // String[] EE2 = {"taula","sofa"};    //Escales est 2
        // String[] EO2 = {"taula","sofa"};    //Escales Oest 2
        String[] WCE = {"bater","dutxa","bater","dutxa"};    //WC Est
        String[] WCO = {"bater","dutxa","bater","dutxa"};    //WC Oest
        String[] HVE = {"taula","sofa","llit","armari","escriptori"};    //Habitació vip Est
        String[] HVO = {"taula","sofa","llit","armari","escriptori"};    //Habitació Vip Oest
        String[] WCVO = {"bater","baynera"};   //WC Habitacio Vip Oest
        String[] WCVE = {"bater","banyera"};   //WC Habitacio Vip Est
        String[] SP2 = {"taula","sofa"};    //Sala P2
        String[] SDC = {"taula","sofa"};    //Sala del Capita
        // String[] PP2 = {"taula","sofa"};    //Passadis P2
        String[] CAP = {"taula","sofa"};    //Capella
        String[] BIB = {"taula","sofa"};    //Biblioteca
        String[] TEA = {"taula","sofa"};    //Teatre

        afegirMobles(zones,"Sala planta 0", SP0 );
        afegirMobles(zones,"Menjador", MJD );
        afegirMobles(zones,"Cuina", CNA );
        // afegirMobles(zones,"Sala de motor", SDM );
        // afegirMobles(zones,"Escala est - sala", EES );
        // afegirMobles(zones,"Escala oest - sala", EOS );
        // afegirMobles(zones,"Passadis est", PE );
        // afegirMobles(zones,"Passadis nord", PN );
        // afegirMobles(zones,"Passadis oest", PO );
        afegirMobles(zones,"Habitació normal est (101)", HN );
        afegirMobles(zones,"Habitació normal est (102)", HN );
        afegirMobles(zones,"Habitació normal est (103)", HN );
        afegirMobles(zones,"Habitació normal oest (104)", HN );
        afegirMobles(zones,"Habitació normal oest (105)", HN );
        afegirMobles(zones,"Habitació normal oest (106)", HN );
        afegirMobles(zones,"Habitació normal nord (107)", HN );
        afegirMobles(zones,"Habitació normal nord (108)", HN );
        afegirMobles(zones,"Habitació Capità", HC );
        afegirMobles(zones,"Neteja est", NE );
        afegirMobles(zones,"Neteja oest", NO );
        // afegirMobles(zones,"Escala oest - planta 2", EO2 );
        // afegirMobles(zones,"Escala est - planta 2", EE2 );
        afegirMobles(zones,"W.C.est", WCE );
        afegirMobles(zones,"W.C.oest", WCO );
        afegirMobles(zones,"Habitació VIP est", HVE );
        afegirMobles(zones,"Habitació VIP oest", HVO );
        afegirMobles(zones,"W.C.VIP est", WCVE );
        afegirMobles(zones,"W.C.VIP oest", WCVO );
        afegirMobles(zones,"Sala planta 2", SP2 );
        afegirMobles(zones,"Sala del capità", SDC );
        // afegirMobles(zones,"Passadis planta 2", PP2 );
        afegirMobles(zones,"Capella", CAP );
        afegirMobles(zones,"Biblioteca", BIB );
        afegirMobles(zones,"Teatre", TEA );
    }

    public void afegirMobles(ArrayList<ubicacions>zones, String nom, String [] nomMobles){
        //Aconseguir el ID de l'habitació a partir del nom
        mobles moble = null;
        int id=-1;

        for (ubicacions sala : zones) {
            if(nom.equals(sala.getNomSala())){
                id=sala.getIdHab();
            }
        }
        if(id!=-1){
            zones.get(id-1).assignarNumMobles(nomMobles.length);
            for(int i=0;i<nomMobles.length;i++){
                switch (nomMobles[i]) {
                    case "taula": moble= new taula(); break;
                    case "llit": moble= new llit(); break;
                    case "armari": moble= new armari(); break;
                    case "sofa": moble= new sofa(); break;
                    case "nevera": moble= new nevera(); break;
                    case "escriptori": moble= new escriptori(); break;
                    case "bater": moble= new bater(); break;
                    case "dutxa": moble= new dutxa(); break;
                    case "banyera": moble= new banyera(); break;
                    default: System.out.println("El moble " + nomMobles[i] + " no existeix"); break;   
                }
                if(moble != null){
                    zones.get(id-1).afegirMoble(moble,i);
                } 
            }
        }else{
            System.out.println("El nom de l'habitació"+ nom +" no existeix");
        }
    }

    private void crearObjectesTitanic(ArrayList<ubicacions> titanic){
        //Objectes que han d'estar guardats a l'habitació.
        crearObjecte(titanic,"Sala planta 0", "clau" );
        crearObjecte(titanic,"Menjador", "menjarTauro" );
        crearObjecte(titanic,"Cuina", "ganivet" );
        crearObjecte(titanic,"Sala de motor", "tancaOxigen" );    //Tanca d'oxigen
        crearObjecte(titanic,"Escala est - sala", "" );
        crearObjecte(titanic,"Escala oest - sala", "clau" );
        crearObjecte(titanic,"Passadis est", "clau" );
        crearObjecte(titanic,"Passadis nord", "" );
        crearObjecte(titanic,"Passadis oest", "" );
        crearObjecte(titanic,"Habitació normal est (101)", "clau", "armari" );  //mirar
        crearObjecte(titanic,"Habitació normal est (102)", "" );
        crearObjecte(titanic,"Habitació normal est (103)", "" );
        crearObjecte(titanic,"Habitació normal oest (104)", "" );
        crearObjecte(titanic,"Habitació normal oest (105)", "" );
        crearObjecte(titanic,"Habitació normal oest (106)", "" );
        crearObjecte(titanic,"Habitació normal nord (107)", "" );
        crearObjecte(titanic,"Habitació normal nord (108)", "" );
        crearObjecte(titanic,"Habitació Capità", "" );
        crearObjecte(titanic,"Neteja est", "" );
        crearObjecte(titanic,"Neteja oest", "" );
        crearObjecte(titanic,"Escala oest - planta 2", "" );
        crearObjecte(titanic,"Escala est - planta 2", "" );
        crearObjecte(titanic,"W.C.est", "" );
        crearObjecte(titanic,"W.C.oest", "" );
        crearObjecte(titanic,"Habitació VIP est", "" );
        crearObjecte(titanic,"Habitació VIP oest", "" );
        crearObjecte(titanic,"W.C.VIP est", "" );
        crearObjecte(titanic,"W.C.VIP oest", "" );
        crearObjecte(titanic,"Sala planta 2", "" );
        crearObjecte(titanic,"Sala del capità", "" );
        crearObjecte(titanic,"Passadis planta 2", "" );
        crearObjecte(titanic,"Capella", "" );
        crearObjecte(titanic,"Biblioteca", "" );
        crearObjecte(titanic,"Teatre", "" );

    }

    private void crearObjecte(ArrayList<ubicacions>zones, String nomHabitacio, String nomObjecte){
        objectesMobils objecte = null;
        for (ubicacions sala : zones) {
            if(nomHabitacio.equals(sala.getNomSala())){
                switch (nomObjecte) {
                    case "clau": objecte = new clau(); break;
                    case "llanterna": objecte = new llanterna(); break;
                    case "menjarTauro": objecte = new menjarTauro(); break;
                    case "motxilla": objecte = new motxilla(); break;
                    default:System.out.println("Hi hagut un error intern (Procediment crearObjecte)");break;
                }
                sala.setObjecte(objecte);
                break;
            }
        }
    }

    private void crearObjecte(ArrayList<ubicacions>zones, String nomHabitacio, String nomObjecte, String nomMoble){
        objectesMobils objecte = null;
        for (ubicacions sala : zones) {
            if(nomHabitacio.equals(sala.getNomSala())){
                //En cas que hi hagi algun objecte dins del moble.
            }
        }

    }
}