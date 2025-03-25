package evaluador;

import asint.SintaxisAbstractaTiny;

public class Evaluador extends SintaxisAbstractaTiny {

    public Evaluador() {
    }

    private void imprime(String string, int fila, int columna) {
        System.out.println(string + "$f:" + fila + ",c:" + columna + "$");
    }

    public void imprime(Prog prog) {
        System.out.println("{");
        imprime(prog.decs());
        imprime(prog.intrs());
        System.out.println("}");
    }

    private void imprime(DecsOpt decs) {
        if (claseDe(decs, Si_Decs.class)) {
            imprime(decs.ldecs());
            System.out.println("&&");
        }
    }

    private void imprime(LDecs ldecs) {
        if (claseDe(ldecs, Mas_Decs.class)) {
            imprime(ldecs.ldecs());
            System.out.println(";");
        }
        imprime(ldecs.dec());
    }

    private void imprime(Dec dec) {
        if (claseDe(dec, Dec_Proc.class)) {
            System.out.println("<proc>");
            imprime(dec.id(), dec.leeFila(), dec.leeCol());
            System.out.println("(");
            imprime(dec.pforms());
            System.out.println(")");
            imprime(dec.prog());
        } else {
            if (claseDe(dec, Dec_Tipo.class)) {
                System.out.println("<type>");
            }
            imprime(dec.tipo());
            imprime(dec.id(), dec.leeFila(), dec.leeCol());
        }
    }

    private void imprime(PForms pforms) {
        if (claseDe(pforms, Si_PForms.class)) {
            imprime(pforms.pforms());
        }
    }

    private void imprime(LPForms pforms) {
        if (claseDe(pforms, Mas_PForms.class)) {
            imprime(pforms.pforms());
            System.out.println(",");
        }
        imprime(pforms.pform());
    }

    private void imprime(PForm pform) {
        imprime(pform.tipo());
        imprime(pform.ref());
        imprime(pform.id(), pform.leeFila(), pform.leeCol());
    }

    private void imprime(Ref ref) {
        if (claseDe(ref, Si_Ref.class)) {
            System.out.println("&");
            // imprime("&", ref.leeFila(), ref.leeCol());
        }
    }

    private void imprime(Tipo tipo) {
        if (claseDe(tipo, T_Iden.class)) {
            imprime(tipo.id(), tipo.leeFila(), tipo.leeCol());
        } else if (claseDe(tipo, T_String.class)) {
            System.out.println("<string>");
        } else if (claseDe(tipo, T_Int.class)) {
            System.out.println("<int>");
        } else if (claseDe(tipo, T_Bool.class)) {
            System.out.println("<bool>");
        } else if (claseDe(tipo, T_Real.class)) {
            System.out.println("<real>");
        } else if (claseDe(tipo, T_Array.class)) {
            imprime(tipo.tipo());
            imprime("[\n" + tipo.ent() + "\n]", tipo.leeFila(), tipo.leeCol());
        } else if (claseDe(tipo, T_Puntero.class)) {
            System.out.println("^");
            imprime(tipo.tipo());
        } else if (claseDe(tipo, T_Struct.class)) {
            System.out.println("<struct>");
            System.out.println("{");
            imprime(tipo.camposS());
            System.out.println("}");
        }
    }

    private void imprime(CamposS camposS) {
        if (claseDe(camposS, Mas_Cmp_S.class)) {
            imprime(camposS.camposS());
            System.out.println(",");
        }
        imprime(camposS.campoS());
    }

    private void imprime(CampoS campoS) {
        imprime(campoS.tipo());
        imprime(campoS.id(), campoS.leeFila(), campoS.leeCol());
    }

    private void imprime(IntrsOpt intrs) {
        if (claseDe(intrs, Si_Intrs.class)) {
            imprime(intrs.intrs());
        }
    }

    private void imprime(LIntrs intrs) {
        if (claseDe(intrs, Mas_Intrs.class)) {
            imprime(intrs.intrs());
            System.out.println(";");
        }
        imprime(intrs.intr());
    }

