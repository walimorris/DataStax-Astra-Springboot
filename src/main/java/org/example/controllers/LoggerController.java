package org.example.controllers;

import org.example.models.keyspaces.loggers.KeyspacesLogger;
import org.example.services.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/")
public class LoggerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);

    private final LoggerService keyspacesLoggerService;

    @Autowired
    public LoggerController(LoggerService keyspacesLoggerService) {
        this.keyspacesLoggerService = keyspacesLoggerService;
    }

    @GetMapping( "/")
    public String loggerByModule(@RequestParam Optional<String> module, @RequestParam Optional<String> createdAt, Model model) {
        if (module.isPresent() && !module.get().isEmpty()) {
            LOGGER.info("Module searched: {}", module.orElse(""));
            model.addAttribute("keyspacesLogger", new KeyspacesLogger());

            List<KeyspacesLogger> result = keyspacesLoggerService.getLogsByModuleDate(module.orElse(null), createdAt.orElse(null));
            model.addAttribute("foundLoggers", result);
            return "logger";
        }
        model.addAttribute("keyspacesLogger", new KeyspacesLogger());
        return "logger";
    }

    @PostMapping( "/add")
    public String keyspacesLoggerByModule(@RequestParam("module") String module,
                                          @RequestParam("message") String message, Model model) {
        LOGGER.info("Module added: {}", module);
        model.addAttribute("keyspacesLogger", new KeyspacesLogger());
        KeyspacesLogger logger = new KeyspacesLogger();
        logger.setModule(module);
        logger.setLogId(UUID.randomUUID());
        logger.setCreatedAt(getTimestampNow());
        logger.setMessage(message);
        try {
            keyspacesLoggerService.insertLog(logger);
            LOGGER.info("ADDED LOG! {}: ", logger.getModule());
        } catch (Exception e) {
            LOGGER.info("ERROR inserting LOG: {}", e.getMessage());
        }
        return "logger";
    }

    private String getTimestampNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of("America/Los_Angeles")));
        return dateFormat.format(new Date());
    }
}
