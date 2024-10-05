package Objectes;

import java.util.ArrayList;

import Sales.ubicacions;
import ObjectesInmobils.porta;

public class clau extends objectesMobils {
    public clau(String nom){
        super("Clau");
    }

    // public void obrirPorta(int idHab, ArrayList<ubicacions>zones){
    //     ubicacions actual = zones.get(idHab-1);
    //     ArrayList<porta> portes = actual.getPortes();

    //     for(int i = 0; i < zones.size(); i++){
    //         if (!portes.get(i).getObert()){
    //             portes.get(i).setObert(true);
    //             i=zones.size();
    //         } else {
    //             System.out.println("Necessites la clau per accedir a la sala.");
    //         }
    //     }
    // }
}