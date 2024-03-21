package edu.upb.chatupb.db;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

public class MyProperties {

    @Getter
    private static MyProperties instance = new MyProperties();

    private MyProperties() {
        // Load properties from the configuration file
        Properties p = new Properties();
        try {
            p.load(MyProperties.class.getResourceAsStream("/etc/config.properties"));

            String url = p.getProperty("url");
            String user = p.getProperty("user");
            String password = p.getProperty("password");

            ConnectionDB.instance.setUp(url, user, password);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
