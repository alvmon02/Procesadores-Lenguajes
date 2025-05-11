package asig_espacio;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class asig_espacio extends ProcesamientoDef {

    private int dir = 0;
    private int nivel = 0;
    private int max_dir = 0;

    private void inc_dir(int tam) {
        dir += tam;
        if (dir > max_dir) {
            max_dir = dir;
        }
    }

    @Override
    public void procesa(Prog prog) {
        prog.bloque().procesa(this);
    }

    public void procesa(Bloque bloque) {
        int dir_ant = dir;
        bloque.decs().procesa(this);
        bloque.intrs().procesa(this);
        dir = dir_ant;
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
        decs.ldecs().procesa2(this);
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }

    @Override
    public void procesa2(Mas_Decs decs) {
        decs.ldecs().procesa2(this);
        decs.dec().procesa2(this);
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
    }

    @Override
    public void procesa2(Una_Dec dec) {
        dec.dec().procesa2(this);
    }

    @Override
    public void procesa(Dec_Var dec_Var) {
        dec_Var.ponDir(dir);
        dec_Var.ponNivel(nivel);
        dec_Var.tipo().procesa(this);
        inc_dir(dec_Var.tipo().tam());
    }

    @Override
    public void procesa2(Dec_Var dec_var) {
        dec_var.tipo().procesa(this);
    }

    @Override
    public void procesa(Dec_Tipo dec_tipo) {
        dec_tipo.tipo().procesa(this);
    }

    @Override
    public void procesa2(Dec_Tipo dec_tipo) {
        dec_tipo.tipo().procesa2(this);
    }

    @Override
    public void procesa(Dec_Proc dec_proc) {
        int dir_ant = dir;
        int max_dir_ant = max_dir;
        nivel++;
        dec_proc.ponNivel(nivel);
        dir = 0;
        max_dir = 0;
        dec_proc.pforms().procesa(this);
        dec_proc.prog().procesa(this);
        dec_proc.ponTam(max_dir);
        dir = dir_ant;
        max_dir = max_dir_ant;
        nivel--;
    }

    // TODO Creo que no es necesario
    @Override
    public void procesa2(Dec_Proc dec_proc) {
        // dec_proc.pforms().procesa2(this);
        // dec_proc.prog().procesa2(this);
    }

    @Override
    public void procesa(Si_PForms si_pforms) {
        si_pforms.pforms().procesa(this);
    }

    @Override
    public void procesa(Mas_PForms mas_pforms) {
        mas_pforms.pforms().procesa(this);
        mas_pforms.pform().procesa(this);
    }

    @Override
    public void procesa(Una_PForm una_pform) {
        una_pform.pform().procesa(this);
    }

    @Override
    public void procesa(PForm pform) {
        pform.tipo().procesa(this);
        pform.ponDir(dir);
        pform.ponNivel(nivel);
        if (claseDe(pform.ref(), Si_Ref.class)) {
            inc_dir(1);

        }
        else {
            inc_dir(pform.tipo().tam());
        }
    }

    @Override
    public void procesa(T_Iden t_iden) {
        t_iden.ponTam(t_iden.vinculo().tipo().tam());
    }

    @Override
    public void procesa(T_String t_string) {
        t_string.ponTam(1);
    }

    @Override
    public void procesa(T_Int t_int) {
        t_int.ponTam(1);
    }

    @Override
    public void procesa(T_Bool t_bool) {
        t_bool.ponTam(1);
    }

    @Override
    public void procesa(T_Real t_real) {
        t_real.ponTam(1);
    }

    @Override
    public void procesa(T_Array t_array) {
        t_array.tipo().procesa(this);
        t_array.ponTam(t_array.tipo().tam() * Integer.parseInt(t_array.ent())); 
    }

    @Override
    public void procesa2(T_Array t_array) {
        t_array.tipo().procesa2(this);
    }

    @Override
    public void procesa(T_Puntero t_puntero) {
        if (!claseDe(t_puntero.tipo(), T_Iden.class)) {
            t_puntero.tipo().procesa(this);
        }
        t_puntero.ponTam(1);
    }

    @Override
    public void procesa2(T_Puntero t_puntero) {
        if (claseDe(t_puntero.tipo(), T_Iden.class)) {
            t_puntero.tipo().ponTam(t_puntero.tipo().vinculo().tam());
        } else {
            t_puntero.tipo().procesa2(this);
        }
    }

    @Override
    public void procesa(T_Struct t_struct) {
        int dirAnt = dir;
        dir = 0;
        t_struct.camposS().procesa(this);
        t_struct.ponTam(dir);
        dir = dirAnt;
    }

    @Override
    public void procesa2(T_Struct t_struct){
        t_struct.camposS().procesa2(this);
    }

    @Override
    public void procesa(Mas_Cmp_S mas_cmp_s) {
        mas_cmp_s.camposS().procesa(this);
        mas_cmp_s.campoS().procesa(this);
    }

    @Override
    public void procesa2(Mas_Cmp_S mas_cmp_s) {
        mas_cmp_s.camposS().procesa2(this);
        mas_cmp_s.campoS().procesa2(this);
    }

    @Override
    public void procesa(Un_Cmp_S una_cmp_s) {
        una_cmp_s.campoS().procesa(this);
    }
    
    @Override
    public void procesa2(Un_Cmp_S una_cmp_s) {
        una_cmp_s.campoS().procesa2(this);
    }

    @Override
    public void procesa(CampoS campoS) {
        campoS.ponDir(dir);
        campoS.tipo().procesa(this);
        // campoS.ponTam(campoS.tipo().tam()); // TODO: Revisar si es necesario
        dir += campoS.tipo().tam();
    }

    @Override
    public void procesa2(CampoS campoS) {
        campoS.tipo().procesa2(this);
    }

    @Override
    public void procesa(Si_Intrs si_intrs) {
        si_intrs.intrs().procesa(this);
    }

    @Override
    public void procesa(Mas_Intrs mas_intrs) {
        mas_intrs.intrs().procesa(this);
        mas_intrs.intr().procesa(this);
    }

    @Override
    public void procesa(Una_Intr una_intr) {
        una_intr.intr().procesa(this);
    }

    @Override
    public void procesa(I_If i_if) {
        i_if.prog().procesa(this);
        i_if.i_else().procesa(this);
    }

    @Override
    public void procesa(I_While i_while) {
        i_while.exp().procesa(this);
        i_while.prog().procesa(this);
    }

    @Override
    public void procesa(I_Prog i_prog) {
        i_prog.prog().procesa(this);
    }

    @Override
    public void procesa(Si_Else si_else) {
        si_else.prog().procesa(this);
    }
}
