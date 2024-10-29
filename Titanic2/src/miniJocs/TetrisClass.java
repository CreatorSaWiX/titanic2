package miniJocs;

import java.util.concurrent.*;
import peces.*;
import java.util.Scanner;
import java.util.Random;

class TetrisClass { 
    public void menuJoc(){
        Scanner e = new Scanner(System.in);
        String resposta = "";
        try {
            while (!resposta.equalsIgnoreCase("exit") && !resposta.equalsIgnoreCase("start")) {
                System.out.println("\nBenvingut al TETRIS.\n\nAconsegueix 100 punts per desbloquejar la porta!\n\n    -Escriu START per començar\n\n    -Escriu CONTROLS per veure com jugar\n\n    -Escriu EXIT per sortir\n\n");
                resposta= e.next();
                if(resposta.equalsIgnoreCase("controls")){
                    mostrarControls();
                }
            }

            if(resposta.equalsIgnoreCase("start")){
                iniciJoc();
            }else{
                System.out.println("Fins la pròxima!");
            }

        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void iniciJoc(){
        String peces[][]=crearPeces();
        mapa mapa = new mapa();
    
        peces pesaActual=null;
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20); 
        

        while (!mapa.getPerdut() && mapa.getPuntuacio()<100) {
            try {
                
                pesaActual=crearPesaRandom(peces,mapa);

                scheduler.schedule(pesaActual,0,TimeUnit.SECONDS);


                while(!pesaActual.getTocarTerre() && !mapa.getPerdut()){
                    pesaActual.moureY();
                    mapa.modificarMapa(pesaActual);
                    mapa.imprimirTauler();
                    Thread.sleep(2000);    
                }

            } catch (Exception err) {
                System.out.println(err);
            }

        }
        scheduler.shutdown();
        System.out.println(mapa.getPerdut());
        System.out.println(mapa.getPuntuacio());
    }


    public peces crearPesaRandom(String [][] arrPeces, mapa tauler){
        
        Random r = new Random();
        int posicio = r.nextInt(arrPeces.length);
        peces pesa = new peces(tauler, arrPeces[posicio]);
        return pesa;
    }

    

    public void mostrarControls(){
        Scanner e = new Scanner(System.in);
        System.out.println("CONTROLS\n\n -Escriu 'A' per moure la pesa a l'esquerra o 'D per moure-la a la dreta.\n -Escriu 'S' per girar la pessa.\n\n COM GUANYAR PUNTS \n\n -Colocar una pessa - 10punts\n -Completar una fila - 50 punts \nEscriu enter per tornar");
        e.nextLine();
        
    }

    public String[][] crearPeces(){
        String formes[][]= {
            {
                "   ",
                "***",
                "   "
            },
            {
                "** ",
                "** ",
                "   "
            },
            {
                " * ",
                " * ",
                " **"
            },
            {
                " * ",
                " * ",
                "** "
            },
            {
                "***",
                " * ",
                "   "
            }
    };
        return formes;
    }
}




