package miniJocs;

import java.util.Scanner;
import java.util.Random;
public class minijoc1{
    Boolean completat=false;
    public minijoc1(){
        iniciarMiniJoc1();
    }

    public Boolean getCompletat(){
        return completat;
    }

    public void iniciarMiniJoc1(){
        //Inicialització de la mapa
        Scanner sc = new Scanner(System.in);
        Random rd = new Random(); 
        boolean situap = false;
        int filap = 6;
        int columnap = 0;
        int vida = 3;
        int fin = 0;
        int mor = 0;
        char[][] laberint = {
            {' ',' ',' ',' ',' ',' ',' ', ' ', ' ', ' '},
            {' ',' ',' ',' ',' ',' ',' ', ' ', ' ', ' '},
            {' ',' ','#','#','#','#','#', '#', '#', ' '},
            {' ',' ','V',' ','-','>',' ', '|', '#', ' '},
            {' ',' ',' ',' ',' ',' ',' ', 'V', '#', ' '},
            {' ',' ',' ',' ',' ',' ',' ', ' ', '#', ' '},
            {'P',' ',' ',' ',' ','A','A', 'A', '#', ' '},
            {'#','#','#','#','#','#','#', '#', '#', ' '},
            {' ',' ',' ',' ','#',' ',' ', ' ', ' ', ' '},
            {' ',' ',' ',' ','#',' ','#', '#', '#', '#'},
        };
        
        //Impressió a la pantalla
        System.out.println("Benvingut al joc, estàs aquí: ");
        System.out.println("......................................");

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                System.out.print(laberint[fila][columna] + " ");
            }
            System.out.println("");
        }

        System.out.println("......................................");
        
        while (fin != 1 && mor != 1) {
            System.out.print("P = Player 01");
            System.out.println("             # = Land");
            String moure = sc.next();
            System.out.println("......................................");
            switch (moure) {
                case "w":
                case "W":
                    if (filap > 0 && laberint[filap - 1][columnap] != '#') {
                        if (laberint[filap-1][columnap] == '@'){
                            vida = vida - 1;
                        } 
                        laberint[filap][columnap] = ' ';
                        filap -= 1;
                        laberint[filap][columnap] = 'P';

                    } else {
                        System.out.println("No pots moure cap amunt");
                        System.out.println("......................................");
                    }
                    break;

                case "s":
                case "S":
                    if (filap < 9 && laberint[filap + 1][columnap] != '#') {
                        if (laberint[filap+1][columnap] == '@'){
                            vida = vida - 1;
                        }
                        laberint[filap][columnap] = ' ';
                        filap += 1;
                        laberint[filap][columnap] = 'P';
                    } else {
                        System.out.println("No pots moure cap avall");
                        System.out.println("......................................");
                    }
                    break;

                case "d":
                case "D":
                    if (columnap < 9 && laberint[filap][columnap + 1] != '#') {
                        if (laberint[filap][columnap+1] == '@'){
                            vida = vida - 1;
                        } 
                        laberint[filap][columnap] = ' ';
                        columnap += 1;
                        laberint[filap][columnap] = 'P';
                        
                    } else {
                        System.out.println("No pots moure cap a la dreta");
                        System.out.println("......................................");
                    }
                    break;

                case "a":
                case "A":
                    if (columnap > 0 && laberint[filap][columnap - 1] != '#') {
                        if (laberint[filap][columnap-1] == '@'){
                            vida = vida - 1;
                        } 
                        laberint[filap][columnap] = ' ';
                        columnap -= 1;
                        laberint[filap][columnap] = 'P';
                        
                    } else {
                        System.out.println("No pots moure cap a l'esquerre");
                        System.out.println("......................................");
                    }
                    break;

                default:
                    System.out.println("Error. Direcció no vàlida.");
                    System.out.println("......................................");
                    break;
            }


            if(columnap == 7 && filap == 4){
                laberint[2][2] = '#';
                laberint[3][2] = '#';
                laberint[4][2] = '#';
                laberint[5][2] = '#';
                laberint[6][2] = '#';
            }

            if(columnap == 3 && filap == 4){
                laberint[6][5] = ' ';
                laberint[7][5] = ' ';
                laberint[6][6] = ' ';
                laberint[7][6] = ' ';
                laberint[6][7] = ' ';
                laberint[7][7] = ' ';
                laberint[6][3] = '-';
                laberint[6][4] = '>';
            }

            if(columnap == 3 && filap == 5){
                laberint[6][5] = ' ';
                laberint[7][5] = ' ';
                laberint[6][6] = ' ';
                laberint[7][6] = ' ';
                laberint[6][7] = ' ';
                laberint[7][7] = ' ';
            }

            if(columnap == 8 && filap == 8){
                laberint[999999999][5] = '#';
            }
            for (int fila = 0; fila < 10; fila++) {
                for (int columna = 0; columna < 10; columna++) {
                    System.out.print(laberint[fila][columna] + " ");
                }
                System.out.println("");
            }
            System.out.println("......................................");
            
            if (vida == 0){  
                mor = 1;
            }
        }

        if (fin == 1){
            System.out.println("Felicitats! Has arribat a la sortida del laberint.");
            completat=true;
        }

        if (mor == 1){
            System.out.println("[Game over]");
        }
    }
}