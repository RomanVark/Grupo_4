package ni.edu.uam.grupo_4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long id;
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;

    @Builder.Default
    private List<String> serviciosInteres = new ArrayList<>();

    private String fotografiaRuta;

    public String getNombreCompleto() {
        return String.format("%s %s", nombres, apellidos).trim();
    }
}
