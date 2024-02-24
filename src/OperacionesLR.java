
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

    public ArrayList<String> resolver1erOrden(ArrayList<String> arr) {
        ArrayList<String> result = new ArrayList();
        result.add("GT");
        result.add("GTGT");
        
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
