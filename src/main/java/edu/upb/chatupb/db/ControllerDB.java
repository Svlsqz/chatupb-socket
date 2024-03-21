package edu.upb.chatupb.db;

import edu.upb.chatupb.server.Contact;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerDB {


    public void agregarContacto(String codigoPersona, String nombre, String ip) {
        try (Connection conn = ConnectionDB.instance.getConnection()) {
            String query = "INSERT INTO contactos (codigo_persona, nombre, ip) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, codigoPersona);
                statement.setString(2, nombre);
                statement.setString(3, ip);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contact> obtenerContactos() {

        try (Connection conn = ConnectionDB.instance.getConnection()) {


            List<Contact> contactos = new ArrayList<>();

            String query = "SELECT * FROM contactos";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Contact contact = Contact.builder()
                            .code(resultSet.getString("codigo_persona"))
                            .name(resultSet.getString("nombre"))
                            .ip(resultSet.getString("ip"))
                            .build();
                    contactos.add(contact);
                }
            }
            return contactos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> obtenerCodigoPersona() {

        try (Connection conn = ConnectionDB.instance.getConnection()) {
            List<String> codigos = new ArrayList<>();

            String query = "SELECT * FROM contactos";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String codigoPersona = resultSet.getString("codigo_persona");
                    codigos.add(codigoPersona);
                }
            }

            return codigos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String obtenerNombre(String codigoPersona) {
        try (Connection conn = ConnectionDB.instance.getConnection()) {
            String query = "SELECT nombre FROM contactos WHERE codigo_persona = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, codigoPersona);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("nombre");
                    } else {
                        return "Desconocido";
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void guardarMensaje(String codigoEmisor, String codigoReceptor, String mensaje, String idMensaje)  {

        try (Connection conn = ConnectionDB.instance.getConnection()) {
            String query = "INSERT INTO mensajes (codigo_emisor, codigo_receptor, mensaje, id_mensaje, fecha, hora) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, codigoEmisor);
                statement.setString(2, codigoReceptor);
                statement.setString(3, mensaje);
                statement.setString(4, idMensaje);

                LocalDate fechaActual = LocalDate.now();
                LocalTime horaActual = LocalTime.now();

                statement.setDate(5, Date.valueOf(fechaActual)); //java.sql.Date.valueOf(fechaActual)
                statement.setTime(6, Time.valueOf(horaActual)); //java.sql.Time.valueOf(horaActual)

                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarMensajes(String codigoPersona) {
        try (Connection conn = ConnectionDB.instance.getConnection()) {
            String query = "DELETE FROM mensajes WHERE codigo_emisor = ? OR codigo_receptor = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, codigoPersona);
                statement.setString(2, codigoPersona);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void editarMensaje(String idMensaje, String mensaje) {
        try (Connection conn = ConnectionDB.instance.getConnection()) {
            String query = "UPDATE mensajes SET mensaje = ? WHERE id_mensaje = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, mensaje);
                statement.setString(2, idMensaje);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public java.util.Date obtenerFechaMensaje(String idMensaje) {
        try (Connection conn = ConnectionDB.instance.getConnection()) {
            String query = "SELECT fecha FROM mensajes WHERE id_mensaje = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, idMensaje);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getDate("fecha");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
