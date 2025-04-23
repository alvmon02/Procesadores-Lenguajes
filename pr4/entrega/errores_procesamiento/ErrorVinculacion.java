package errores_procesamiento;

public class ErrorVinculacion extends ErrorProcesamiento {
    TipoErrorVinculacion tipo;
    String id;

    private enum TipoErrorVinculacion {
        ERROR_NO_DECLARADO("identificador no declarado"),
        ERROR_DUPLICADO("declaracion duplicada"),;

        private final String descripcion;

        TipoErrorVinculacion(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    public static ErrorVinculacion errorNoDeclarado(int fila, int columna, String id) {
        return new ErrorVinculacion(fila, columna, TipoErrorVinculacion.ERROR_NO_DECLARADO, id);
    }

    public static ErrorVinculacion errorDuplicado(int fila, int columna, String id) {
        return new ErrorVinculacion(fila, columna, TipoErrorVinculacion.ERROR_DUPLICADO, id);
    }

    private ErrorVinculacion(int fila, int columna, TipoErrorVinculacion tipo, String id) {
        super(fila, columna);
        this.tipo = tipo;
        this.id = id;
    }

    public String toStringJuez() {
        return "Errores_vinculacion fila:" + fila + " col:" + columna;
    }

    public String toStringHumano() {
        return fila + "," + columna + ":" + tipo + ":" + id;
    }
}
