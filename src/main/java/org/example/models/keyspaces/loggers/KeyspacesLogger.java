package org.example.models.keyspaces.loggers;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;

@Table(value = "logs")
public class KeyspacesLogger {

    @PrimaryKey(value = "module")
    @Column(value = "module")
    private String module;

    @Column(value = "created_at")
    private String createdAt;

    @Column(value = "logid")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID logId;

    @Column(value = "message")
    private String message;

    public KeyspacesLogger() {}

    public KeyspacesLogger(String module, String message, String createdAt, UUID logId) {
        this.module = module;
        this.message = message;
        this.createdAt = createdAt;
        this.logId = logId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String modules) {
        this.module = modules;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
