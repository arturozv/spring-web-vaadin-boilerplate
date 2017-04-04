import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import groovy.transform.Field

import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*

homeDir = System.getProperty("app.home.dir") ?: "."
@Field Level logLevel = getLevel()

def getLevel() {
    if (logLevel == null) {
        def logLevelLocal = DEBUG
        def env = System.getProperty("SPRING_PROFILES_ACTIVE") ?: "dev"
        if (env != "dev") {
            logLevelLocal = INFO
        }
        logLevel = logLevelLocal;
        println("Log config -> env: $env, logLevel: $logLevel")
    }
    return logLevel
}

scan()
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%date{ISO8601} [%p] %c - %m%n"
    }
}

//--------------------------------------------------------------------------------------------------

appender("ADMIN", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/admin.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("ADMIN_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("ADMIN")
}
appender("ADMIN_ERRORS", RollingFileAppender) {
    filter(ThresholdFilter) {
        level = ERROR
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/admin_error.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("ADMIN_ERRORS_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("ADMIN_ERRORS")
}

//--------------------------------------------------------------------------------------------------

appender("WEB", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/web.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("WEB_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("WEB")
}
appender("WEB_ERRORS", RollingFileAppender) {
    filter(ThresholdFilter) {
        level = ERROR
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/web_error.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("WEB_ERRORS_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("WEB_ERRORS")
}

//--------------------------------------------------------------------------------------------------

appender("SERVICE", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/service.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("SERVICE_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("SERVICE")
}
appender("SERVICE_ERRORS", RollingFileAppender) {
    filter(ThresholdFilter) {
        level = ERROR
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/service_error.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("SERVICE_ERRORS_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("SERVICE_ERRORS")
}

//--------------------------------------------------------------------------------------------------

appender("BATCH", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/batch.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("BATCH_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("BATCH")
}
appender("BATCH_ERRORS", RollingFileAppender) {
    filter(ThresholdFilter) {
        level = ERROR
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${homeDir}/log/batch_error.%d{yyyy-MM-dd}.log"
        maxHistory = 90
    }
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("utf-8")
        pattern = "%d %-5level [%thread] %logger{0}: %msg%n"
    }
}
appender("BATCH_ERRORS_ASYNC", AsyncAppender) {
    queueSize = 512
    discardingThreshold = 0
    appenderRef("BATCH_ERRORS")
}

//--------------------------------------------------------------------------------------------------

logger("com.shootr.web.admin", logLevel, ["ADMIN_ASYNC", "ADMIN_ERRORS_ASYNC"])
logger("com.shootr.web.service", logLevel, ["SERVICE_ASYNC", "SERVICE_ERRORS_ASYNC"])
logger("com.shootr.web.dao", logLevel, ["SERVICE_ASYNC", "SERVICE_ERRORS_ASYNC"])
logger("com.shootr.web.batch", logLevel, ["BATCH_ASYNC", "BATCH_ERRORS_ASYNC"])
logger("com.shootr.web.web", logLevel, ["WEB_ASYNC", "WEB_ERRORS_ASYNC"])
root(INFO, ["CONSOLE"])