package Objectes;

public class llanterna extends objectesMobils{
    private boolean ences = false;
    private int bateria = 20;

    public llanterna(){
        super("llanterna");
    }

    public int getbateria(){
        return this.bateria;
    }

    public boolean ences(){
        return this.ences;
    }
}