    private void imprime(Intr intr) {
        if (claseDe(intr, I_Eval.class)) {
            System.out.println("@");
            imprime(intr.exp());
        } else if (claseDe(intr, I_If.class)) {
            System.out.println("<if>");
            imprime(intr.exp());
            imprime(intr.prog());
            imprime(intr.i_else());
        } else if (claseDe(intr, I_While.class)) {
            System.out.println("<while>");
            imprime(intr.exp());
            imprime(intr.prog());
        } else if (claseDe(intr, I_Read.class)) {
            System.out.println("<read>");
            imprime(intr.exp());
        } else if (claseDe(intr, I_Write.class)) {
            System.out.println("<write>");
            imprime(intr.exp());
        } else if (claseDe(intr, I_NL.class)) {
            System.out.println("<nl>");
        } else if (claseDe(intr, I_New.class)) {
            System.out.println("<new>");
            imprime(intr.exp());
        } else if (claseDe(intr, I_Delete.class)) {
            System.out.println("<delete>");
            imprime(intr.exp());
        } else if (claseDe(intr, I_Call.class)) {
            System.out.println("<call>");
            imprime(intr.id(), intr.leeFila(), intr.leeCol());
            System.out.println("(");
            imprime(intr.preals());
            System.out.println(")");
        } else if (claseDe(intr, I_Prog.class)) {
            imprime(intr.prog());
        }
    }

    private void imprime(I_Else i_else) {
        if (claseDe(i_else, Si_Else.class)) {
            System.out.println("<else>");
            imprime(i_else.prog());
        }
    }

    private void imprime(PReals preals) {
        if (claseDe(preals, Si_PReals.class)) {
            imprime(preals.preals());
        }
    }

    private void imprime(LPReals preals) {
        if (claseDe(preals, Mas_PReals.class)) {
            imprime(preals.preals());
            System.out.println(",");
        }
        imprime(preals.exp());
    }

    private void imprime(Exp exp) {
        if (claseDe(exp, Asig.class)) {
            imprime(exp.opnd0(), 1);
            imprime("=", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 0);
        } else if (claseDe(exp, Comp.class)) {
            imprime(exp.opnd0(), 1);
            imprime("==", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 2);
        } else if (claseDe(exp, Dist.class)) {
            imprime(exp.opnd0(), 1);
            imprime("!=", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 2);
        } else if (claseDe(exp, Menor.class)) {
            imprime(exp.opnd0(), 1);
            imprime("<", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 2);
        } else if (claseDe(exp, Mayor.class)) {
            imprime(exp.opnd0(), 1);
            imprime(">", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 2);
        } else if (claseDe(exp, MenorIgual.class)) {
            imprime(exp.opnd0(), 1);
            imprime("<=", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 2);
        } else if (claseDe(exp, MayorIgual.class)) {
            imprime(exp.opnd0(), 1);
            imprime(">=", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 2);
        } else if (claseDe(exp, Suma.class)) {
            imprime(exp.opnd0(), 2);
            imprime("+", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 3);
        } else if (claseDe(exp, Resta.class)) {
            imprime(exp.opnd0(), 3);
            imprime("-", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 3);
        } else if (claseDe(exp, And.class)) {
            imprime(exp.opnd0(), 4);
            imprime("<and>", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 3);
        } else if (claseDe(exp, Or.class)) {
            imprime(exp.opnd0(), 4);
            imprime("<or>", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 4);
        } else if (claseDe(exp, Mul.class)) {
            imprime(exp.opnd0(), 4);
            imprime("*", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 5);
        } else if (claseDe(exp, Div.class)) {
            imprime(exp.opnd0(), 4);
            imprime("/", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 5);
        } else if (claseDe(exp, Porcentaje.class)) {
            imprime(exp.opnd0(), 4);
            imprime("%", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1(), 5);
        } else if (claseDe(exp, Negativo.class)) {
            imprime("-", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd0(), 5);
        } else if (claseDe(exp, Negado.class)) {
            System.out.println("<not>");
            imprime(exp.opnd0(), 5);
        } else if (claseDe(exp, Index.class)) {
            imprime(exp.opnd0());
            imprime("[", exp.leeFila(), exp.leeCol());
            imprime(exp.opnd1());
            System.out.println("]");
        } else if (claseDe(exp, Acceso.class)) {
            imprime(exp.opnd0());
            System.out.println(".");
            imprime(exp.id(), exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, Indireccion.class)) {
            imprime(exp.opnd0());
            imprime("^", exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, Lit_ent.class) || claseDe(exp, Lit_real.class)) {
            imprime(exp.num(), exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, True.class)) {
            imprime("<true>", exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, False.class)) {
            imprime("<false>", exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, Cadena.class)) {
            imprime(exp.string(), exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, Iden.class)) {
            imprime(exp.id(), exp.leeFila(), exp.leeCol());
        } else if (claseDe(exp, Null.class)) {
            imprime("<null>", exp.leeFila(), exp.leeCol());
        }
    }

    private void imprime(Exp opnd, int np) {
        if (opnd.prioridad() < np) {
            System.out.print("(");
        }
        imprime(opnd);
        if (opnd.prioridad() < np) {
            System.out.print(")");
        }
    }

    @SuppressWarnings("rawtypes")
    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }
}
