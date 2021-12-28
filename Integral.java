import java.util.List;
import java.util.ArrayList;

public class Integral {
    private Evaluador eval;



    public Integral(){
        this.eval = new Evaluador();
    }

    public double resolverIntegral(String funcion, double limiteInferior, double limiteSuperior, int numPuntos) throws Exception{
        List<Punto> puntos = new ArrayList<>();

        String func = eval.convertir(funcion);

        puntos.add(new Punto(limiteInferior,0));

        for(int i = 0; i <=numPuntos; i++){
            puntos.add(new Punto(limiteInferior + i*(limiteSuperior-limiteInferior)/numPuntos, 
            eval.funcion(func,limiteInferior + i*(limiteSuperior-limiteInferior)/numPuntos)));
            
            puntos.add(new Punto(limiteInferior + i*(limiteSuperior-limiteInferior)/numPuntos, 
            eval.funcion(func,limiteInferior + i*(limiteSuperior-limiteInferior)/numPuntos)));
        }

        puntos.add(new Punto(limiteSuperior,0));

        puntos.add(new Punto(limiteSuperior,0));
        puntos.add(new Punto(limiteInferior,0));

        return new Poligono(puntos).getArea();
    }

    public static void main(String[] args) throws Exception {
        Integral intt = new Integral();
        String funcion = "x^2";
        System.out.println("La funcion a integras es: "+ funcion);
        System.out.println("Limite inferior "+0);
        System.out.println("Limite superior "+5);
        System.out.println("El area bajo la curva es: "+ intt.resolverIntegral(funcion, 0, 5, 100));


    }

}
