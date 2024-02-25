
import java.util.ArrayList;

/**
 *
 * @author SpartanLuck 117
 */
public class OperacionesLR {

    /*
    public ArrayList<String> resolver1erOrden(ArrayList<Token> cad){
        ArrayList<String> result = new ArrayList();
        
    }
    public ArrayList<String> resolver2doOrden(ArrayList<Token> cad){
        ArrayList<String> result = new ArrayList();
        
    }*/
    static ArrayList<Derivaciones> arr = new ArrayList();

    public ArrayList<String> concatenarCadenas(ArrayList<String> cad1, ArrayList<String> cad2) {
        ArrayList<String> result = new ArrayList();
        if (cad1.isEmpty()) {
            return cad2;
        } else if (cad2.isEmpty()) {
            return cad1;
        }
        for (int i = 0; i < cad1.size(); i++) {
            for (int j = 0; j < cad2.size(); j++) {
                if (cad1.get(i).equals("Ɛ") && cad2.get(j).equals("Ɛ")) {
                    continue;
                } else if (!cad1.get(i).equals("Ɛ") && cad2.get(j).equals("Ɛ")) {
                    result.add(cad1.get(i));
                } else if (cad1.get(i).equals("Ɛ") && !cad2.get(j).equals("Ɛ")) {
                    result.add(cad2.get(j));
                } else {
                    result.add(cad1.get(i) + cad2.get(j));
                }
            }
        }
        return result;
    }

    public ArrayList<String> UnirCadenas(ArrayList<String> cad1, ArrayList<String> cad2) {
        for (int i = 0; i < cad2.size(); i++) {
            cad1.add(cad2.get(i));
        }
        return cad1;
    }

