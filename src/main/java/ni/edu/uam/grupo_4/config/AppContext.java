package ni.edu.uam.grupo_4.config;

import ni.edu.uam.grupo_4.dao.ClientDao;
import ni.edu.uam.grupo_4.dao.InMemoryClientDao;

public final class AppContext {
    private static final ClientDao CLIENT_DAO = new InMemoryClientDao();

    private AppContext() {
    }

    public static ClientDao getClientDao() {
        return CLIENT_DAO;
    }
}
