package net.reyadeyat.api.service.spring.redis;

import net.reyadeyat.api.service.spring.SpringApiEnvironment;

public class RedisConfig {
    public String host;
    public Integer port;
    public String username;
    public String password;
    public Integer database;
    public Integer connect_timeout;
    //public Integer max_connections;
    //public Integer max_idle_connections;

    public RedisConfig() {
        host = SpringApiEnvironment.getProperty("net.reyadeyat.api.library.data.redis.host");
        port = Integer.valueOf(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.data.redis.port"));
        database = Integer.valueOf(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.data.redis.database"));
        username = SpringApiEnvironment.getProperty("net.reyadeyat.api.library.data.redis.username");
        password = SpringApiEnvironment.getProperty("net.reyadeyat.api.library.data.redis.password");
        connect_timeout = Integer.valueOf(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.data.redis.connect_timeout"));
        //max_connections = Integer.valueOf(environment.getProperty("net.reyadeyat.api.library.data.redis.max.connections"));
        //max_idle_connections = Integer.valueOf(environment.getProperty("net.reyadeyat.api.library.data.redis.max.idle.connections"));
    }
}
