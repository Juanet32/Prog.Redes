package tateti;

public class Celda {

    private String estado; // VACIA, X, O

    public Celda() {
        estado = "VACIA";
    }

    public void vaciar() {
        estado = "VACIA";
    }

    public void marcar(String simbolo) {
        estado = simbolo;
    }

    public boolean esVacia() {
        return estado.equals("VACIA");
    }

    public String getEstado() {
        return estado;
    }
}
