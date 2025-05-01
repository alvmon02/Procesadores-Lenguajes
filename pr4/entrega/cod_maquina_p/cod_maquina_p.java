package cod_maquina_p;
import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import asint.TiposBase.tReal;
import maquinap.*;

public class cod_maquina_p extends ProcesamientoDef{

    private MaquinaP m = new MaquinaP(5,10,10,2); // habrá que inicializarla con los params correctos

    public void procesa(Prog prog) {

    }

    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
    }

    public void procesa(No_Decs decs) {
    }

    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }

    public void procesa(Una_Dec dec) {

        dec.procesa(this);
    }

    public void procesa(Dec_Var dec_Var) {
    }

    public void procesa(Dec_Tipo dec_Tipo) {
    }

    public void procesa(Dec_Proc dec_Proc) {

    }

    public void procesa(Si_PForms si_PForms) {
    }

    public void procesa(No_PForms no_PForms) {
    }

    public void procesa(Mas_PForms mas_PForms) {
    }

    public void procesa(Una_PForm una_PForm) {
    }

    public void procesa(PForm pform) {
    }

    public void procesa(Si_Ref si_Ref) {
    }

    public void procesa(No_Ref no_Ref) {
    }

    public void procesa(T_Iden tIden) {
    }

    public void procesa(T_String tstring) {
    }

    public void procesa(T_Int tint) {
    }

    public void procesa(T_Bool tbool) {
    }

    public void procesa(T_Real treal) {
    }

    public void procesa(T_Array tArray) {
    }

    public void procesa(T_Puntero tPuntero) {
    }

    public void procesa(T_Struct tStruct) {
    }

    public void procesa(Mas_Cmp_S mas_Cmp_S) {
    }

    public void procesa(Un_Cmp_S un_Cmp_S) {
    }

    public void procesa(CampoS campoS) {
    }

    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
    }

    public void procesa(No_Intrs no_Intrs) {
    }

    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
    }

    public void procesa(Una_Intr una_Intr) {
        una_Intr.intr().procesa(this);
    }

    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
        m.emit(m.desapila());
    }

    public void procesa(I_If i_If) {
        i_If.exp().procesa(this);
        if (es_designador()){
            m.emit(m.apila_ind());
        }
        else{
            m.emit(m.ir_f(i_If.sig()));
            i_If.prog().procesa(this);
            m.emit(m.ir_a(i_If.fin()));
            i_If.i_else().procesa(this);
        }
    }

    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        if (es_designador()){
            m.emit(m.apila_ind());
        }
        else{
            m.emit(m.ir_f(i_While.sig()));
            i_While.prog().procesa(this);
            m.emit(m.ir_a(i_While.prim()));
        }
    }

    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
        m.emit(m.read());
        m.emit(m.desapila_ind());
    }

    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
        if(es_designador()){
            m.emit(m.apila_ind());
        }
    }

    public void procesa(I_NL i_Nl) {
    }

    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        m.emit(m.alloc(i_New.tipoNodo().tamano()));
    }

    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        m.emit(m.apila_ind());
        m.emit(m.dealloc(i_Delete.tipoNodo().tamano()));
    }

    public void procesa(I_Call i_Call) {
    }

    public void procesa(I_Prog i_Prog) {
    }

    public void procesa(Si_Else si_Else) {
    }

    public void procesa(No_Else no_Else) {
    }

    public void procesa(Si_PReals si_PReals) {
    }

    public void procesa(No_PReals no_PReals) {
    }

    public void procesa(Mas_PReals mas_PReals) {
    }

    public void procesa(Un_PReal un_PReal) {
    }

    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);

        if (exp.opnd0().tipoNodo() == tReal && exp.opnd1().tipoNodo() == tInt) {
            if (es_designador(exp.opnd1())) {
                m.emit(m.apila_ind());
                m.emit(m.int2real()); 
                m.emit(m.desapila_ind());
            }
        } else {
            if (es_designador(exp.opnd1())) {
                m.emit(m.copia(exp.opnd1().tipoNodo().tamano())); 
            } else {
                m.emit(m.desapila_ind());
            }
        }
    }

    public void procesa(Comp exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.comp()); // Emitimos la instrucción de comparación
    }

    public void procesa(Dist exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.dist()); // Emitimos la instrucción de distinción
    }

    public void procesa(Menor exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.lt()); // Emitimos la instrucción de menor que
    }

    public void procesa(Mayor exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.gt()); // Emitimos la instrucción de mayor que
    }

    public void procesa(MenorIgual exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.leq()); // Emitimos la instrucción de menor o igual que
    }

    public void procesa(MayorIgual exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.geq()); // Emitimos la instrucción de mayor o igual que
    }

    public void procesa(Suma exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        if (exp.opnd0().tipoNodo() == tReal || exp.opnd1().tipoNodo() == tReal) {
            if (exp.opnd0().tipoNodo() == tInt) {
                m.emit(m.int2real_subcima()); // Convertimos subcima de int a real
            }
            if (exp.opnd1().tipoNodo() == tInt) {
                m.emit(m.int2real_cima()); // Convertimos cima de int a real
            }
        }

        m.emit(m.suma()); // Emitimos la instrucción de suma
    }

    public void procesa(Resta exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        if (exp.opnd0().tipoNodo() == tReal || exp.opnd1().tipoNodo() == tReal) {
            if (exp.opnd0().tipoNodo() == tInt) {
                m.emit(m.int2real_subcima()); // Convertimos subcima de int a real
            }
            if (exp.opnd1().tipoNodo() == tInt) {
                m.emit(m.int2real_cima()); // Convertimos cima de int a real
            }
        }

        m.emit(m.resta()); // Emitimos la instrucción de resta
    }

    public void procesa(And exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.and()); // Emitimos la instrucción de AND
    }

    public void procesa(Or exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.or()); // Emitimos la instrucción de OR
    }

    public void procesa(Mul exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        if (exp.opnd0().tipoNodo() == tReal || exp.opnd1().tipoNodo() == tReal) {
            if (exp.opnd0().tipoNodo() == tInt) {
                m.emit(m.int2real_subcima()); // Convertimos subcima de int a real
            }
            if (exp.opnd1().tipoNodo() == tInt) {
                m.emit(m.int2real_cima()); // Convertimos cima de int a real
            }
        }

        m.emit(m.mul()); // Emitimos la instrucción de multiplicación
    }

    public void procesa(Div exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        if (exp.opnd0().tipoNodo() == tReal || exp.opnd1().tipoNodo() == tReal) {
            if (exp.opnd0().tipoNodo() == tInt) {
                m.emit(m.int2real_subcima()); // Convertimos subcima de int a real
            }
            if (exp.opnd1().tipoNodo() == tInt) {
                m.emit(m.int2real_cima()); // Convertimos cima de int a real
            }
        }

        m.emit(m.div()); // Emitimos la instrucción de división
    }

    public void procesa(Porcentaje exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        exp.opnd1().procesa(this);
        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }

        m.emit(m.porcentaje()); // Emitimos la instrucción de módulo
    }

    public void procesa(Negativo exp) {
        exp.opnd().procesa(this);
        if (es_designador(exp.opnd())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        m.emit(m.negativo()); // Negamos el valor de la cima
    }

    public void procesa(Negado exp) {
        exp.opnd().procesa(this);
        if (es_designador(exp.opnd())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        m.emit(m.negado()); // Negamos el valor de la cima
    }

    public void procesa(Index exp) {
        exp.opnd0().procesa(this); // Obtenemos la dirección del array
        exp.opnd1().procesa(this); // Obtenemos el índice
        m.emit(m.apila_int(exp.opnd0().tipoNodo().tamano())); // Apilamos el tamaño del tipo del array
        m.emit(m.mul()); // Apilamos el desplazamiento del índice
        m.emit(m.suma()); // Sumamos el desplazamiento del índice a la dirección de comienzo del array
    }

    public void procesa(Acceso exp) {
        exp.opnd().procesa(this); // Determinamos la dirección de E
        int d = exp.desplazamiento(); // Obtenemos el desplazamiento del campo id
        m.emit(m.apila_int(d)); // Apilamos el desplazamiento
        m.emit(m.suma()); // Sumamos el desplazamiento del campo a la dirección de comienzo de E
    }

    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this); // Determinamos dirección de E
        m.emit(m.apila_ind()); // Apilamos la dirección a la que apunta el puntero
    }

    public void procesa(Lit_ent exp) {
        m.emit(m.apila_int(Integer.parseInt(exp.num()))); // Apilamos el valor entero
    }

    public void procesa(True exp) {
        m.emit(m.apila_bool(true));
    }

    public void procesa(False exp) {
        m.emit(m.apila_bool(false));
    }

    public void procesa(Lit_real exp) {
        m.emit(m.apila_real(Float.parseFloat(exp.num()))); // Apilamos el valor real
    }

    public void procesa(Cadena exp) {
        m.emit(m.apila_str(exp.string())); // Apilamos la cadena
    }

    public void procesa(Iden exp) {
    }

    public void procesa(Null exp) {
        m.emit(m.apila_int(-1));
    }
}
