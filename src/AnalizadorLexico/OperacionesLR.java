package AnalizadorLexico;

import AnalizadorLexico.Derivaciones;
import java.util.ArrayList;

public class OperacionesLR {

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
                    result.add("Ɛ");
                } else if (cad1.get(i).equals("Ɛ") && !cad2.get(j).equals("Ɛ")) {
                    result.add(cad2.get(j));
                } else if (!cad1.get(i).equals("Ɛ") && cad2.get(j).equals("Ɛ")) {
                    result.add(cad1.get(i));
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

        if (!exp.equals("")) {

            System.out.println("Namas Expongo y ya. de DRIVARCION:");
            int vuelta = 0;
            int uniones = 0;
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
                        uniones++;
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
            if (uniones == 1) {
                Derivaciones der = new Derivaciones();
                der.content = exp2doOrdenUnion(result.get(0).content, exp);
                result.set(0, der);
                System.out.println("Es una union");
                System.out.println("RESULTADO:" + der.content);
                return result;
            } else {
                Derivaciones der = new Derivaciones();
                der.content = resolver2doOrden(result.get(result.size()-1).content, exp);
                result.set(0, der);
                System.out.println("No es una union");
                System.out.println("RESULTADO:" + der.content);
                return result;
            }
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

    public ArrayList<String> exp2doOrdenUnion(ArrayList<String> idents, String tipo) {
        System.out.println("Esta es una OP U");
        ArrayList<String> result = new ArrayList();
        ArrayList<String> tmp = resolver2doOrden(idents.get(0), tipo);
        ArrayList<String> tmp2 = resolver2doOrden(idents.get(1), tipo);
        for (int j = 0; j < tmp.size(); j++) {
            result.add(tmp.get(j));
        }
        for (int j = 0; j < tmp2.size(); j++) {
            result.add(tmp2.get(j));
        }
        tmp.add(idents.get(0)+idents.get(1));
        tmp2.add(idents.get(1)+idents.get(0));
        
        tmp.add(tmp.get(0)+tmp.get(tmp.size()-1));
        tmp2.add(tmp2.get(0)+tmp2.get(tmp2.size()-1));
        
        tmp.add(tmp.get(tmp.size()-2)+tmp.get(0));
        tmp2.add(tmp2.get(tmp2.size()-2)+tmp2.get(0));
        
        tmp.add(tmp.get(0)+tmp2.get(1));
        tmp2.add(tmp2.get(0)+tmp.get(1));
        
        System.out.println("Idents: " + idents);
        System.out.println("Temp: " + tmp);
        System.out.println("Temp2: " + tmp2);
        
        for (int i = 0; i < tmp2.size(); i++) {
            tmp.add(tmp2.get(i));
        }
        
        return tmp;
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
        int MAX_ELEM = 3;
        ArrayList<String> result = new ArrayList();
        String sum = "";
        if (ident.equalsIgnoreCase("Ɛ")) {
            result.add("Ɛ");
            //TIPO: Cerradura de Kleene
        } else if (tipo.equalsIgnoreCase("*")) {
            result.add("Ɛ");
            for (int i = 0; i < MAX_ELEM; i++) {
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
            for (int i = 0; i < Integer.parseInt(tipo); i++) {
                sum += ident;
                result.add(sum);
            }
        }
        return result;
    }
}
