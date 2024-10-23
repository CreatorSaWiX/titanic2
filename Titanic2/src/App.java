import java.util.Scanner;

import Objectes.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.ArrayList;
import Personatges.calamarsMitjans;
import Personatges.enemics;
import Personatges.jugador;
import Personatges.meduses;
import Personatges.peixosLlanterna;
import Personatges.taurons;
import Sales.*;
import ObjectesInmobils.*;
import temps.*;


public class App {
    Scanner e = new Scanner(System.in);
    ArrayList<enemics> enemics;

    public static void main(String[] args) throws Exception {
        App start = new App();
        start.start();
    }

    public void start(){

        String opcio="0";
        do{
            //Menú del joc
            System.out.println("\n");
            System.out.println("*****************************");
            System.out.println("****   T I T A N   I I   ****");
            System.out.println("*****************************");
            System.out.println("\nOpcions:");
            System.out.println("1) Jugar \n2) Informació \n3) Termes i condicions \n4) Sortir");
            opcio = e.next();

            switch (opcio) {
                case "1": jugar(); break;
                case "2": tecles(); break;
                case "3": termesCondicions(); break;
                case "4": break;
                default: break;
            }
        } while(!opcio.equals("4"));
        
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
        
        //Crear mobles en les habitacions
        crearMobles(titanic,submari);
        System.out.println("Mobles titanic creats");

        //Crear objectes en les habitacions
        crearObjectesSubmari(submari);
        crearObjectesTitanic(titanic);
        crearEnemicsTitanic(titanic);
        System.out.println("Objectes creades amb èxit");
        System.out.println("tot creat correctement");

        iniciarJuagabilitat(submari,titanic);
    }

    public void tecles(){
        System.out.println("*********************************");
        System.out.println("****   I N F O R M A C I O   ****");
        System.out.println("*********************************");
        System.out.println("Per mostrar descripció, caldrà introduir la lletra 'd'.");
        System.out.println("Per mostrar inventari, caldrà introduir la lletra 'i'.");
        System.out.println("Per mostrar agafar objecte del terre, caldrà introduir la lletra 'g'.");
        System.out.println("Per mostrar tirar objecte a terre, caldrà introduir la lletra 'f'.");
        System.out.println("PEGI 3                  (hi ha una tanca d'oxigen al titan i el pots agafar escribint 't')");
        System.out.println("COPYRIGHT © 2024 NexoDynamics S.L");
        System.out.println("\n Escriu 'e' per sortir");
        String text = e.nextLine();
        while(!text.equals("e")){
            text = e.nextLine();
        }
    }

    public void iniciarJuagabilitat(ubicacions submari, ArrayList<ubicacions> titanic){
        boolean agafarTancaT = false;
        boolean agafarTancaSM = false;
        boolean agafarTancaC = false;
        jugador jugador = new jugador();
        int resposta=0;
        boolean fi=false;        
        ubicacions[] habitacionsDisp=null;
        String text = "";
        String rString;
        boolean espai=true;
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * COMENÇA EL JOC
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
              
        while (!fi && jugador.getOxigen()>0) {
            try{
                //Retornar les habitacions que pot anar
                habitacionsDisp=crearArrayPossiblesMoviments(titanic,submari,jugador);

                //Mostrar les opcions a jugador i retorna la seva resposta
                rString = mostrarOpcions(habitacionsDisp, jugador);
                
                //Possibles respostes
                if(rString.equalsIgnoreCase("d")){
                    //Mostrar descripció de la sala, objectes que hi ha i armaris. Si hi ha armaris, pot mirar els obj que hi ha endins
                    mostrarDescripcio(submari, titanic, agafarTancaT, agafarTancaSM, agafarTancaC, jugador, resposta, fi, text, rString, espai);
                } else if(rString.equalsIgnoreCase("i")){
                    //Interactuar i mostrar el inventari
                    interactuarAmbInventari(jugador,submari,titanic);
                } else{
                    fi = moureEntreHabitacions(rString,jugador, submari, titanic,habitacionsDisp); 
                    jugador.restarOxigen();
                }   
            }catch(Exception err){
                System.out.println("Escriu una opció vàlida");
                resposta=0;
            }
        }

        if(jugador.getOxigen()<=0){
            System.out.println("GAME OVER :( ");
        }
    }
    
