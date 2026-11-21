package pe.edu.unsch.banco.aplicacion;

import java.math.BigDecimal;
import java.util.Objects;
import pe.edu.unsch.banco.aplicacion.puertos.MovimientoRepository;
import pe.edu.unsch.banco.dominio.CuentaBancaria;

/** Casos de uso: orquesta entidad y puerto, sin importar infraestructura. */
public final class CuentaService {
    private final MovimientoRepository movimientoRepository;
    private CuentaBancaria cuentaActiva;

    public CuentaService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = Objects.requireNonNull(
                movimientoRepository, "El repositorio es obligatorio.");
    }

    public void registrarCuenta(String titular, BigDecimal saldoInicial) {
        if (cuentaActiva != null) {
            throw new IllegalStateException(
                    "Ya existe una cuenta activa a nombre de " + cuentaActiva.getTitular()
                    + ". No se reemplazara.");
        }
        CuentaBancaria nuevaCuenta = new CuentaBancaria(titular, saldoInicial);
        movimientoRepository.guardar("Cuenta creada | Titular: " + nuevaCuenta.getTitular()
                + " | Saldo inicial: S/ " + formato(nuevaCuenta.getSaldo()));
        cuentaActiva = nuevaCuenta;
    }

    public void depositar(BigDecimal monto) {
        CuentaBancaria original = obtenerCuentaActiva();
        CuentaBancaria actualizada = original.depositar(monto);
        movimientoRepository.guardar("Titular: " + original.getTitular()
                + " | Deposito: S/ " + formato(monto)
                + " | Saldo actual: S/ " + formato(actualizada.getSaldo()));
        cuentaActiva = actualizada;
    }

    public void retirar(BigDecimal monto) {
        CuentaBancaria original = obtenerCuentaActiva();
        CuentaBancaria actualizada = original.retirar(monto);
        movimientoRepository.guardar("Titular: " + original.getTitular()
                + " | Retiro: S/ " + formato(monto)
                + " | Saldo actual: S/ " + formato(actualizada.getSaldo()));
        cuentaActiva = actualizada;
    }

    public CuentaBancaria consultarCuenta() {
        return obtenerCuentaActiva();
    }

    private CuentaBancaria obtenerCuentaActiva() {
        if (cuentaActiva == null) {
            throw new IllegalStateException("No hay una cuenta activa. Registre una primero.");
        }
        return cuentaActiva;
    }

    private static String formato(BigDecimal valor) {
        return valor.setScale(2).toPlainString();
    }
}
