package ni.edu.uam.grupo_4.dao;

import ni.edu.uam.grupo_4.model.Client;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryClientDaoTest {

    @Test
    void completesCrudCycle() {
        ClientDao dao = new InMemoryClientDao();
        Client client = Client.builder()
                .nombres("Ana")
                .apellidos("López")
                .tipoCliente("Natural")
                .ciudad("Managua")
                .fechaNacimiento(LocalDate.of(1998, 4, 12))
                .tipoSolicitud("Cotización")
                .serviciosInteres(List.of("Instalación"))
                .build();

        dao.save(client);
        assertNotNull(client.getId());
        assertEquals(1, dao.findAll().size());

        client.setCiudad("Masaya");
        assertTrue(dao.update(client));
        assertEquals("Masaya", dao.findById(client.getId()).orElseThrow().getCiudad());

        assertTrue(dao.deleteById(client.getId()));
        assertFalse(dao.findById(client.getId()).isPresent());
    }
}
