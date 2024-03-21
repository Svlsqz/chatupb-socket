package edu.upb.chatupb.db;

import java.io.IOException;
import java.util.Properties;

public class MyProperties {

    static {

        Properties p = new Properties();

        try {
            p.load(MyProperties.class.getResourceAsStream("/etc/config.properties"));

            String url = p.getProperty("url");
            String user = p.getProperty("user");
            String password = p.getProperty("password");

            ConnectionDB.instance.setUp(url, user, password);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
