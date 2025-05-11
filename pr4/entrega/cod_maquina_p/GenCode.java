package cod_maquina_p;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import maquinap.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Stack;

public class GenCode extends ProcesamientoDef {

    private Stack<Dec_Proc> subPendientes = new Stack<>();

    private boolean esDesignador(Exp exp) {
        return claseDe(exp, Index.class) ||
                claseDe(exp, Acceso.class) ||
                claseDe(exp, Indireccion.class) ||
                claseDe(exp, Iden.class);
    }

    Reader reader = new InputStreamReader(System.in); // Inicializa el lector de entrada
    private MaquinaP m = new MaquinaP(reader, 500, 5000, 5000, 10);

    private void procesarSubPendientes() {
        while (!subPendientes.empty()) {
            Dec_Proc sub = subPendientes.pop();
            m.emit(m.apilad(sub.nivel()));
            sub.prog().procesa(this);
            m.emit(m.desactiva(sub.nivel(), sub.tam()));
            m.emit(m.ir_ind());
        }
    }

    @Override
    public void procesa(Prog prog) {
        prog.bloque().procesa(this);
        m.emit(m.stop());
        procesarSubPendientes();
    }

    @Override
    public void procesa(Bloque bloque) {
        bloque.decs().procesa(this);
        bloque.intrs().procesa(this);
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        subPendientes.push(dec_Proc);
    }

