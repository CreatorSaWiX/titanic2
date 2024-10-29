package peces;

import peces.mapa;
import java.util.Scanner;
public class peces implements Runnable {
    int y;
    int x;
    
    String pesa[];
    mapa tauler=null;
    Boolean tocarTerre=false;
    Scanner e = new Scanner(System.in);
    public peces(mapa map, String pesa[]){
        this.pesa=pesa;
        y=0;
        x=4;
        this.tauler=map;
        this.e = e;
    }

    public void run(){
        
        String r;
        while (!tocarTerre) {
            r= e.next();
            if (!tocarTerre) {
                if(r.equalsIgnoreCase("w")){
                    girar();
                    tauler.modificarMapa(this);
                    tauler.imprimirTauler();
                }else if(r.equalsIgnoreCase("d")){
                    moureX(1);
                    tauler.modificarMapa(this);
                    tauler.imprimirTauler();
                    
                }else if(r.equalsIgnoreCase("a")){
                    moureX(-1);
                    tauler.modificarMapa(this);
                    tauler.imprimirTauler();
                }else if(r.equalsIgnoreCase("s")){
                    moureY();
                    updateTocarTerre();
                    tauler.modificarMapa(this);
                    tauler.imprimirTauler();
                }
                
            }
            r="";
        }
    }

    public void moureX(int endavant){
        int  puntMinimX=-1;
        int  puntMaxX=4;
        
        for (int i = 0; i < pesa.length; i++) {
            for(int j=0; j< pesa[i].length();j++){
                if(pesa[i].charAt(j)=='*'){
                    if(endavant==1){
                        if(puntMinimX<j){
                            puntMinimX=j;
                        }
                    }else{
                        if(puntMaxX>j){
                            puntMaxX=j;
                        }
                    }    
                }
            }
        }
        
        if(endavant==1){
            if(puntMaxX-1+x<=tauler.getTauler().length){
                x++;
            }
        }else{
            if(puntMaxX-1+x>=0){
                x--;
            }
        }
        
    }

    public void moureY(){
        y+=1;
        updateTocarTerre();
    }

    public void girar(){
        String pesa2[]=new String[3];
        
        for(int i=0;i<pesa.length;i++){
            pesa2[i]=pesa[i];
        }

        pesa[0]="";
        pesa[0]+=pesa2[2].charAt(0);
        pesa[0]+=pesa2[1].charAt(0);
        pesa[0]+=pesa2[0].charAt(0);

        pesa[1]="";
        pesa[1]+=pesa2[2].charAt(1);
        pesa[1]+=pesa2[1].charAt(1);
        pesa[1]+=pesa2[0].charAt(1);

        pesa[2]="";
        pesa[2]+=pesa2[2].charAt(2);
        pesa[2]+=pesa2[1].charAt(2);
        pesa[2]+=pesa2[0].charAt(2);

        System.out.println(x);
        if(x<=0 && pesa[0].charAt(0)=='*' || x<=0 && pesa[1].charAt(0)=='*' || x<=0 && pesa[2].charAt(0)=='*'){
            x++;
            
        }else if(x>=tauler.getTauler().length-2 && pesa[0].charAt(2)=='*' || x>=tauler.getTauler().length-2 && pesa[1].charAt(2)=='*' || x>=tauler.getTauler().length-2 && pesa[2].charAt(2)=='*'){
            if(x>=9){
                x=8;
            }    
            
        }

        if(y==1 || y==0){
            moureY();
        }

        
        //TODO controlar que al girar no toqui un '*'
        
    }

    
    public Boolean getTocarTerre(){
        return tocarTerre;
    }

    public void updateTocarTerre(){
        int [] puntMinimX={-1,-1,-1};
        int [] puntMinimY={-1,-1,-1};
        for (int i = 0; i < pesa.length; i++) {
            for(int j=0; j< pesa[i].length();j++){
                if(pesa[i].charAt(j)=='*'){
                    if(puntMinimX[j]<j){
                        puntMinimX[j]=j;
                    }
                    if(puntMinimY[j]<i){
                        puntMinimY[j]=i;
                    }
                }
            }
        }
        for (int i = 0; i < puntMinimY.length; i++) {
            if(puntMinimY[i]!=-1){
                if(tauler.getTauler2()[0].length==y+puntMinimY[i]+1){
                    tocarTerre=true;
                    
                }else{
                    if(tauler.getTauler2()[y+puntMinimY[i]][x+puntMinimX[i]][0]=='*'){
                        tocarTerre=true;
                        
                    }
                }
            }
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String[] getPesa(){
        return pesa;
    }

    
}
