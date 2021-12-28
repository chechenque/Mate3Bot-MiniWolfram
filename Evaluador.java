import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Clase que evalua una expresion matematica, la pasa notacion postfija
 * y posteriormente dado un valor de x la evalua
 */
public class Evaluador {
    private static final String OPERADORESBINARIOS = "+ - * / ^ %";
    private static final String FUNCIONES[] = {"1 2 3 4 5 6 7 8 9 0 ( ) x e + - * / ^ %",
    "pi",
    "ln(",
    "log( abs( sen( sin( cos( tan( sec( csc( cot( sgn(",
    "rnd() asen( asin( acos( atan( asec( acot( asenh( asinh( cosh( tanh( sech( csch( coth( sqrt(",
    "round( asenh( acosh( atanh( asech( acsch( acsch( acoth("}; 

    private static final String PARENTESIS = "( ln log abs sen sin cos tan sec csc"
    +"cot asen asin acos atan asec acsc acot senh sinh cosh tanh sech csch coth"
    +"sqrt round";



                /*( sec( csc( cot( sgn( asec( acot( asenh( asinh( cosh( tanh( sech( csch( coth(",
 asenh( acosh( atanh( asech( acsch( acsch( acoth("}; 

                */


    private String ultimoLeido;

    public Evaluador(){
        ultimoLeido = "0";
    }

    /**
     * Metodo que nos regresa la prioridad de un operador
     * @param operador El operador
     * @return El nivel de prioridad
     */
    private int prioridad(char operador){
        if(operador == '+'|| operador == '-'){
            return 0;
        }else if(operador == '*' || operador == '/' || operador == '%'){
            return 1;
        }else if (operador == '^'){
            return 2;
        }
        return -1;
    }

    /**
     * Metodo que nos permite sacar operadores para concatenarlos
     * @param numeros Pila de Numeros
     * @param operadores Pila de Operadores
     */
    private void sacaOperador(Stack numeros, Stack operadores){
        String operador;
        String a;
        String b;
        try {
            operador = (String)operadores.pop();

            if(OPERADORESBINARIOS.indexOf(operador)!=-1){
                b = (String) numeros.pop();
                a = (String) numeros.pop();
                numeros.push(new String(a+" "+b+" "+operador));
            }else{
                a = (String) numeros.pop();
                numeros.push(new String(a+" "+operador));
            }
        }catch(EmptyStackException e){
            System.out.println("Error sacaOp"+e);
        }
    }

    /**
     * Metodo que saca todos los operadores para poder introducir otro
     * @param numeros Pila de numeros
     * @param operadores Pila de operadores
     * @param operador El operador a meter
     */
    private void sacaOperadores(Stack numeros, Stack operadores, String operador){
        while(!operadores.empty() &&
            PARENTESIS.indexOf((String) operadores.peek()) ==-1 &&
            ((String) operadores.peek()).length() == 1 &&
            prioridad(((String)operadores.peek()).charAt(0)) >= prioridad(operador.charAt(0))){
                System.out.println("Dentro while");
                sacaOperador(numeros,operadores);
        }
        operadores.push(operador);
    }

    /**
     * Metodo que nos indica si un caracter es un numero
     * @param s El caracter en forma de String a verificar
     * @return true si es un numero false en otro caso
     */
    private boolean esNumero(String s){
        if(s.charAt(0) >= '0' && (s.charAt(0) <= '9')){
            return true;
        }
        return false;
    }

    /**
     * Metodo que nos convierte la expresion dada por el usuario a notacion prefica
     * @param funcion La funcion a convertir
     * @return La expresion en la notacion dada
     * @throws Exception En caso de que este mal escrita
     */
    public String convertir(String funcion) throws Exception{
        Stack numeros = new Stack(); //Pila de Numeros
        Stack operadores = new Stack(); //Pila de operadores
        String fragmento; //Fragmento de la cadena a convertir
        int pos = 0; //posicion de la cadena
        int tam = 0;
        byte cont = 1; //Numero maximo que se puede adquirir del tam de la funcion

        String expresionLimpia = funcion.replace(" ","").toLowerCase();

        while(pos < expresionLimpia.length()){
            tam = 0;
            cont = 1;

            while(tam == 0 && cont <= 6){
                if(pos + cont <= expresionLimpia.length() && 
                FUNCIONES[cont-1].indexOf(expresionLimpia.substring(pos,pos+cont)) != -1){
                    tam = cont;
                }
                cont++;
            }

            if(tam == 0){//ERROR
                ultimoLeido = "0";
                throw new Exception("Error en la expresion 0");
            }else if(tam == 1){
                if(esNumero(expresionLimpia.substring(pos,pos+tam))){
                    fragmento = "";
                
                    do{
                        fragmento = fragmento+expresionLimpia.charAt(pos);
                        pos++;
                    }while(pos < expresionLimpia.length() &&
                    (esNumero(expresionLimpia.substring(pos,pos+tam)) ||
                    expresionLimpia.charAt(pos) == '.' || 
                    expresionLimpia.charAt(pos) == ','
                    ));
                    try{
                        //System.out.println(fragmento);
                        Double.parseDouble(fragmento);
                    }catch(NumberFormatException e){
                        ultimoLeido = "0";
                        System.out.println("Error en la escritura "+ e);
                    }

                    numeros.push(new String(fragmento));
                    pos--;
                }else if(expresionLimpia.charAt(pos) == 'x' ||
                        expresionLimpia.charAt(pos) == 'e'){
                    numeros.push(expresionLimpia.substring(pos,pos+tam));
                }else if(expresionLimpia.charAt(pos) == '+'||
                    expresionLimpia.charAt(pos) == '*'||
                    expresionLimpia.charAt(pos) == '/'||
                    expresionLimpia.charAt(pos) == '%'){
                    sacaOperadores(numeros,operadores,expresionLimpia.substring(pos,pos+tam));
                }else if(expresionLimpia.charAt(pos) == '^'){
                    operadores.push(new String("^"));
                }else if(expresionLimpia.charAt(pos) == '('){
                    operadores.push(new String("("));
                }else if(expresionLimpia.charAt(pos) == ')'){
                
                    while(!operadores.isEmpty() && 
                    PARENTESIS.indexOf(((String) operadores.peek())) == -1){
                        sacaOperador(numeros,operadores);
                    }
                    if(!((String)operadores.peek()).equals("(")){
                        numeros.push(new String(((String)numeros.pop())+ " " + operadores.pop()));
                    }else{
                        operadores.pop();
                    }
                }
            }else if(tam >= 2){
                fragmento = expresionLimpia.substring(pos,pos+tam);
                if(fragmento.equals("pi")){
                    numeros.push(fragmento);
                }else if(fragmento.equals("rnd()")){
                    numeros.push("rnd");
                }else{
                    operadores.push(fragmento.substring(0,fragmento.length()-1));
                }
            }
            pos += tam;
        }

        while(!operadores.isEmpty()){
            sacaOperador(numeros,operadores);
        }

        ultimoLeido = ((String) numeros.pop());
        
        if(!numeros.isEmpty()){
            System.out.println(numeros.peek());
            ultimoLeido = "0";
            System.out.println("Error en la expresion gg");
        }

        return ultimoLeido;
    }

