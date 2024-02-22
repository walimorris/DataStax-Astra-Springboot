package org.example.repositories.keyspaces;

import com.datastax.oss.driver.api.core.CqlSession;
import org.example.models.keyspaces.loggers.KeyspacesLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.mapping.CassandraPersistentEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.support.MappingCassandraEntityInformation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeyspacesLoggerRepository extends CassandraRepository<KeyspacesLogger, String> {

    @Query("SELECT module, logId, message, created_at FROM error_logs.logs WHERE module = ?0")
    List<KeyspacesLogger> getLogsByModule(String module);

    @Query("Select module, logId, message, created_at FROM error_logs.logs WHERE module = ?0 AND created_at = ?1")
    List<KeyspacesLogger> getLogsByModuleDate(String module, String createdAt);

//    @Consistency(DefaultConsistencyLevel.LOCAL_QUORUM)
    @Query("INSERT INTO error_logs.logs (module, created_at, logId, message) VALUES (?0, ?1, ?2, ?3)")
    void insertLog(String module, String createdAt, UUID uuids, String message);

}
