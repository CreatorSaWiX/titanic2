import java.util.Scanner;
import java.util.ArrayList;
import ObjectesInmobils.porta;
import Personatges.jugador;
import Sales.*;


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
        ubicacions submari = new ubicacions("submari",0,"");
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
        String rString="";
        int idSegonaSala;
        int cont;

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
                if(rString.equalsIgnoreCase("d")){
                    //Descripció de l'habitació en la que estàs
                    if(jugador.getSalaActual()!=0){
                        System.out.println(titanic.get(jugador.getSalaActual()-1).getDescripcio());
                    }else{
                        System.out.println(submari.getDescripcio());
                    }
                }else if(rString.equalsIgnoreCase("i")){
                    //Inventari
                } else{
                    //Moure entre zones
                    resposta= Integer.parseInt(rString);
                    if(jugador.getSalaActual()==0 && resposta==2){ //En cas de finalitzar el joc
                        fi=true;
                    }else{
                        jugador.moure(habitacionsDisp[resposta-1].getIdHab());
                        if(jugador.getSalaActual()!=0){
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
                adicio = " est";
            } else if (i < 6) {     // 3 habitacions a oest
                adicio = " oest";
            } else {    // 2 habitacions nord
                adicio = " nord";
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
        crearPortaHabitacio("Passadis est", "Habitació normal est", zones);
        crearPortaHabitacio("Passadis est", "Habitació normal est", zones);
        crearPortaHabitacio("Passadis est", "Habitació normal est", zones);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest", zones);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest", zones);
        crearPortaHabitacio("Passadis oest", "Habitació normal oest", zones);
        crearPortaHabitacio("Passadis nord", "Habitació normal nord", zones);
        crearPortaHabitacio("Passadis nord", "Habitació normal nord", zones);
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
}