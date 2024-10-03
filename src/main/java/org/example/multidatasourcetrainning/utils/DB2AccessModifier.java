package org.example.multidatasourcetrainning.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DB2AccessModifier {
    private static final String DATASOURCE = "datasource2";

    private static final String REVOKE_READONLY = "ALTER DATABASE " + DATASOURCE + " READ ONLY = 0";
    private static final String GRANT_READ_WRITE = "ALTER DATABASE " + DATASOURCE + " READ ONLY = 1";

    @Autowired
    @PersistenceContext(unitName = "db2")
    @Qualifier("entityManagerFactory2")
    private EntityManager em;

    /**
     * Set the datasource 2 to read-only
     */
    @Transactional("transactionManager2")
    public void lockDatabase() {
        try {
            em.createNativeQuery(GRANT_READ_WRITE).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set datasource 2 to read-write
     */
    @Transactional("transactionManager2")
    public void unlockDatabase() {
        try {
            em.createNativeQuery(REVOKE_READONLY).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    private List<String> datasource2Metadata() {
        List<Object> metadata = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        try {
            metadata = em.createNativeQuery(null).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Object o : metadata) {
            columnNames.add(o.toString());
        }
        return columnNames;
    }

    @Deprecated
    private String queryGenerator(List<String> tableNames) {
        StringBuilder query = new StringBuilder("LOCK TABLES ");
        for (String tableName : tableNames) {
            tableName = DATASOURCE + "." + tableName + " READ, ";
            query.append(tableName);
        }
        query.deleteCharAt(query.length() - 2);
        return query.toString();
    }
}
