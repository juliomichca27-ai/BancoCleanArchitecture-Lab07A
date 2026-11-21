# Actividad C — Pruebas P01–P07 (ejecución individual real)

**Modalidad:** individual. **Entorno mostrado en IntelliJ IDEA:** Windows con OpenJDK 17.0.2. **Fecha de evidencias:** 18 de noviembre de 2026.

Se distinguen las **pruebas manuales realizadas en IntelliJ** (capturas originales adjuntas en `evidencias/`) de las pruebas automatizadas incluidas en el proyecto, que se pueden ejecutar con `PruebasBanco.java`.

| Caso | Acción manual realizada | Resultado observado en las capturas | Evidencia |
|---|---|---|---|
| P01 | `1` → `Ana` → `1000` | «Cuenta registrada correctamente» | `07_pruebas_P01_P02.png` |
| P02 | `2` → `200` | «Depósito exitoso»; el historial registra saldo S/ 1200.00 | `07_pruebas_P01_P02.png`, `12_historial_movimientos.png` |
| P03 | `3` → `150` y luego `4` | «Retiro exitoso»; Ana tiene S/ 1050.00 | `08_prueba_P03_consulta.png` |
| P04 | `3` → `2000` | «Operación rechazada: Fondos insuficientes» | `09_pruebas_P04_P05.png` |
| P05 | `2` → `-10` | «Operación rechazada: El monto debe ser mayor que cero» | `09_pruebas_P04_P05.png` |
| P06 | `1` → `Luis` → `500`, luego `4` | Rechaza segunda cuenta; Ana sigue con S/ 1050.00 | `10_prueba_P06_saldo_conservado.png` |
| P07 | `5`, volver a ejecutar `Main` y elegir `4` | Aviso de historial y «No hay una cuenta activa. Registre una primero» | `11a_prueba_P07_salida.png`, `11b_prueba_P07_aviso.png`, `11_prueba_P07_reinicio.png` |

**Resultado manual:** P01–P07 coinciden con los resultados esperados. Al finalizar P06, Ana conserva un saldo de **S/ 1050.00**. En P07, la cuenta ya no está activa al iniciar una nueva instancia del programa.

> **Nota sobre P06:** la prueba manual se realizó con Luis y **S/ 500**. `PruebasBanco.java` contiene el mismo caso usando S/ 300; el importe no afecta el requisito evaluado (impedir reemplazar una cuenta activa). La guía no fija el monto de la segunda cuenta.

## Evidencia de `movimientos.txt`

La captura `12_historial_movimientos.png` muestra un único archivo con el historial del programa original y el de Clean Architecture. Los registros correspondientes a las pruebas manuales P01–P03 son:

- **Línea 6:** `Cuenta creada | Titular: Ana | Saldo inicial: S/ 1000.00`
- **Línea 7:** `Titular: Ana | Deposito: S/ 200.00 | Saldo actual: S/ 1200.00`
- **Línea 8:** `Titular: Ana | Retiro: S/ 150.00 | Saldo actual: S/ 1050.00`

No hay nuevos registros asociados a P04–P06 porque las operaciones se rechazaron. Las líneas 1–4 pertenecen a la **ejecución del código original**, y la línea 5 a una ejecución anterior de la versión refactorizada. No se borraron los registros previos para generar estas evidencias.

**Alcance técnico:** `movimientos.txt` es un **historial**, no un mecanismo para reconstruir el estado de una cuenta. Al reiniciar, el servicio comienza sin cuenta activa. No significa que se hayan borrado los movimientos guardados.

## Evidencias de la Actividad A y corrección de la Actividad B

| Momento | Evidencia | Hallazgo |
|---|---|---|
| Código original | `01_ejecucion_codigo_original.png`, `02_consulta_saldo_original.png` | Julio realiza depósitos y retiros; su saldo llega a S/ 420.00. |
| Código original | `03_reemplazo_cuenta_original.png` | Registrar a Ana Cecilia con S/ 1000 reemplaza la cuenta activa de Julio. |
| Código refactorizado | `05_registro_duplicado_rechazado.png` | Se rechaza registrar una segunda cuenta mientras Julio Cesar está activo. |
| Código refactorizado | `06_saldo_conservado.png` | Julio Cesar conserva S/ 1000.00 después del rechazo. |

## Pruebas automatizadas ejecutadas en IntelliJ (evidencia real)

Además de las siete pruebas manuales, se ejecutó la clase `src/test/java/pe/edu/unsch/banco/PruebasBanco.java` en **IntelliJ IDEA con OpenJDK 17.0.2** el **18 de noviembre de 2026**. Su consola mostró `OK` en cada caso P01–P07 y concluyó con:

```text
RESULTADO: 7/7 pruebas correctas.
```

**Captura original del estudiante:** `evidencias/13_pruebas_automatizadas.png`. Las pruebas automatizadas utilizan un repositorio de movimientos en memoria (no modifican `movimientos.txt`). La prueba P06 automatizada usa S/ 300 para Luis, mientras que la ejecución manual usó S/ 500; en ambos casos se verificó el rechazo del segundo registro.

### Estado de las pruebas y entrega

- Pruebas manuales P01–P07: **7/7 observadas y verificadas**.
- Pruebas automatizadas P01–P07: **7/7 con captura real**.
- Historial: comprobado mediante `12_historial_movimientos.png`.
- Publicación en GitHub (rama, Pull Request y etiqueta `lab-7a`): **pendiente de ejecutar en el repositorio del estudiante**.

