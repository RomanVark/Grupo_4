package grupo_4.clientes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private List<String> servicios;
    private String fotoUri;

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