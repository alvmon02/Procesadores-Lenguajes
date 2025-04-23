package errores_procesamiento;

public class ErrorPretipado extends ErrorProcesamiento {
    TipoErrorPretipado tipo;
    String id;

    private enum TipoErrorPretipado {
        ERROR_DIMENSION_NEGATIVA("la dimension no puede ser negativa"),
        ERROR_TIPO_NO_DECLARADO("no esta declarado como un tipo"),
        ERROR_CAMPO_DUPLICADO("campo duplicado"),;

        private final String descripcion;

        TipoErrorPretipado(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    public static ErrorPretipado errorDimensionNegativa(int fila, int columna) {
        return new ErrorPretipado(fila, columna, TipoErrorPretipado.ERROR_DIMENSION_NEGATIVA);
    }

    public static ErrorPretipado errorTipoNoDeclarado(int fila, int columna, String id) {
        return new ErrorPretipado(fila, columna, TipoErrorPretipado.ERROR_TIPO_NO_DECLARADO, id);
    }

    public static ErrorPretipado errorCampoDuplicado(int fila, int columna, String id) {
        return new ErrorPretipado(fila, columna, TipoErrorPretipado.ERROR_CAMPO_DUPLICADO, id);
    }

    private ErrorPretipado(int fila, int columna, TipoErrorPretipado tipo, String id) {
        super(fila, columna);
        this.tipo = tipo;
        this.id = id;
    }

    private ErrorPretipado(int fila, int columna, TipoErrorPretipado tipo) {
        super(fila, columna);
        this.tipo = tipo;
    }

    public String toStringJuez() {
        return "Errores_pretipado fila:" + fila + " col:" + columna;
    }

    public String toStringHumano() {
        switch (tipo) {
            case ERROR_DIMENSION_NEGATIVA:
                return fila + "," + columna + ":" + tipo;
            case ERROR_TIPO_NO_DECLARADO:
                return fila + "," + columna + ":" + id + " " + tipo;
            case ERROR_CAMPO_DUPLICADO:
                return fila + "," + columna + ":" + tipo + ":" + id;
        }
        return null;
    }

}
