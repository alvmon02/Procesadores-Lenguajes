package asint;

import java.util.HashMap;
import java.util.Map;



public class SintaxisAbstractaEval {
      
    private static void imprimeOpnd(Exp opnd, int np) {
        if(opnd.prioridad() < np) {System.out.print("(");};
        opnd.imprime();
        if(opnd.prioridad() < np) {System.out.print(")");};        
    }
    private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1) {
        imprimeOpnd(opnd0,np0);
        System.out.print(" "+op+" ");
        imprimeOpnd(opnd1,np1);
    }
    
    public static abstract class Nodo  {
       public Nodo() {
		   fila=col=-1;
       }   
	   private int fila;
	   private int col;
	   public Nodo ponFila(int fila) {
		    this.fila = fila;
            return this;			
	   }
	   public Nodo ponCol(int col) {
		    this.col = col;
            return this;			
	   }
	   public int leeFila() {
		  return fila; 
	   }
	   public int leeCol() {
		  return col; 
	   }
           public abstract void imprime();
    }
    

    public static abstract class Exp  extends  Nodo {
       public Exp() {
          super();
       }   
       public abstract int prioridad();
    }
   
    
    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;
        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }
    }
            
    public static class Suma extends ExpBin {
        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public void imprime() {
            imprimeExpBin(opnd0,"+",opnd1,0,1);
        }
        public int prioridad() {return 0;}
        
        public String toString() {
            return "suma("+opnd0+","+opnd1+")";
        } 
    }
    public static class Resta extends ExpBin {
        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public void imprime() {
            imprimeExpBin(opnd0,"-",opnd1,0,1);
        }
       public int prioridad() {return 0;}
       public String toString() {
            return "resta("+opnd0+","+opnd1+")";
        } 
    }
    public static class Mul extends ExpBin {
        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public void imprime() {
            imprimeExpBin(opnd0,"*",opnd1,0,1);
        }
        public int prioridad() {return 1;}
        public String toString() {
            return "mul("+opnd0+","+opnd1+")";
        } 
    }
    public static class Div extends ExpBin {
        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public void imprime() {
            imprimeExpBin(opnd0,"/",opnd1,0,1);
        }
        public int prioridad() {return 1;}
        public String toString() {
            return "div("+opnd0+","+opnd1+")";
        } 
    }
    
    public static class Lit_ent extends Exp {
        private String num;
        public Lit_ent(String num) {
            super();
            this.num = num;
        }
        public void imprime() {
            System.out.print(num);
        }
        public int prioridad() {return 2;}
        public String toString() {
            return "lit_ent("+num+"["+leeFila()+","+leeCol()+"])";
        } 
    }

    public static class Lit_real extends Exp {
        private String num;
        public Lit_real(String num) {
            super();
            this.num = num;
        }
        public void imprime() {
            System.out.print(num);
        }
        public int prioridad() {return 2;}
        public String toString() {
            return "lit_real("+num+"["+leeFila()+","+leeCol()+"])";
        } 
    }
	
	
    public static class Iden extends Exp {
        private String id;
        public Iden(String id) {
            super();
            this.id = id;
        }
        public void imprime() {
            System.out.print(id);
        }
        public int prioridad() {return 2;}
        public String toString() {
            return "iden("+id+"["+leeFila()+","+leeCol()+"])";
        } 
    }
    public static class Dec extends Nodo {
        private String id;
        private Exp exp;
        public Dec(String id, Exp exp) {
            this.id = id;
            this.exp = exp;
        }
        public void imprime() {
            System.out.println();
            System.out.print("  "+id+"=");
            exp.imprime();
        }
        public String toString() {
            return "dec("+id+"["+leeFila()+","+leeCol()+"],"+exp+")";
        } 
    }
    public static abstract class Decs extends Nodo {
       public Decs() {
       }
       public LDecs ldecs() {throw new UnsupportedOperationException();}

    }
    public static class Si_decs extends Decs {
       private LDecs decs; 
       public Si_decs(LDecs decs) {
          super();
          this.decs = decs;
       }   
        public void imprime() {
            System.out.print("donde");
            decs.imprime();
        }
        public String toString() {
            return "si_decs("+decs+")";
        } 
 
    }
    public static class No_decs extends Decs {
       public No_decs() {
          super();
       }   
       public String toString() {
            return "no_decs()";
        } 
        public void imprime() {}
    }

    public static abstract class LDecs extends Nodo {
       public LDecs() {
		   super();
       }
       public Dec dec() {throw new UnsupportedOperationException();}
       public LDecs ldecs() {throw new UnsupportedOperationException();}
    }
    	
    public static class Muchas_decs extends LDecs {
       private LDecs decs;
       private Dec dec;
       public Muchas_decs(LDecs decs, Dec dec) {
          super();
          this.dec = dec;
          this.decs = decs;
       }
       public void imprime() {
           decs.imprime();
           System.out.print(",");
           dec.imprime();
       }
       public String toString() {
            return "muchas_decs("+decs+","+dec+")";
        } 
    }

    public static class Una_dec extends LDecs {
       private Dec dec;
       public Una_dec(Dec dec) {
          super();
          this.dec = dec;
       }
       public void imprime() {
           dec.imprime();
       }
       public String toString() {
            return "una_dec("+dec+")";
        } 
    }

    public static class Prog extends Nodo {
	   private Exp exp;
	   private Decs decs;
       public Prog(Exp exp, Decs decs) {
		   super();
		   this.exp = exp;
		   this.decs = decs;
       }   
       public void imprime() {
           System.out.println("evalua");
           System.out.print("  ");
           exp.imprime();
           System.out.println();
           decs.imprime();
           System.out.println();
       }
       public String toString() {
            return "prog("+exp+","+decs+")";
        } 
    }

     // Constructoras    
    public Prog prog(Exp exp, Decs decs) {
        return new Prog(exp,decs);
    }
    public Exp suma(Exp opnd0, Exp opnd1) {
        return new Suma(opnd0,opnd1);
    }
    public Exp resta(Exp opnd0, Exp opnd1) {
        return new Resta(opnd0,opnd1);
    }
    public Exp mul(Exp opnd0, Exp opnd1) {
        return new Mul(opnd0,opnd1);
    }
    public Exp div(Exp opnd0, Exp opnd1) {
        return new Div(opnd0,opnd1);
    }
    public Exp lit_ent(String num) {
        return new Lit_ent(num);
    }
    public Exp lit_real(String num) {
        return new Lit_real(num);
    }
    public Exp iden(String num) {
        return new Iden(num);
    }
    public Dec dec(String id, Exp exp) {
        return new Dec(id,exp);
    }
    public Decs si_decs(LDecs decs) {
        return new Si_decs(decs);
    }
    public Decs no_decs() {
        return new No_decs();
    }
    public LDecs muchas_decs(LDecs decs, Dec dec) {
        return new Muchas_decs(decs,dec);
    }
    public LDecs una_dec(Dec dec) {
        return new Una_dec(dec);
    }
}
