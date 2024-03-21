package edu.upb.chatupb.db;

import java.io.IOException;
import java.util.Properties;

public class MyProperties {

    static {
        Properties p = new Properties();

        try {
            p.load(MyProperties.class.getResourceAsStream("etc/config.properties"));

            ConnectionDB connectionDB = ConnectionDB.instance;
            connectionDB.setUp(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
