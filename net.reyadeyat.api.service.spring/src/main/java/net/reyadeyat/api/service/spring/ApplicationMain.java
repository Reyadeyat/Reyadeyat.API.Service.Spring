package net.reyadeyat.api.service.spring;

import jakarta.annotation.PostConstruct;
import net.reyadeyat.api.library.logger.EmbeddedLogger;
import net.reyadeyat.api.library.http.TLS;
import net.reyadeyat.api.library.util.BooleanParser;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;

@PropertySource("classpath:application.yml")
@ComponentScan(basePackages = {"net.reyadeyat.api", "net.reyadeyat.api.library.data.source", "net.reyadeyat.api.library.server", "net.reyadeyat.api.library.web", "net.reyadeyat.api.library.binary.file", "net.reyadeyat.api.library.auth", "net.reyadeyat.api.service.spring", "net.reyadeyat.api.servlet"})
public class ApplicationMain implements ApplicationRunner, WebMvcConfigurer {
    
    @Autowired
    private Environment environment;
    
    @PostConstruct
    public void init() {
        try {
            new SpringApiEnvironment(environment);
        } catch (Exception exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "SpringApiEnvironment initialization error!", exception);
        }
    }
    
    protected void enableProfile(String active_profile) {
        System.out.println("AEPB - spring.profiles.active");
        System.out.println(System.getProperty("spring.profiles.active"));
        System.setProperty("spring.profiles.active", active_profile);
        System.out.println("AEPA - spring.profiles.active");
        System.out.println(System.getProperty("spring.profiles.active"));
    }
    
    public static void main(String[] args) {
        try {
            /*ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationMain.class).bannerMode(Banner.Mode.OFF).run(args);
            ConfigurableEnvironment environment = context.getEnvironment();
            String[] active_profiles = environment.getActiveProfiles();
            for (String profile : active_profiles) {
                System.out.println("Active Profile: " + profile);
            }
            if (active_profiles.length == 0) {
                ((ConfigurableEnvironment) environment).setActiveProfiles("local");
            }*/
            new SpringApplicationBuilder(ApplicationMain.class).bannerMode(Banner.Mode.OFF).profiles("server").run(args);
        } catch (Exception ex) {
            System.err.println("Startup fail stop exception => " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String app_name = SpringApiEnvironment.getProperty("spring.application.name");
        String app_welcome = SpringApiEnvironment.getProperty("spring.application.welcome");
        String server_port = SpringApiEnvironment.getProperty("server.port");
        String log_level = SpringApiEnvironment.getProperty("net.reyadeyat.api.library.logging.log_level").toUpperCase();
        String print_break = SpringApiEnvironment.getProperty("net.reyadeyat.api.library.logging.print_break");
        Boolean print_logger = BooleanParser.parse(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.logging.print_logger"));
        Boolean print_time = BooleanParser.parse(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.logging.print_time"));
        File file = new File(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.logging.file_path"));
        if (file.exists() == false) {
            file.mkdirs();
        }
        EmbeddedLogger.build(app_name, log_level, print_break, print_logger, print_time, file.toPath().toString());
        //new EmbeddedLogger(environment).build("./");
        Logger.getLogger(getClass().getName()).log(Level.parse("NOTE"), "Test Log Level NOTE");
        Logger.getLogger(getClass().getName()).log(Level.parse("STEP"), "Test Log Level STEP");
        Logger.getLogger(getClass().getName()).log(Level.parse("DATA"), "Test Log Level DATA");
        Logger.getLogger(getClass().getName()).log(Level.parse("SQL"), "Test Log Level SQL");
        Logger.getLogger(getClass().getName()).log(Level.parse("TRACE"), "Test Log Level TRACE");
        TLS.loadSocketFactory(SpringApiEnvironment.getProperty("net.reyadeyat.api.library.server.tls.file"));
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Started "+app_welcome+" ["+app_name+ ":"+server_port+"]\n\tLog level \""+log_level+"\""+"\n\tLog Path \""+file.getAbsolutePath()+"\"");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE")
                .allowedHeaders("XX-SHCS", "XX-SHCX", "Origin", "Authorization", "X-Custom-Header", "X-Requested-With", "User-Agent", "Content-Type", "cache-control", "Connection", "Content-Length", "Accept-Encoding", "Accept-Charset", "Accept", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                .exposedHeaders("XX-SHCS", "XX-SHCX", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowedOrigins("https://localhost:4200")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
