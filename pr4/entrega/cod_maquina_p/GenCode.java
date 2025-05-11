package cod_maquina_p;

import java.util.Arrays;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinap.*;

import java.io.InputStreamReader;
import java.io.Reader;


public class GenCode extends ProcesamientoDef {

    private boolean esDesignador(Exp exp) {
        return claseDe(exp, Index.class) ||
                claseDe(exp, Acceso.class) ||
                claseDe(exp, Indireccion.class) ||
                claseDe(exp, Iden.class);
    }

    Reader reader = new InputStreamReader(System.in); // Inicializa el lector de entrada
    private MaquinaP m = new MaquinaP(reader, 5, 10, 10, 2); // habrá que inicializarla con los params correctos

    public void procesa(Prog prog) {
        prog.bloque().procesa(this);
    }

    public void procesa(Bloque bloque) {
        bloque.decs().procesa(this);
        bloque.intrs().procesa(this);
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

    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
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
        if (esDesignador(i_If.exp())) {
            m.emit(m.apila_ind());
        } else {
            m.emit(m.ir_f(i_If.sig()));
            i_If.prog().procesa(this);
            m.emit(m.ir_a(i_If.fin()));
            i_If.i_else().procesa(this);
        }
    }

    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        if (esDesignador(i_While.exp())) {
            m.emit(m.apila_ind());
        } else {
            m.emit(m.ir_f(i_While.sig()));
            i_While.prog().procesa(this);
            m.emit(m.ir_a(i_While.prim()));
        }
    }

    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this); // dirección de lectura se deja en la cima
        m.emit(m.read()); // lee el valor y se guarda en la cima
        m.emit(m.desapila_ind()); // guarda la cima en la dirección de la subcima
    }

    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this); // apilamos el valor a escribir
        if (esDesignador(i_Write.exp())) { // si el valor es una direccion
            m.emit(m.apila_ind()); // apilamos el valor de la dirección
        }
        m.emit(m.write()); // escribimos el valor de la cima
    }

    public void procesa(I_NL i_Nl) {
        // TODO hay que ver si hace algo, puede que tenga que escribir un salto de línea
    }

    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        m.emit(m.alloc(i_New.tipo().tam())); // se aisgna espacio desde la posicion de la cima. Tamaño asignado el del
                                             // tipo
    }

    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        m.emit(m.apila_ind());
        m.emit(m.dealloc(i_Delete.tipo().tam())); // desapila la cima que tiene la direccion de inicio y desde esa pos
                                                  // se libera el tamaño del tipo
    }

    public void procesa(I_Call i_Call) {
        Dec_Proc proc = (Dec_Proc) i_Call.vinculo();
        m.emit(m.activa(proc.nivel(), proc.tam(), i_Call.sig())); // Activamos el registro de activación

        genCodeParams(proc.pforms(), i_Call.preals());

        m.emit(m.ir_a(proc.prim())); // Saltamos a la dirección de inicio del procedimiento
    }

    private void genCodeParams(PForms pforms, PReals preals) {
        if (pforms instanceof No_PForms && preals instanceof No_PReals) {
            handleNoPFormsAndNoPReals();
        } else {
            if (claseDe(pforms, Si_PForms.class) && claseDe(preals, Si_PReals.class)) {
                handleSiPFormsAndSiPReals((Si_PForms) pforms, (Si_PReals) preals);
            } else if (claseDe(pforms, Mas_PForms.class) && claseDe(preals, Mas_PReals.class)) {
                handleMasPFormsAndMasPReals((Mas_PForms) pforms.pforms(), (Mas_PReals) preals.preals());
            } else if (claseDe(pforms, Una_PForm.class) && claseDe(preals, Un_PReal.class)) {
                handleUnaPFormAndUnPReal((Una_PForm) pforms.pforms(), (Un_PReal) preals.preals());
            } else {
                throw new IllegalArgumentException("Error: No se puede procesar la combinación de PForms y PReals.");
            }
        }
    }

    private void handleNoPFormsAndNoPReals() {
        // No hacemos nada, ya que no hay parámetros formales ni reales
    }

    private void genCodeParams(LPForms pforms, LPReals preals) {
        if (claseDe(pforms, No_PForms.class) && claseDe(preals, No_PReals.class)) {
            return; // no hacemos nada
        } else if (claseDe(pforms, Mas_PForms.class) && claseDe(preals, Mas_PReals.class)) {
            handleMasPFormsAndMasPReals((Mas_PForms) pforms, (Mas_PReals) preals);
        } else if (claseDe(pforms, Una_PForm.class) && claseDe(preals, Un_PReal.class)) {
            handleUnaPFormAndUnPReal((Una_PForm) pforms, (Un_PReal) preals);
        } else {
            throw new IllegalArgumentException("Error: No se puede procesar la combinación de PForms y PReals.");
        }
    }

    private void handleSiPFormsAndSiPReals(Si_PForms siPforms, Si_PReals siPreals) {
        genCodeParams(siPforms.pforms(), siPreals.preals());
    }

    private void handleMasPFormsAndMasPReals(Mas_PForms masPforms, Mas_PReals masPreals) {
        genCodeParams(masPforms.pforms(), masPreals.preals());
        genCodeParams(masPforms.pform(), masPreals.exp());
    }

    private void handleUnaPFormAndUnPReal(Una_PForm unaPform, Un_PReal unPreal) {
        genCodeParams(unaPform.pform(), unPreal.exp());
    }

    private void genCodeParams(PForm pform, Exp exp) {
        m.emit(m.dup()); // Duplicamos la cima (dir de comienzo del registro de activación)
        m.emit(m.apila_int(pform.dir())); // Apilamos la dirección del parámetro
        m.emit(m.suma()); // Calculamos la dirección de comienzo del parámetro
        exp.procesa(this); // Generamos el código para el valor/dirección del parámetro

        if (pform.ref() instanceof No_Ref) {
            if (esDesignador(exp)) { // Parámetro formal por valor y real es designador
                m.emit(m.copia(pform.tipo().tam())); // Copiamos el valor apuntado por el designador
            } else {
                m.emit(m.desapila_ind()); // Guardamos el valor en la dirección del parámetro
            }
        } else {
            if (esDesignador(exp)) { // Parámetro formal por referencia y real es designador
                m.emit(m.desapila_ind()); // Guardamos el valor apuntado por el preal en la dirección del pform
            } else {
                throw new IllegalArgumentException(
                        "Error: el parámetro formal es por referencia y el real no es designador.");
            }
        }
    }

    public void procesa(I_Prog i_Prog) {
        i_Prog.procesa(this);
    }

    public void procesa(Si_Else si_Else) {
        si_Else.procesa(this);
    }

    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) { // asignación
                                                                                                           // de un int
                                                                                                           // a un real
            if (esDesignador(exp.opnd1())) { // si el real es un designador
                m.emit(m.apila_ind()); // apilamos el valor apuntado por el designador
                m.emit(m.int2real()); // convertimos el valor de la cima a real
                m.emit(m.desapila_ind()); // guardamos el valor convertido a int en la dirección de opnd0
            }
        } else {
            if (esDesignador(exp.opnd1())) {
                m.emit(m.copia(exp.opnd1().tipo().tam()));
            } else {
                m.emit(m.desapila_ind()); // coge la cima(valor que se quiere asignar), la subcima (dirección de la
                                          // exp0) y se le asigna el valor
            }
        }
    }



    private void prepararOperandos(final ExpBin exp) {
        // operando 0
        exp.opnd0().procesa(this);
        if (esDesignador(exp.opnd0())) {
            m.emit(m.apila_ind());
        }
        // conversión antes de segundo operando
        if (claseDe(exp.opnd0().vinculo(), T_Int.class)
            && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real());
        }

        // operando 1
        exp.opnd1().procesa(this);
        if (esDesignador(exp.opnd1())) {
            m.emit(m.apila_ind());
        }
        // conversión tras segundo operando
        if (claseDe(exp.opnd0().vinculo(), T_Real.class)
            && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real());
        }
    }

    private void prepararOperando(final ExpUni exp) {
        exp.opnd0().procesa(this);
        if (esDesignador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
    }

    public void procesa(Comp exp) {
        prepararOperandos(exp);
        m.emit(m.comp()); // Emitimos la instrucción de comparación
    }

    public void procesa(Dist exp) {
        prepararOperandos(exp);
        m.emit(m.dist()); // Emitimos la instrucción de distinto
    }

    public void procesa(Menor exp) {
        prepararOperandos(exp);
        m.emit(m.lt()); // Emitimos la instrucción de menor que
    }

    public void procesa(Mayor exp) {
        prepararOperandos(exp);
        m.emit(m.gt()); // Emitimos la instrucción de mayor que
    }

    public void procesa(MenorIgual exp) {
        prepararOperandos(exp);
        m.emit(m.leq()); // Emitimos la instrucción de menor o igual que
    }

    public void procesa(MayorIgual exp) {
        prepararOperandos(exp);
        m.emit(m.geq()); // Emitimos la instrucción de mayor o igual que
    }

    public void procesa(Suma exp) {
        prepararOperandos(exp);
        m.emit(m.suma()); // Emitimos la instrucción de suma
    }

    public void procesa(Resta exp) {
        prepararOperandos(exp);
        m.emit(m.resta()); // Emitimos la instrucción de resta (suma con el negativo del segundo operando)
    }

    public void procesa(And exp) {
        prepararOperandos(exp);
        m.emit(m.and()); // Emitimos la instrucción de AND
    }

    public void procesa(Or exp) {
        prepararOperandos(exp);
        m.emit(m.or()); // Emitimos la instrucción de OR
    }

    public void procesa(Mul exp) {
        prepararOperandos(exp);
        m.emit(m.mul()); // Emitimos la instrucción de multiplicación
    }

    public void procesa(Div exp) {
        prepararOperandos(exp);
        m.emit(m.div()); // Emitimos la instrucción de división
    }

    public void procesa(Porcentaje exp) {
        prepararOperandos(exp);
        m.emit(m.porcentaje()); // Emitimos la instrucción de módulo
    }

    public void procesa(Negativo exp) {
        prepararOperando(exp); // Preparamos el operando
        m.emit(m.negativo()); // Negamos el valor de la cima
    }

  
    public void procesa(Negado exp) {
        prepararOperando(exp);
        m.emit(m.negado()); // Negamos el valor de la cima
    }

    public void procesa(Index exp) {
        exp.opnd0().procesa(this); // Obtenemos la dirección del array
        exp.opnd1().procesa(this); // Obtenemos el índice
        if (esDesignador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        m.emit(m.apila_int(exp.opnd0().tipo().tam())); // Apilamos el tamaño del tipo del array
        m.emit(m.mul()); // Apilamos el desplazamiento del índice
        m.emit(m.suma()); // Sumamos el desplazamiento del índice a la dirección de comienzo del array
    }

    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this); // Determinamos la dirección de E
        int d = exp.desplazamiento(); // Obtenemos el indice que se pide
        int tam_tipo = exp.opnd0().tipo().tam(); // Obtenemos el tamaño del tipo de E
        m.emit(m.apila_int(tam_tipo)); // Apilamos el tamaño del tipo de E
        m.emit(m.suma()); // Sumamos el desplazamiento del campo a la dirección de comienzo de E
        m.emit(m.acceso()); // Emitimos la instrucción de acceso al campo
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
        // TODO algo habra que hacer aquí hacer lo de procesa_acceso_id de la memoria
    }

    public void procesa(Null exp) {
        m.emit(m.apila_int(-1));
    }

}
