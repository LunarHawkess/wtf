package com.maratsan.shop.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class ErrorHandlingConfig {

    @Value("${app.error.handling.enabled:true}")
    private Boolean enabled = true;

    @Value("${app.error.handling.logging-level:system}")
    private LoggingLevel loggingLevel = LoggingLevel.SYSTEM;


    public Boolean isLoggingEnabled(LoggingLevel loggingLevel) {
        if (this.loggingLevel.equals(LoggingLevel.NONE)) {
            return false;
        }
        return this.loggingLevel.equals(LoggingLevel.ALL) || this.loggingLevel.equals(loggingLevel);
    }


    public enum LoggingLevel {
        ALL,
        BUSINESS,
        SYSTEM,
        NONE
    }

}
