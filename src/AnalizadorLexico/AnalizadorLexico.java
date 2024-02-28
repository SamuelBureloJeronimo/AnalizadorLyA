/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AnalizadorLexico;

import java.util.ArrayList;

/**
 *
 * @author marti
 */
public class AnalizadorLexico {

    ArrayList<Derivaciones> Separaciones = new ArrayList();
    OperacionesLR op = new OperacionesLR();
    String analisisLexico = "";

    public ArrayList<String> solucionFinal(ArrayList<Derivaciones> Separaciones) {
        System.out.println("== Eliminar las cadenas vacías ==");
        int rftg = 0;
        for (int i = 0; i < Separaciones.size() - 1; i++) {
            if (Separaciones.get(i).content.get(0).equalsIgnoreCase("Ɛ") && Separaciones.get(i).content.size() == 1) {
                if (i == 0) {
                    System.out.println("Se removio:" + Separaciones.get(i).content);
                    Separaciones.remove(i);
                    System.out.println("Se removio:" + Separaciones.get(i + 1).content);
                    Separaciones.remove(i);
                    rftg = 0;
                } else {
                    System.out.println("Se removio:" + Separaciones.get(i).content);
                    Separaciones.remove(i);
                    rftg = 0;
                    i--;
                }

            }
            System.out.println(Separaciones.get(i).content.get(0));
            //Si se repite el signo en el siguiente
            if (Separaciones.get(i).content.get(0).equalsIgnoreCase("|")) {
                if (rftg > 0) {
                    System.out.println("Se removio:" + Separaciones.get(i).content);
                    Separaciones.remove(i);
                    i--;
                    rftg = 1;
                } else {
                    rftg++;
                }
            } else if (Separaciones.get(i).content.get(0).equalsIgnoreCase(".")) {
                if (rftg > 0) {
                    System.out.println("Se removio:" + Separaciones.get(i).content);
                    Separaciones.remove(i);
                    i--;
                    rftg = 1;
                } else {
                    rftg++;
                }
            } else {
                rftg = 0;
            }
        }
        if (Separaciones.get(Separaciones.size() - 1).content.get(0).equalsIgnoreCase("Ɛ") && Separaciones.get(Separaciones.size() - 1).content.size() == 1) {
            System.out.println("Se removio:" + Separaciones.get(Separaciones.size() - 1).content);
            Separaciones.remove(Separaciones.size() - 1);
            System.out.println("Se removio:" + Separaciones.get(Separaciones.size() - 1).content);
            Separaciones.remove(Separaciones.size() - 1);
        }

        System.out.println("=== CASI FINAL XD ===\n");
        System.out.println("LISTO PARA SU RESOLUCIÓN");
        for (int i = 0; i < Separaciones.size(); i++) {
            System.out.println(Separaciones.get(i).content);
        }
        System.out.println("\n");
        System.out.println("=========================");
        int vuelta = 0;
        if (Separaciones.size() != 1) {
            while (true) {
                for (int i = vuelta; i < Separaciones.size(); i++) {
                    System.out.println("Vuelta: " + vuelta);
                    if (Separaciones.get(i).content.get(0).equals(".")) {
                        System.out.println("=======");
                        System.out.println("Cad1:" + Separaciones.get(i - 1).content);
                        System.out.println("Cad2: " + Separaciones.get(i + 1).content);
                        Separaciones.get(i + 1).content = op.concatenarCadenas(Separaciones.get(i - 1).content, Separaciones.get(i + 1).content);
                        System.out.println("CONCATENAR: " + Separaciones.get(i + 1).content);
                        vuelta = i + 2;
                        System.out.println("=======");
                        break;
                    } else if (Separaciones.get(i).content.get(0).equals("|")) {
                        System.out.println("=======");
                        System.out.println("Cad1:" + Separaciones.get(i - 1).content);
                        System.out.println("Cad2: " + Separaciones.get(i + 1).content);
                        Separaciones.get(i + 1).content = op.UnirCadenas(Separaciones.get(i - 1).content, Separaciones.get(i + 1).content);
                        System.out.println("UNIR: " + Separaciones.get(i + 1).content);
                        vuelta = i + 2;
                        System.out.println("=======");
                        break;
                    }
                }
                if (vuelta >= Separaciones.size()) {
                    break;
                }
            }
        }

        System.out.println("Resultado:");
        System.out.println(Separaciones.get(Separaciones.size() - 1).content);

        System.out.println("\n\nFinal");

        return Separaciones.get(Separaciones.size() - 1).content;
    }

