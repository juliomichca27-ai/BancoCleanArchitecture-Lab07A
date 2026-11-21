package pe.edu.unsch.banco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import pe.edu.unsch.banco.aplicacion.CuentaService;
import pe.edu.unsch.banco.aplicacion.puertos.MovimientoRepository;

/** Pruebas P01-P07 sin dependencias externas ni JUnit. */
public final class PruebasBanco {
    private static int correctas = 0;

    public static void main(String[] args) {
        RepositorioEnMemoria repo = new RepositorioEnMemoria();
        CuentaService banco = new CuentaService(repo);

        banco.registrarCuenta("Ana", new BigDecimal("1000"));
        verificar("P01: registra Ana y saldo 1000",
                banco.consultarCuenta().getTitular().equals("Ana")
                        && saldoEs(banco, "1000") && repo.movimientos.size() == 1);

        banco.depositar(new BigDecimal("200"));
        verificar("P02: deposita 200, saldo 1200 y registro",
                saldoEs(banco, "1200") && repo.movimientos.size() == 2
                        && repo.movimientos.get(1).contains("Deposito: S/ 200.00"));

        banco.retirar(new BigDecimal("150"));
        verificar("P03: retira 150, saldo 1050 y registro",
                saldoEs(banco, "1050") && repo.movimientos.size() == 3
                        && repo.movimientos.get(2).contains("Retiro: S/ 150.00"));

        verificar("P04: retiro de 2000 rechazado, saldo intacto",
                rechaza(() -> banco.retirar(new BigDecimal("2000")))
                        && saldoEs(banco, "1050") && repo.movimientos.size() == 3);

        verificar("P05: deposito de -10 rechazado, saldo intacto",
                rechaza(() -> banco.depositar(new BigDecimal("-10")))
                        && saldoEs(banco, "1050") && repo.movimientos.size() == 3);

        verificar("P06: segunda cuenta rechazada, Ana permanece",
                rechaza(() -> banco.registrarCuenta("Luis", new BigDecimal("300")))
                        && banco.consultarCuenta().getTitular().equals("Ana")
                        && saldoEs(banco, "1050") && repo.movimientos.size() == 3);

        CuentaService nuevoInicio = new CuentaService(repo);
        verificar("P07: al iniciar otra vez no se restaura cuenta",
                rechaza(nuevoInicio::consultarCuenta) && repo.movimientos.size() == 3);

        System.out.printf("%nRESULTADO: %d/7 pruebas correctas.%n", correctas);
    }

    private static boolean saldoEs(CuentaService banco, String esperado) {
        return banco.consultarCuenta().getSaldo().compareTo(new BigDecimal(esperado)) == 0;
    }

    private static boolean rechaza(Runnable operacion) {
        try {
            operacion.run();
            return false;
        } catch (IllegalArgumentException | IllegalStateException e) {
            return true;
        }
    }

    private static void verificar(String caso, boolean pasa) {
        System.out.printf("%s | %s%n", pasa ? "OK" : "ERROR", caso);
        if (!pasa) {
            throw new AssertionError("Fallo: " + caso);
        }
        correctas++;
    }

    private static final class RepositorioEnMemoria implements MovimientoRepository {
        private final List<String> movimientos = new ArrayList<>();

        @Override
        public void guardar(String movimiento) {
            movimientos.add(movimiento);
        }
    }
}
