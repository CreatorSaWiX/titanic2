package peces;

import peces.peces;
public class mapa implements Runnable{
    char tauler[][][]={ {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}}};

    char tauler2[][][]={{{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}},
                        {{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '},{' '}}};

    int punts=0;
    Boolean perdut=false;
    public mapa(){

    }

    public void run(){


    }

    public char[][][] getTauler(){
        return tauler;
    }
    public char[][][] getTauler2(){
        return tauler2;
    }

    public void modificarMapa(peces pesa){

        if(pesa.getTocarTerre()){
            for (int i1 = 0; i1 < tauler2.length; i1++) {
                for (int j2 = 0; j2 < tauler2[i1].length; j2++) {
                    tauler2[i1][j2][0]=tauler[i1][j2][0];
                }
            }
            if(pesa.getY()<=1){
                perdut=true;
            }else{
                punts+=10;
                Boolean lineaComp=true;
                for (int i = 0; i < tauler.length; i++) {
                    lineaComp=true;
                    for (int j = 0; j < tauler.length; j++) {
                        if(tauler2[i][j][0]==' '){
                            lineaComp=false;
                        }
                    }
                    if(lineaComp){
                        for (int j = 0; j < tauler.length; j++) {
                            tauler2[i][j][0]=' ';
                        }
                        baixarFilesSobre();
                        punts+=50;
                        i=0;
                    }
                }
            }    
        }

        for (int i = 0; i < tauler.length; i++) {
            for (int j = 0; j < tauler[i].length; j++) {
                tauler[i][j][0]=tauler2[i][j][0];
            }
        }

        for(int i=0;i<3;i++){
            for (int j = 0; j < pesa.getPesa()[i].length(); j++) {
                if(!pesa.getTocarTerre()){
                    if(pesa.getPesa()[i].charAt(j)!=' '){
                        if(pesa.getY()<tauler.length && pesa.getX()<tauler[0].length){
                            tauler[pesa.getY()+i][pesa.getX()+j][0]=pesa.getPesa()[i].charAt(j);
                        }
                    }
                }
            }
        }
    }
    
    public void baixarFilesSobre(){
        boolean canvi=false;
        for (int i = 0; i < tauler2.length-1; i++) {
            canvi=false;
            for (int j = 0; j < tauler2.length; j++) {
                if (i>0 && tauler2[i-1][j][0]=='*' && tauler2[i][j][0]==' ') {
                    tauler2[i][j][0]='*';
                    tauler2[i-1][j][0]=' ';
                    canvi=true;
                }        
            }
            if(canvi){
                i=0;
            }
        }
    }

    public void imprimirTauler(){
        System.out.println("\n\nPunts: "+punts+"\n|-----------|");
        for (int i = 0; i < getTauler().length; i++) {
            System.out.print("|");
            for(int j=0;j < getTauler()[i].length;j++){
                System.out.print(getTauler()[i][j][0]);    
            }
            System.out.println("|");
        }
    }

    public Boolean getPerdut(){
        return perdut;
    }

    public int getPuntuacio(){
        return punts;
    }
}
