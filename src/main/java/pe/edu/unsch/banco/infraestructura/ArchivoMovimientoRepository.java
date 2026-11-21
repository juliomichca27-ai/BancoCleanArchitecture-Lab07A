package pe.edu.unsch.banco.infraestructura;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import pe.edu.unsch.banco.aplicacion.puertos.MovimientoRepository;

/** Adaptador externo: registra movimientos en un archivo de texto UTF-8. */
public final class ArchivoMovimientoRepository implements MovimientoRepository {
    private static final DateTimeFormatter FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Path rutaArchivo;

    public ArchivoMovimientoRepository() {
        this(Path.of("movimientos.txt"));
    }

    public ArchivoMovimientoRepository(Path rutaArchivo) {
        this.rutaArchivo = Objects.requireNonNull(rutaArchivo, "Ruta obligatoria.");
    }

    @Override
    public void guardar(String movimiento) {
        try {
            Path directorio = rutaArchivo.toAbsolutePath().getParent();
            if (directorio != null) {
                Files.createDirectories(directorio);
            }
            try (FileWriter archivo = new FileWriter(
                    rutaArchivo.toFile(), StandardCharsets.UTF_8, true)) {
                archivo.write(LocalDateTime.now().format(FECHA)
                        + " | " + movimiento + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar el movimiento.", e);
        }
    }
}
