/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AnalizadorLexico;

/**
 *
 * @author marti
 */
public class Token {

    private String lexema;
    private String categ;

    Token(String lexema, String categ) {
        this.lexema = lexema;
        this.categ = categ;
    }

    public String getLex() {
        return this.lexema;
    }

    public String getCateg() {
        return this.categ;
    }

}
