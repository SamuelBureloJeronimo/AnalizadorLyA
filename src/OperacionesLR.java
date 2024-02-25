
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
    public ArrayList<Derivaciones> resolver1erOrden(ArrayList<Token> arr) {
        ArrayList<String> tmp = new ArrayList();
        //Elimina los parentesis de inicio y del final
        arr.remove(0);
        arr.remove(arr.size()-1);
        AnalizadorLexico anl = new AnalizadorLexico();
        return anl.identificarOrden(arr);
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
            ArrayList<String> tmp = resolver2doOrden(idents.get(i),tipo);
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
        if (ident.equalsIgnoreCase("Ɛ")) {
            return result;
        }
        //TIPO: Cerradura de Kleene
        if (tipo.equalsIgnoreCase("*")) {
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
