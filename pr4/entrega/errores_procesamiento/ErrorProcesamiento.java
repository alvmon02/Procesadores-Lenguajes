package errores_procesamiento;

public abstract class ErrorProcesamiento implements Comparable<ErrorProcesamiento> {

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

    @Override
    public int compareTo(ErrorProcesamiento o) {
        int cmp = Integer.compare(fila, o.fila);
        if (cmp == 0) {
            return Integer.compare(columna, o.columna);
        }
        return cmp;
    }
}
