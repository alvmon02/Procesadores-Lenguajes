package prueba;

import java.util.HashSet;
import java.util.Set;

import asint.SintaxisAbstractaTiny.*;
import tipadoPost.TipadoV2;
import asint.ProcesamientoDef;

public class Prueba {

    public static int cont = 10;

    private static class Compatibilizador {

        private static Set<ParTipos> yaCompatibles;

        private static class ParTipos {

            Tipo t0, t1;

            protected ParTipos(Tipo t0, Tipo t1) {
                this.t0 = t0;
                this.t1 = t1;
            }
        }

        protected static boolean compatibles(Tipo t0, Tipo t1) {
            yaCompatibles = new HashSet<>();
            yaCompatibles.add(new ParTipos(t0, t1));
            return unificables(t0, t1);
        }

        private static boolean unificables(Tipo t0, Tipo t1) {
            cont -= 1;
            if (cont == 0) {
                System.out.println("Pasando por aqui, " +  cont);
                return false;
            }
            Tipo t0p = TipadoV2.refenciar(t0), t1p = TipadoV2.refenciar(t1);

            if ((ProcesamientoDef.claseDe(t0p, T_Int.class) && ProcesamientoDef.claseDe(t1p, T_Int.class)) ||
                    (ProcesamientoDef.claseDe(t0p, T_Real.class) && (ProcesamientoDef.claseDe(t1p, T_Int.class)
                            || ProcesamientoDef.claseDe(t1p, T_Real.class)))
                    ||
                    (ProcesamientoDef.claseDe(t0p, T_Bool.class) && ProcesamientoDef.claseDe(t1p, T_Bool.class)) ||
                    (ProcesamientoDef.claseDe(t0p, T_String.class) && ProcesamientoDef.claseDe(t1p, T_String.class)))
                return true;
            else if (ProcesamientoDef.claseDe(t0p, T_Array.class) && ProcesamientoDef.claseDe(t1p, T_Array.class))
                return Integer.parseInt(t0p.ent()) == Integer.parseInt(t1p.ent())
                        && son_unificables(t0p.tipo(), t1p.tipo());
            else if (ProcesamientoDef.claseDe(t0p, T_Struct.class) && ProcesamientoDef.claseDe(t1p, T_Struct.class))
                return n_campos(t0p.camposS()) == n_campos(t1p.camposS())
                        && son_campos_unificables(t0p.camposS(), t1p.camposS());
            else if (ProcesamientoDef.claseDe(t0p, T_Puntero.class) && ProcesamientoDef.claseDe(t1p, T_Null.class))
                return true;
            else if (ProcesamientoDef.claseDe(t0p, T_Puntero.class) && ProcesamientoDef.claseDe(t1p, T_Puntero.class))
                return son_unificables(t0p.tipo(), t1p.tipo());
            else
                return false;
        }

        private static boolean son_unificables(Tipo t0, Tipo t1) {
            if (yaCompatibles.contains(new ParTipos(t0, t1))) {
                return true;
            }
            yaCompatibles.add(new ParTipos(t0, t1));
            return unificables(t0, t1);
        }

        private static int n_campos(CamposS camposs) {
            if (ProcesamientoDef.claseDe(camposs, Mas_Cmp_S.class))
                return 1 + n_campos(camposs.camposS());
            else
                return 1;
        }

        private static boolean son_campos_unificables(CamposS camposs0, CamposS camposs1) {
            boolean sonRestoUnificables = true;
            if (ProcesamientoDef.claseDe(camposs0, Mas_Cmp_S.class))
                sonRestoUnificables = son_campos_unificables(camposs0.camposS(), camposs1.camposS());
            return sonRestoUnificables && son_campos_unificables(camposs0.campoS(), camposs1.campoS());
        }

        private static boolean son_campos_unificables(CampoS campos0, CampoS campos1) {
            return son_unificables(TipadoV2.refenciar(campos0.tipo()), TipadoV2.refenciar(campos1.tipo()));
        }
    }

    public static void main(String[] args) {

        Tipo t0 = new T_Struct(new Mas_Cmp_S(new Un_Cmp_S(new CampoS(new T_Real(), "Hola0")), new CampoS(new T_Null(), "Hola0")));
        t0.camposS().campoS().ponTipo(new T_Puntero(t0));
        Tipo t1 = new T_Struct(new Mas_Cmp_S(new Un_Cmp_S(new CampoS(new T_Int(), "Hola1")), new CampoS(new T_Puntero(new T_Int()), null)));
        t1.camposS().campoS().ponTipo(new T_Puntero(t1));
        System.out.println(Compatibilizador.compatibles(t0, t1));
    }
}
