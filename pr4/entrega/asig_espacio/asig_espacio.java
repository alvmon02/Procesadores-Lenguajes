package asig_espacio;


import java.util.Collection;

import java.util.List;
import java.util.ArrayList;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import errores_procesamiento.*;

public class asig_espacio extends ProcesamientoDef{
    
    private int dir = 0;
    private int nivel = 0;
    private int max_dir = 0;

    private void inc_dir(int tam){
        dir += tam;
        if (dir > max_dir) {
            max_dir = dir;
        }
    }

    @Override
    public void procesa(Prog prog){
        prog.bloque().procesa(this);
    }

    public void procesa(Bloque bloque){
        bloque.decs().procesa(this);
        bloque.intrs().procesa(this);
    }

    @Override
    public void procesa(Si_Decs decs){
        decs.ldecs().procesa(this);
        decs.ldecs().procesa2(this);
    }

    @Override
    public void procesa(Mas_Decs decs){
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }


    @Override
    public void procesa2(Mas_Decs decs){
        decs.ldecs().procesa2(this);
        decs.dec().procesa2(this);
    }


    @Override
    public void procesa(Una_Dec dec){
        dec.dec().procesa(this);
    }

    @Override
    public void procesa2(Una_Dec dec){
        dec.dec().procesa2(this);
    }

    @Override
    public void procesa (Dec_Var dec_Var){
        dec_Var.ponDir(dir);
        dec_Var.ponNivel(nivel); //todo
        dec_Var.tipo().procesa(this);
        inc_dir(dec_Var.tipo().tam()); //todo
    }

    @Override
    public void procesa2(Dec_Var dec_var){
        dec_var.tipo().procesa(this);
    }

    @Override
    public void procesa(Dec_Tipo dec_tipo){
        dec_tipo.tipo().procesa(this);
    }

    @Override
    public void procesa2(Dec_Tipo dec_tipo){
        dec_tipo.tipo().procesa2(this);
    }

