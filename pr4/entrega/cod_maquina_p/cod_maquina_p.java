package cod_maquina_p;
import java.util.Arrays;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinap.*;

public class cod_maquina_p extends ProcesamientoDef{

    private boolean es_designador(SintaxisAbstractaTiny.Exp exp) {
        
        return false;
    }

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
        if (es_designador(i_If.exp())) {
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
        if (es_designador(i_While.exp())) {
            m.emit(m.apila_ind());
        }
        else{
            m.emit(m.ir_f(i_While.sig()));
            i_While.prog().procesa(this);
            m.emit(m.ir_a(i_While.prim()));
        }
    }

    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this); // dirección de lectura se deja en la cima
        m.emit(m.read()); // lee el valor y se guarda en la cima
        m.emit(m.desapila_ind()); //guarda la cima en la dirección de la subcima
    }

    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this); //apilamos el valor a escribir
        if(es_designador(i_Write.exp())){ // si el valor es una direccion
            m.emit(m.apila_ind());  // apilamos el valor de la dirección
        }
        m.emit(m.write()); // escribimos el valor de la cima
    }

    public void procesa(I_NL i_Nl) {
    }

    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        m.emit(m.alloc(i_New.tipo().tam())); // se aisgna espacio desde la posicion de la cima. Tamaño asignado el del tipo
    }

    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        m.emit(m.apila_ind());
        m.emit(m.dealloc(i_Delete.tipo().tam())); // desapila la cima que tiene la direccion de inicio y desde esa pos se libera el tamaño del tipo
    }

    public void procesa(I_Call i_Call) {
        m.emit(m.activa(i_Call.vinculo().nivel(), i_Call.vinculo().tam(), i_Call.sig())); // Activamos el registro de activación, dejamos en la cima la direccion de comienzo del registro

        for (PReals preal : Arrays.asList(i_Call.preals())) {
            genPasoParam(i_Call.vinculo(), preal); // Generamos el paso de parámetros
        }

        m.emit(m.desapilad(i_Call.vinculo().nivel()));  //desapilamos una direccion de la pila de evaluación (dirección inicio datos) se guarda en display de nivel proc.nivel

        m.emit(m.desactiva(i_Call.vinculo().nivel(), i_Call.vinculo().tam())); // desactivamos el registro de activación dejamos en la cima la dirección de retorno
        m.emit(m.ir_ind()); // Saltamos a la dirección de retorno del resgistro de activación
    }

    private void genPasoParam(Proc proc, PReal preal) {
        m.emit(m.dup()); // Duplicamos la cima (dir de comienzo de los datos del registro de activación) par mantener invariante de que la cima es la dirección de comienzo del registro de activación
        m.emit(m.apila_int(preal.tamano())); // Apilamos el tamaño del parámetro
        m.emit(m.suma()); // Tenemos en la cima la dirección de comienzo del parámetro
        preal.procesa(this); // Generamos el código para el valor/dirección del parámetro

        if (proc.pform().ref() instanceof No_Ref) {
            if (es_designador(preal)) { // Parámetro formal por valor y real es designador
                m.emit(m.copia(proc.pform().tipo().tamano())); //cima (dir apuntada preal) subcima (dir inicio del param en el registro activacion) se realiza copia del tamaño del tipo
            } else {
                m.emit(m.desapila_ind()); // cima es el valor y subcima la dirección de comienzo del parámetro guardamos el valor en la dirección del param
            }
        } else {
            if (es_designador(preal)) { // Parámetro formal por referencia y real es designador
                m.emit(m.desapila_ind()); // Guardamos el valor apuntado por el preal en la dirección del pform
            } else {
                throw new IllegalArgumentException("Error: el parámetro formal es por referencia y el real no es designador.");
            }
        }
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

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) { //asignación de un int a un real
            if (es_designador(exp.opnd1())) { // si el real es un designador
                m.emit(m.apila_ind()); //apilamos el valor apuntado por el designador
                m.emit(m.int2real()); //convertimos el valor de la cima a real
                m.emit(m.desapila_ind()); //guardamos el valor convertido a int en la dirección de opnd0
            }
        } else { 
            if (es_designador(exp.opnd1())) {
                m.emit(m.copia(exp.opnd1().tipo().tam())); 
            } else {
                m.emit(m.desapila_ind()); // coge la cima(valor que se quiere asignar), la subcima (dirección de la exp0) y se le asigna el valor
            }
        }
    }

    public void procesa(Comp exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.comp()); // Emitimos la instrucción de comparación
    }

    public void procesa(Dist exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.dist()); // Emitimos la instrucción de distinción
    }

    public void procesa(Menor exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.lt()); // Emitimos la instrucción de menor que
    }

    public void procesa(Mayor exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }
        m.emit(m.gt()); // Emitimos la instrucción de mayor que
    }

    public void procesa(MenorIgual exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.leq()); // Emitimos la instrucción de menor o igual que
    }

    public void procesa(MayorIgual exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.geq()); // Emitimos la instrucción de mayor o igual que
    }

    public void procesa(Suma exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.suma()); // Emitimos la instrucción de suma
    }

    public void procesa(Resta exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.resta()); // Emitimos la instrucción de resta
    }

    public void procesa(And exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.and()); // Emitimos la instrucción de AND
    }

    public void procesa(Or exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.or()); // Emitimos la instrucción de OR
    }

    public void procesa(Mul exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.mul()); // Emitimos la instrucción de multiplicación
    }

    public void procesa(Div exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.div()); // Emitimos la instrucción de división
    }

    public void procesa(Porcentaje exp) {
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd0().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Int.class) && claseDe(exp.opnd1().vinculo(), T_Real.class)) {
            m.emit(m.int2real()); // Convertimos subcima de int a real
        }

        if (es_designador(exp.opnd1())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        else{
            exp.opnd1().procesa(this);
        }

        if (claseDe(exp.opnd0().vinculo(), T_Real.class) && claseDe(exp.opnd1().vinculo(), T_Int.class)) {
            m.emit(m.int2real()); // Convertimos cima de int a real
        }

        m.emit(m.porcentaje()); // Emitimos la instrucción de módulo
    }

    public void procesa(Negativo exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        m.emit(m.negativo()); // Negamos el valor de la cima
    }

    public void procesa(Negado exp) {
        exp.opnd0().procesa(this);
        if (es_designador(exp.opnd0())) {
            m.emit(m.apila_ind()); // Apilamos el valor apuntado por el designador
        }
        m.emit(m.negado()); // Negamos el valor de la cima
    }

    public void procesa(Index exp) {
        exp.opnd0().procesa(this); // Obtenemos la dirección del array
        exp.opnd1().procesa(this); // Obtenemos el índice
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
    }

    public void procesa(Null exp) {
        m.emit(m.apila_int(-1));
    }


}
