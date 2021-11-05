
public class Segmento{
    private Punto puntoInicial;
    private Punto puntoFinal;
    private double longitud;

    public Segmento(Punto puntoInicial, Punto puntoFinal, double longitud){
        this.puntoInicial = puntoInicial;
        this.puntoFinal = puntoFinal;
        this.longitud = longitud;
    }

    public Segmento(Punto puntoInicial, Punto puntoFinal){
        this.puntoInicial = puntoInicial;
        this.puntoFinal = puntoFinal;
        longitud = calculaLongitud();
    }

    private double calculaLongitud(){
        //x**2 + y**2
        double x1 = puntoInicial.getCoordenadaX();
        double y1 = puntoInicial.getCoordenadaY();

        double x2 = puntoFinal.getCoordenadaX();
        double y2 = puntoFinal.getCoordenadaY();

        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    public double getLongitud(){
        return longitud;
    }

    public static void main(String[] args){
        Segmento s1 = new Segmento(new Punto(0,0),new Punto(0,2));
        System.out.println(s1.getLongitud());
    }
}