package alex;

public enum ClaseLexica {
    IDEN,
    ENT,
    REAL,
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
    EOF("<EOF>");

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