    /*
     * 
     */
    public ArrayList<Derivaciones> resolver1erOrden(ArrayList<Token> arr, String exp) {
        //Elimina los parentesis de inicio y del final
        arr.remove(0);
        arr.remove(arr.size() - 1);

        AnalizadorLexico anl = new AnalizadorLexico();
        ArrayList<Derivaciones> result = anl.identificarOrden(arr);
        System.out.println("Lenght: " + result.size());

        if (!exp.equals("")) {
            System.out.println("Namas Expongo y ya. de DRIVARCION:");
            int vuelta = 0;
            while (true) {
                for (int i = vuelta; i < result.size(); i++) {
                    System.out.println("Entro al For.");
                    System.out.println(result.get(i).content);
                    if (result.get(i).content.get(0).equals("|")) {
                        System.out.println("Cad1" + result.get(i - 1).content);
                        System.out.println("Cad2: " + result.get(i + 1).content);
                        result.get(i + 1).content = UnirCadenas(result.get(i - 1).content, result.get(i + 1).content);
                        System.out.println("UNION: " + result.get(i + 1).content);
                        vuelta += i + 2;
                        break;
                    } else if (result.get(i).content.get(0).equals(".")) {
                        System.out.println("Cad1" + result.get(i - 1).content);
                        System.out.println("Cad2: " + result.get(i + 1).content);
                        result.get(i + 1).content = concatenarCadenas(result.get(i - 1).content, result.get(i + 1).content);
                        System.out.println("CONCATENAR: " + result.get(i + 1).content);
                        vuelta += i + 2;
                        break;
                    }
                }
                if (vuelta >= result.size()) {
                    break;
                }
            }
            Derivaciones der = new Derivaciones();
            der.content = resolver2doOrden(result.get(0).content, exp);
            result.set(0, der);
            System.out.println("RESULTADO:" + der.content);
            return result;

        } else {
            if (result.size() == 1) {
                System.out.println("Resultado de DRIVARCION:");
                System.out.println(result.get(0).content);
                return result;
            }
            int rftg = 0;
            for (int i = 0; i < result.size() - 1; i++) {
                System.out.println(result.get(i).content.get(0));
                //Si se repite el signo en el siguiente
                if (result.get(i).content.get(0).equalsIgnoreCase("|")) {
                    if (rftg > 0) {
                        System.out.println("Se removio:" + result.get(i).content);
                        result.remove(i);
                        i--;
                        rftg = 1;
                    } else {
                        rftg++;
                    }
                } else if (result.get(i).content.get(0).equalsIgnoreCase(".")) {
                    if (rftg > 0) {
                        System.out.println("Se removio:" + result.get(i).content);
                        result.remove(i);
                        i--;
                        rftg = 1;
                    } else {
                        rftg++;
                    }
                } else {
                    rftg = 0;

                }
            }
            System.out.println("=========================");
            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i).content);
            }
            System.out.println("=========================");
            int vuelta = 0;
            while (true) {
                for (int i = vuelta; i < result.size(); i++) {
                    System.out.println("Entro al For.");
                    System.out.println(result.get(i).content);
                    if (result.get(i).content.get(0).equals("|")) {
                        System.out.println("Cad1" + result.get(i - 1).content);
                        System.out.println("Cad2: " + result.get(i + 1).content);
                        result.get(i + 1).content = UnirCadenas(result.get(i - 1).content, result.get(i + 1).content);
                        System.out.println("UNION: " + result.get(i + 1).content);
                        vuelta += i + 2;
                        break;
                    } else if (result.get(i).content.get(0).equals(".")) {
                        System.out.println("Cad1" + result.get(i - 1).content);
                        System.out.println("Cad2: " + result.get(i + 1).content);
                        result.get(i + 1).content = concatenarCadenas(result.get(i - 1).content, result.get(i + 1).content);
                        System.out.println("CONCATENAR: " + result.get(i + 1).content);
                        vuelta += i + 2;
                        break;
                    }
                }
                if (vuelta >= result.size()) {
                    break;
                }
            }
            for (int i = 0; i < result.size() - 1; i++) {
                result.remove(i);
            }
            return result;
        }
    }

    private int searchIndex(ArrayList<String> tokensAnalizados, int p_a, int p_c, int index) {
        if (p_a == p_c) { //Cuando ya se cerraron todos los parentesis se apertura y de cierre
            //verifica si tiene un exponente al final
            if (index < tokensAnalizados.size()) {
                if (tokensAnalizados.get(index).substring(0, 1).equalsIgnoreCase("^")) {
                    return index;
                }
            }
            return index - 1;
            //Cuando se encuentra un "("
        } else if (tokensAnalizados.get(index).equals("(")) {
            //System.out.println("p_a");
            return searchIndex(tokensAnalizados, p_a + 1, p_c, index + 1);
            //Cuando se encuentra un "("
        } else if (tokensAnalizados.get(index).equals(")")) {
            //System.out.println("p_c");
            return searchIndex(tokensAnalizados, p_a, p_c + 1, index + 1);
        } else {
            //System.out.println("-----");
            return searchIndex(tokensAnalizados, p_a, p_c, index + 1);
        }
    }

    public ArrayList<String> resolver2doOrden(ArrayList<String> idents, String tipo) {
        ArrayList<String> result = new ArrayList();
        for (int i = 0; i < idents.size(); i++) {
            ArrayList<String> tmp = resolver2doOrden(idents.get(i), tipo);
            for (int j = 0; j < tmp.size(); j++) {
                result.add(tmp.get(j));
            }
        }
        return result;
    }

    public ArrayList<String> resolver2doOrden(String ident, String tipo) {
        int MAX_ELEM = 4;
        ArrayList<String> result = new ArrayList();
        String sum = "";
        if(ident.equalsIgnoreCase("Ɛ")){
            result.add("Ɛ");
            //TIPO: Cerradura de Kleene
        } else if (tipo.equalsIgnoreCase("*")) {
            result.add("Ɛ");
            for (int i = 1; i < MAX_ELEM; i++) {
                sum += ident;
                result.add(sum);
            }

            //TIPO: Cerradura Positiva
        } else if (tipo.equalsIgnoreCase("+")) {
            for (int i = 0; i < MAX_ELEM; i++) {
                sum += ident;
                result.add(sum);
            }

            //TIPO: Exponenciación
        } else if (tipo.matches("[0-9]*")) {
            result.add("Ɛ");
            for (int i = 1; i < Integer.parseInt(tipo); i++) {
                sum += ident;
                result.add(sum);
            }
        }
        return result;
    }
}
