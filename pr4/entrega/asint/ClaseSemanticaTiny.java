package asint;

public class ClaseSemanticaTiny extends SintaxisAbstractaTiny {
    public ClaseSemanticaTiny() {
        super();
    }

    public Exp mkop(String op, Exp opnd1, Exp opnd2) {
        switch (op) {
            case "==":
                return e_comp(opnd1, opnd2);
            case "!=":
                return e_dist(opnd1, opnd2);
            case "<":
                return e_lt(opnd1, opnd2);
            case ">":
                return e_gt(opnd1, opnd2);
            case "<=":
                return e_leq(opnd1, opnd2);
            case ">=":
                return e_geq(opnd1, opnd2);
            case "*":
                return e_mul(opnd1, opnd2);
            case "/":
                return e_div(opnd1, opnd2);
            case "%":
                return e_porcentaje(opnd1, opnd2);
            default:
                throw new UnsupportedOperationException("Bad op");
        }
    }

    public Exp mkunary(String op, Exp opnd) {
        switch (op) {
            case "-":
                return e_negativo(opnd);
            case "not":
            case "<not>":
                return e_negado(opnd);
            default:
                throw new UnsupportedOperationException("Bad op");
        }
    }
}
