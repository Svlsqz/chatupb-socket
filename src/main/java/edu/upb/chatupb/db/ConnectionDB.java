package edu.upb.chatupb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private String url, user, password;
    private Connection conn;

    static final ConnectionDB instance = new ConnectionDB();

    public void setUp(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;

        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida");
            } else {
                System.out.println("Conexión a la base de datos fallida");
            }
        }  catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Connection getConnection() {
             return conn;
    }
    private ConnectionDB() {


    }


}
