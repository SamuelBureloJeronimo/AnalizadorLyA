
import java.util.ArrayList;

/**
 *
 * @author SpartanLuck 117
 */
public class AnalizadorLexico {

    //ArrayList<Token> tokens = new ArrayList();
    ArrayList<Token> tokensAnalizados = new ArrayList();
    ArrayList<Derivaciones> Uniones = new ArrayList();
    OperacionesLR op = new OperacionesLR();

    AnalizadorLexico() {
        /*
        tokens.add(new Token("(", "Agrupador"));
        tokens.add(new Token(")", "Agrupador"));
        tokens.add(new Token("^*", "Operador"));
        tokens.add(new Token("^+", "Operador"));
        tokens.add(new Token("|", "Operador"));
        tokens.add(new Token(".", "Operador"));
        tokens.add(new Token("Ɛ", "Constante"));
         */
        String cadena = "1^2.Ɛ.4.(a|b)^+.x^**.Ɛ^*";
        String cadena2 = "t^*.a^4.Ɛ.(4^**|c^2).r^*.(1^*)";
        String cadena3 = "1^2.Ɛ.((a^*)|(b^*))^+.x^**.(i|o)";
        String cadena4 = "(r^**|I^+).1^2.yt.Ɛ^+.((a^*)|(b^*))^+.Ɛ.x^**.(i|o)^2";
        analizarCadena(cadena4);
        resolverCadena(cadena4);
        ArrayList c1 = new ArrayList();
        c1.add("Ɛ");
        c1.add("1");
        c1.add("11");
        c1.add("111");
        ArrayList c2 = new ArrayList();
        c2.add("Ɛ");
        c2.add("x");
        c2.add("xx");
        c2.add("xxx");
        System.out.println(op.concatenarCadenas(c1, c2));
    }

    public static void main(String[] args) {
        AnalizadorLexico al = new AnalizadorLexico();
    }

    private void resolverCadena(String cadena) {
        System.out.println("\n==== Indentificar el orden de resolución de la ecuación ====\n");

        //eliminarCadenaVacia();
        System.out.println("=== Inicio ===");

        for (int i = 0; i < tokensAnalizados.size(); i++) {
            //Juntar una exponenciacion cuando la base es un Identificador
            if (tokensAnalizados.get(i).getCateg().equalsIgnoreCase("Identificador") || tokensAnalizados.get(i).getLex().equalsIgnoreCase("Ɛ")) {
                if (tokensAnalizados.get(i + 1).getLex().substring(0, 1).equals("^") && (i + 1) < tokensAnalizados.size()) {
                    System.out.println("** 2do Orden: " + tokensAnalizados.get(i).getLex() + tokensAnalizados.get(i + 1).getLex());
                    ArrayList lst = op.resolver2doOrden(tokensAnalizados.get(i).getLex(), tokensAnalizados.get(i + 1).getLex().substring(1, 2));
                    System.out.println("RES:" + lst);
                    Derivaciones dr2 = new Derivaciones();
                    dr2.add(lst);
                    Uniones.add(dr2);
                } else {
                    System.out.println("*** 3er Orden: " + tokensAnalizados.get(i).getLex());
                    ArrayList lst = new ArrayList();
                    if (tokensAnalizados.get(i).getLex().equals("Ɛ")) {
                        Derivaciones dr2 = new Derivaciones();
                        Uniones.add(dr2);
                    } else {
                        lst.add(tokensAnalizados.get(i).getLex());
                        Derivaciones dr2 = new Derivaciones();
                        dr2.add(lst);
                        Uniones.add(dr2);
                    }
                    
                }
            } //Juntar una exponenciacion cuando la base es un parentesis
            else if (tokensAnalizados.get(i).getLex().equalsIgnoreCase("(")) {
                int ind = searchIndex(1, 0, i + 1);
                System.out.println("* 1er Orden: " + i + ", " + ind);
                ArrayList arrlt = new ArrayList();
                for (int j = i; j <= ind; j++) {
                    arrlt.add(tokensAnalizados.get(j).getLex());
                }
                System.out.println(arrlt);
                Derivaciones dr2 = new Derivaciones();
                dr2.add(op.resolver1erOrden(arrlt));
                Uniones.add(dr2);
                i = ind;
            } else if (tokensAnalizados.get(i).getLex().equalsIgnoreCase(".")) {
                System.out.println("UNION");
            }
        }
        System.out.println("=== Final ===\n");
        System.out.println("LISTO PARA SU UNIÓN");
        for (int i = 0; i < Uniones.size(); i++) {
            if(Uniones.get(i).content.isEmpty()){
                Uniones.remove(i);
                i--;
                continue;
            }
            System.out.println(Uniones.get(i).content);
        }
        System.out.println("");
    }

