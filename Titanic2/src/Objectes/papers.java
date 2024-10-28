package Objectes;

public class papers extends objectesMobils{
    String contingut;
    public papers(String contingut){
        super("paper");
        this.contingut=contingut;
    }

    public void llegir(){
        System.out.println("----------------------------------------");
        System.out.println(contingut);
        System.out.println("----------------------------------------");
    }
}
