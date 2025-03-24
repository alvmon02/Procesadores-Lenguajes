package evaluacion;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import java.util.HashMap;
import java.util.Map;

public class Evaluacion extends ProcesamientoDef {
    private Map<String,Float> env;
     // para almacenar el resultado de la funcion
     // de procesamiento 'eval'
    private float resul; 
    public Evaluacion() {
        env = new HashMap<>();
    }
   public class ECteNoDefinida extends RuntimeException {
        public ECteNoDefinida(String msg) {
            super(msg);
        }
    }
    public class ECteDuplicada extends RuntimeException {
        public ECteDuplicada(String msg) {
            super(msg);
        }
    }
    public float leeResul() {
        return resul;
    } 
    
    
    public void procesa(Prog prog) {
        prog.decs().procesa(this);
        prog.intrs().procesa(this);
    }
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
    }
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }
    public void procesa(Una_Dec decs) {
        decs.dec().procesa(this);
    }
    
    
    public void procesa(Dec_Var dec) {
        if(env.containsKey(dec.id())) {
            throw new ECteDuplicada("Cte duplicada: "+dec.id()+
                                     " fila:"+dec.leeFila()+" col:"+dec.leeCol()); 
        }
        else {
            dec.tipo().procesa(this);
            env.put(dec.id(),resul);
        }
    }
    
    public void procesa(Dec_Tipo dec) {
        if(env.containsKey(dec.id())) {
            throw new ECteDuplicada("Cte duplicada: "+dec.id()+
                                     " fila:"+dec.leeFila()+" col:"+dec.leeCol()); 
        }
        else {
            dec.tipo().procesa(this);
            env.put(dec.id(),resul);
        }
    }
    
    public void procesa(Dec_Proc dec) {
        if(env.containsKey(dec.id())) {
            throw new ECteDuplicada("Cte duplicada: "+dec.id()+
                                     " fila:"+dec.leeFila()+" col:"+dec.leeCol()); 
        }
        else {
            dec.pforms().procesa(this);
            dec.prog().procesa(this);
            env.put(dec.id(),resul);
        }
    }
    
    public void procesa(Si_PForms si_PForms) {
        si_PForms.pforms().procesa(this);
    }
    
    public void procesa(Mas_PForms mas_PForms) {
        mas_PForms.pforms().procesa(this);
        mas_PForms.pform().procesa(this);
    }
    
    public void procesa(Una_PForm una_PForm) {
        una_PForm.pform().procesa(this);
    }
    
    public void procesa(PForm pForm) {
        if(env.containsKey(pForm.id())) {
            throw new ECteDuplicada("Cte duplicada: "+pForm.id()+
                                     " fila:"+pForm.leeFila()+" col:"+pForm.leeCol()); 
        }
        else {
            pForm.tipo().procesa(this);
            pForm.ref().procesa(this);
            env.put(pForm.id(),resul);
        }
    }
    
    public void procesa(T_Iden tIden) {
        if(env.containsKey(tIden.id())) {
            throw new ECteDuplicada("Cte duplicada: "+tIden.id()+
                                     " fila:"+tIden.leeFila()+" col:"+tIden.leeCol()); 
        }
        else {
            env.put(tIden.id(),resul);
        }
    }
    
    public void procesa(T_Array tArray) {
        tArray.tipo().procesa(this);
        tArray.ent().procesa(this);
    }
    
    public void procesa(T_Puntero tPuntero) {
        tPuntero.tipo().procesa(this);
    }
    
    public void procesa(T_Struct tStruct) {
        tStruct.camposS().procesa(this);
    }
    
    public void procesa(Mas_Cmp_S mas_Cmp_S) {
        mas_Cmp_S.camposS().procesa(this);
        mas_Cmp_S.campoS().procesa(this);
    }
    
    public void procesa(Un_Cmp_S un_Cmp_S) {
        un_Cmp_S.campoS().procesa(this);
    }
    
    public void procesa(CampoS campoS) {
        if(env.containsKey(campoS.id())) {
            throw new ECteDuplicada("Cte duplicada: "+campoS.id()+
                                     " fila:"+campoS.leeFila()+" col:"+campoS.leeCol()); 
        }
        else {
            campoS.tipo().procesa(this);
            env.put(campoS.id(),resul);
        }
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
    }
    
    public void procesa(I_If i_If) {
        i_If.exp().procesa(this);
        i_If.prog().procesa(this);
        i_If.i_else().procesa(this);
    }
    
    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        i_While.prog().procesa(this);
    }

    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
    }

    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
    }
    
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
    }
    
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
    }
    
    public void procesa(I_Call i_Call) {
        if(env.containsKey(i_Call.id())) {
            throw new ECteDuplicada("Cte duplicada: "+i_Call.id()+
                                     " fila:"+i_Call.leeFila()+" col:"+i_Call.leeCol()); 
        }
        else {
            i_Call.preals().procesa(this);
            env.put(i_Call.id(),resul);
        }
    }
    
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
    }
    
    public void procesa(Si_Else si_Else) {
        si_Else.prog().procesa(this);
    }
    
    public void procesa(Si_PReals si_PReals) {
        si_PReals.preals().procesa(this);
    }
    
    public void procesa(Mas_PReals mas_PReals) {
        mas_PReals.preals().procesa(this);
        mas_PReals.exp().procesa(this);
    }
    
    public void procesa(Un_PReal un_PReal) {
        un_PReal.exp().procesa(this);
    }
    
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }
    
    public void procesa(Comp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (exp.opnd0().equals(exp.opnd1())) {
            env.put(exp.id(), "true");
        }
        else
            env.put(exp.id(), "false");
    }
    
    public void procesa(Dist exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (!exp.opnd0().equals(exp.opnd1())) {
            env.put(exp.id(), "true");
        }
        else
            env.put(exp.id(), "false");
    }
    
    public void procesa(Menor exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (exp.opnd0() < exp.opnd1()) {
            env.put(exp.id(), "true");
        }
        else
            env.put(exp.id(), "false");
    }
    
    public void procesa(Mayor exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (exp.opnd0() > exp.opnd1()) {
            env.put(exp.id(), "true");
        }
        else
            env.put(exp.id(), "false");
    }
    
    public void procesa(MenorIgual exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (exp.opnd0() <= exp.opnd1()) {
            env.put(exp.id(), "true");
        }
        else
            env.put(exp.id(), "false");
    }
    
    public void procesa(MayorIgual exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (exp.opnd0() <= exp.opnd1()) {
            env.put(exp.id(), "true");
        }
        else
            env.put(exp.id(), "false");
    }
    
    
    public void procesa(Suma exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 + resul;
    }
    
    public void procesa(Resta exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 - resul;
    }
    
    public void procesa(And exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 && resul;
    }
    
    public void procesa(Or exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 || resul;
    }
    
    public void procesa(Mul exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 * resul;
    }    
    public void procesa(Div exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 / resul;
    }
    
    public void procesa(Porcentaje exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = resul;
        exp.opnd1().procesa(this);
        resul =  val_opnd0 % resul;
    }
    
    public void procesa(Negativo exp) {
        exp.opnd0().procesa(this);
        float val_opnd0 = -resul;
    }
    
    @Override
    public void procesa(Negado exp) {
        exp.opnd0().procesa(this);
        resul = ~resul;
    }
    
    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }
    
    @Override
    public void procesa(Acceso exp) {
       if(! env.containsKey(exp.id())) {
                throw new ECteNoDefinida("Cte indefinida: "+exp.id()+
                                        " fila:"+exp.leeFila()+" col:"+exp.leeCol()); 
       }
       else {
            exp.opnd0().procesa(this);
           resul = env.get(exp.id());
       }
        
    }
    
    @Override
    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this);
    }
    
    public void procesa(Lit_ent exp) {
         resul = Float.valueOf(exp.num()).floatValue();
    }
    
    @Override
    public void procesa(True exp) {
        exp.procesa(this);
    }
    
    @Override
    public void procesa(False exp) {
        exp.procesa(this);
    }
    
    public void procesa(Lit_real exp) {
         resul = Float.valueOf(exp.num()).floatValue();
    }
    
    @Override
    public void procesa(Cadena exp) {
       if(! env.containsKey(exp.string())) {
                throw new ECteNoDefinida("Cte indefinida: "+exp.string()+
                                        " fila:"+exp.leeFila()+" col:"+exp.leeCol()); 
       }
       else {
           resul = env.get(exp.string());
       }
        
    }    
    public void procesa(Iden exp) {
       if(! env.containsKey(exp.id())) {
                throw new ECteNoDefinida("Cte indefinida: "+exp.id()+
                                        " fila:"+exp.leeFila()+" col:"+exp.leeCol()); 
       }
       else {
           resul = env.get(exp.id());
       }
    }
    
}