    private int searchIndex(int p_a, int p_c, int index) {
        if (p_a == p_c) { //Cuando ya se cerraron todos los parentesis se apertura y de cierre
            //verifica si tiene un exponente al final
            if (index < tokensAnalizados.size()) {
                if (tokensAnalizados.get(index).getLex().substring(0, 1).equalsIgnoreCase("^")) {
                    return index;
                }
            }
            return index - 1;
            //Cuando se encuentra un "("
        } else if (tokensAnalizados.get(index).getLex().equals("(")) {
            //System.out.println("p_a");
            return searchIndex(p_a + 1, p_c, index + 1);
            //Cuando se encuentra un "("
        } else if (tokensAnalizados.get(index).getLex().equals(")")) {
            //System.out.println("p_c");
            return searchIndex(p_a, p_c + 1, index + 1);
        } else {
            //System.out.println("-----");
            return searchIndex(p_a, p_c, index + 1);
        }
    }

    //Inicializa todos los componentes
    private void analizarCadena(String cadena) {
        System.out.println("==== ANALIZADOR LÉXICO By Samuel Burelos Jerónimo ====\n");
        while (cadena.length() > 0) {
            String c = cadena.substring(0, 1);
            if (c.matches("[0-9]*") || c.matches("[A-Z]*") || c.matches("[a-z]*")) {

                String ident = c + searchNextAZ_09(cadena.substring(1));
                System.out.println("<Identificador>     " + ident);
                tokensAnalizados.add(new Token(ident, "Identificador"));
                cadena = cadena.substring(ident.length());

            } else {
                if (cadena.length() > 1 && c.equals("^")) {
                    String lt = cadena.substring(1, 2);
                    if (lt.equals("*") || lt.equals("+") || lt.matches("[0-9]*")) {
                        System.out.println("<Operador>          " + cadena.substring(0, 2));
                        tokensAnalizados.add(new Token(cadena.substring(0, 2), "Operador"));
                        if (cadena.length() > 2 && cadena.substring(2, 3).equals("*")) {
                            cadena = cadena.substring(3);
                        } else {
                            cadena = cadena.substring(2);
                        }

                    } else {
                        System.out.println("Error de Sinstaxis");
                        return;
                    }

                } else if (c.equals("(") || c.equals(")")) {
                    tokensAnalizados.add(new Token(c, "Agrupador"));
                    System.out.println("<Agrupador>         " + c);
                    cadena = cadena.substring(1);
                } else if (c.equals("|") || c.equals(".")) {
                    tokensAnalizados.add(new Token(c, "Operador"));
                    System.out.println("<Operador>          " + c);
                    cadena = cadena.substring(1);
                } else if (c.equals("Ɛ")) {
                    tokensAnalizados.add(new Token("Ɛ", "Constante"));
                    System.out.println("<Constante>         " + c);
                    cadena = cadena.substring(1);
                } else {
                    tokensAnalizados.add(new Token(c, "No identificado"));
                    System.out.println("<DESCONOCIDO>        " + c);
                    cadena = cadena.substring(1);
                }

            }
        }
    }

    private String searchNextAZ_09(String cadena) {
        String c = cadena.substring(0, 1);
        if (cadena.equalsIgnoreCase("")) {
            return "";
        } else if (c.matches("[0-9]*") || c.matches("[A-Z]*") || c.matches("[a-z]*")) {
            return c + searchNextAZ_09(cadena.substring(1));
        } else {
            return "";
        }
    }
}
