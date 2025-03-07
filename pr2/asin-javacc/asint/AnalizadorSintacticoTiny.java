/* AnalizadorSintacticoTiny.java */
/* Generated By:JavaCC: Do not edit this line. AnalizadorSintacticoTiny.java */
package asint;

public class AnalizadorSintacticoTiny implements AnalizadorSintacticoTinyConstants {
    protected void newToken(Token t) {}

  final public void analiza() throws ParseException {
    trace_call("analiza");
    try {

      programa();
      jj_consume_token(0);
    } finally {
      trace_return("analiza");
    }
}

  final public void programa() throws ParseException {
    trace_call("programa");
    try {

      bloque();
    } finally {
      trace_return("programa");
    }
}

  final public void bloque() throws ParseException {
    trace_call("bloque");
    try {

      jj_consume_token(35);
      declar_opt();
      instr_opt();
      jj_consume_token(36);
    } finally {
      trace_return("bloque");
    }
}

  final public void declar_opt() throws ParseException {
    trace_call("declar_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_int:
      case t_real:
      case t_bool:
      case t_string:
      case proc:
      case struct:
      case type:
      case iden:
      case 45:{
        lista_declaraciones();
        jj_consume_token(37);
        break;
        }
      default:
        jj_la1[0] = jj_gen;

      }
    } finally {
      trace_return("declar_opt");
    }
}

  final public void lista_declaraciones() throws ParseException {
    trace_call("lista_declaraciones");
    try {

      declaracion();
      rlista_decs();
    } finally {
      trace_return("lista_declaraciones");
    }
}

  final public void rlista_decs() throws ParseException {
    trace_call("rlista_decs");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 38:{
        jj_consume_token(38);
        declaracion();
        rlista_decs();
        break;
        }
      default:
        jj_la1[1] = jj_gen;

      }
    } finally {
      trace_return("rlista_decs");
    }
}

  final public void declaracion() throws ParseException {
    trace_call("declaracion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_int:
      case t_real:
      case t_bool:
      case t_string:
      case struct:
      case iden:
      case 45:{
        declaracion_variable();
        break;
        }
      case type:{
        declaracion_tipo();
        break;
        }
      case proc:{
        declaracion_proc();
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("declaracion");
    }
}

  final public void declaracion_variable() throws ParseException {
    trace_call("declaracion_variable");
    try {

      tipo_0();
      jj_consume_token(iden);
    } finally {
      trace_return("declaracion_variable");
    }
}

  final public void declaracion_tipo() throws ParseException {
    trace_call("declaracion_tipo");
    try {

      jj_consume_token(type);
      tipo_0();
      jj_consume_token(iden);
    } finally {
      trace_return("declaracion_tipo");
    }
}

  final public void declaracion_proc() throws ParseException {
    trace_call("declaracion_proc");
    try {

      jj_consume_token(proc);
      jj_consume_token(iden);
      jj_consume_token(39);
      param_formales();
      jj_consume_token(40);
      bloque();
    } finally {
      trace_return("declaracion_proc");
    }
}

  final public void param_formales() throws ParseException {
    trace_call("param_formales");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_int:
      case t_real:
      case t_bool:
      case t_string:
      case struct:
      case iden:
      case 45:{
        lista_params();
        break;
        }
      default:
        jj_la1[3] = jj_gen;

      }
    } finally {
      trace_return("param_formales");
    }
}

  final public void lista_params() throws ParseException {
    trace_call("lista_params");
    try {

      parametro();
      rlista_param();
    } finally {
      trace_return("lista_params");
    }
}

  final public void rlista_param() throws ParseException {
    trace_call("rlista_param");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 41:{
        jj_consume_token(41);
        parametro();
        rlista_param();
        break;
        }
      default:
        jj_la1[4] = jj_gen;

      }
    } finally {
      trace_return("rlista_param");
    }
}

  final public void parametro() throws ParseException {
    trace_call("parametro");
    try {

      tipo_0();
      ref_op();
      jj_consume_token(iden);
    } finally {
      trace_return("parametro");
    }
}

  final public void ref_op() throws ParseException {
    trace_call("ref_op");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 42:{
        jj_consume_token(42);
        break;
        }
      default:
        jj_la1[5] = jj_gen;

      }
    } finally {
      trace_return("ref_op");
    }
}

  final public void tipo_0() throws ParseException {
    trace_call("tipo_0");
    try {

      tipo_1();
      tipo_0Prime();
    } finally {
      trace_return("tipo_0");
    }
}

  final public void tipo_0Prime() throws ParseException {
    trace_call("tipo_0Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 43:{
        jj_consume_token(43);
        jj_consume_token(lit_ent);
        jj_consume_token(44);
        tipo_0Prime();
        break;
        }
      default:
        jj_la1[6] = jj_gen;

      }
    } finally {
      trace_return("tipo_0Prime");
    }
}

