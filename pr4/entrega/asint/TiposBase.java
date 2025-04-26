package asint;

import java.util.ArrayList;
import java.util.List;

public class TiposBase {

    public static abstract class TipoNodo {

        @SuppressWarnings("rawtypes")
        protected static boolean claseDe(Object o, Class c) {
            return o.getClass() == c;
        }

        public TipoNodo setBase(TipoNodo tipo) {
            throw new UnsupportedOperationException("Base can only be set for ARRAY or PUNT");
        }

        public TipoNodo base() {
            throw new UnsupportedOperationException("Base can only be get from ARRAY or PUNT");
        }

        public int dim() {
            throw new UnsupportedOperationException("Base can only be get from ARRAY or PUNT");
        }

        public TipoNodo addBase(String string, TipoNodo base) {
            throw new UnsupportedOperationException("Base can only be added for STRUCT");
        }

        public TipoNodo busquedaCampo(String idCampo) {
            throw new UnsupportedOperationException("Campo can only be searched in STRUCT");
        }

        public boolean isInt() {
            return false;
        }

        public boolean isReal() {
            return false;
        }

        public boolean isBool() {
            return false;
        }

        public boolean isCadena() {
            return false;
        }

        public boolean isPunt() {
            return false;
        }

        public boolean isNull() {
            return false;
        }

        public boolean isArray() {
            return false;
        }

        public boolean isStruct() {
            return false;
        }

        public boolean isOk() {
            return false;
        }

        public boolean isError() {
            return false;
        }

        public abstract boolean compatible(TipoNodo tipo);
    }

    public static class tInt extends TipoNodo {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return claseDe(tipo, tInt.class);
        }

        @Override
        public boolean isInt() {
            return true;
        }

        @Override
        public String toString() {
            return "INT";
        }
    }

    public static class tReal extends TipoNodo {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return claseDe(tipo, tInt.class) || claseDe(tipo, tReal.class);
        }

        @Override
        public boolean isReal() {
            return true;
        }

        @Override
        public String toString() {
            return "REAL";
        }
    }

    public static class tBool extends TipoNodo {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return claseDe(tipo, tBool.class);
        }

        @Override
        public boolean isBool() {
            return true;
        }

        @Override
        public String toString() {
            return "BOOL";
        }
    }

    public static class tCadena extends TipoNodo {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return claseDe(tipo, tCadena.class);
        }

        @Override
        public boolean isCadena() {
            return true;
        }

        @Override
        public String toString() {
            return "STRING";
        }
    }

    public abstract static class TipoConTipoBase extends TipoNodo {
        protected TipoNodo tipoBase;

        @Override
        public TipoNodo setBase(TipoNodo tipo) {
            tipoBase = tipo;
            return this;
        }

        @Override
        public TipoNodo base() {
            return tipoBase;
        }
    }

    public static class tPunt extends TipoConTipoBase {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return claseDe(tipo, tNull.class) || (claseDe(tipo, tPunt.class) && tipoBase.compatible(tipo.base()));
        }

        @Override
        public boolean isPunt() {
            return true;
        }

        @Override
        public String toString() {
            return "PUNT";
        }
    }

    public static class tNull extends TipoNodo {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return false;
        }

        @Override
        public boolean isNull() {
            return true;
        }

        @Override
        public String toString() {
            return "NULL";
        }
    }

    public static class tArray extends TipoConTipoBase {

        int dim;

        public tArray(int dim) {
            this.dim = dim;
        }

        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return claseDe(tipo, tArray.class) && this.dim == tipo.dim() && tipoBase.compatible(tipo.base());
        }

        @Override
        public boolean isArray() {
            return true;
        }

        @Override
        public String toString() {
            return "ARRAY";
        }
    }

    public static class tStruct extends TipoNodo {

        private class campoStruct {
            String id;
            TipoNodo tipo;

            campoStruct(String id, TipoNodo tipo) {
                this.id = id;
                this.tipo = tipo;
            }
        }

        private List<campoStruct> campos = new ArrayList<>();

        @Override
        public TipoNodo addBase(String string, TipoNodo base) {
            this.campos.add(new campoStruct(string, base));
            return this;
        }

        @Override
        public TipoNodo busquedaCampo(String idCampo) {
            for (campoStruct campo : campos) {
                if (campo.id.equals(idCampo)) {
                    return campo.tipo;
                }
            }
            return new tError();
        }

        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            if (!claseDe(tipo, tStruct.class))
                return false;

            if (this.campos.size() != ((tStruct) tipo).campos.size())
                return false;

            for (int i = 0; i < this.campos.size(); i++) {
                if (!this.campos.get(i).tipo.compatible(((tStruct) tipo).campos.get(i).tipo)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean isStruct() {
            return true;
        }

        @Override
        public String toString() {
            return "STRUCT";
        }
    }

    public static class tOk extends TipoConTipoBase {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return false;
        }

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public String toString() {
            return "OK";
        }
    }

    public static class tError extends TipoConTipoBase {
        @Override
        public boolean compatible(TiposBase.TipoNodo tipo) {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public String toString() {
            return "ERROR";
        }
    }
}
