# Laboratorio 07-A — Arquitectura limpia (Clean Architecture)

**Curso:** Construcción y Evolución de Software (IS-388) — UNSCH.

Este proyecto refactoriza el `SistemaBancario.java` suministrado por el docente: separa dominio, aplicación, puerto de persistencia, infraestructura y presentación. Mantiene las cinco opciones (registrar, depositar, retirar, consultar saldo y salir). Solo hay **una cuenta activa por ejecución**: se prohíbe reemplazarla, incluso si se intenta registrar otra.

## Requisitos

- JDK 17 o superior.
- IntelliJ IDEA (opcional: también funciona por terminal).
- Git para entregar en GitHub.

## Estructura

```text
BancoInicial/
  SistemaBancario.java             # Codigo original de diagnostico
src/main/java/pe/edu/unsch/banco/
  dominio/CuentaBancaria.java      # Reglas: montos y saldos
  aplicacion/CuentaService.java    # Casos de uso bancarios
  aplicacion/puertos/MovimientoRepository.java # Interfaz
  infraestructura/ArchivoMovimientoRepository.java # Archivo de texto
  presentacion/Main.java          # Menu, Scanner y mensajes
src/test/java/pe/edu/unsch/banco/
  PruebasBanco.java                # P01-P07 sin dependencias externas
DIAGNOSTICO.md
PRUEBAS.md
SUSTENTACION.md
evidencias/                       # Capturas reales de IntelliJ y registros auxiliares
```

## Ejecución rápida en Windows

Si tienes JDK 17+ instalado, puedes hacer doble clic en `ejecutar_windows.bat` para iniciar el menú, o en `probar_windows.bat` para ejecutar las siete pruebas.

## Ejecutar en IntelliJ IDEA

1. Abrir la carpeta **Laboratorio07A_Banco_CleanArchitecture** como proyecto.
2. Configurar **Project SDK = JDK 17 o superior**.
3. Marcar `src/main/java` como **Sources Root** y `src/test/java` como **Test Sources Root** (clic derecho > Mark Directory as); si IntelliJ ya los reconoce, omitir.
4. Abrir `src/main/java/pe/edu/unsch/banco/presentacion/Main.java`, clic en ▶ junto a `main`, y **Run 'Main.main()'**.
5. Para probar P01-P07, ejecutar `src/test/java/pe/edu/unsch/banco/PruebasBanco.java` (opcional además de la ejecución manual ya documentada).
6. El archivo `movimientos.txt` aparece en el **directorio de trabajo**, normalmente la raíz del proyecto. Consultar *Run > Edit Configurations > Working directory* si no aparece allí.

## Ejecutar por terminal (PowerShell, desde la raíz del proyecto)

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out (Get-ChildItem src\main\java -Recurse -Filter *.java).FullName
java -cp out pe.edu.unsch.banco.presentacion.Main
```

**Pruebas automatizadas (P01-P07):**

```powershell
javac -encoding UTF-8 -cp out -d out (Get-ChildItem src\test\java -Recurse -Filter *.java).FullName
java -cp out pe.edu.unsch.banco.PruebasBanco
```

**Código inicial por separado (para evidencia de la Actividad A):**

```powershell
javac BancoInicial\SistemaBancario.java
java -cp BancoInicial SistemaBancario
```

> El código inicial usa dos `Scanner` sobre `System.in`, tal como lo entregó el docente. Según la entrada y el entorno, pueden producirse interferencias al leer. No altera la evidencia del problema arquitectónico: la clase mezcla responsabilidades y sobrescribe cuentas.

## Capas y regla de dependencias

```text
  Presentación (Main) ────────> Aplicación (CuentaService)
         │                              │
         │                              ├──> Dominio (CuentaBancaria)
         │                              └──> Puerto (MovimientoRepository)
         │                                          ▲
         └──> Infraestructura (ArchivoMovimientoRepository) ─┘
```

La flecha indica dependencia de código. **Dominio** no importa aplicación ni infraestructura. **Aplicación** no importa infraestructura y utiliza la interfaz `MovimientoRepository` (inyección por constructor). El adaptador `ArchivoMovimientoRepository` implementa el contrato y concentra `FileWriter`.

Si se sustituye el archivo por JDBC/H2, se crea un nuevo adaptador (`JdbcMovimientoRepository`) y se cambia su instanciación en `Main`; ni `CuentaBancaria` ni `CuentaService` requieren cambios.

## Decisiones técnicas

- Se utiliza `BigDecimal`, porque `double` representa muchos decimales de manera aproximada y puede introducir errores en dinero. Se aceptan cantidades con hasta 2 decimales.
- `CuentaBancaria` es **inmutable**: `depositar` y `retirar` devuelven una cuenta con saldo actualizado. El servicio registra el movimiento **antes de reemplazar** la cuenta, evitando actualizarla si falla la escritura del historial.
- Solo se almacena el **historial de movimientos** en `movimientos.txt`. **No se persisten cuentas**, por lo que una ejecución nueva comienza sin cuenta activa, incluso cuando ya existe el archivo. Esto responde al P07; persistir cuentas completamente está fuera del alcance solicitado.
- `movimientos.txt` se ignora por Git para evitar incluir datos de ejecución; se entregan las capturas originales de las pruebas reales y ejemplos de salida en `evidencias/`.

## Modalidad de trabajo: individual (indicación oral del docente)

Aunque el apartado 8 de la guía impresa presenta roles para equipos, el docente indicó realizar este laboratorio **de forma individual**. Por eso, una sola persona debe comprender y presentar todas las capas, el diagnóstico, las pruebas y la documentación. **No hay reparto de tareas entre integrantes.**

## Entrega en GitHub

Trabajar en un repositorio propio. Como la guía también pide evidencias de ramas y Pull Requests, se recomienda conservar ese flujo **sin formar un equipo**: crear `develop`, trabajar personalmente en una rama `feature/clean-architecture`, abrir un Pull Request de esa rama a `develop` y fusionarlo tras probar. Por último, etiquetar el commit final con `lab-7a`.

Si el docente permite una entrega individual directa en `main`, seguir su instrucción; el código y la documentación no dependen del flujo de ramas.

Consulta `SUSTENTACION.md` para los comandos y `PRUEBAS.md` para las evidencias obligatorias. **Evidencias reales ya incorporadas:** 14 capturas proporcionadas por el estudiante (código original, refactorización, pruebas manuales P01–P07, historial de movimientos y pruebas automatizadas 7/7 en IntelliJ con JDK 17.0.2). **Prueba automatizada ejecutada por el estudiante:** `evidencias/13_pruebas_automatizadas.png`, con resultado `7/7 pruebas correctas` en IntelliJ (JDK 17.0.2). **Pendiente:** publicar en GitHub con las ramas y la etiqueta correspondientes, y compartir el enlace. No se incluyen URLs inventadas.
