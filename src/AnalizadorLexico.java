
import java.util.ArrayList;

/**
 *
 * @author SpartanLuck 117
 */
public class AnalizadorLexico {

    //ArrayList<Token> tokens = new ArrayList();
    ArrayList<Token> tokensAnalizados = new ArrayList();
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
        analizarCadena("rt^2.Ɛ.(a1|(1BY|p^+))^**.x^+.E^*");
    }

    public static void main(String[] args) {
        AnalizadorLexico al = new AnalizadorLexico();
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
                    System.out.println("<Agrupador>         " + c);
                    cadena = cadena.substring(1);
                } else if (c.equals("|") || c.equals(".")) {
                    System.out.println("<Operador>          " + c);
                    cadena = cadena.substring(1);
                } else if (c.equals("Ɛ")) {
                    System.out.println("<Constante>         " + c);
                    cadena = cadena.substring(1);
                } else {
                    System.out.println("<Aun no identificado>        " + c);
                    cadena = cadena.substring(1);
                }

            }
        }
        System.out.println("");
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

    private String reducirIdempotente() {
        return "";
    }
}
