/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package AnalizadorLexico;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author marti
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        MainFrame mf = new MainFrame();

        mf.setVisible(true);
        mf.setLocationRelativeTo(null);

        AnalizadorLexico al = new AnalizadorLexico();

        ArrayList<String> solucionFinal = al.solucionFinal(al.identificarOrden(al.analizarCadena("a^2.ba^3")));

    }

}
