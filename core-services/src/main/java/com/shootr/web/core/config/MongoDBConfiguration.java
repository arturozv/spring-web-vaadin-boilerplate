package com.shootr.web.core.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.shootr.web.core.dao.DaoPackage;
import com.shootr.web.core.dao.domain.MongoSynchronizedListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@EnableMongoRepositories(basePackageClasses = DaoPackage.class)
public class MongoDBConfiguration extends AbstractMongoConfiguration {
    private final Logger log = LoggerFactory.getLogger(MongoDBConfiguration.class);

    @Autowired
    private MongoDBConfigurationProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Bean
    public MongoSynchronizedListener mongoSynchronizedListener() {
        return new MongoSynchronizedListener();
    }

    @Bean
    @Override
    public MongoClient mongo() throws Exception {
        log.info(mongoProperties.toString());

        List<ServerAddress> serverAddresses = mongoProperties.getHosts().stream()
                .map(host -> new ServerAddress(host, mongoProperties.getPort()))
                .collect(Collectors.toList());

        MongoClientOptions options = new MongoClientOptions.Builder()
                .connectionsPerHost(mongoProperties.getConnectionsPerHost())
                .minConnectionsPerHost(4)
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .socketTimeout(7000)
                .connectTimeout(7000)
                .socketKeepAlive(false)
                .maxWaitTime(10001)
                .threadsAllowedToBlockForConnectionMultiplier(5)
                .readPreference(ReadPreference.valueOf(mongoProperties.getReadPreference()))
                .build();

        MongoClient mongoClient = serverAddresses.size() > 1 ?
                new MongoClient(serverAddresses, options) :
                new MongoClient(serverAddresses.get(0), options);

        if (mongoClient.getReplicaSetStatus() != null) {
            log.info("Connected to MongoDB Replica Set: " + mongoClient.getReplicaSetStatus());
        } else {
            log.info("Connected to MongoDB in Single Node mode!");
        }

        return mongoClient;
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singletonList(DaoPackage.class.getPackage().getName());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }
}
