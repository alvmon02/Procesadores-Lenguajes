package alex;

public enum ClaseLexica {
    IDEN,
    ENT,
    REAL,
    STRING,
    PAP("("),
    PCIERRE(")"),
    LLAVEAP("{"),
    LLAVECIERRE("}"),
    LT("<"),
    LEQ("<="),
    GT(">"),
    GEQ(">="),
    COMP("=="),
    DIST("!="),
    IGUAL("="),
    PCOMA(";"),
    MAS("+"),
    MENOS("-"),
    MUL("*"),
    DIV("/"),
    INIEVAL("@"),
    FINDECLAR("&&"),
    P_INT("<int>"),
    P_REAL("<real>"),
    P_BOOL("<bool>"),
    P_AND("<and>"),
    P_OR("<or>"),
    P_NOT("<not>"),
    P_TRUE("<true>"),
    P_FALSE("<false>"),
    P_STRING("<string>"),
    P_NULL("<null>"),
    P_PROC("<proc>"),
    P_IF("<if>"),
    P_ELSE("<else>"),
    P_WHILE("<while>"),
    P_STRUCT("<struct>"),
    P_NEW("<new>"),
    P_DELETE("<delete>"),
    P_READ("<read>"),
    P_WRITE("<write>"),
    P_NL("<nl>"),
    P_TYPE("<type>"),
    P_CALL("<call>"),
    PORCENTAJE("%"),
    AND("&"),
    COMA(","),
    CORCHETEAP("["),
    CORCHETECIERRE("]"),
    PUNTO("."),
    EXPONENTE("^"),
    EOF("EOF");

    private String image;

    public String getImage() {
        return image;
    }

    private ClaseLexica() {
        image = toString();
    }

    private ClaseLexica(String image) {
        this.image = image;
    }

}
