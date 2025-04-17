package errores_procesamiento;

public abstract class ErrorProcesamiento {

    int fila;
    int columna;

    public ErrorProcesamiento(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public abstract String toStringJuez();

    public abstract String toStringHumano();

    public String toString() {
        return toStringHumano();
    }
}
