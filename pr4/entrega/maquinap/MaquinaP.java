package maquinap;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    }

    private List<Instruccion> codigoP;
    private Stack<Valor> pilaEvaluacion;
    private Valor[] datos;
    private int pc;

    public interface Instruccion {
        void ejecuta();
    }

    private ISuma ISUMA = new ISuma();

    private class ISuma implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() + opnd2.valorReal()));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() + opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "suma";
        };
    }

    private IMul IMUL = new IMul();

    private class IMul implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() * opnd2.valorReal()));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() * opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "mul";
        };
    }

    private IAnd IAND = new IAnd();

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

    private e_negado E_NEGADO = new e_negado();

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

    private e_negativo E_NEGATIVO = new e_negativo();

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

    private e_or E_OR = new e_or();

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

    private e_porcentaje E_PORCENTAJE = new e_porcentaje();

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

    private e_div E_DIV = new e_div();

    private class e_div implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd2.valorInt() == 0)
                throw new EAccesoIlegitimo();
            if (opnd1 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() / opnd2.valorReal()));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() / opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "div";
        };
    }

    private e_resta E_RESTA = new e_resta();

    private class e_resta implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorReal(opnd1.valorReal() - opnd2.valorReal()));
            } else {
                pilaEvaluacion.push(new ValorInt(opnd1.valorInt() - opnd2.valorInt()));
            }
            pc++;
        }

        public String toString() {
            return "resta";
        };
    }

    private e_geq E_GEQ = new e_geq();

    private class e_geq implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorReal() >= opnd2.valorReal()));
            } else if (opnd1 instanceof ValorInt) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() >= opnd2.valorInt()));
            } else if (opnd1 instanceof ValorStr) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorStr().compareTo(opnd2.valorStr()) >= 0));
            } else if (opnd1 instanceof ValorBool) {
                if (opnd1.valorBool() == opnd2.valorBool() || opnd1.valorBool() == true) {
                    pilaEvaluacion.push(new ValorBool(true));
                } else {
                    pilaEvaluacion.push(new ValorBool(false));
                }
            }
            pc++;
        }

        public String toString() {
            return "geq";
        };
    }

    private e_leq E_LEQ = new e_leq();

    private class e_leq implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorReal() <= opnd2.valorReal()));
            } else if (opnd1 instanceof ValorInt) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() <= opnd2.valorInt()));
            } else if (opnd1 instanceof ValorStr) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorStr().compareTo(opnd2.valorStr()) <= 0));
            } else if (opnd1 instanceof ValorBool) {
                if (!opnd1.valorBool() || opnd1.valorBool() == opnd2.valorBool()) {
                    pilaEvaluacion.push(new ValorBool(true));
                } else {
                    pilaEvaluacion.push(new ValorBool(false));
                }
            }
            pc++;
        }

        public String toString() {
            return "leq";
        };
    }

    private e_gt E_GT = new e_gt();

    private class e_gt implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorReal() > opnd2.valorReal()));
            } else if (opnd1 instanceof ValorInt) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));
            } else if (opnd1 instanceof ValorStr) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorStr().compareTo(opnd2.valorStr()) > 0));
            } else if (opnd1 instanceof ValorBool) {
                if (opnd1.valorBool() && !opnd2.valorBool()) {
                    pilaEvaluacion.push(new ValorBool(true));
                } else {
                    pilaEvaluacion.push(new ValorBool(false));
                }
            }
            pc++;
        }

        public String toString() {
            return "gt";
        };
    }

    private e_lt E_LT = new e_lt();

    private class e_lt implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorReal() < opnd2.valorReal()));
            } else if (opnd1 instanceof ValorInt) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));
            } else if (opnd1 instanceof ValorStr) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorStr().compareTo(opnd2.valorStr()) < 0));
            } else if (opnd1 instanceof ValorBool) {
                if (!opnd1.valorBool() && opnd2.valorBool()) {
                    pilaEvaluacion.push(new ValorBool(true));
                } else {
                    pilaEvaluacion.push(new ValorBool(false));
                }
            }
            pc++;
        }

        public String toString() {
            return "lt";
        };
    }

    private e_dist E_DIST = new e_dist();

    private class e_dist implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorReal() != opnd2.valorReal()));
            } else if (opnd1 instanceof ValorInt) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() != opnd2.valorInt()));
            } else if (opnd1 instanceof ValorStr) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorStr().compareTo(opnd2.valorStr()) != 0));
            } else if (opnd1 instanceof ValorBool) {
                if (opnd1.valorBool() != opnd2.valorBool()) {
                    pilaEvaluacion.push(new ValorBool(true));
                } else {
                    pilaEvaluacion.push(new ValorBool(false));
                }
            }
            pc++;
        }

        public String toString() {
            return "dist";
        };
    }

    private e_comp E_COMP = new e_comp();

    private class e_comp implements Instruccion {
        public void ejecuta() {
            Valor opnd2 = pilaEvaluacion.pop();
            Valor opnd1 = pilaEvaluacion.pop();
            if (opnd1 instanceof ValorReal || opnd2 instanceof ValorReal) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorReal() == opnd2.valorReal()));
            } else if (opnd1 instanceof ValorInt) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorInt() == opnd2.valorInt()));
            } else if (opnd1 instanceof ValorStr) {
                pilaEvaluacion.push(new ValorBool(opnd1.valorStr().compareTo(opnd2.valorStr()) == 0));
            } else if (opnd1 instanceof ValorBool) {
                if (opnd1.valorBool() == opnd2.valorBool()) {
                    pilaEvaluacion.push(new ValorBool(true));
                } else {
                    pilaEvaluacion.push(new ValorBool(false));
                }
            }
            pc++;
        }

        public String toString() {
            return "comp";
        };
    }

    private int2real INT2REAL = new int2real();

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

    private desapila DESAPILA = new desapila();

    private class desapila implements Instruccion {
        public void ejecuta() {
            if (!pilaEvaluacion.isEmpty())
                pilaEvaluacion.pop();
            pc++;
        }

        public String toString() {
            return "desapila";
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

    private IRead IREAD = new IRead();

    private class IRead implements Instruccion {
        public void ejecuta() {
            Valor valor = null;
            try {
                if (scanner.hasNextInt()) {
                    valor = new ValorInt(scanner.nextInt());
                    scanner.nextLine();
                } else if (scanner.hasNextFloat()) {
                    valor = new ValorReal(scanner.nextFloat());
                    scanner.nextLine();
                } else if (scanner.hasNextLine()) {
                    valor = new ValorStr(scanner.nextLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            pilaEvaluacion.push(valor);
            pc++;
        }

        public String toString() {
            return "read";
        };
    }

    private IWrite IWRITE = new IWrite();

    private class IWrite implements Instruccion {
        public void ejecuta() {
            Valor opnd = pilaEvaluacion.pop();
            System.out.print(opnd.toString());
            pc++;
        }

        public String toString() {
            return "write";
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

    private IApilaind IAPILAIND = new IApilaind();

    private class IApilaind implements Instruccion {
        public void ejecuta() {
            if (pilaEvaluacion.isEmpty())
                System.out.println("Pila vacia en pc " + pc);
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

    private IDesapilaind IDESAPILAIND = new IDesapilaind();

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
            gestorPilaActivaciones.fijaDisplay(nivel, base + 2);
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

    private IDup IDUP = new IDup();

    private class IDup implements Instruccion {
        public void ejecuta() {
            if (pilaEvaluacion.isEmpty()) {
                System.out.println("Pila vacia en pc " + pc);
            }
            pilaEvaluacion.push(pilaEvaluacion.peek());
            pc++;
        }

        public String toString() {
            return "dup";
        }
    }

    private Instruccion ISTOP = new IStop();

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

    private Instruccion IIRIND = new IIrind();

    private class IIrind implements Instruccion {
        public void ejecuta() {
            pc = pilaEvaluacion.pop().valorInt();
        }

        public String toString() {
            return "ir-ind";
        }
    }

    private Instruccion NL = new NL();

    private class NL implements Instruccion {
        public void ejecuta() {
            System.out.println();
            pc++;
        }

        public String toString() {
            return "NL";
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

    public Instruccion read() {
        return IREAD;
    }

    public Instruccion write() {
        return IWRITE;
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
        return INT2REAL;
    }

    public Instruccion desapila() {
        return DESAPILA;
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

    public Instruccion nl() {
        return NL;
    }

    public void emit(Instruccion i) {
        codigoP.add(i);
    }

    private int tamdatos;
    private int tamheap;
    private int ndisplays;
    private Scanner scanner;

    public MaquinaP(Reader buffer, int tamdatos, int tampila, int tamheap, int ndisplays) {
        this.tamdatos = tamdatos;
        this.tamheap = tamheap;
        this.ndisplays = ndisplays;
        this.scanner = new Scanner(buffer);
        this.codigoP = new ArrayList<>();
        pilaEvaluacion = new Stack<>();
        datos = new Valor[tamdatos + tampila + tamheap];
        this.pc = 0;
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

    // public static void main(String[] args) {
    // MaquinaP m = new MaquinaP(5, 10, 10, 2);

    // /*
    // * int x;
    // * proc store(int v) {
    // * x = v
    // * }
    // * &&
    // * call store(5)
    // */

    // m.emit(m.activa(1, 1, 8));
    // m.emit(m.dup());
    // m.emit(m.apila_int(0));
    // m.emit(m.suma());
    // m.emit(m.apila_int(5));
    // m.emit(m.desapila_ind());
    // m.emit(m.desapilad(1));
    // m.emit(m.ir_a(9));
    // m.emit(m.stop());
    // m.emit(m.apila_int(0));
    // m.emit(m.apilad(1));
    // m.emit(m.apila_int(0));
    // m.emit(m.suma());
    // m.emit(m.copia(1));
    // m.emit(m.desactiva(1, 1));
    // m.emit(m.ir_ind());
    // m.muestraCodigo();
    // m.ejecuta();
    // m.muestraEstado();
    // }
}
