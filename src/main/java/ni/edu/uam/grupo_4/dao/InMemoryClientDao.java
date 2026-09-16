package ni.edu.uam.grupo_4.dao;

import ni.edu.uam.grupo_4.model.Client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryClientDao implements ClientDao {
    private final Map<Long, Client> clients = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public synchronized Client save(Client client) {
        if (client.getId() == null) {
            client.setId(sequence.incrementAndGet());
        }
        clients.put(client.getId(), client);
        return client;
    }

    @Override
    public synchronized Optional<Client> findById(Long id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public synchronized List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public synchronized boolean update(Client client) {
        if (client.getId() == null || !clients.containsKey(client.getId())) {
            return false;
        }
        clients.put(client.getId(), client);
        return true;
    }

    @Override
    public synchronized boolean deleteById(Long id) {
        return clients.remove(id) != null;
    }
}
