package org.example.services;

import org.example.models.keyspaces.loggers.KeyspacesLogger;
import org.example.repositories.keyspaces.KeyspacesLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggerService {
    private final KeyspacesLoggerRepository keyspacesLoggerRepository;

    @Autowired
    public LoggerService(KeyspacesLoggerRepository keyspacesLoggerRepository) {
        this.keyspacesLoggerRepository = keyspacesLoggerRepository;
    }

    public List<KeyspacesLogger> getLogsByModuleDate(String module, String createdAt) {
        if (createdAt.isEmpty()) {
            return keyspacesLoggerRepository.getLogsByModule(module);
        }
        return keyspacesLoggerRepository.getLogsByModuleDate(module, createdAt);
    }

    public void insertLog(KeyspacesLogger log) {
        keyspacesLoggerRepository.insertLog(log.getModule(), log.getCreatedAt(), log.getLogId(), log.getMessage());
    }
}
