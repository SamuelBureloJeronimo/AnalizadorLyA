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
public class Derivaciones {

    ArrayList<String> content = new ArrayList();
    int orden;

    public void add(ArrayList<String> content, int orden) {
        this.content = content;
        this.orden = orden;
    }

}
