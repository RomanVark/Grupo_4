package grupo_4.clientes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private List<String> servicios;
    private String fotografia;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getServiciosTexto() {
        if (servicios == null || servicios.isEmpty()) {
            return "";
        }
        return servicios.stream().collect(Collectors.joining(", "));
    }
}
