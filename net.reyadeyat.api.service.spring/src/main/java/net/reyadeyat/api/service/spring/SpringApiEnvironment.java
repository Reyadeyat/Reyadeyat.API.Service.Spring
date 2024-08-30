package net.reyadeyat.api.service.spring;

import net.reyadeyat.api.library.util.BooleanParser;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.reyadeyat.api.library.environment.ApiEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class SpringApiEnvironment extends ApiEnvironment {
    
    static private Environment environment;
    
    @Autowired
    public SpringApiEnvironment(Environment environment) throws Exception {
        super();
        SpringApiEnvironment.environment = environment;
        Logger.getLogger(SpringApiEnvironment.class.getName()).log(Level.INFO, "Environment initialized for service '"+ApiEnvironment.getProperty("spring.application.name")+"'");
    }
    
    static public Environment getEnvironment() {
        return SpringApiEnvironment.environment;
    }
    
    @Override
    protected String _getProperty(String property_name) {
        return SpringApiEnvironment.environment.getProperty(property_name);
    }
    
    @Override
    protected String _getString(String property_name) {
        return SpringApiEnvironment.environment.getProperty(property_name);
    }
    
    @Override
    protected int _getInteger(String integer_property) throws Exception {
        return Integer.parseInt(SpringApiEnvironment.getProperty(integer_property));
    }
    
    @Override
    protected boolean _getBoolean(String boolean_property) throws Exception {
        return BooleanParser.parse(SpringApiEnvironment.getProperty(boolean_property));
    }
}