// Se asegura de que los arrays se reconozcan correctamente
  final public   void tipo_1() throws ParseException {
    trace_call("tipo_1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 45:{
        puntero();
        break;
        }
      case t_int:
      case t_real:
      case t_bool:
      case t_string:
      case struct:
      case iden:{
        tipo_2();
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo_1");
    }
}

  final public void tipo_2() throws ParseException {
    trace_call("tipo_2");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_int:
      case t_real:
      case t_bool:
      case t_string:{
        tipo_basico();
        break;
        }
      case iden:{
        jj_consume_token(iden);
        break;
        }
      case struct:{
        struct();
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo_2");
    }
}

// Se ajustó para que acepte tipos básicos
  final public   void tipo_basico() throws ParseException {
    trace_call("tipo_basico");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_int:{
        jj_consume_token(t_int);
        break;
        }
      case t_real:{
        jj_consume_token(t_real);
        break;
        }
      case t_bool:{
        jj_consume_token(t_bool);
        break;
        }
      case t_string:{
        jj_consume_token(t_string);
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo_basico");
    }
}

  final public void struct() throws ParseException {
    trace_call("struct");
    try {

      jj_consume_token(struct);
      jj_consume_token(35);
      campos_struct();
      jj_consume_token(36);
    } finally {
      trace_return("struct");
    }
}

  final public void campos_struct() throws ParseException {
    trace_call("campos_struct");
    try {

      lista_campos_struct();
    } finally {
      trace_return("campos_struct");
    }
}

  final public void lista_campos_struct() throws ParseException {
    trace_call("lista_campos_struct");
    try {

      campo_struct();
      rlista_campo_struct();
    } finally {
      trace_return("lista_campos_struct");
    }
}

  final public void rlista_campo_struct() throws ParseException {
    trace_call("rlista_campo_struct");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 41:{
        jj_consume_token(41);
        campo_struct();
        rlista_campo_struct();
        break;
        }
      default:
        jj_la1[10] = jj_gen;

      }
    } finally {
      trace_return("rlista_campo_struct");
    }
}

  final public void campo_struct() throws ParseException {
    trace_call("campo_struct");
    try {

      tipo_0();
      jj_consume_token(iden);
    } finally {
      trace_return("campo_struct");
    }
}

  final public void puntero() throws ParseException {
    trace_call("puntero");
    try {

      jj_consume_token(45);
      tipo_1();
    } finally {
      trace_return("puntero");
    }
}

  final public void instr_opt() throws ParseException {
    trace_call("instr_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_if:
      case t_while:
      case t_new:
      case delete:
      case read:
      case write:
      case nl:
      case call:
      case 35:
      case 46:{
        lista_instrucciones();
        break;
        }
      default:
        jj_la1[11] = jj_gen;

      }
    } finally {
      trace_return("instr_opt");
    }
}

  final public void lista_instrucciones() throws ParseException {
    trace_call("lista_instrucciones");
    try {

      instruccion();
      rlista_instrs();
    } finally {
      trace_return("lista_instrucciones");
    }
}

  final public void rlista_instrs() throws ParseException {
    trace_call("rlista_instrs");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 38:{
        jj_consume_token(38);
        instruccion();
        rlista_instrs();
        break;
        }
      default:
        jj_la1[12] = jj_gen;

      }
    } finally {
      trace_return("rlista_instrs");
    }
}

  final public void instruccion() throws ParseException {
    trace_call("instruccion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 46:{
        instruccion_eval();
        break;
        }
      case t_if:{
        instruccion_if();
        break;
        }
      case t_while:{
        instruccion_while();
        break;
        }
      case read:{
        instruccion_read();
        break;
        }
      case write:{
        instruccion_write();
        break;
        }
      case nl:{
        instruccion_nl();
        break;
        }
      case t_new:{
        instruccion_new();
        break;
        }
      case delete:{
        instruccion_delete();
        break;
        }
      case call:{
        instruccion_call();
        break;
        }
      case 35:{
        instruccion_compuesta();
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("instruccion");
    }
}

  final public void instruccion_eval() throws ParseException {
    trace_call("instruccion_eval");
    try {

      jj_consume_token(46);
      expresion();
    } finally {
      trace_return("instruccion_eval");
    }
}

  final public void instruccion_if() throws ParseException {
    trace_call("instruccion_if");
    try {

      jj_consume_token(t_if);
      expresion();
      bloque();
      instruccion_else_opt();
    } finally {
      trace_return("instruccion_if");
    }
}

  final public void instruccion_else_opt() throws ParseException {
    trace_call("instruccion_else_opt");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_else:{
        instruccion_else();
        break;
        }
      default:
        jj_la1[14] = jj_gen;

      }
    } finally {
      trace_return("instruccion_else_opt");
    }
}

  final public void instruccion_else() throws ParseException {
    trace_call("instruccion_else");
    try {

      jj_consume_token(t_else);
      bloque();
    } finally {
      trace_return("instruccion_else");
    }
}

  final public void instruccion_while() throws ParseException {
    trace_call("instruccion_while");
    try {

      jj_consume_token(t_while);
      expresion();
      bloque();
    } finally {
      trace_return("instruccion_while");
    }
}

  final public void instruccion_read() throws ParseException {
    trace_call("instruccion_read");
    try {

      jj_consume_token(read);
      expresion();
    } finally {
      trace_return("instruccion_read");
    }
}

  final public void instruccion_write() throws ParseException {
    trace_call("instruccion_write");
    try {

      jj_consume_token(write);
      expresion();
    } finally {
      trace_return("instruccion_write");
    }
}

  final public void instruccion_nl() throws ParseException {
    trace_call("instruccion_nl");
    try {

      jj_consume_token(nl);
    } finally {
      trace_return("instruccion_nl");
    }
}

  final public void instruccion_new() throws ParseException {
    trace_call("instruccion_new");
    try {

      jj_consume_token(t_new);
      expresion();
    } finally {
      trace_return("instruccion_new");
    }
}

  final public void instruccion_delete() throws ParseException {
    trace_call("instruccion_delete");
    try {

      jj_consume_token(delete);
      expresion();
    } finally {
      trace_return("instruccion_delete");
    }
}

  final public void instruccion_call() throws ParseException {
    trace_call("instruccion_call");
    try {

      jj_consume_token(call);
      jj_consume_token(iden);
      jj_consume_token(39);
      params_reales();
      jj_consume_token(40);
    } finally {
      trace_return("instruccion_call");
    }
}

  final public void params_reales() throws ParseException {
    trace_call("params_reales");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case not:
      case nulo:
      case t_true:
      case t_false:
      case iden:
      case lit_ent:
      case lit_real:
      case cadena:
      case 39:
      case 49:{
        lista_params_reales();
        break;
        }
      default:
        jj_la1[15] = jj_gen;

      }
    } finally {
      trace_return("params_reales");
    }
}

  final public void lista_params_reales() throws ParseException {
    trace_call("lista_params_reales");
    try {

      param_real();
      rlista_params_reales();
    } finally {
      trace_return("lista_params_reales");
    }
}

  final public void rlista_params_reales() throws ParseException {
    trace_call("rlista_params_reales");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 41:{
        jj_consume_token(41);
        param_real();
        rlista_params_reales();
        break;
        }
      default:
        jj_la1[16] = jj_gen;

      }
    } finally {
      trace_return("rlista_params_reales");
    }
}

  final public void param_real() throws ParseException {
    trace_call("param_real");
    try {

      expresion();
    } finally {
      trace_return("param_real");
    }
}

  final public void instruccion_compuesta() throws ParseException {
    trace_call("instruccion_compuesta");
    try {

      bloque();
    } finally {
      trace_return("instruccion_compuesta");
    }
}

  final public void expresion() throws ParseException {
    trace_call("expresion");
    try {

      e0();
    } finally {
      trace_return("expresion");
    }
}

  final public void e0() throws ParseException {
    trace_call("e0");
    try {

      e1();
      e0Prime();
    } finally {
      trace_return("e0");
    }
}

  final public void e0Prime() throws ParseException {
    trace_call("e0Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 47:{
        jj_consume_token(47);
        e0();
        break;
        }
      default:
        jj_la1[17] = jj_gen;

      }
    } finally {
      trace_return("e0Prime");
    }
}

  final public void e1() throws ParseException {
    trace_call("e1");
    try {

      e2();
      e1Prime();
    } finally {
      trace_return("e1");
    }
}

  final public void e1Prime() throws ParseException {
    trace_call("e1Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:{
        op1();
        e2();
        e1Prime();
        break;
        }
      default:
        jj_la1[18] = jj_gen;

      }
    } finally {
      trace_return("e1Prime");
    }
}

  final public void e2() throws ParseException {
    trace_call("e2");
    try {

      e3();
      e2Prime();
    } finally {
      trace_return("e2");
    }
}

  final public void e2Prime() throws ParseException {
    trace_call("e2Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 48:{
        jj_consume_token(48);
        e3();
        e2Prime();
        break;
        }
      case 49:{
        jj_consume_token(49);
        e3();
        break;
        }
      default:
        jj_la1[19] = jj_gen;

      }
    } finally {
      trace_return("e2Prime");
    }
}

  final public void e3() throws ParseException {
    trace_call("e3");
    try {

      e4();
      e3Prime();
    } finally {
      trace_return("e3");
    }
}

  final public void e3Prime() throws ParseException {
    trace_call("e3Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case and:{
        jj_consume_token(and);
        e3();
        break;
        }
      case or:{
        jj_consume_token(or);
        e4();
        break;
        }
      default:
        jj_la1[20] = jj_gen;

      }
    } finally {
      trace_return("e3Prime");
    }
}

  final public void e4() throws ParseException {
    trace_call("e4");
    try {

      e5();
      e4Prime();
    } finally {
      trace_return("e4");
    }
}

  final public void e4Prime() throws ParseException {
    trace_call("e4Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 56:
      case 57:
      case 58:{
        op4();
        e5();
        e4Prime();
        break;
        }
      default:
        jj_la1[21] = jj_gen;

      }
    } finally {
      trace_return("e4Prime");
    }
}

  final public void e5() throws ParseException {
    trace_call("e5");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case nulo:
      case t_true:
      case t_false:
      case iden:
      case lit_ent:
      case lit_real:
      case cadena:
      case 39:{
        e6();
        break;
        }
      case not:
      case 49:{
        op5();
        e5();
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("e5");
    }
}

  final public void e6() throws ParseException {
    trace_call("e6");
    try {

      e7();
      e6Prime();
    } finally {
      trace_return("e6");
    }
}

  final public void e6Prime() throws ParseException {
    trace_call("e6Prime");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 43:
      case 45:
      case 59:{
        op6();
        e6Prime();
        break;
        }
      default:
        jj_la1[23] = jj_gen;

      }
    } finally {
      trace_return("e6Prime");
    }
}

  final public void e7() throws ParseException {
    trace_call("e7");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case lit_ent:{
        jj_consume_token(lit_ent);
        break;
        }
      case lit_real:{
        jj_consume_token(lit_real);
        break;
        }
      case t_true:
      case t_false:{
        booleano();
        break;
        }
      case cadena:{
        jj_consume_token(cadena);
        break;
        }
      case iden:{
        jj_consume_token(iden);
        break;
        }
      case nulo:{
        jj_consume_token(nulo);
        break;
        }
      case 39:{
        e8();
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("e7");
    }
}

  final public void booleano() throws ParseException {
    trace_call("booleano");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case t_true:{
        jj_consume_token(t_true);
        break;
        }
      case t_false:{
        jj_consume_token(t_false);
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("booleano");
    }
}

  final public void e8() throws ParseException {
    trace_call("e8");
    try {

      jj_consume_token(39);
      e0();
      jj_consume_token(40);
    } finally {
      trace_return("e8");
    }
}

  final public void op1() throws ParseException {
    trace_call("op1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 50:{
        jj_consume_token(50);
        break;
        }
      case 51:{
        jj_consume_token(51);
        break;
        }
      case 52:{
        jj_consume_token(52);
        break;
        }
      case 53:{
        jj_consume_token(53);
        break;
        }
      case 54:{
        jj_consume_token(54);
        break;
        }
      case 55:{
        jj_consume_token(55);
        break;
        }
      default:
        jj_la1[26] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op1");
    }
}

  final public void op4() throws ParseException {
    trace_call("op4");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 56:{
        jj_consume_token(56);
        break;
        }
      case 57:{
        jj_consume_token(57);
        break;
        }
      case 58:{
        jj_consume_token(58);
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op4");
    }
}

  final public void op5() throws ParseException {
    trace_call("op5");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 49:{
        jj_consume_token(49);
        break;
        }
      case not:{
        jj_consume_token(not);
        break;
        }
      default:
        jj_la1[28] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op5");
    }
}

  final public void op6() throws ParseException {
    trace_call("op6");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 43:{
        jj_consume_token(43);
        expresion();
        jj_consume_token(44);
        break;
        }
      case 59:{
        jj_consume_token(59);
        jj_consume_token(iden);
        break;
        }
      case 45:{
        jj_consume_token(45);
        break;
        }
      default:
        jj_la1[29] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op6");
    }
}

  /** Generated Token Manager. */
  public AnalizadorSintacticoTinyTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[30];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0xa0888e00,0x0,0xa0888e00,0x80808e00,0x0,0x0,0x0,0x80808e00,0x80808e00,0x8e00,0x0,0x5f500000,0x0,0x5f500000,0x200000,0x80074000,0x0,0x0,0x0,0x0,0x3000,0x0,0x80074000,0x0,0x80070000,0x60000,0x0,0x0,0x4000,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x2000,0x40,0x2000,0x2000,0x200,0x400,0x800,0x2000,0x0,0x0,0x200,0x4008,0x40,0x4008,0x0,0x20087,0x200,0x8000,0xfc0000,0x30000,0x0,0x7000000,0x20087,0x8002800,0x87,0x0,0xfc0000,0x7000000,0x20000,0x8002800,};
	}

  {
      enable_tracing();
  }
  /** Constructor with InputStream. */
  public AnalizadorSintacticoTiny(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public AnalizadorSintacticoTiny(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public AnalizadorSintacticoTiny(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public AnalizadorSintacticoTiny(AnalizadorSintacticoTinyTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(AnalizadorSintacticoTinyTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	   trace_token(token, " (in getNextToken)");
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[60];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 30; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 60; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  private int trace_indent = 0;
/** Enable tracing. */
  final public void enable_tracing() {
	 trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
	 trace_enabled = false;
  }

  protected void trace_call(String s) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Call:	" + s);
	 }
	 trace_indent = trace_indent + 2;
  }

  protected void trace_return(String s) {
	 trace_indent = trace_indent - 2;
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Return: " + s);
	 }
  }

  protected void trace_token(Token t, String where) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Consumed token: <" + tokenImage[t.kind]);
	   if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t.image) + "\"");
	   }
	   System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
	 }
  }

  protected void trace_scan(Token t1, int t2) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Visited token: <" + tokenImage[t1.kind]);
	   if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t1.image) + "\"");
	   }
	   System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
	 }
  }

}