    /**
     * Metodo que nos dice si una cadena es un numero
     * @param cadena La cadena a Evaluar
     * @return true si es un numero
     */
    private static boolean isNumeric(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    /**
     * Metodo que nos permite calcular la funcion a partir de una x dada
     * @param expresion La expresion a evaluar con notacion postfija
     * @param x El valor de x que tomar
     * @return El valor obtenido despues de evaluar
     */
    public double funcion(String expresion,double x){
        Stack pila = new Stack();
        double a,b;
        StringTokenizer tokens = new StringTokenizer(expresion);
        String tokenActual;

        try{

            while(tokens.hasMoreTokens()){
                tokenActual = tokens.nextToken();
                
                if(tokenActual.equals("e")){
                    Double d = Math.E;
                    pila.push(d);
                }else if(tokenActual.equals("pi")){
                    Double d = Math.PI;
                    pila.push(d);
                }else if(isNumeric(tokenActual)){
                    Double d = Double.parseDouble(tokenActual);
                    pila.push(d);
                }else if(tokenActual.equals("x")){
                    Double d = x;
                    pila.push(d);
                }else if(tokenActual.equals("+")){
                    b = ((Double)pila.pop()).doubleValue();
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)a+b);
                }else if(tokenActual.equals("-")){
                    b = ((Double)pila.pop()).doubleValue();
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)a-b);
                }else if(tokenActual.equals("*")){
                    b = ((Double)pila.pop()).doubleValue();
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)a*b);
                }else if(tokenActual.equals("/")){
                    b = ((Double)pila.pop()).doubleValue();
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)a/b);
                }else if(tokenActual.equals("%")){
                    b = ((Double)pila.pop()).doubleValue();
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)a%b);
                }else if(tokenActual.equals("^")){
                    b = ((Double)pila.pop()).doubleValue();
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)Math.pow(a, b));
                }else if(tokenActual.equals("ln")){
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)Math.log(a));
                }else if(tokenActual.equals("log")){
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)Math.log10(a));
                }else if(tokenActual.equals("sen")||tokenActual.equals("sin")){
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)Math.sin(a));
                }else if(tokenActual.equals("abs")){
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)Math.abs(a));
                }else if(tokenActual.equals("cos")){
                    a = ((Double)pila.pop()).doubleValue();
                    pila.push((Double)Math.cos(a));
                }else if(tokenActual.equals("tan")){
                    a = ((Double)pila.pop());
                    pila.push((Double)Math.cos(a));
                }else if(tokenActual.equals("rnd")|| tokenActual.equals("round")){
                    a = ((Double)pila.pop());
                    b = Math.round(a);
                    pila.push((Double)b);
                }else if(tokenActual.equals("asen")||tokenActual.equals("asin")){
                    a = ((Double)pila.pop());
                    pila.push((Double)Math.asin(a));
                }else if(tokenActual.equals("acos")){
                    a = ((Double)pila.pop());
                    pila.push((Double)Math.acos(a));
                }else if(tokenActual.equals("atan")){
                    a = ((Double)pila.pop());
                    pila.push((Double)Math.atan(a));
                }else if(tokenActual.equals("sqrt")){
                    a = ((Double)pila.pop());
                    pila.push((Double)Math.sqrt(a));
                }
            }
        }catch(EmptyStackException e){
            System.out.println("Error "+e);
        }catch(NumberFormatException e){
            System.out.println("Error "+e);
        }catch(ArithmeticException e){
            System.out.println("Error "+e);
        }

        a = ((Double) pila.pop()).doubleValue();

        if(!pila.empty()){
            System.out.println("Error en la expresion");
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        Evaluador eval = new Evaluador();
        String prueba = "sqrt(4*x^2)";
        System.out.println("La ecuacion en forma infija es: "+ prueba);
        String resultado = eval.convertir(prueba);
        System.out.println("La ecuacion en forma postfija es: "+ resultado);
        System.out.println(eval.funcion(resultado,2));

    }

}
