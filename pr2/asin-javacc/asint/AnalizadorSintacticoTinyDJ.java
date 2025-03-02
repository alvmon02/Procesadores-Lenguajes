package asint;

import java.io.Reader;


public class AnalizadorSintacticoTinyDJ extends AnalizadorSintacticoTiny {
    private void imprime(Token t) {
        switch(t.kind) {
            case t_int: System.out.println("<int>"); break;
            case t_real: System.out.println("<real>"); break;
            case t_bool: System.out.println("<bool>"); break;
            case and: System.out.println("<and>"); break;
            case or: System.out.println("<or>"); break;
            case not: System.out.println("<not>"); break;
            case t_string: System.out.println("<string>"); break;
            case nulo: System.out.println("<null>"); break;
            case t_true: System.out.println("<true>"); break;
            case t_false: System.out.println("<false>"); break;
            case proc: System.out.println("<proc>"); break;
            case t_if: System.out.println("<if>"); break;
            case t_else: System.out.println("<else>"); break;
            case t_while: System.out.println("<while>"); break;
            case struct: System.out.println("<struct>"); break;
            case t_new: System.out.println("<new>"); break;
            case delete: System.out.println("<delete>"); break;
            case read: System.out.println("<read>"); break;
            case write: System.out.println("<write>"); break;
            case nl: System.out.println("<nl>"); break;
            case type: System.out.println("<type>"); break;
            case call: System.out.println("<call>"); break;
            case EOF: System.out.println("<EOF>"); break;
            default: System.out.println(t.image);
        }
    }
    
    public AnalizadorSintacticoTinyDJ(Reader r) {
        super(r);
        disable_tracing();
    }
    final protected void trace_token(Token t, String where) {
        imprime(t);
    }
}
