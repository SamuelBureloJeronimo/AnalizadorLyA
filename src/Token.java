/**
 *
 * @author SpartanLuck 117
 */
public class Token {
    private String lexema;
    private String categ;
    
    Token(String lexema, String categ){
        this.lexema = lexema;
        this.categ = categ;
    }
    public String getLex(){
        return this.lexema;
    }
    public String getCateg(){
        return this.categ;
    }
}
