package com.microsoft.aad.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @author MehmetAlpGuler
 */
@Component
public class GetPropertyValues {
    public String getProperties(String parameter)  {
            Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(parameter);
    }
}
