# Cómo actualizar tu laboratorio 07-A sin perder trabajo

## Opción recomendada: usar el ZIP SOLO_ACTUALIZACION

1. Cierra IntelliJ y haz una copia de seguridad de la carpeta de trabajo actual.
2. Abre el ZIP de actualización y extrae su contenido **dentro de la raíz de tu proyecto actual** (`Laboratorio07A_Banco_CleanArchitecture/`).
3. Reemplaza solo `README.md`, `DIAGNOSTICO.md` y `PRUEBAS.md` con los archivos de este ZIP.
4. Fusiona la carpeta `evidencias/` para incluir las 14 capturas y actualizar `LEEME.md`.
5. **NO borres ni reemplaces** `movimientos.txt`, `src/`, `BancoInicial/`, `SUSTENTACION.md`, `.idea/`, `.git/` ni archivos `.bat`.
6. Verifica que existe `evidencias/13_pruebas_automatizadas.png` y que el final de `PRUEBAS.md` documenta el resultado 7/7.
7. Vuelve a abrir el proyecto y prepara GitHub.

## Si prefieres usar el ZIP COMPLETO

Descomprímelo EN UNA CARPETA NUEVA, y luego copia solamente los tres documentos y la carpeta `evidencias/` a tu proyecto actual. No reemplaces todo el proyecto: el tuyo contiene el historial real y posiblemente configuraciones locales.

## Lo que NO está en el ZIP

- `movimientos.txt`: es tu historial REAL de ejecución local. Mantén el archivo que ya tienes (en `.gitignore` se evita subirlo a GitHub).
- `.idea/` y `.git/`: pertenecen a tu entorno y a tu repositorio local.
- `out/`: clases compiladas que se regeneran automáticamente y no deben enviarse como código fuente.

## Estado

Pruebas manuales P01–P07: verificadas. Captura automatizada `13_pruebas_automatizadas.png`: 7/7. Publicación en GitHub: todavía pendiente.
