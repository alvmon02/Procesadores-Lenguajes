package maquinap;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MaquinaP {
    public static class EAccesoIlegitimo extends RuntimeException {
    }

    public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
        public EAccesoAMemoriaNoInicializada(int pc, int dir) {
            super("pinst:" + pc + " dir:" + dir);
        }
    }

    public static class EAccesoFueraDeRango extends RuntimeException {
    }

    private GestorMemoriaDinamica gestorMemoriaDinamica;
    private GestorPilaActivaciones gestorPilaActivaciones;

    private abstract class Valor {

        public abstract void ejecuta(InstruccionBin iBin, Valor v);

        public int valorInt() {
            throw new EAccesoIlegitimo();
        }

        public boolean valorBool() {
            throw new EAccesoIlegitimo();
        }

        public String valorStr() {
            throw new EAccesoIlegitimo();
        }

        public float valorReal() {
            throw new EAccesoIlegitimo();
        }
    }

    private class ValorInt extends Valor {
        private int valor;

        public ValorInt(int valor) {
            this.valor = valor;
        }

        public int valorInt() {
            return valor;
        }

        public String toString() {
            return String.valueOf(valor);
        }

        @Override
        public void ejecuta(InstruccionBin iBin, Valor v) {
            iBin.ejecuta(this, (ValorInt) v);
        }
    }

    private class ValorBool extends Valor {
        private boolean valor;

        public ValorBool(boolean valor) {
            this.valor = valor;
        }

        public boolean valorBool() {
            return valor;
        }

        public String toString() {
            return String.valueOf(valor);
        }

        @Override
        public void ejecuta(InstruccionBin iBin, Valor v) {
            iBin.ejecuta(this, (ValorBool) v);
        }
    }

    // ############### NUEVAS CLASES VALOR ###############

    private class ValorStr extends Valor {
        private String valor;

        public ValorStr(String valor) {
            this.valor = valor;
        }

        public String valorStr() {
            return valor;
        }

        public String toString() {
            return valor;
        }

        @Override
        public void ejecuta(InstruccionBin iBin, Valor v) {
            iBin.ejecuta(this, (ValorStr) v);
        }
    }

    private class ValorReal extends Valor {
        private float valor;

        public ValorReal(float valor) {
            this.valor = valor;
        }

        public float valorReal() {
            return valor;
        }

        public String toString() {
            return String.valueOf(valor);
        }

        @Override
        public void ejecuta(InstruccionBin iBin, Valor v) {
            iBin.ejecuta(this, (ValorReal) v);
        }
    }

    private List<Instruccion> codigoP;
    private Stack<Valor> pilaEvaluacion;
    private Valor[] datos;
    private int pc;

    public interface Instruccion {
        void ejecuta();
    }

    private abstract class InstruccionBin implements Instruccion {
        public void ejecuta(ValorInt opnd1, ValorInt opnd2) {

        }

        public void ejecuta(ValorBool opnd1, ValorBool opnd2) {

        }

        public void ejecuta(ValorReal opnd1, ValorReal opnd2) {

        }

        public void ejecuta(ValorStr opnd1, ValorStr opnd2) {

        }
    }

    private class ISumaEj extends InstruccionBin {

        @Override
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            opnd1.ejecuta(this, opnd2);
            pc++;
        }

        @Override
        public void ejecuta(ValorInt opnd1, ValorInt opnd2) {
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt() + opnd2.valorInt()));
        }

        @Override
        public void ejecuta(ValorReal opnd1, ValorReal opnd2) {
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal() + opnd2.valorReal()));
        }
    }

    private ISuma ISUMA;

    private class ISuma implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorReal(val1 + val2));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() + opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "suma";
        };
    }

    private IMul IMUL;

    private class IMul implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorReal(val1 * val2));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() * opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "mul";
        };
    }

    private IAnd IAND;

    private class IAnd implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorBool(opnd1.valorBool() && opnd2.valorBool()));
            pc++;
        }

        public String toString() {
            return "and";
        };
    }

    // ############### NUEVAS CLASES INSTRUCCION OPERACIONES ###############

    private e_negado E_NEGADO;

    private class e_negado implements Instruccion {
        public void ejecuta() {
            Valor opnd = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorBool(!opnd.valorBool()));
            pc++;
        }

        public String toString() {
            return "negado";
        };
    }

    private e_negativo E_NEGATIVO;

    private class e_negativo implements Instruccion {
        public void ejecuta() {
            Valor opnd = pilaEvaluacion.pop();
            if (opnd instanceof ValorReal) {
                pilaEvaluacion.push(new ValorReal(-opnd.valorReal()));
            } else {
                pilaEvaluacion.push(new ValorInt(-opnd.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "negativo";
        };
    }

    private e_or E_OR;

    private class e_or implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorBool(opnd1.valorBool() || opnd2.valorBool()));
            pc++;
        }

        public String toString() {
            return "or";
        };
    }

    private e_porcentaje E_PORCENTAJE;

    private class e_porcentaje implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt() % opnd2.valorInt()));
            pc++;
        }

        public String toString() {
            return "porcentaje";
        };
    }

    private e_div E_DIV;

    private class e_div implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd2.valorInt() == 0)
                throw new EAccesoIlegitimo();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorReal(val1 / val2));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() / opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "div";
        };
    }

    private e_resta E_RESTA;

    private class e_resta implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorReal(val1 - val2));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() - opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "resta";
        };
    }

    private e_geq E_GEQ;

    private class e_geq implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorBool(val1 >= val2));
            } else {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() >= opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "geq";
        };
    }

    private e_leq E_LEQ;

    private class e_leq implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorBool(val1 <= val2));
            } else {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() <= opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "leq";
        };
    }

    private e_gt E_GT;

    private class e_gt implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorBool(val1 > val2));
            } else {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "gt";
        };
    }

    private e_lt E_LT;

    private class e_lt implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorBool(val1 < val2));
            } else {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "lt";
        };
    }

    private e_dist E_DIST;

    private class e_dist implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorBool(val1 != val2));
            } else {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() != opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "dist";
        };
    }

    private e_comp E_COMP;

    private class e_comp implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                float val1 = (opnd1 instanceof ValorReal) ? opnd1.valorReal() : opnd1.valorInt();
                float val2 = (opnd2 instanceof ValorReal) ? opnd2.valorReal() : opnd2.valorInt();
                pilaEvaluacion.push(new ValorBool(val1 == val2));
            } else {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() == opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "comp";
        };
    }

    private class int2real implements Instruccion {
        public void ejecuta() {
            Valor opnd = pilaEvaluacion.pop();
            pilaEvaluacion.push(new ValorReal(opnd.valorInt()));
            pc++;
        }

        public String toString() {
            return "int2real";
        };
    }

    private class IApilaInt implements Instruccion {
        private int valor;

        public IApilaInt(int valor) {
            this.valor = valor;
        }

        public void ejecuta() {
            pilaEvaluacion.push(new ValorInt(valor));
            pc++;
        }

        public String toString() {
            return "apila-int(" + valor + ")";
        };
    }

    private class IApilaBool implements Instruccion {
        private boolean valor;

        public IApilaBool(boolean valor) {
            this.valor = valor;
        }

        public void ejecuta() {
            pilaEvaluacion.push(new ValorBool(valor));
            pc++;
        }

        public String toString() {
            return "apila-bool(" + valor + ")";
        };
    }

    // ############### NUEVAS CLASES INSTRUCCION ###############
    private class IApilaStr implements Instruccion {
        private String valor;

        public IApilaStr(String valor) {
            this.valor = valor;
        }

        public void ejecuta() {
            pilaEvaluacion.push(new ValorStr(valor));
            pc++;
        }

        public String toString() {
            return "apila-str(" + valor + ")";
        };
    }

    private class IApilaReal implements Instruccion {
        private float valor;

        public IApilaReal(float valor) {
            this.valor = valor;
        }

        public void ejecuta() {
            pilaEvaluacion.push(new ValorReal(valor));
            pc++;
        }

        public String toString() {
            return "apila-real(" + valor + ")";
        };
    }

    private class IIrA implements Instruccion {
        private int dir;

        public IIrA(int dir) {
            this.dir = dir;
        }

        public void ejecuta() {
            pc = dir;
        }

        public String toString() {
            return "ir-a(" + dir + ")";
        };
    }

    private class IIrF implements Instruccion {
        private int dir;

        public IIrF(int dir) {
            this.dir = dir;
        }

        public void ejecuta() {
            if (!pilaEvaluacion.pop().valorBool()) {
                pc = dir;
            } else {
                pc++;
            }
        }

        public String toString() {
            return "ir-f(" + dir + ")";
        };
    }

    private class ICopia implements Instruccion {
        private int tam;

        public ICopia(int tam) {
            this.tam = tam;
        }

        public void ejecuta() {
            int dirOrigen = pilaEvaluacion.pop().valorInt();
            int dirDestino = pilaEvaluacion.pop().valorInt();
            if ((dirOrigen + (tam - 1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            if ((dirDestino + (tam - 1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            for (int i = 0; i < tam; i++)
                datos[dirDestino + i] = datos[dirOrigen + i];
            pc++;
        }

        public String toString() {
            return "copia(" + tam + ")";
        };
    }

    private IApilaind IAPILAIND;

    private class IApilaind implements Instruccion {
        public void ejecuta() {
            int dir = pilaEvaluacion.pop().valorInt();
            if (dir >= datos.length)
                throw new EAccesoFueraDeRango();
            if (datos[dir] == null)
                throw new EAccesoAMemoriaNoInicializada(pc, dir);
            pilaEvaluacion.push(datos[dir]);
            pc++;
        }

        public String toString() {
            return "apila-ind";
        };
    }

    private IDesapilaind IDESAPILAIND;

    private class IDesapilaind implements Instruccion {
        public void ejecuta() {
            Valor valor = pilaEvaluacion.pop();
            int dir = pilaEvaluacion.pop().valorInt();
            if (dir >= datos.length)
                throw new EAccesoFueraDeRango();
            datos[dir] = valor;
            pc++;
        }

        public String toString() {
            return "desapila-ind";
        };
    }

    private class IAlloc implements Instruccion {
        private int tam;

        public IAlloc(int tam) {
            this.tam = tam;
        }

        public void ejecuta() {
            int inicio = gestorMemoriaDinamica.alloc(tam);
            pilaEvaluacion.push(new ValorInt(inicio));
            pc++;
        }

        public String toString() {
            return "alloc(" + tam + ")";
        };
    }

    private class IDealloc implements Instruccion {
        private int tam;

        public IDealloc(int tam) {
            this.tam = tam;
        }

        public void ejecuta() {
            int inicio = pilaEvaluacion.pop().valorInt();
            gestorMemoriaDinamica.free(inicio, tam);
            pc++;
        }

        public String toString() {
            return "dealloc(" + tam + ")";
        };
    }

    private class IActiva implements Instruccion {
        private int nivel;
        private int tamdatos;
        private int dirretorno;

        public IActiva(int nivel, int tamdatos, int dirretorno) {
            this.nivel = nivel;
            this.tamdatos = tamdatos;
            this.dirretorno = dirretorno;
        }

        public void ejecuta() {
            int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
            datos[base] = new ValorInt(dirretorno);
            datos[base + 1] = new ValorInt(gestorPilaActivaciones.display(nivel));
            pilaEvaluacion.push(new ValorInt(base + 2));
            pc++;
        }

        public String toString() {
            return "activa(" + nivel + "," + tamdatos + "," + dirretorno + ")";
        }
    }

    private class IDesactiva implements Instruccion {
        private int nivel;
        private int tamdatos;

        public IDesactiva(int nivel, int tamdatos) {
            this.nivel = nivel;
            this.tamdatos = tamdatos;
        }

        public void ejecuta() {
            int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
            gestorPilaActivaciones.fijaDisplay(nivel, datos[base + 1].valorInt());
            pilaEvaluacion.push(datos[base]);
            pc++;
        }

        public String toString() {
            return "desactiva(" + nivel + "," + tamdatos + ")";
        }

    }

    private class IDesapilad implements Instruccion {
        private int nivel;

        public IDesapilad(int nivel) {
            this.nivel = nivel;
        }

        public void ejecuta() {
            gestorPilaActivaciones.fijaDisplay(nivel, pilaEvaluacion.pop().valorInt());
            pc++;
        }

        public String toString() {
            return "desapilad(" + nivel + ")";
        }
    }

    private IDup IDUP;

    private class IDup implements Instruccion {
        public void ejecuta() {
            pilaEvaluacion.push(pilaEvaluacion.peek());
            pc++;
        }

        public String toString() {
            return "dup";
        }
    }

    private Instruccion ISTOP;

    private class IStop implements Instruccion {
        public void ejecuta() {
            pc = codigoP.size();
        }

        public String toString() {
            return "stop";
        }
    }

    private class IApilad implements Instruccion {
        private int nivel;

        public IApilad(int nivel) {
            this.nivel = nivel;
        }

        public void ejecuta() {
            pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
            pc++;
        }

        public String toString() {
            return "apilad(" + nivel + ")";
        }

    }

    private Instruccion IIRIND;

    private class IIrind implements Instruccion {
        public void ejecuta() {
            pc = pilaEvaluacion.pop().valorInt();
        }

        public String toString() {
            return "ir-ind";
        }
    }

    public Instruccion suma() {
        return ISUMA;
    }

    public Instruccion resta() {
        return E_RESTA;
    }

    public Instruccion mul() {
        return IMUL;
    }

    public Instruccion and() {
        return IAND;
    }

    public Instruccion or() {
        return E_OR;
    }

    public Instruccion div() {
        return E_DIV;
    }

    public Instruccion porcentaje() {
        return E_PORCENTAJE;
    }

    public Instruccion geq() {
        return E_GEQ;
    }

    public Instruccion leq() {
        return E_LEQ;
    }

    public Instruccion gt() {
        return E_GT;
    }

    public Instruccion lt() {
        return E_LT;
    }

    public Instruccion dist() {
        return E_DIST;
    }

    public Instruccion comp() {
        return E_COMP;
    }

    public Instruccion negativo() {
        return E_NEGATIVO;
    }

    public Instruccion negado() {
        return E_NEGADO;
    }

    public Instruccion apila_int(int val) {
        return new IApilaInt(val);
    }

    public Instruccion apila_bool(boolean val) {
        return new IApilaBool(val);
    }

    public Instruccion apila_str(String val) {
        return new IApilaStr(val);
    } // NUEVO

    public Instruccion apila_real(float val) {
        return new IApilaReal(val);
    } // NUEVO

    public Instruccion int2real() {
        return new int2real();
    }

    public Instruccion apilad(int nivel) {
        return new IApilad(nivel);
    }

    public Instruccion apila_ind() {
        return IAPILAIND;
    }

    public Instruccion desapila_ind() {
        return IDESAPILAIND;
    }

    public Instruccion copia(int tam) {
        return new ICopia(tam);
    }

    public Instruccion ir_a(int dir) {
        return new IIrA(dir);
    }

    public Instruccion ir_f(int dir) {
        return new IIrF(dir);
    }

    public Instruccion ir_ind() {
        return IIRIND;
    }

    public Instruccion alloc(int tam) {
        return new IAlloc(tam);
    }

    public Instruccion dealloc(int tam) {
        return new IDealloc(tam);
    }

    public Instruccion activa(int nivel, int tam, int dirretorno) {
        return new IActiva(nivel, tam, dirretorno);
    }

    public Instruccion desactiva(int nivel, int tam) {
        return new IDesactiva(nivel, tam);
    }

    public Instruccion desapilad(int nivel) {
        return new IDesapilad(nivel);
    }

    public Instruccion dup() {
        return IDUP;
    }

    public Instruccion stop() {
        return ISTOP;
    }

    public void emit(Instruccion i) {
        codigoP.add(i);
    }

    private int tamdatos;
    private int tamheap;
    private int ndisplays;

    public MaquinaP(int tamdatos, int tampila, int tamheap, int ndisplays) {
        this.tamdatos = tamdatos;
        this.tamheap = tamheap;
        this.ndisplays = ndisplays;
        this.codigoP = new ArrayList<>();
        pilaEvaluacion = new Stack<>();
        datos = new Valor[tamdatos + tampila + tamheap];
        this.pc = 0;
        ISUMA = new ISuma();
        IAND = new IAnd();
        IMUL = new IMul();
        IAPILAIND = new IApilaind();
        IDESAPILAIND = new IDesapilaind();
        IIRIND = new IIrind();
        IDUP = new IDup();
        ISTOP = new IStop();
        gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos, (tamdatos + tampila) - 1, ndisplays);
        gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos + tampila, (tamdatos + tampila + tamheap) - 1);
    }

    public void ejecuta() {
        while (pc != codigoP.size()) {
            codigoP.get(pc).ejecuta();
        }
    }

    public void muestraCodigo() {
        System.out.println("CodigoP");
        for (int i = 0; i < codigoP.size(); i++) {
            System.out.println(" " + i + ":" + codigoP.get(i));
        }
    }

    public void muestraEstado() {
        System.out.println("Tam datos:" + tamdatos);
        System.out.println("Tam heap:" + tamheap);
        System.out.println("PP:" + gestorPilaActivaciones.pp());
        System.out.print("Displays:");
        for (int i = 1; i <= ndisplays; i++)
            System.out.print(i + ":" + gestorPilaActivaciones.display(i) + " ");
        System.out.println();
        System.out.println("Pila de evaluacion");
        for (int i = 0; i < pilaEvaluacion.size(); i++) {
            System.out.println(" " + i + ":" + pilaEvaluacion.get(i));
        }
        System.out.println("Datos");
        for (int i = 0; i < datos.length; i++) {
            System.out.println(" " + i + ":" + datos[i]);
        }
        System.out.println("PC:" + pc);
    }

    public static void main(String[] args) {
        MaquinaP m = new MaquinaP(5, 10, 10, 2);

        /*
         * int x;
         * proc store(int v) {
         * x = v
         * }
         * &&
         * call store(5)
         */

        m.emit(m.activa(1, 1, 8));
        m.emit(m.dup());
        m.emit(m.apila_int(0));
        m.emit(m.suma());
        m.emit(m.apila_int(5));
        m.emit(m.desapila_ind());
        m.emit(m.desapilad(1));
        m.emit(m.ir_a(9));
        m.emit(m.stop());
        m.emit(m.apila_int(0));
        m.emit(m.apilad(1));
        m.emit(m.apila_int(0));
        m.emit(m.suma());
        m.emit(m.copia(1));
        m.emit(m.desactiva(1, 1));
        m.emit(m.ir_ind());
        m.muestraCodigo();
        m.ejecuta();
        m.muestraEstado();
    }
}