    private ubicacions[] crearArrayPossiblesMoviments(ArrayList<ubicacions> titanic, ubicacions submari, jugador jugador){
        boolean coincideixClau=false;
        ubicacions habitacionsDisp[] = null;
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

                int cont=0;//COntador per indicar la posició de la habitació en l'array
                for (porta portaActual : titanic.get(idSalaActual).getPortes()) { //Obtenir les portes de l'habitació
                    int idSegonaSala=portaActual.checkIdHab(idSalaActual+1);    //Obtenir el id de l'altra habitació (se li suma 1 al id x tornar a tenir el id de l'habitació)
                    habitacionsDisp[cont]=titanic.get(idSegonaSala-1);    //Obtenir la segona habitació (Se li resta 1 pq la posició de l'habitació en l'array equival al id -1)
                    cont++;
                }
            }
            return habitacionsDisp;
    }


    private void mostrarDescripcio(ubicacions submari, ArrayList<ubicacions> titanic, boolean agafarTancaT, boolean agafarTancaSM, boolean agafarTancaC, jugador jugador, int resposta, boolean fi, String text, String rString, boolean espai) {
        //Descripció de l'habitació en la que estàs
        text = "";
        if(jugador.getSalaActual()!=0){
            if(titanic.get(jugador.getSalaActual()-1).getObjecte()==null){
                String salaActual = titanic.get(jugador.getSalaActual()-1).getNomSala();
                if(salaActual.equalsIgnoreCase("Sala de motors") && !agafarTancaSM){
                    agafarTancaSM = tancaOxigen(salaActual, jugador);
                } else if(salaActual.equalsIgnoreCase("Capella") && !agafarTancaC){
                    agafarTancaC = tancaOxigen(salaActual, jugador);
                }else{
                    System.out.println(titanic.get(jugador.getSalaActual()-1).getDescripcio());
                }
            }else{
                System.out.println(titanic.get(jugador.getSalaActual()-1).getDescripcio());
                rString=e.next();
                if(rString.equals("g")){
                    if(titanic.get(jugador.getSalaActual()-1).getObjecte() instanceof clau == false){
                        espai=jugador.agafarObjecte(titanic.get(jugador.getSalaActual()-1).getObjecte());
                    }else{
                        jugador.afegirClau(titanic.get(jugador.getSalaActual()-1).getObjecte());
                        espai=true;
                    }
                    if(espai){
                        titanic.get(jugador.getSalaActual()-1).agafarObjecte();
                    }else{
                        System.out.println("No tens prou espai per guardar l'objecte");
                    }
                }    
            }
        }else{
            System.out.println(submari.getDescripcio());
        }
        
        System.out.println("\n Escriu 'e' per sortir");
        
        while(!text.equalsIgnoreCase("e")){
            text = e.next();
            if(jugador.getSalaActual()==0){     //En cas que el jugador està en el submarí
                if(submari.getObjecte()==null){
                    if(text.equalsIgnoreCase("g") && !agafarTancaT){
                        System.out.println("Has obtingut una tanca d'oxigen! (+50 Max oxigen)");
                        jugador.actualitzarMaxOxigen();
                        agafarTancaT = true;
                    } else if(text.equalsIgnoreCase("g")){
                        System.out.println("Ja has agafat la tanca d'oxigen punyetero!");
                    }
                }else{
                    if(text.equalsIgnoreCase("g")){
                        if(submari.getObjecte()!=null){
                            if(submari.getObjecte() instanceof objectesMobils){
                                espai=jugador.agafarObjecte(submari.getObjecte());
                            }else{
                                jugador.afegirClau(submari.getObjecte());
                                espai=true;
                            }
                            if(espai){
                                submari.agafarObjecte();
                            }
                        }else{
                            System.out.println("No hi ha objectes en el terra");
                        }
                    }
                }
            }    
            try {
                if(jugador.getSalaActual()==0){
                    if(submari.getMobles()!=null){
                        if(text.equals("1")){
                            submari.getMobles()[0].llegirMapa();
                        }
                        else if(Integer.parseInt(text)<=submari.getMobles().length){
                            objectesMobils objAgafat=submari.getMobles()[Integer.parseInt(text)-1].interactuarAmbMoble(jugador);
                            if(objAgafat!=null){
                                jugador.agafarObjecte(objAgafat);
                            }
                            text="e";
                        }
                    } 
                }else{
                    if(titanic.get(jugador.getSalaActual()-1).getMobles()!=null){
                        if(Integer.parseInt(text)<=titanic.get(jugador.getSalaActual()-1).getMobles().length){
                            jugador.agafarObjecte(titanic.get(jugador.getSalaActual()-1).getMobles()[Integer.parseInt(text)-1].interactuarAmbMoble(jugador));
                            text="e";
                        }
                    }    
                }
                
            } catch (Exception err) {
                
            }
        }
    }

    private String mostrarOpcions(ubicacions[] habitacionsDisp, jugador jugador){
        String rString;
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

        return rString;
    }

    private void interactuarAmbInventari(jugador jugador, ubicacions submari, ArrayList<ubicacions> titanic){
        String text="";
        jugador.mostrarInventari();
        System.out.println("Escriu el nom del Objecte que vols deixar a terre o 'e' per sortir");
        while(!text.equalsIgnoreCase("e")){
            text = e.next();
            jugador.deixarObjecte(text,submari,titanic);
        }
    }

    private boolean moureEntreHabitacions(String rString, jugador jugador, ubicacions submari, ArrayList<ubicacions> titanic, ubicacions[] habitacionsDisp){
        //Moure entre zones
        int idHabAntiga=0;
        boolean fi=false;
        int resposta= Integer.parseInt(rString);
        boolean coincideixClau=false;
        
        if(jugador.getSalaActual()==0 && resposta==2){ //En cas d'estar en el submari i finalitzar el joc
            fi=true;
        }else{ 
            //En cas de voler-te moure entre habitacions 
            idHabAntiga=jugador.getSalaActual();    //Obtenir el id de la sala en la que estàs abans de moure
            
            jugador.moure(habitacionsDisp[resposta-1].getIdHab()); //Canviar d'habitació


            
            if(jugador.getSalaActual()!=0){
                salaEnemics(titanic, jugador, idHabAntiga, enemics); 
            }else{
                System.out.println("Canvies la tanca d'oxigen gastada per una de nova.");
                jugador.canviarOxigen();
            }

            if(idHabAntiga!=0){ //En cas de que no estava en el submari
                //Per portes bloquejades
                for (porta portaActual : titanic.get(idHabAntiga-1).getPortes()) { //Anar passant porta per porta de l'habiatció antiga
                    if(portaActual.checkIdHab(idHabAntiga)==jugador.getSalaActual()){   //Obtenir la porta a la que acaba d'accedir
                        if(!portaActual.getObert()){ //En cas de que la porta no estigui oberta
                            System.out.println("Aquesta porta no està oberta");
                            System.out.println("Vols desbloquejar la porta? (s/n)");
                            rString=e.next();
                            if(rString.toLowerCase().charAt(0)=='s'){
                                int i=0;
                                //Aqui s'hauria de comprobar si el jugador té la clau de la prota
                                for (objectesMobils clauActual : jugador.getClauer()) {
                                    if(clauActual.getIdClau()==jugador.getSalaActual()){
                                        coincideixClau=true;
                                        jugador.utilitzarClau(i);
                                        break;
                                    }else{
                                        i++;
                                    }
                                }
                                if(coincideixClau){
                                    //Aixó es fa en cas de tenir la clau
                                    System.out.println("Desbloqueges la porta i segueixes endavant");
                                    portaActual.setObert(true);
                                    break;
                                }else{
                                    jugador.moure(idHabAntiga);
                                    System.out.println("No tenies aquesta clau, perds oxigen per intentar obrir-la");
                                    break;
                                }
                                
                            }else{
                                System.out.println("Et quedes en la mateixa habitació");
                                jugador.moure(idHabAntiga);
                                break;
                            }
                        }else if(!portaActual.getEnterrada()){
                            portaEnterrada(portaActual,jugador,idHabAntiga);
                        }else{
                            if(jugador.getSalaActual()==0){
                                jugador.canviarOxigen();
                            }
                            break;
                        }
                    }
                }

                //En cas que la sala sigui fosca
                salaFosca(jugador,titanic,idHabAntiga);
                
            }
        }
        return fi;
    }

    private void portaEnterrada(porta portaActual, jugador jugador, int idHabAntiga){
        System.out.println("Una montanya de sorra et tapa el cami, amb una pala podries apartar la sorra.");
        if(jugador.tePala()){
            System.out.println("Vols utilitzar una pala? (s/n)");
            String r=e.next().toLowerCase();
            if(r.equals("s")){
                jugador.utilitzarPala();
                portaActual.desenterrar();
            }else if(r.equals("n")){
                System.out.println("Tornes enrere");
                jugador.moure(idHabAntiga);
            }else{
                System.out.println("Tornes enrere");
                jugador.moure(idHabAntiga);
            }
        }else{
            System.out.println("No ten una pala amb la qual apartar la sorra.");
            jugador.moure(idHabAntiga);

        }
    }

    private void salaEnemics(ArrayList<ubicacions> titanic, jugador jugador, int idHabAntiga, ArrayList<enemics> enemics){
        String habFutur = titanic.get(jugador.getSalaActual()-1).getNomSala();
        for (enemics enemic : enemics) {
            if(enemic.getSalaEnemic().equals(habFutur)){
                switch (enemic.getNomEnemic()) {
                    case "medusa": casMedusa(jugador, idHabAntiga); break;
                    case "tauro": casTauro(jugador, idHabAntiga); break;
                    case "peixLlanterna": casPeixLLanterna(jugador, idHabAntiga); break;
                    case "calamar": casCalamar(jugador, idHabAntiga); break;
                    default: break;
                }
            }
        }            
    }

    private void casTauro(jugador jugador, int idHabAntiga){
        String text;
        System.out.print("Hi ha un tauró.");
        //En cas de tenir mennjar de taurons
        if(jugador.objecteEnInventari("menjarTauro")){
            System.out.println(" Vols utilitzar el menjar de taurons que tens? (s/n)");
            text = e.next();
            while (text.equalsIgnoreCase("s") && text.equalsIgnoreCase("n")) {
                System.out.println("Sí (s) o No (n)????");
                text = e.next();
            }
            if(text.equals("s")){
                System.out.println("Utilitzes el menjar del tauró per distreure'l.");
                jugador.utilitzarObjecte("menjarTauro");
            }else{
                //En cas de no utilitzar el menjar de taurons
                atacar(jugador,idHabAntiga);
            }
        }else{
            //Si no es té menjar taurons 
            atacar(jugador,idHabAntiga);
        }    
    }

    private void casMedusa(jugador jugador, int idHabAntiga){
        String text;
        System.out.print("Hi ha una medusa. Vols equivar-los? (s/n)");
        text = e.next();
        while (text.equalsIgnoreCase("s") && text.equalsIgnoreCase("n")) {
            System.out.println("Sí (s) o No (n)????");
            text = e.next();
        }
        if(text.equals("s")){
            meduses.esquivar(e, jugador);
        } else {
            jugador.moure(idHabAntiga);
        }
    }

    private void casPeixLLanterna(jugador jugador, int idHabAntiga){
        String text;
        System.out.print("Hi ha un peix llanterna.");
        if(jugador.objecteEnInventari("menjarTauro")){
            System.out.println(" Vols utilitzar el menjar de taurons que tens? (s/n)");
            text = e.next();
            while (text.equalsIgnoreCase("s") && text.equalsIgnoreCase("n")) {
                System.out.println("Sí (s) o No (n)????");
                text = e.next();
            }
            if(text.equals("s")){
                System.out.println("Utilitzes el menjar del tauró per distreure'l.");
                jugador.utilitzarObjecte("menjarTauro");
            }
        }
    }

    private void casCalamar(jugador jugador, int idHabAntiga){
        String text;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20); 
        

        InteraccioUsuari interaccioUsuari = new InteraccioUsuari(jugador);        
        ComptadorCalamars comptador = new ComptadorCalamars(false,jugador,interaccioUsuari);

        System.out.println("Hi ha un calamar. Cada 2s perdràs oxigen, espameja tecla 'a' per alliberar-te");        
        scheduler.schedule(interaccioUsuari,0,TimeUnit.SECONDS);
        int cont=2;
        while (!interaccioUsuari.finalitzat() && jugador.getOxigen()>0) {
            try {
                Thread.sleep(2000); 
                scheduler.schedule(comptador,cont,TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scheduler.shutdown();

        if(jugador.objecteEnInventari("ganivet") || jugador.objecteEnInventari("arpo")){
            System.out.println("T'alliberes. Vols matar el calamar? (s/n)");
            text = e.next();
            while (text.equalsIgnoreCase("s") && text.equalsIgnoreCase("n")) {
                System.out.println("Sí (s) o No (n)????");
                text = e.next();
            }
            if(text.equalsIgnoreCase("s")){
                atacarAmbArma(jugador, text, idHabAntiga);
            } else {
                jugador.moure(idHabAntiga);
            }
        }
    }

    private void atacar(jugador jugador, int idHabAntiga){
        String text="";
        System.out.println(" Vols atacar? (s/n)");
        text = e.next();
        while (text.equalsIgnoreCase("s") && text.equalsIgnoreCase("n")) {
            System.out.println("Sí (s) o No (n)????");
            text = e.next();
        }
        if(text.equalsIgnoreCase("s")){
            atacarAmbArma(jugador, text, idHabAntiga);
        } else {
            jugador.moure(idHabAntiga);
        }
    }

    private void atacarAmbArma(jugador jugador, String text, int idHabAntiga){
        if(jugador.objecteEnInventari("ganivet") && jugador.objecteEnInventari("arpo")){
            System.out.println("Quina arma vols utilitzar? Ganivet(g) o Arpo(a)");
            text = e.next();
            while(!text.equalsIgnoreCase("g") && !text.equalsIgnoreCase("a")){
                System.out.println("Escriu 'g' per utilitzar ganivet o escriu 'a' per utilitzar arpó");
                text = e.next();
            }

            if(text.equalsIgnoreCase("g")){
                jugador.getObjecteInventari("ganivet").utilitzar(jugador);
                jugador.utilitzarObjecte("ganivet");
            } else {
                jugador.getObjecteInventari("arpo").utilitzar(jugador);
                jugador.utilitzarObjecte("arpo");
            }

        } else if(jugador.objecteEnInventari("ganivet")){
            jugador.getObjecteInventari("ganivet").utilitzar(jugador);
            jugador.utilitzarObjecte("ganivet");
        } else  if(jugador.objecteEnInventari("arpo")){
            jugador.getObjecteInventari("arpo").utilitzar(jugador);
            jugador.utilitzarObjecte("arpo");
        } else {
            System.out.println("No tens cap arma");
            jugador.moure(idHabAntiga);
        }
    }

    private void salaFosca(jugador jugador, ArrayList<ubicacions>titanic, int idHabAntiga){
        String rString="";
        if(jugador.getSalaActual()!=0){
            if(titanic.get(jugador.getSalaActual()-1).getFosc()){
                if(!jugador.llenternaUtilitzable()){
                    System.out.println("No pots entrar en una habitació fosca sense la llanterna amb bateria!");
                    jugador.moure(idHabAntiga);
                }else{
                    //En cas de portar la llenterna amb bateria
                    System.out.println("Vols encendre la llenterna? (s/n)");
                    rString=e.next();
                    if(rString.toLowerCase().equals("s")){
                        jugador.utilitzarLlenterna();
                    }else if(rString.toLowerCase().equals("n")){
                        jugador.moure(idHabAntiga);
                        System.out.println("Tornes a l'habitació a la que estaves amb menys oxigen");
                    }else{
                        jugador.moure(idHabAntiga);
                        System.out.println("Tornes a l'habitació a la que estaves amb menys oxigen");
                    }
                }
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
            if(i<3){    //Fer fosques les habitacions de la 101 a la 103
                habitacioNormal.setFosc(true);
            }
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

        //Les boleanes indican si la porta esta desbloqeuejada o no, la segona indica si esta enterrada o no
        crearPortaHabitacio("Sala planta 0", "Menjador", zones,true,true);
        crearPortaHabitacio("Menjador", "Cuina", zones,false,true);
        crearPortaHabitacio("Sala planta 0", "Sala de motors", zones,true,true);
        crearPortaHabitacio("Sala planta 0", "Escala est - sala", zones,true,true);
        crearPortaHabitacio("Sala planta 0", "Escala oest - sala", zones,true,true);
        crearPortaHabitacio("Escala est - sala", "Escala oest - sala", zones,true,true);
        crearPortaHabitacio("Passadis est", "Escala est - sala", zones,true,true);
        crearPortaHabitacio("Escala oest - sala", "Passadis oest", zones,true,false);
        crearPortaHabitacio("Passadis oest", "Passadis nord", zones,true,true);
        crearPortaHabitacio("Passadis est", "Passadis nord", zones,true,true);
        crearPortaHabitacio("Passadis est", "Habitació normal est (101)", zones,false,true);
        crearPortaHabitacio("Passadis est", "Habitació normal est (102)", zones,false,true);
        crearPortaHabitacio("Passadis est", "Habitació normal est (103)", zones,true,true);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest (104)", zones,false,true);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest (105)", zones,true,true);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest (106)", zones,true,true);
        crearPortaHabitacio("Passadis nord", "Habitació normal nord (107)", zones,false,true);
        crearPortaHabitacio("Passadis nord", "Habitació normal nord (108)", zones,true,true);
        crearPortaHabitacio("Passadis nord", "Habitació Capità", zones,true,true); //Porta contrasenya
        crearPortaHabitacio("Passadis est", "Neteja est", zones,false,true);
        crearPortaHabitacio("Passadis est", "W.C.est", zones,true,true);
        crearPortaHabitacio("Passadis oest", "W.C.oest", zones,true,true);
        crearPortaHabitacio("Passadis oest", "Neteja oest", zones,false,true); 
        crearPortaHabitacio("Passadis est", "Habitació VIP est", zones,false,true); //Porta minijoc
        crearPortaHabitacio("Passadis oest", "Habitació VIP oest", zones,false,true);   //Porta minijoc
        crearPortaHabitacio("Habitació VIP est", "W.C.VIP est", zones,true,true);
        crearPortaHabitacio("Habitació VIP oest", "W.C.VIP oest", zones,true,true);
        crearPortaHabitacio("Escala est - planta 2", "Sala planta 2", zones,true,true);
        crearPortaHabitacio("Escala oest - planta 2", "Sala planta 2", zones,true,true);
        crearPortaHabitacio("Passadis est", "Escala est - planta 2", zones,true,true);
        crearPortaHabitacio("Passadis oest", "Escala oest - planta 2", zones,true,true);
        crearPortaHabitacio("Sala planta 2", "Passadis planta 2", zones,true,true);
        crearPortaHabitacio("Passadis planta 2", "Capella", zones,true,false);
        crearPortaHabitacio("Passadis planta 2", "Biblioteca", zones,true,true);
        crearPortaHabitacio("Passadis planta 2", "Teatre", zones,true,true);
        crearPortaHabitacio("Passadis planta 2", "Sala del capità", zones,true,true);   //Porta contrasenya
    }

    public void crearPortaHabitacio(String n1, String n2, ArrayList<ubicacions>zones, Boolean obert, Boolean enterrat) {
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
        
        porta newDoor = new porta(id1, id2,obert,enterrat);
        
        try {
            zones.get(id1-1).afegirPorta(newDoor);
            zones.get(id2-1).afegirPorta(newDoor);    
        } catch (Exception e) {
            System.out.println("Hi ha un nom d'habitació mal escrit");
        }
    }

    //Afegir mobles a habitacions
    public void crearMobles(ArrayList<ubicacions> zones,ubicacions submari){
        //Crear mobles submari
        String[] SUB = {"mapa","armari"};    //Submari
        afegirMoblesSubmari(submari, SUB);


        //Crear obles titanic
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
        String[] WCVO = {"bater","banyera"};   //WC Habitacio Vip Oest
        String[] WCVE = {"bater","banyera"};   //WC Habitacio Vip Est
        String[] SP2 = {"taula","taula","taula","cafeteria"};    //Sala P2 
        String[] SDC = {"taula","sofa"};    //Sala del Capita   TODO
        // String[] PP2 = {"taula","sofa"};    //Passadis P2
        String[] CAP = {"taula","sofa"};    //Capella           TODO
        String[] BIB = {"escriptori","escriptori","sofa"};    //Biblioteca
        String[] TEA = {"taula","sofa"};    //Teatre            TODO

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

    public void afegirMoblesSubmari(ubicacions submari, String [] nomMobles){
        submari.assignarNumMobles(nomMobles.length);
        mobles moble=null;
        for (int i = 0; i < nomMobles.length; i++) {
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
                case "cafeteria": moble= new cafeteria(); break;
                case "mapa": moble= new mapa(); break;
                default: System.out.println("El moble " + nomMobles[i] + " no existeix"); break;   
            }
            if(moble != null){
                submari.afegirMoble(moble,i);
            }
        }
    }

    public void afegirMobles(ArrayList<ubicacions>zones, String nom, String [] nomMobles){
        //Aconseguir el ID de l'habitació a partir del nom
        mobles moble = null;
        int id= obtenirIdHabitacio(zones,nom);
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
                    case "cafeteria": moble= new cafeteria(); break;
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

        //Ganivet, menjarTauro, llanterna.
        crearObjecte(titanic,"Sala planta 0", "clau", "", "Cuina" );
        crearObjecte(titanic,"Sala planta 0", "ganivet", "taula", "" );
        crearObjecte(titanic,"Menjador", "menjarTauro","taula","" );
        crearObjecte(titanic,"Menjador", "menjarTauro","","" );
        crearObjecte(titanic,"Menjador", "menjarTauro","taula","" );
        crearObjecte(titanic,"Cuina", "ganivet","taula","" );
        //Escala est - sala no hi ha res
        crearObjecte(titanic,"Escala oest - sala", "clau","","Neteja est" );   
        crearObjecte(titanic,"Passadis est", "clau","","Habitació normal est (102)" );
        crearObjecte(titanic,"Passadis nord", "clau","","Habitació normal est (101)" );
        //Passadís oest està tapada (usar pala), no hi ha res.
        crearObjecte(titanic,"Habitació normal est (101)", "clau", "escriptori","Neteja est" ); 
        crearObjecte(titanic,"Habitació normal est (101)", "motxilla", "armari","" ); //És una habitació fosca i tancada
        //Habitació 102 fosca i tancada no hi ha res.
        crearObjecte(titanic,"Habitació normal est (103)", "clau", "armari","Neteja oest" ); 
        crearObjecte(titanic,"Habitació normal oest (104)", "motxilla","escriptori","" ); 
        crearObjecte(titanic,"Habitació normal oest (104)", "clau","","Habitació normal nord (107)" ); 
        //Habitació 105 oberta no hi ha res.
        crearObjecte(titanic,"Habitació normal oest (106)", "clau","","Habitació normal oest (104)" );  
        crearObjecte(titanic,"Habitació normal nord (107)", "ganivet","armari","" );
        crearObjecte(titanic,"Habitació normal nord (107)", "ganivet","armari","" );
        crearObjecte(titanic,"Habitació normal nord (107)", "ganivet","armari","" );
        crearObjecte(titanic,"Habitació normal nord (107)", "arpo","llit","" );
        //Habitació 108 oberta no hi ha res
        crearObjecte(titanic,"Habitació Capità", "arpo","","" );
        crearObjecte(titanic,"Habitació Capità", "llibreta","escriptori","" ); //TODO (3 fulls(1. El pare ha establert la contrasenya per les figures geomètriques, 2. Triangle 2, 3. cercle 3 ))
        crearObjecte(titanic,"Neteja est", "pala","","" );          
        crearObjecte(titanic,"Neteja oest", "clau","armari","Habitació VIP est" );
        crearObjecte(titanic,"Neteja oest", "clau","armari","Habitació VIP oest" );
        //Escala oest - planta 2 no hi ha res
        crearObjecte(titanic,"Escala est - planta 2", "","","" );       //TODO Paper de la llibreta (Dibuix hexàgon 4)
        crearObjecte(titanic,"W.C.est", "menjarTauro","bater","" ); 
        crearObjecte(titanic,"W.C.est", "","","" );     //TODO Paper de la llibreta (Contrasenya hab capità)
        crearObjecte(titanic,"W.C.oest", "ganivet","banyera","" );
        //Habitació VIP est no hi ha res
        crearObjecte(titanic,"Habitació VIP oest", "ganivet","escriptori","" );
        crearObjecte(titanic,"W.C.VIP est", "menjarTauro","banyera","" );
        //W.C.VIP oest no hi ha res
        crearObjecte(titanic,"Sala planta 2", "pala","","" );
        crearObjecte(titanic,"Sala del capità", "arpo","","" );
        //Passadis planta 2 no hi ha cap objecte     //TODO pista per un dels minijocs (desde descripció com alguna rascada en la paret o algo semblant)
        //Capella hi ha la tanca d'oxigen
        crearObjecte(titanic,"Biblioteca", "","","" );      //TODO Paper de la llibreta (Dibuix quadrat 1)
        crearObjecte(titanic,"Teatre", "diamantHope" ,"","");     //Aquí s'acabar el joc i és menjat pel calamar Gegant.
    }

    public void crearObjecte(ArrayList<ubicacions>zones, String nomHabitacio, String nomObjecte, String nomMoble, String nomHabitacioClau){
        objectesMobils objecte = null;
        int id=0;
        for (ubicacions sala : zones) {
            if(nomHabitacio.equals(sala.getNomSala())){
                switch (nomObjecte) {
                    case "clau":
                         id= obtenirIdHabitacio(zones,nomHabitacioClau);
                         if(id!=-1){
                            objecte = new clau(id,nomHabitacioClau);
                         }else{
                            System.out.println("Al intentar crear la clau per "+nomHabitacio+" hi ha hagut un error");
                         }
                         break;
                    case "llanterna": objecte = new llanterna(); break;
                    case "menjarTauro": objecte = new menjarTauro(); break;
                    case "motxilla": objecte = new motxilla(); break;
                    case "ganivet": objecte = new ganivet(); break;
                    case "pala": objecte = new pala();break;
                    case "arpo": objecte = new arpo();break;
                    default: break;
                }
                if(objecte!=null){
                    if(nomMoble.equals("")){
                        sala.setObjecte(objecte);
                    }else{
                        try{
                            for (mobles moble : sala.getMobles()) {
                                if(moble.getNom().equals(nomMoble)){
                                    moble.afegirObecte(objecte);
                                }
                            }
                        }catch(Exception err){
                            System.out.println("L'habitació "+ nomHabitacio +" no té mobles");
                        }    
                    }
                }
                break;
            }
        }
    }

    public void crearObjectesSubmari(ubicacions submari){
        objectesMobils objecte = new llanterna();
        submari.setObjecte(objecte);
    }

    public void crearEnemicsTitanic (ArrayList<ubicacions> titanic){
        enemics = new ArrayList<>();
        crearEnemics(titanic, "Menjador", "calamarMitja", enemics);  
        crearEnemics(titanic, "W.C.VIP est", "calamarMitja", enemics);  
        crearEnemics(titanic, "Passadis nord", "tauro", enemics);  
        crearEnemics(titanic, "Habitació normal est (101)", "peixLlanterna", enemics);  
        crearEnemics(titanic, "Habitació normal est (102)", "peixLlanterna", enemics);  
        crearEnemics(titanic, "Habitació normal oest (106)", "tauro", enemics);  
        crearEnemics(titanic, "W.C.oest", "medusa", enemics);  
        crearEnemics(titanic, "Neteja oest", "tauro", enemics);  
        crearEnemics(titanic, "Escala oest - planta 2", "medusa", enemics);  
        crearEnemics(titanic, "Biblioteca", "medusa", enemics);  
        crearEnemics(titanic, "Passadis planta 2", "tauro", enemics);  
    }

    public void crearEnemics(ArrayList<ubicacions>zones, String nomHabitacio, String nomEnemic, ArrayList<enemics> enemics){
        enemics enemic = null;
        for (ubicacions sala : zones) {
            if(nomHabitacio.equals(sala.getNomSala())){
                switch (nomEnemic) {
                    case "tauro": enemic = new taurons(nomHabitacio); break;
                    case "calamarMitja": enemic = new calamarsMitjans(nomHabitacio); break;
                    case "medusa": enemic = new meduses(nomHabitacio); break;
                    case "peixLlanterna": enemic = new peixosLlanterna(nomHabitacio); break;
                    default: /*salta*/ break;
                }
                enemics.add(enemic);
            }
        }
    }

    private boolean tancaOxigen(String salaActual, jugador jugador){
        boolean agafar = false;
        System.out.println("Hi ha una tanca d'oxigen en " + salaActual + ".");
        String resposta = e.next();
        if(resposta.equalsIgnoreCase("g")){
            System.out.println("Has obtingut una tanca d'oxigen! (+50 Max oxigen)");
            jugador.actualitzarMaxOxigen();
            agafar = true;
        } 
        return agafar;
    }

    public int obtenirIdHabitacio(ArrayList<ubicacions>zones,String nom){
        int id=-1;
        for (ubicacions ubicacio : zones) {
            if(nom.equals(ubicacio.getNomSala())){
                id=ubicacio.getIdHab();
            }
        }
        return id;
    }

    public void termesCondicions(){
        System.out.println("********************************************");
        System.out.println("**       Termes i Condicions de TITAN II  **");
        System.out.println("********************************************");
        System.out.println("Copyright © 2024 NexoDynamics S.L. Tots els drets reservats.");
        System.out.println("\n1. ★ Edat Recomanada ★");
        System.out.println("   TITAN II és un joc classificat per a majors de 3 anys segons el sistema PEGI 3.");
        System.out.println("   El contingut del joc és apte per a tots els públics.");
        System.out.println("\n2. ★ Propietat Intel·lectual ★");
        System.out.println("   Totes les imatges, noms, personatges i altres elements dins del joc són propietat exclusiva de NexoDynamics S.L.");
        System.out.println("   Estan protegits per les lleis de drets d'autor.");
        System.out.println("\n3. ★ Limitació de Responsabilitat ★");
        System.out.println("   NexoDynamics S.L. no es fa responsable de danys directes o indirectes derivats de l'ús del joc,");
        System.out.println("   incloent-hi qualsevol pèrdua de progrés, errors tècnics o fallades del sistema.");
        System.out.println("\n4. ★ Ús del Joc ★");
        System.out.println("   Aquest joc està destinat a l'ús personal i no comercial.");
        System.out.println("   Queda prohibit modificar, copiar, distribuir o vendre qualsevol part del joc sense permís explícit de NexoDynamics S.L.");
        System.out.println("   !!Hi ha una tanca d'oxigen dins del titan II. En aquella zona, entra una lletra 't' per obtenir aquella millora de tanca d'oxigen!!");
        System.out.println("\n5. ★ Contingut del Joc ★");
        System.out.println("   TITAN II narra la història d'en Pepet en la seva aventura submarina per explorar el Titanic.");
        System.out.println("   El jugador haurà de resoldre enigmes, trobar objectes i evitar perills marins.");
        System.out.println("   El joc inclou armes limitades i esdeveniments desafiants.");
        System.out.println("\n6. ★ Modificacions i Actualitzacions ★");
        System.out.println("   NexoDynamics S.L. es reserva el dret a actualitzar o modificar el joc en qualsevol moment,");
        System.out.println("   així com aquests termes i condicions.");
        System.out.println("\n7. ★ Jurisdicció ★");
        System.out.println("   Aquestes condicions es regeixen per la legislació espanyola, i qualsevol disputa serà resolta als tribunals competents d'Espanya.");
        System.out.println();
        System.out.println("********************************************");
        System.out.println("   En accedir i jugar a TITAN II, accepteu   ");
        System.out.println("   aquests termes i condicions.");
        System.out.println("********************************************");
        System.out.println("\n Escriu 'e' per sortir");
        String text = e.nextLine();
        while(!text.equals("e")){
            text = e.nextLine();
        }
    }
}