    @Override
    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
    }

    @Override
    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
    }

    @Override
    public void procesa(Una_Intr una_Intr) {
        una_Intr.intr().procesa(this);
    }

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
        m.emit(m.desapila());
    }

    private void procesa_exp(Exp exp) {
        exp.procesa(this);
        procesa_acc(exp);
    }

    private void procesa_acc(Exp exp) {
        if (esDesignador(exp)) {
            m.emit(m.apila_ind());
        }
    }

    @Override
    public void procesa(I_If i_If) {
        procesa_exp(i_If.exp());
        m.emit(m.ir_f(i_If.sig()));
        i_If.prog().procesa(this);
        i_If.i_else().procesa(this);
    }

    @Override
    public void procesa(I_While i_While) {
        procesa_exp(i_While.exp());
        m.emit(m.ir_f(i_While.sig()));
        i_While.prog().procesa(this);
        m.emit(m.ir_a(i_While.prim()));
    }

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this); // dirección de lectura se deja en la cima
        m.emit(m.read()); // lee el valor y se guarda en la cima
        m.emit(m.desapila_ind()); // guarda la cima en la dirección de la subcima
    }

    @Override
    public void procesa(I_Write i_Write) {
        procesa_exp(i_Write.exp());
        m.emit(m.write()); // escribimos el valor de la cima
    }

    @Override
    public void procesa(I_NL i_Nl) {
        m.emit(m.nl()); // escribimos un salto de línea
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        m.emit(m.alloc(i_New.exp().tipo().tipo().tam())); // se aisgna espacio desde la posicion de la cima.
                                                          // Tamaño asignado el del tipo
        m.emit(m.desapila_ind());
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        m.emit(m.apila_ind());
        m.emit(m.dealloc(i_Delete.exp().tipo().tipo().tam())); // desapila la cima que tiene la direccion de inicio y
                                                               // desde esa pos se libera el tamaño del tipo
    }

    @Override
    public void procesa(I_Call i_Call) {
        Dec_Proc proc = (Dec_Proc) i_Call.vinculo();
        m.emit(m.activa(proc.nivel(), proc.tam(), i_Call.sig())); // Activamos el registro de activación

        genCodeParams(proc.pforms(), i_Call.preals());

        m.emit(m.ir_a(proc.prim())); // Saltamos a la dirección de inicio del procedimiento
    }

    private void genCodeParams(PForms pforms, PReals preals) {
        if (claseDe(pforms, Si_PForms.class)) {
            genCodeParams(pforms.pforms(), preals.preals());
        }
    }

    private void genCodeParams(LPForms pforms, LPReals preals) {
        if (claseDe(pforms, Mas_PForms.class)) {
            genCodeParams(pforms.pforms(), preals.preals());
        }
        genCodeParams(pforms.pform(), preals.exp());
    }

    private void genCodeParams(PForm pform, Exp exp) {
        m.emit(m.dup()); // Duplicamos la cima (dir de comienzo del registro de activación)
        m.emit(m.apila_int(pform.dir())); // Apilamos la dirección del parámetro
        m.emit(m.suma()); // Calculamos la dirección de comienzo del parámetro
        exp.procesa(this); // Generamos el código para el valor/dirección del parámetro

        if (claseDe(pform.ref(), No_Ref.class)) {
            if (claseDe(referenciar(pform.tipo()), T_Real.class)
                    && claseDe(referenciar(exp.tipo()), T_Int.class)) { // Parámetro formal por valor y real es int
                procesa_acc(exp);
                m.emit(m.int2real()); // Convertimos el valor de la cima a real
                m.emit(m.desapila_ind()); // Guardamos el valor convertido a real en la dirección del parámetro
            } else if (esDesignador(exp)) { // Parámetro formal por valor y real es designador
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

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
    }

    @Override
    public void procesa(Si_Else si_Else) {
        m.emit(m.ir_a(si_Else.sig()));
        si_Else.prog().procesa(this);
    }

    @Override
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) { // asignación
                                                                                                           // de un int
                                                                                                           // a un real
            procesa_acc(exp.opnd1());
            m.emit(m.int2real()); // convertimos el valor de la cima a real
            m.emit(m.desapila_ind()); // guardamos el valor convertido a int en la dirección de opnd0
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
        procesa_exp(exp.opnd0());
        // conversión antes de segundo operando
        if (claseDe(referenciar(exp.opnd0().tipo()), T_Int.class)
                && claseDe(referenciar(exp.opnd1().tipo()), T_Real.class)) {
            m.emit(m.int2real());
        }

        // operando 1
        procesa_exp(exp.opnd1());
        // conversión tras segundo operando
        if (claseDe(referenciar(exp.opnd0().tipo()), T_Real.class)
                && claseDe(referenciar(exp.opnd1().tipo()), T_Int.class)) {
            m.emit(m.int2real());
        }
    }

    @Override
    public void procesa(Comp exp) {
        prepararOperandos(exp);
        m.emit(m.comp()); // Emitimos la instrucción de comparación
    }

    @Override
    public void procesa(Dist exp) {
        prepararOperandos(exp);
        m.emit(m.dist()); // Emitimos la instrucción de distinto
    }

    @Override
    public void procesa(Menor exp) {
        prepararOperandos(exp);
        m.emit(m.lt()); // Emitimos la instrucción de menor que
    }

    @Override
    public void procesa(Mayor exp) {
        prepararOperandos(exp);
        m.emit(m.gt()); // Emitimos la instrucción de mayor que
    }

    @Override
    public void procesa(MenorIgual exp) {
        prepararOperandos(exp);
        m.emit(m.leq()); // Emitimos la instrucción de menor o igual que
    }

    @Override
    public void procesa(MayorIgual exp) {
        prepararOperandos(exp);
        m.emit(m.geq()); // Emitimos la instrucción de mayor o igual que
    }

    @Override
    public void procesa(Suma exp) {
        prepararOperandos(exp);
        m.emit(m.suma()); // Emitimos la instrucción de suma
    }

    @Override
    public void procesa(Resta exp) {
        prepararOperandos(exp);
        m.emit(m.resta()); // Emitimos la instrucción de resta (suma con el negativo del segundo operando)
    }

    @Override
    public void procesa(And exp) {
        prepararOperandos(exp);
        m.emit(m.and()); // Emitimos la instrucción de AND
    }

    @Override
    public void procesa(Or exp) {
        prepararOperandos(exp);
        m.emit(m.or()); // Emitimos la instrucción de OR
    }

    @Override
    public void procesa(Mul exp) {
        prepararOperandos(exp);
        m.emit(m.mul()); // Emitimos la instrucción de multiplicación
    }

    @Override
    public void procesa(Div exp) {
        prepararOperandos(exp);
        m.emit(m.div()); // Emitimos la instrucción de división
    }

    @Override
    public void procesa(Porcentaje exp) {
        prepararOperandos(exp);
        m.emit(m.porcentaje()); // Emitimos la instrucción de módulo
    }

    @Override
    public void procesa(Negativo exp) {
        procesa_exp(exp); // Preparamos el operando
        m.emit(m.negativo()); // Negamos el valor de la cima
    }

    @Override
    public void procesa(Negado exp) {
        procesa_exp(exp);
        m.emit(m.negado()); // Negamos el valor de la cima
    }

    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this); // Obtenemos la dirección del array
        procesa_exp(exp.opnd1()); // Obtenemos el índice
        m.emit(m.apila_int(referenciar(referenciar(exp.opnd0().tipo()).tipo()).tam())); // Apilamos el tamaño del tipo
                                                                                        // del array
        m.emit(m.mul()); // Apilamos el desplazamiento del índice
        m.emit(m.suma()); // Sumamos el desplazamiento del índice a la dirección de comienzo del array
    }

    @Override // TODO revisar acceso esta mal
    public void procesa(Acceso exp) {
        // Generamos código para obtener la dirección base de la estructura
        exp.opnd0().procesa(this);

        // calulamos la dirección del campo
        Tipo tipoRegistro = referenciar(exp.opnd0().tipo());
        int desplazamiento = calcularDesplazamientoCampo(exp.id(), tipoRegistro.camposS());

        // apilamos el desplazamiento calculado
        m.emit(m.apila_int(desplazamiento));

        // sumamos el desplazamiento a la dirección base para obtener la dirección del
        // campo
        m.emit(m.suma());
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this); // Determinamos dirección de E
        m.emit(m.apila_ind()); // Apilamos la dirección a la que apunta el puntero
    }

    @Override
    public void procesa(Lit_ent exp) {
        m.emit(m.apila_int(Integer.parseInt(exp.num()))); // Apilamos el valor entero
    }

    @Override
    public void procesa(True exp) {
        m.emit(m.apila_bool(true));
    }

    @Override
    public void procesa(False exp) {
        m.emit(m.apila_bool(false));
    }

    @Override
    public void procesa(Lit_real exp) {
        m.emit(m.apila_real(Float.parseFloat(exp.num()))); // Apilamos el valor real
    }

    @Override
    public void procesa(Cadena exp) {
        m.emit(m.apila_str(exp.string())); // Apilamos la cadena
    }

    @Override
    public void procesa(Iden exp) {
        Nodo dec = exp.vinculo();

        // Verificamos qué tipo de declaración es
        if (claseDe(dec, Dec_Var.class)) {
            if (dec.nivel() == 0) {
                m.emit(m.apila_int(dec.dir()));
            } else {
                m.emit(m.apilad(dec.nivel()));
                m.emit(m.apila_int(dec.dir()));
                m.emit(m.suma());
            }
        } else if (claseDe(dec, PForm.class)) {
            m.emit(m.apilad(dec.nivel()));
            m.emit(m.apila_int(dec.dir()));
            m.emit(m.suma());
            if (claseDe(((PForm) dec).ref(), Si_Ref.class)) {
                m.emit(m.apila_ind());
            }
        }
    }

    @Override
    public void procesa(Null exp) {
        m.emit(m.apila_int(-1));
    }

    private int calcularDesplazamientoCampo(String id, CamposS camposS) {
        if (id.equals(camposS.campoS().id())) {
            return camposS.campoS().dir(); // Este es el campo que buscamos
        } else {
            if (claseDe(camposS, Mas_Cmp_S.class)) {
                return calcularDesplazamientoCampo(id, camposS.camposS());
            } else { // No deberia de llegar aqui
                throw new RuntimeException("Campo no encontrado: " + id);
            }
        }
    }
}
