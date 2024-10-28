package Objectes;
import java.util.Scanner;
import java.util.ArrayList;
public class llibreta extends objectesMobils {
    ArrayList<objectesMobils> llibreta = new ArrayList<>();
    public llibreta(){
        super("llibreta");
    }

    public void afegirPaper(objectesMobils paper){
        llibreta.add(paper);
    }

    public void llegirPagines(){
        Scanner e = new Scanner(System.in);
        String r = "";
        int rInt;
        if(llibreta.size()>0){
            while (!r.equalsIgnoreCase("e")) {
                System.out.println("Quina pàgina vols llegir? (Tens "+llibreta.size()+" pàgines)\nEscriu 'e' per sortir");
                r=e.next();
                try {
                    rInt = Integer.parseInt(r);
                    if(rInt<=llibreta.size() && rInt>0){
                        llibreta.get(rInt-1).llegir();
                        r=e.nextLine();
                    }else{
                        System.out.println("Les pàgines van del 1 a "+llibreta.size());
                    }
                } catch (Exception err) {
                    if(!r.equalsIgnoreCase("e")){
                        System.out.println("Escriu una opció vàlida");
                    }
                }
            }
        }else{
            System.out.println("No tens pàgines en la llibreta");
        }
    }
}