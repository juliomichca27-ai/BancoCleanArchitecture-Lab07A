package pe.edu.unsch.banco.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Entidad de dominio: no depende de consola, archivos ni servicios. */
public final class CuentaBancaria {
    private final String titular;
    private final BigDecimal saldo;

    public CuentaBancaria(String titular, BigDecimal saldoInicial) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("El titular no puede estar vacio.");
        }
        BigDecimal saldoValidado = normalizar(saldoInicial);
        if (saldoValidado.signum() < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.titular = titular.trim();
        this.saldo = saldoValidado;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    /** Devuelve una nueva cuenta para no cambiar el saldo si falla el registro. */
    public CuentaBancaria depositar(BigDecimal monto) {
        BigDecimal valido = validarMontoPositivo(monto);
        return new CuentaBancaria(titular, saldo.add(valido));
    }

    /** Rechaza retiros que superen el saldo disponible. */
    public CuentaBancaria retirar(BigDecimal monto) {
        BigDecimal valido = validarMontoPositivo(monto);
        if (valido.compareTo(saldo) > 0) {
            throw new IllegalArgumentException("Fondos insuficientes.");
        }
        return new CuentaBancaria(titular, saldo.subtract(valido));
    }

    private static BigDecimal validarMontoPositivo(BigDecimal monto) {
        BigDecimal normalizado = normalizar(monto);
        if (normalizado.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        return normalizado;
    }

    private static BigDecimal normalizar(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El monto es obligatorio.");
        }
        try {
            return valor.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Solo se permiten importes con hasta dos decimales.");
        }
    }
}
