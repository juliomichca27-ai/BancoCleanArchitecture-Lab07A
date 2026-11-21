# Sustentación — Laboratorio 07-A

## Respuestas directas a las cinco preguntas de la guía

**1. ¿Qué problema concreto resolvieron al separar `CuentaBancaria` de `Main`?** Separamos las reglas bancarias del menú y del teclado. Ahora se pueden probar depósitos y retiros sin depender de la consola.

**2. ¿Por qué `CuentaService` recibe `MovimientoRepository` en su constructor?** Porque necesita guardar movimientos sin depender de un archivo específico. Así podemos usar otra implementación, como una base de datos o un repositorio de prueba.

**3. ¿Qué archivos deberían cambiar si reemplazamos `FileWriter` por JDBC/H2?** Crearíamos un nuevo adaptador de infraestructura y cambiaríamos la instancia del repositorio en `Main`. No sería necesario modificar `CuentaBancaria`, `CuentaService` ni `MovimientoRepository`.

**4. ¿En qué capa se valida que el retiro no exceda el saldo?** En la capa de dominio, dentro del método `retirar()` de `CuentaBancaria`.

**5. ¿Qué diferencia existe entre registrar movimientos y persistir cuentas?** Registrar movimientos guarda un historial de operaciones. Persistir cuentas permite recuperar titulares y saldos al reiniciar. Aquí solo existe el historial.

## Dificultades encontradas y decisiones adoptadas

- **Código monolítico:** el programa original mezclaba teclado, negocio y archivos. Se resolvió separándolo en capas y paquetes.
- **Cuenta sobrescrita:** se agregó validación en `CuentaService` para impedir un segundo registro mientras exista una cuenta activa.
- **Cifras monetarias:** se cambió `double` por `BigDecimal`, limitando a dos decimales para evitar errores de precisión.
- **Errores de usuario:** se controlaron montos negativos, fondos insuficientes y entradas no numéricas en el menú.
- **Historial vs. persistencia:** se deja explícito que `movimientos.txt` no recupera cuentas al volver a iniciar.

## Conclusiones

La arquitectura limpia separó las reglas del banco de la consola y del archivo de movimientos. Gracias a la interfaz `MovimientoRepository`, cambiar el medio de almacenamiento no obliga a modificar el dominio. La validación de montos y saldos está concentrada en `CuentaBancaria`, lo que facilita las pruebas. Los casos P01–P07 comprueban el comportamiento esperado, incluido el rechazo de una segunda cuenta. El alcance sigue siendo una cuenta activa por ejecución, sin recuperación automática al reiniciar.

## Desarrollo individual — responsabilidades completas

El docente indicó que este laboratorio se realice individualmente. Por tanto, **una sola persona** realiza todo lo solicitado:

1. **Actividad A:** ejecutar el `SistemaBancario.java` inicial; elaborar el diagnóstico y responder las preguntas orientadoras.
2. **Actividad B:** implementar `CuentaBancaria`, `CuentaService`, `MovimientoRepository`, `ArchivoMovimientoRepository` y `Main`, respetando las dependencias.
3. **Actividad C:** comprobar P01–P07 y guardar las capturas reales, incluida la consulta de saldo y `movimientos.txt`.
4. **Entrega:** completar `README.md`, `DIAGNOSTICO.md`, `PRUEBAS.md`, respuestas de sustentación, dificultades y conclusiones; subir el trabajo al repositorio GitHub propio.

## GitHub — procedimiento individual

La guía describe ramas `feature/`, Pull Request a `develop` y etiqueta `lab-7a`. Si el docente desea conservar ese flujo para cada estudiante, lo puedes realizar **tú solo** en tu repositorio.

**Desde un repositorio propio recién creado, con el proyecto copiado en su raíz:**

```bash
git init
git add .
git commit -m "chore: agrega base del laboratorio 07-A"
git branch -M main
git remote add origin URL_DE_TU_REPOSITORIO
git push -u origin main

git switch -c develop
git push -u origin develop

git switch -c feature/clean-architecture
# Realizar y guardar aquí los cambios de la refactorización.
git add .
git commit -m "feat: aplica Clean Architecture al sistema bancario"
git push -u origin feature/clean-architecture
```

**Nota:** si partes del ZIP con todo el código ya incorporado, el commit inicial puede ser la versión base y el commit `feature/` puede documentar una mejora real o la incorporación de evidencias. No crees commits vacíos ni presentes un historial ficticio.

En GitHub, abre un **Pull Request** de `feature/clean-architecture` hacia `develop`, revísalo y fusiónalo. Después:

```bash
git switch develop
git pull origin develop
# Verificar compilación y casos P01-P07.
git tag lab-7a
git push origin lab-7a
```

Se crea **una sola etiqueta `lab-7a`** sobre el commit final de `develop`. Si el docente ha simplificado el procedimiento GitHub para la modalidad individual, seguir esa indicación. Solo entrega enlaces y capturas que hayas generado realmente.
