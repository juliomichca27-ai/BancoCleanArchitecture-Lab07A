package pe.edu.unsch.banco.presentacion;

import java.math.BigDecimal;
import java.util.Scanner;
import pe.edu.unsch.banco.aplicacion.CuentaService;
import pe.edu.unsch.banco.dominio.CuentaBancaria;
import pe.edu.unsch.banco.infraestructura.ArchivoMovimientoRepository;

/** Adaptador de entrada: menu, Scanner y mensajes al usuario. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        CuentaService servicio = new CuentaService(new ArchivoMovimientoRepository());
        try (Scanner entrada = new Scanner(System.in)) {
            System.out.println("AVISO: movimientos.txt solo conserva el historial.");
            System.out.println("La cuenta no se recupera automaticamente al reiniciar.");

            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                String opcion = entrada.nextLine().trim();
                try {
                    switch (opcion) {
                        case "1":
                            System.out.print("Ingrese titular: ");
                            String titular = entrada.nextLine();
                            BigDecimal saldoInicial = leerMonto(entrada, "Ingrese saldo inicial: ");
                            servicio.registrarCuenta(titular, saldoInicial);
                            System.out.println("Cuenta registrada correctamente.");
                            break;
                        case "2":
                            servicio.depositar(leerMonto(entrada, "Monto a depositar: "));
                            System.out.println("Deposito exitoso.");
                            break;
                        case "3":
                            servicio.retirar(leerMonto(entrada, "Monto a retirar: "));
                            System.out.println("Retiro exitoso.");
                            break;
                        case "4":
                            CuentaBancaria cuenta = servicio.consultarCuenta();
                            System.out.println("Titular: " + cuenta.getTitular());
                            System.out.println("Saldo: S/ " + cuenta.getSaldo().toPlainString());
                            break;
                        case "5":
                            System.out.println("Saliendo del sistema...");
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opcion invalida.");
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Operacion rechazada: " + e.getMessage());
                }
            }
        }
    }

    private static BigDecimal leerMonto(Scanner entrada, String mensaje) {
        System.out.print(mensaje);
        String texto = entrada.nextLine().trim();
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un monto numerico valido (por ejemplo, 150.50).");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA BANCARIO | CLEAN ARCHITECTURE ===");
        System.out.println("1. Registrar cuenta");
        System.out.println("2. Depositar dinero");
        System.out.println("3. Retirar dinero");
        System.out.println("4. Consultar saldo");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }
}
