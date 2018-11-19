package com.graph.framework.resources.disconf;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@DisconfFile(filename = "application.properties")
public class ApplicationProperties {

    private String dsDriverClassName;
    private String dsUrl;
    private String dsUserName;
    private String dsPassword;
    private String dsHikariMaxLifeTime;
    private String dsHikariMaxPoolSize;
    private String mapperLocation;
    private String redisHost;
    private String redisPort;
    private String redisTimeout;
    private String redisPassword;
    private String redisDatabase;
    private String redisPoolMaxActive;
    private String redisPoolMaxWait;
    private String reidsPoolMaxIdle;
    private String redisMinIdle;
    private String rabbitHost;
    private String rabbitPort;
    private String rabbitUserName;
    private String rabbitPassword;
    private String rabbitVirtualHost;

    @DisconfFileItem(name = "spring.datasource.driver-class-name", associateField = "dsDriverClassName")
    public String getDsDriverClassName() {
        return dsDriverClassName;
    }

    public void setDsDriverClassName(String dsDriverClassName) {
        this.dsDriverClassName = dsDriverClassName;
    }

    @DisconfFileItem(name = "spring.datasource.url", associateField = "dsUrl")
    public String getDsUrl() {
        return dsUrl;
    }

    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }

    @DisconfFileItem(name = "spring.datasource.username", associateField = "dsUserName")
    public String getDsUserName() {
        return dsUserName;
    }

    public void setDsUserName(String dsUserName) {
        this.dsUserName = dsUserName;
    }

    @DisconfFileItem(name = "spring.datasource.password", associateField = "dsPassword")
    public String getDsPassword() {
        return dsPassword;
    }

    public void setDsPassword(String dsPassword) {
        this.dsPassword = dsPassword;
    }

    @DisconfFileItem(name = "spring.datasource.hikari.maxLifetime", associateField = "dsHikariMaxLifeTime")
    public String getDsHikariMaxLifeTime() {
        return dsHikariMaxLifeTime;
    }

    public void setDsHikariMaxLifeTime(String dsHikariMaxLifeTime) {
        this.dsHikariMaxLifeTime = dsHikariMaxLifeTime;
    }

    @DisconfFileItem(name = "spring.datasource.hikari.maximumPoolSize", associateField = "dsHikariMaxPoolSize")
    public String getDsHikariMaxPoolSize() {
        return dsHikariMaxPoolSize;
    }

    public void setDsHikariMaxPoolSize(String dsHikariMaxPoolSize) {
        this.dsHikariMaxPoolSize = dsHikariMaxPoolSize;
    }

    @DisconfFileItem(name = "mybatis.mapperLocations", associateField = "mapperLocation")
    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    @DisconfFileItem(name = "spring.redis.host", associateField = "redisHost")
    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    @DisconfFileItem(name = "spring.redis.port", associateField = "redisPort")
    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    @DisconfFileItem(name = "spring.redis.timeout", associateField = "redisTimeout")
    public String getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(String redisTimeout) {
        this.redisTimeout = redisTimeout;
    }

    @DisconfFileItem(name = "spring.redis.password", associateField = "redisPassword")
    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    @DisconfFileItem(name = "spring.redis.database", associateField = "redisDatabase")
    public String getRedisDatabase() {
        return redisDatabase;
    }

    public void setRedisDatabase(String redisDatabase) {
        this.redisDatabase = redisDatabase;
    }

    @DisconfFileItem(name = "spring.redis.lettuce.pool.max-active", associateField = "redisPoolMaxActive")
    public String getRedisPoolMaxActive() {
        return redisPoolMaxActive;
    }

    public void setRedisPoolMaxActive(String redisPoolMaxActive) {
        this.redisPoolMaxActive = redisPoolMaxActive;
    }

    @DisconfFileItem(name = "spring.redis.lettuce.pool.max-wait", associateField = "redisPoolMaxWait")
    public String getRedisPoolMaxWait() {
        return redisPoolMaxWait;
    }

    public void setRedisPoolMaxWait(String redisPoolMaxWait) {
        this.redisPoolMaxWait = redisPoolMaxWait;
    }

    @DisconfFileItem(name = "spring.redis.lettuce.pool.max-idle", associateField = "reidsPoolMaxIdle")
    public String getReidsPoolMaxIdle() {
        return reidsPoolMaxIdle;
    }

    public void setReidsPoolMaxIdle(String reidsPoolMaxIdle) {
        this.reidsPoolMaxIdle = reidsPoolMaxIdle;
    }

    @DisconfFileItem(name = "spring.redis.lettuce.pool.min-idle", associateField = "redisMinIdle")
    public String getRedisMinIdle() {
        return redisMinIdle;
    }

    public void setRedisMinIdle(String redisMinIdle) {
        this.redisMinIdle = redisMinIdle;
    }

    @DisconfFileItem(name = "spring.rabbitmq.host", associateField = "rabbitHost")
    public String getRabbitHost() {
        return rabbitHost;
    }

    public void setRabbitHost(String rabbitHost) {
        this.rabbitHost = rabbitHost;
    }

    @DisconfFileItem(name = "spring.rabbitmq.port", associateField = "rabbitPort")
    public String getRabbitPort() {
        return rabbitPort;
    }

    public void setRabbitPort(String rabbitPort) {
        this.rabbitPort = rabbitPort;
    }

    @DisconfFileItem(name = "spring.rabbitmq.userName", associateField = "rabbitUserName")
    public String getRabbitUserName() {
        return rabbitUserName;
    }

    public void setRabbitUserName(String rabbitUserName) {
        this.rabbitUserName = rabbitUserName;
    }

    @DisconfFileItem(name = "spring.rabbitmq.password", associateField = "rabbitPassword")
    public String getRabbitPassword() {
        return rabbitPassword;
    }

    public void setRabbitPassword(String rabbitPassword) {
        this.rabbitPassword = rabbitPassword;
    }

    @DisconfFileItem(name = "spring.rabbitmq.virtualHost", associateField = "rabbitVirtualHost")
    public String getRabbitVirtualHost() {
        return rabbitVirtualHost;
    }

    public void setRabbitVirtualHost(String rabbitVirtualHost) {
        this.rabbitVirtualHost = rabbitVirtualHost;
    }

}
