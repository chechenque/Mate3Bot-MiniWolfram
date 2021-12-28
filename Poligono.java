import java.util.List;
import java.util.ArrayList;

public class Poligono {
    private List<Punto> vertices;
    private List<Segmento> aristas;
    private double perimetro;
    private double area;

    public Poligono(List<Punto> vertices, List<Segmento> aristas,double perimetro, double area) {
        this.vertices = vertices;
        this.aristas = aristas;
        this.perimetro = perimetro;
        this.area = area;
    }

    public Poligono(List<Punto> vertices){
        this.vertices = vertices;
        this.aristas = creaAristas();
        this.perimetro = calculaPerimetro();
        this.area = calculaArea();
    }

    public List<Punto> getVertices(){
        return vertices;
    }

    public List<Segmento> getAristas(){
        return aristas;
    }

    public double getPerimetro(){
        return perimetro;
    }

    public double getArea(){
        return area;
    }

    private List<Segmento> creaAristas(){
        List<Segmento> listaAristas = new ArrayList<Segmento>();
        
        int i = 0;
        while(i< vertices.size()){
            Punto p1 = new Punto(vertices.get(i).getCoordenadaX(),vertices.get(i).getCoordenadaY());
            Punto p2 = new Punto(vertices.get(i+1).getCoordenadaX(),vertices.get(i+1).getCoordenadaY());

            listaAristas.add(new Segmento(p1,p2));
            i+= 2;
        }

        return listaAristas;
    }

    private double calculaPerimetro(){
        double perimetro = 0;

        for(int i=0;i< aristas.size();i++){
            perimetro += aristas.get(i).getLongitud();
        }

        return perimetro;
    }

    private double calculaArea(){
        List<Punto> temp = quitaRepetidos();
        
        /*for(int i = 0; i < temp.size(); ++i){
            System.out.println(temp.get(i).toString());
        }*/


        double suma = 0;
        int s = 0;
        int l = temp.size();

        for(int i = 0; i<temp.size(); ++i){
            s = (((i-1))%l + l)%l;
            suma += temp.get(i).getCoordenadaX()*(temp.get((i+1)%temp.size()).getCoordenadaY() - temp.get(s).getCoordenadaY());
        }

        suma = Math.abs(suma);
        return suma/2;
    }

    private List<Punto> quitaRepetidos(){
        List<Punto> temp = new ArrayList<>();
        temp.add(vertices.get(0));
        for(int i=1;i<vertices.size();++i){
            double cx = vertices.get(i).getCoordenadaX();
            double cy = vertices.get(i).getCoordenadaY();

            if(contiene(temp,new Punto(cx,cy))){

            }else{
                temp.add(new Punto(cx, cy));
            }

        }

        return temp;
    }

    public boolean compara(Punto a, Punto b){
        double x1 = a.getCoordenadaX();
        double y1 = a.getCoordenadaY();

        double x2 = b.getCoordenadaX();
        double y2 = b.getCoordenadaY();

        return (x1 == x2) && (y1 == y2);
    }

    public boolean contiene(List<Punto> lista, Punto p){
        for(int i =0; i<lista.size(); ++i){
            if(compara(lista.get(i),p)){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args){
        List<Punto> listaPunto = new ArrayList<>();
        listaPunto.add(new Punto(0,0));
        listaPunto.add(new Punto(0,3));

        listaPunto.add(new Punto(0,3));
        listaPunto.add(new Punto(3,3));

        listaPunto.add(new Punto(3,3));
        listaPunto.add(new Punto(3,0));

        listaPunto.add(new Punto(3,0));
        listaPunto.add(new Punto(0,0));


        
        Poligono p = new Poligono(listaPunto);


        for(int i = 0; i<p.getAristas().size(); ++i){
            System.out.println("La arista "+i+ " tiene longitud : "+ p.getAristas().get(i).getLongitud());
        }

        System.out.println("El perimetro es: "+ p.getPerimetro());
        System.out.println("El area es: "+ p.getArea());
        
    }

}