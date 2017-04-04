package com.shootr.web.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoDBConfigurationProperties {
    private List<String> hosts;
    private Integer port;
    private String database;
    private Integer connectionsPerHost;
    private String readPreference;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Integer getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(Integer connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public String getReadPreference() {
        return readPreference;
    }

    public void setReadPreference(String readPreference) {
        this.readPreference = readPreference;
    }

    @Override
    public String toString() {
        return "MongoDBConfigurationProperties [hosts=" + hosts + ", port=" + port + ", database=" + database
                + ", connectionsPerHost=" + connectionsPerHost + ", readPreference=" + readPreference + "]";
    }
}