    public ArrayList<Derivaciones> identificarOrden(ArrayList<Token> tokensAnalizados) {
        System.out.println("\n==== Indentificar el orden de resolución de la ecuación ====\n");

        //eliminarCadenaVacia();
        System.out.println("=== Inicio ===");

        for (int i = 0; i < tokensAnalizados.size(); i++) {
            //Juntar una exponenciacion cuando la base es un Identificador
            if (tokensAnalizados.get(i).getCateg().equalsIgnoreCase("Identificador") || tokensAnalizados.get(i).getLex().equalsIgnoreCase("Ɛ")) {
                if ((i + 1) < tokensAnalizados.size()) {
                    if (tokensAnalizados.get(i + 1).getLex().substring(0, 1).equals("^")) {
                        System.out.println("** 2do Orden: " + tokensAnalizados.get(i).getLex() + tokensAnalizados.get(i + 1).getLex());
                        ArrayList lst = op.resolver2doOrden(tokensAnalizados.get(i).getLex(), tokensAnalizados.get(i + 1).getLex().substring(1));
                        Derivaciones dr2 = new Derivaciones();
                        dr2.add(lst, 4);
                        Separaciones.add(dr2);
                    } else {
                        System.out.println("*** 3er Orden: " + tokensAnalizados.get(i).getLex());
                        ArrayList lst = new ArrayList();
                        if (tokensAnalizados.get(i).getLex().equals("Ɛ")) {
                            lst.add(tokensAnalizados.get(i).getLex());
                            Derivaciones dr2 = new Derivaciones();
                            dr2.add(lst, 3);
                            Separaciones.add(dr2);
                        } else {
                            lst.add(tokensAnalizados.get(i).getLex());
                            Derivaciones dr2 = new Derivaciones();
                            dr2.add(lst, 3);
                            Separaciones.add(dr2);
                        }

                    }

                } else {
                    System.out.println("*** 3er Orden: " + tokensAnalizados.get(i).getLex());
                    ArrayList lst = new ArrayList();
                    if (tokensAnalizados.get(i).getLex().equals("Ɛ")) {
                        lst.add(tokensAnalizados.get(i).getLex());
                        Derivaciones dr2 = new Derivaciones();
                        dr2.add(lst, 3);
                        Separaciones.add(dr2);
                    } else {
                        lst.add(tokensAnalizados.get(i).getLex());
                        Derivaciones dr2 = new Derivaciones();
                        dr2.add(lst, 3);
                        Separaciones.add(dr2);
                    }

                }
            } //Juntar una exponenciacion cuando la base es un parentesis
            else if (tokensAnalizados.get(i).getLex().equalsIgnoreCase("(")) {
                int ind = searchIndex(tokensAnalizados, 1, 0, i + 1);
                ArrayList<String> arrlt = new ArrayList();
                ArrayList<Token> othr = new ArrayList();
                for (int j = i; j <= ind; j++) {
                    arrlt.add(tokensAnalizados.get(j).getLex());
                    othr.add(tokensAnalizados.get(j));
                }

                System.out.println("* 1er Orden: " + arrlt);
                if (arrlt.get(arrlt.size() - 1).substring(0, 1).equals("^")) {
                    ArrayList<Derivaciones> dr = op.resolver1erOrden(othr, arrlt.get(arrlt.size() - 1).substring(1));
                    Separaciones.add(dr.get(0));
                } else {
                    ArrayList<Derivaciones> dr = op.resolver1erOrden(othr, "");
                    for (int j = 0; j < dr.size(); j++) {
                        Separaciones.add(dr.get(j));
                    }

                }

                //Separaciones.add(dr2);
                i = ind;

            } else if (tokensAnalizados.get(i).getLex().equalsIgnoreCase(".")) {
                Derivaciones dr2 = new Derivaciones();
                ArrayList lst = new ArrayList();
                lst.add(".");
                dr2.add(lst, 4);
                Separaciones.add(dr2);
                System.out.println("**** 4to Orden: .");
            } else if (tokensAnalizados.get(i).getLex().equalsIgnoreCase("|")) {
                Derivaciones dr2 = new Derivaciones();
                ArrayList lst = new ArrayList();
                lst.add("|");
                dr2.add(lst, 4);
                Separaciones.add(dr2);
                System.out.println("**** 4to Orden: |");
            }
        }
        System.out.println("=== Final ===\n");
        System.out.println("LISTO PARA SU RESOLUCIÓN");
        for (int i = 0; i < Separaciones.size(); i++) {
            System.out.println(Separaciones.get(i).content);
        }
        System.out.println("\n");
        return Separaciones;
    }

