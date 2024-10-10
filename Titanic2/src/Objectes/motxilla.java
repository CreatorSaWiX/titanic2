package Objectes;

public class motxilla extends objectesMobils{
    objectesMobils espai[] = new objectesMobils[3];
    public motxilla(){
        super("motxilla");
    }

    public objectesMobils[] getEspai(){
        return espai;
    }
}