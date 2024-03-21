package edu.upb.chatupb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import edu.upb.chatupb.db.MyProperties;

public class ConnectionDB {
    private String url, user, password;
    private Connection conn;

    static final ConnectionDB instance = new ConnectionDB();

    static final MyProperties myProperties = new MyProperties();

    public void setUp( String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;

    }
    private ConnectionDB() {}

    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url,user, password);
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida");
            } else {
                System.out.println("Conexión a la base de datos fallida");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
