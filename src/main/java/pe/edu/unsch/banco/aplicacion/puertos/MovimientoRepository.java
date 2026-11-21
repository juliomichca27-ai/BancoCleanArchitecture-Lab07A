package pe.edu.unsch.banco.aplicacion.puertos;

/** Contrato de salida: la aplicacion no conoce la tecnologia de almacenamiento. */
public interface MovimientoRepository {
    void guardar(String movimiento);
}
