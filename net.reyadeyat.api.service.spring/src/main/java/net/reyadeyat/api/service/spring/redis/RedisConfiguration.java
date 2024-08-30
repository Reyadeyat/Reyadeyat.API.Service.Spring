package net.reyadeyat.api.service.spring.redis;

import java.time.Duration;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
class RedisConfiguration {

    @Autowired
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {

        RedisConfig redis_config = new RedisConfig();
        System.out.print("redis_config.host");
        System.out.println(redis_config.host);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redis_config.host);
        redisStandaloneConfiguration.setUsername(redis_config.username);
        redisStandaloneConfiguration.setPort(redis_config.port);
        redisStandaloneConfiguration.setDatabase(redis_config.database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redis_config.password));

        SSLParameters sslParameters = new SSLParameters();
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        JedisClientConfiguration.JedisSslClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration
                .builder()
                .connectTimeout(Duration.ofSeconds(redis_config.connect_timeout))
                .useSsl()
                .sslParameters(sslParameters)
                .sslSocketFactory(sslSocketFactory);

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;
    }
}
