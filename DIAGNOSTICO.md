# Actividad A — Diagnóstico del código inicial

**Clase analizada:** `BancoInicial/SistemaBancario.java` (script proporcionado por el docente).

| Responsabilidad concentrada | Dónde se observa | Cambio que obligaría a modificar la misma clase |
|---|---|---|
| Capturar entrada | `Scanner`, `nextLine()`, `nextDouble()` en registrar/depositar/retirar | Usar interfaz gráfica, API REST o entrada con validación distinta |
| Validar operaciones | Condiciones `monto > 0` y `monto <= saldo` | Exigir importes de hasta dos decimales o un límite diario de retiros |
| Actualizar saldos | `saldo += monto` y `saldo -= monto` | Introducir comisiones, intereses o transferencias |
| Guardar archivos | `guardarMovimiento()` usa `FileWriter` | Reemplazar el archivo por JDBC/H2 o por un servidor remoto |
| Mostrar resultados | `System.out.println` en cada operación | Mostrar mensajes en una app móvil o traducir interfaz |
| Coordinar el flujo | `main`, `do-while`, `switch` | Añadir nuevos comandos o cambiar navegación |
| Gestionar cuenta activa | Campos mutables `titular` y `saldo` | Registrar varias cuentas o rechazar el intento de reemplazo |

## Problemas identificados

1. **Responsabilidades mezcladas:** `SistemaBancario` realiza interfaz, reglas, almacenamiento y coordinación. Cada cambio obliga a tocar una clase demasiado grande.
2. **Dependencia directa de la tecnología:** el modelo depende de `Scanner`, `System.out` y `FileWriter`, lo que dificulta probar reglas sin teclado o archivos.
3. **Sobrescritura de la cuenta:** al volver a elegir «Registrar cuenta», el programa cambia `titular` y `saldo` sin preguntar; desaparece la cuenta activa anterior.
4. **Dinero en `double`:** algunas cifras decimales no se representan de forma exacta en binario; `BigDecimal` evita esos errores habituales.
5. **Errores de entrada:** `nextDouble()` puede arrojar una excepción con texto inválido, sin recuperación adecuada en el menú.
6. **Persistencia limitada:** `movimientos.txt` conserva registros, pero no reconstruye la cuenta en el siguiente inicio.

## Preguntas orientadoras

**¿Qué pasa si cambiamos el archivo por una base de datos?** En el código inicial habría que editar `SistemaBancario`, mezclando un cambio técnico con el negocio. En Clean Architecture, se cambia el adaptador de infraestructura y la configuración en `Main`.

**¿Por qué la entidad no debería usar `Scanner` o `FileWriter`?** Porque esas clases son detalles de entrada y almacenamiento. La entidad debe concentrarse en las reglas bancarias y poder probarse sin consola ni disco.

**¿Qué pasa al registrar una cuenta distinta?** El código inicial reemplaza el titular y el saldo previos. La versión refactorizada devuelve un error y mantiene la cuenta original.

## Evidencia real del defecto de reemplazo (Actividad A)

En la ejecución individual del **código original**, la cuenta de Julio César Michca Llallahui comenzó con **S/ 920**, recibió un **depósito de S/ 500** (saldo **S/ 1420**) y luego un **retiro de S/ 1000**, quedando con **S/ 420**. Posteriormente se registró a **Ana Cecilia con S/ 1000**. Al consultar, el sistema mostró únicamente la cuenta y el saldo de Ana, en lugar de los de Julio. Esto es consecuencia de sobrescribir los atributos `titular` y `saldo`; **no significa que se haya borrado físicamente el historial de Julio**. Las operaciones anteriores permanecen visibles en `movimientos.txt`.

Evidencias: `evidencias/01_ejecucion_codigo_original.png`, `evidencias/02_consulta_saldo_original.png`, `evidencias/03_reemplazo_cuenta_original.png` y `evidencias/12_historial_movimientos.png`.

En la **versión refactorizada**, el intento de registrar una nueva cuenta fue rechazado, y la opción «Consultar saldo» confirmó que el titular original y S/ 1000 seguían activos. Evidencias: `evidencias/05_registro_duplicado_rechazado.png` y `evidencias/06_saldo_conservado.png`.

## Justificación de la refactorización

`CuentaBancaria` concentra las reglas; `CuentaService` coordina operaciones; `MovimientoRepository` permite sustituir el almacenamiento; `ArchivoMovimientoRepository` contiene `FileWriter`; `Main` maneja entrada y mensajes. Así se respeta la dirección de dependencias y los cambios técnicos dejan de afectar al dominio.
