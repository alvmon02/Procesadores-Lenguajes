package pretipado;

import asint.*;
import asint.SintaxisAbstractaTiny.*;
import errores_procesamiento.ErrorProcesamiento;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pretipado extends ProcesamientoDef {

    private Set<String> variablesDeclaradas;

    private List<ErrorProcesamiento> errorProcesamientos = new ArrayList<>();
    public boolean hayErrores() {
        return errorProcesamientos.size() > 0;
    }

    public List<ErrorProcesamiento> errores() {
        return errorProcesamientos;
    }

    public Pretipado() {
        errorProcesamientos = new ArrayList<>();
    }

    private void abreAmbito() {
        variablesDeclaradas = new HashSet<>();
    }

    private void cierraAmbito() {
        variablesDeclaradas = null;
    }

    @SuppressWarnings("rawtypes")
    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }

    @Override
    public void procesa(Prog prog) {
        prog.decs().procesa(this);
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
    public void procesa(Dec_Var dec_Var) {
        dec_Var.tipo().procesa(this);
    }

    @Override
    public void procesa(Dec_Tipo dec_Tipo) {
        dec_Tipo.tipo().procesa(this);
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        dec_Proc.pforms().procesa(this);
        dec_Proc.prog().procesa(this);
    }

    @Override
    public void procesa(Si_PForms si_PForms) {
        si_PForms.pforms().procesa(this);
    }

    @Override
    public void procesa(Mas_PForms mas_PForm) {
        mas_PForm.pforms().procesa(this);
        mas_PForm.pform().procesa(this);
    }

    @Override
    public void procesa(Una_PForm una_PForm) {
        una_PForm.pform().procesa(this);
    }

    @Override
    public void procesa(PForm pform) {
        pform.tipo().procesa(this);
    }

    @Override
    public void procesa(T_Iden tIden) {
        if (!claseDe(tIden.vinculo(), Dec_Tipo.class)) {
            errorProcesamientos.add(
                    errores_procesamiento.ErrorPretipado.errorTipoNoDeclarado(tIden.leeFila(), tIden.leeCol(), tIden.id()));
        }
    }

    @Override
    public void procesa(T_Array tArray) {
        tArray.tipo().procesa(this);
        if (Integer.parseInt(tArray.ent()) <= 0) {
            errorProcesamientos.add(errores_procesamiento.ErrorPretipado.errorDimensionNegativa(tArray.leeFila(), tArray.leeCol()));
        }
    }

    @Override
    public void procesa(T_Puntero tPuntero) {
        tPuntero.tipo().procesa(this);
    }

    @Override
    public void procesa(T_Struct tStruct) {
        abreAmbito();
        tStruct.camposS().procesa(this);
        cierraAmbito();
    }

    @Override
    public void procesa(Mas_Cmp_S mas_Cmp_S) {
        mas_Cmp_S.camposS().procesa(this);
        mas_Cmp_S.campoS().procesa(this);
    }

    @Override
    public void procesa(Un_Cmp_S un_Cmp_S) {
        un_Cmp_S.campoS().procesa(this);
    }

    @Override
    public void procesa(CampoS campoS) {
        campoS.tipo().procesa(this);
        if (variablesDeclaradas.contains(campoS.id())) {
            errorProcesamientos.add(errores_procesamiento.ErrorPretipado.errorCampoDuplicado(campoS.leeFila(), campoS.leeCol(), campoS.id()));
        } else {
            variablesDeclaradas.add(campoS.id());
        }
    }
}
