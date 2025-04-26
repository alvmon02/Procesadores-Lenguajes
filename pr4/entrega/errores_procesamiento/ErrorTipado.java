package errores_procesamiento;

public class ErrorTipado extends ErrorProcesamiento {
    TipoErrorTipado tipo;
    String id;

    private enum TipoErrorTipado {
        ERROR_TIPOS_INCOMPATIBLES_ASIG("tipos incompatibles en asignacion"),
        ERROR_TIPOS_INCOMPATIBLES_OP("tipos incompatibles en operacion"),
        ERROR_TIPO_INCOMPATIBLE_OP("tipo incompatible en operacion"),
        ERROR_CAMPO_INEXISTENTE("campo inexistente"),
        ERROR_TIPOS_IMCOMPATIBLES_INDX("tipos incompatibles en indexacion"),
        ERROR_ACCESO_NO_REG("se trata de acceder a un campo de un objeto que no es un registro"),
        ERROR_TIPO_INCOMPATIBLE_PFORMAL("tipo incompatible con tipo de parametro formal"),
        ERROR_TIPO_REAL("el tipo debe ser real"),
        ERROR_ESPERABA_DESIGNADOR("se esperaba un designador"),
        ERROR_NO_VARIABLE("no es variable ni parametro"),
        ERROR_TIPO_PUNTERO("esperado tipo puntero"),
        ERROR_DESIGNADOR_ESPERADO("designador esperado"),
        ERROR_DESIGNADOR_IZQ("la parte izquierda debe ser un designador"),
        ERROR_NO_LEGIBLE("valor no legible"),
        ERROR_NO_IMPRIMIBLE("valor no imprimible"),
        ERROR_BOOLEANA("esperada expresion booleana"),
        ERROR_N_PARAM_DIST("el numero de parametros reales no coincide con el numero de parametros formales"),
        ERROR_NO_SUBPROGRAMA("no es un subprograma"),
        ;

        private final String descripcion;

        TipoErrorTipado(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    public static ErrorTipado errorTiposIncompatiblesAsig(int fila, int columna, String tipo1, String tipo2) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPOS_INCOMPATIBLES_ASIG, tipo1 + " " + tipo2);
    }

    public static ErrorTipado errorTiposIncompatiblesOp(int fila, int columna, String tipo1, String tipo2) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPOS_INCOMPATIBLES_OP, tipo1 + " " + tipo2);
    }

    public static ErrorTipado errorTipoIncompatibleOp(int fila, int columna, String tipo) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPOS_INCOMPATIBLES_OP, tipo);
    }

    public static ErrorTipado errorCampoInexistente(int fila, int columna, String id) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_CAMPO_INEXISTENTE, id);
    }

    public static ErrorTipado errorTiposIncompatiblesIndx(int fila, int columna, String id) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPOS_IMCOMPATIBLES_INDX, id);
    }

    public static ErrorTipado errorAccesoNoReg(int fila, int columna, String id) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_ACCESO_NO_REG, id);
    }

    public static ErrorTipado errorTipoIncompatiblePFormal(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPO_INCOMPATIBLE_PFORMAL);
    }

    public static ErrorTipado errorTipoReal(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPO_REAL);
    }

    public static ErrorTipado errorEsperabaDesignador(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_ESPERABA_DESIGNADOR);
    }

    public static ErrorTipado errorNoVariable(int fila, int columna, String id) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_NO_VARIABLE, id);
    }

    public static ErrorTipado errorTipoPuntero(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_TIPO_PUNTERO);
    }

    public static ErrorTipado errorDesignadorEsperado(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_DESIGNADOR_ESPERADO);
    }

    public static ErrorTipado errorDesignadorIzq(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_DESIGNADOR_IZQ);
    }

    public static ErrorTipado errorNoLegible(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_NO_LEGIBLE);
    }

    public static ErrorTipado errorNoImprimible(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_NO_IMPRIMIBLE);
    }

    public static ErrorTipado errorBooleana(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_BOOLEANA);
    }

    public static ErrorTipado errorNParamDist(int fila, int columna) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_N_PARAM_DIST);
    }

    public static ErrorTipado errorNoSubprograma(int fila, int columna, String id) {
        return new ErrorTipado(fila, columna, TipoErrorTipado.ERROR_NO_SUBPROGRAMA, id);
    }

    private ErrorTipado(int fila, int columna, TipoErrorTipado tipo, String id) {
        super(fila, columna);
        this.tipo = tipo;
        this.id = id;
    }

    private ErrorTipado(int fila, int columna, TipoErrorTipado tipo) {
        super(fila, columna);
        this.tipo = tipo;
    }

    public String toStringJuez() {
        return "Errores_tipado fila:" + fila + " col:" + columna;
    }

    public String toStringHumano() {
        switch (tipo) {
            case ERROR_NO_SUBPROGRAMA:
            case ERROR_NO_VARIABLE:
                return fila + "," + columna + ":" + id + " " + tipo;
            case ERROR_CAMPO_INEXISTENTE:
            case ERROR_TIPOS_INCOMPATIBLES_ASIG:
            case ERROR_TIPOS_INCOMPATIBLES_OP:
            case ERROR_ACCESO_NO_REG:
            case ERROR_TIPOS_IMCOMPATIBLES_INDX:
            case ERROR_TIPO_INCOMPATIBLE_OP:
                return fila + "," + columna + ":" + tipo + ":" + id;
            default:
                return fila + "," + columna + ":" + tipo;
        }
    }

}