    private int searchIndex(ArrayList<Token> tokensAnalizados, int p_a, int p_c, int index) {
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
            return searchIndex(tokensAnalizados, p_a + 1, p_c, index + 1);
            //Cuando se encuentra un "("
        } else if (tokensAnalizados.get(index).getLex().equals(")")) {
            //System.out.println("p_c");
            return searchIndex(tokensAnalizados, p_a, p_c + 1, index + 1);
        } else {
            //System.out.println("-----");
            return searchIndex(tokensAnalizados, p_a, p_c, index + 1);
        }
    }

    //Inicializa todos los componentes
    public ArrayList<Token> analizarCadena(String cadena) {
        ArrayList<Token> tokensAnalizados = new ArrayList();
        analisisLexico = "==== ANALIZADOR LÉXICO ====\n";
        System.out.println("==== ANALIZADOR LÉXICO By Samuel Burelos Jerónimo ====\n");
        while (cadena.length() > 0) {
            String c = cadena.substring(0, 1);
            if (c.matches("[0-9]*") || c.matches("[A-Z]*") || c.matches("[a-z]*")) {

                String ident = c + searchNextAZ_09(cadena.substring(1));
                analisisLexico += "\n<Identificador>       " + ident;
                System.out.println("<Identificador>       " + ident);
                tokensAnalizados.add(new Token(ident, "Identificador"));
                cadena = cadena.substring(ident.length());

            } else {
                if (cadena.length() > 1 && c.equals("^")) {
                    String lt = cadena.substring(1, 2);
                    if (lt.equals("*") || lt.equals("+")) {
                        analisisLexico += "\n<Operador>           " + cadena.substring(0, 2);
                        System.out.println("<Operador>                " + cadena.substring(0, 2));
                        tokensAnalizados.add(new Token(cadena.substring(0, 2), "Operador"));
                        if (cadena.length() > 2 && cadena.substring(2, 3).equals("*")) {
                            cadena = cadena.substring(3);
                        } else {
                            cadena = cadena.substring(2);
                        }
                    } else if (lt.matches("[0-9]*")) {

                        String ident = c + searchNextAZ_09(cadena.substring(1));
                        System.out.println("<Operador>                " + ident);
                        analisisLexico += "\n<Operador>           " + ident;
                        tokensAnalizados.add(new Token(ident, "Operador"));
                        cadena = cadena.substring(ident.length());

                    } else {
                        System.out.println("Error de Sinstaxis");
                    }

                } else if (c.equals("(") || c.equals(")")) {
                    tokensAnalizados.add(new Token(c, "Agrupador"));
                    analisisLexico += "\n<Agrupador>           " + c;
                    System.out.println("<Agrupador>               " + c);
                    cadena = cadena.substring(1);
                } else if (c.equals("|") || c.equals(".")) {
                    tokensAnalizados.add(new Token(c, "Operador"));
                    analisisLexico += "\n<Operador>           " + c;
                    System.out.println("<Operador>                " + c);
                    cadena = cadena.substring(1);
                } else if (c.equals("Ɛ")) {
                    tokensAnalizados.add(new Token("Ɛ", "Constante"));
                    System.out.println("<Constante>               " + c);
                    analisisLexico += "\n<Constante>           " + c;
                    cadena = cadena.substring(1);
                } else {
                    tokensAnalizados.add(new Token(c, "No identificado"));
                    System.out.println("\n<DESCONOCIDO>              " + c);
                    analisisLexico += "<Desconocido>           " + c;
                    cadena = cadena.substring(1);
                }

            }
        }
        return tokensAnalizados;
    }

    private String searchNextAZ_09(String cadena) {
        if (cadena.isEmpty()) {
            return "";
        }
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