    @Override
    public void procesa(Dec_Proc dec_proc){
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

    @Override
    public void procesa2(Dec_Proc dec_proc){
        dec_proc.pforms().procesa2(this);
        dec_proc.prog().procesa2(this);
    }

    @Override
    public void procesa(Si_PForms si_pforms){
        si_pforms.pforms().procesa(this);
    }

    @Override
    public void procesa(Mas_PForms mas_pforms){
        mas_pforms.pforms().procesa(this);
        mas_pforms.pform().procesa(this);
    }

    @Override
    public void procesa(Una_PForm una_pform){
        una_pform.pform().procesa(this);
    }

    @Override
    public void procesa(T_Iden t_iden){
        t_iden.ponTam(t_iden.vinculo().tipo().tam()); //todo
    }

    @Override
    public void procesa2(T_Iden t_iden){
    }

    @Override
    public void procesa(T_String t_string){
        t_string.ponTam(1); //todo
    }

    @Override
    public void procesa2(T_String t_string){
    }

    @Override
    public void procesa(T_Int t_int){
        t_int.ponTam(1); //todo
    }

    @Override
    public void procesa2(T_Int t_int){
    }

    @Override
    public void procesa(T_Bool t_bool){
        t_bool.ponTam(1); //todo
    }

    @Override
    public void procesa2(T_Bool t_bool){
    }

    @Override
    public void procesa(T_Real t_real){
        t_real.ponTam(1); //todo
    }

    @Override
    public void procesa2(T_Real t_real){
    }

    @Override
    public void procesa(T_Array t_array){
        t_array.ponDir(dir); //todo
        t_array.tipo().procesa(this);
        t_array.ponTam(t_array.tipo().tam() * t_array.numelems()); //todo
    }

    @Override
    public void procesa2(T_Array t_array){
    }

    @Override
    public void procesa(T_Puntero t_puntero){
        if (!(t_puntero.tipo() instanceof T_Iden)) {
            t_puntero.tipo().procesa(this);
        }
        t_puntero.ponTam(1);
    }

    @Override
    public void procesa2(T_Puntero t_puntero){
        if (t_puntero.tipo() instanceof T_Iden) {
            T_Iden tipoIden = (T_Iden) t_puntero.tipo();
            if (tipoIden.vinculo() instanceof Dec_Tipo) {
                Dec_Tipo decTipo = (Dec_Tipo) tipoIden.vinculo();
                tipoIden.ponTam(decTipo.tipo().tam());
            }
        } else {
            t_puntero.tipo().procesa2(this);
        }
    }

    @Override
    public void procesa(T_Struct t_struct){
        t_struct.camposS().procesa(this);
    }

    @Override
    public void procesa(Mas_Cmp_S mas_cmp_s){
        mas_cmp_s.camposS().procesa(this);
        mas_cmp_s.campoS().procesa2(this);
    }

    @Override
    public void procesa(Un_Cmp_S una_cmp_s){
        una_cmp_s.campoS().procesa2(this);
    }

    @Override
    public void procesa(CampoS campoS){
        campoS.ponDir(dir); //todo
        campoS.tipo().procesa(this);
        campoS.ponTam(campoS.tipo().tam()); //todo
        dir += campoS.tam(); //todo
    }


    @Override
    public void procesa(Si_Intrs si_intrs){
        si_intrs.intrs().procesa(this);
    }

    @Override
    public void procesa(Mas_Intrs mas_intrs){
        mas_intrs.intrs().procesa(this);
        mas_intrs.intr().procesa(this);
    }

    @Override
    public void procesa(Una_Intr una_intr){
        una_intr.intr().procesa(this);
    }

    @Override
    public void procesa(I_Eval i_eval) {
        i_eval.exp().procesa(this);
    }

    @Override
    public void procesa(I_If i_if) {
        i_if.exp().procesa(this);
        i_if.prog().procesa(this);
        i_if.i_else().procesa(this);
    }

    @Override
    public void procesa(I_While i_while) {
        i_while.exp().procesa(this);
        i_while.prog().procesa(this);
    }

    @Override
    public void procesa(I_Read i_read) {
        i_read.exp().procesa(this);
    }

    @Override
    public void procesa(I_Write i_write) {
        i_write.exp().procesa(this);
    }

    @Override
    public void procesa(I_NL i_nl) {
        
    }

    @Override
    public void procesa(I_New i_new) {
        i_new.exp().procesa(this);
    }

    @Override
    public void procesa(I_Delete i_delete) {
        i_delete.exp().procesa(this);
    }

    @Override
    public void procesa(I_Call i_call) {
        i_call.preals().procesa(this);
    }

    @Override
    public void procesa(I_Prog i_prog) {
        i_prog.prog().procesa(this);
    }

    @Override
    public void procesa(Si_Else si_else) {
        si_else.prog().procesa(this);
    }

    @Override
    public void procesa(No_Else no_else) {
        // No operation
    }

    @Override
    public void procesa(Si_PReals si_preals) {
        si_preals.preals().procesa(this);
    }

    @Override
    public void procesa(No_PReals no_preals) {
        // No operation
    }

    @Override
    public void procesa(Mas_PReals mas_preals) {
        mas_preals.preals().procesa(this);
        mas_preals.exp().procesa(this);
    }

    @Override
    public void procesa(Un_PReal un_preals) {
        un_preals.exp().procesa(this);
    }

    @Override
    public void procesa(Asig e_asig) {
        e_asig.opnd0().procesa(this);
        e_asig.opnd1().procesa(this);
    }

    @Override
    public void procesa(Comp e_comp) {
        e_comp.opnd0().procesa(this);
        e_comp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Dist e_dist) {
        e_dist.opnd0().procesa(this);
        e_dist.opnd1().procesa(this);
    }

    @Override
    public void procesa(Menor e_lt) {
        e_lt.opnd0().procesa(this);
        e_lt.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mayor e_gt) {
        e_gt.opnd0().procesa(this);
        e_gt.opnd1().procesa(this);
    }

    @Override
    public void procesa(MenorIgual e_leq) {
        e_leq.opnd0().procesa(this);
        e_leq.opnd1().procesa(this);
    }

    @Override
    public void procesa(MayorIgual e_geq) {
        e_geq.opnd0().procesa(this);
        e_geq.opnd1().procesa(this);
    }

    @Override
    public void procesa(Suma e_suma) {
        e_suma.opnd0().procesa(this);
        e_suma.opnd1().procesa(this);
    }

    @Override
    public void procesa(Resta e_resta) {
        e_resta.opnd0().procesa(this);
        e_resta.opnd1().procesa(this);
    }

    @Override
    public void procesa(And e_and) {
        e_and.opnd0().procesa(this);
        e_and.opnd1().procesa(this);
    }

    @Override
    public void procesa(Or e_or) {
        e_or.opnd0().procesa(this);
        e_or.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mul e_mul) {
        e_mul.opnd0().procesa(this);
        e_mul.opnd1().procesa(this);
    }

    @Override
    public void procesa(Div e_div) {
        e_div.opnd0().procesa(this);
        e_div.opnd1().procesa(this);
    }

    @Override
    public void procesa(Porcentaje e_porcentaje) {
        e_porcentaje.opnd0().procesa(this);
        e_porcentaje.opnd1().procesa(this);
    }

    @Override
    public void procesa(Negativo e_negativo) {
        e_negativo.opnd0().procesa(this);
    }

    @Override
    public void procesa(Negado e_negado) {
        e_negado.opnd0().procesa(this);
    }

    @Override
    public void procesa(Index e_indexado) {
        e_indexado.opnd0().procesa(this);
        e_indexado.opnd1().procesa(this);
    }

    @Override
    public void procesa(Acceso e_campo) {
        e_campo.opnd0().procesa(this);
    }

    @Override
    public void procesa(Indireccion e_puntero) {
        e_puntero.opnd0().procesa(this);
    }
}
