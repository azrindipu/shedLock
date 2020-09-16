package com.azrin.schedular.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.mongo.MongoLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ShedLockConfig {

    @Value("${mongo.uri}")
    private String mongoUri;

    @Value("${mongo.database.name}")
    private String dbName;

    @Value("${shed.lock.pool.size}")
    private String shedLockPoolSize;

    @Value("${shed.lock.lock.at.most}")
    private String shedLockLockAtMost;

    @Bean
    public LockProvider lockProvider(){
        MongoClientURI uri = new MongoClientURI(mongoUri);
        MongoClient mongoClient = new MongoClient(uri);
        return new MongoLockProvider(mongoClient, dbName);
    }

    @Bean
    public ScheduledLockConfiguration taskScheduler(LockProvider lockProvider){
        return ScheduledLockConfigurationBuilder
                .withLockProvider(lockProvider)
                .withPoolSize(Integer.parseInt(shedLockPoolSize))
                .withDefaultLockAtMostFor(Duration.ofMinutes(Integer.parseInt(shedLockLockAtMost)))
                .build();
    }
}
