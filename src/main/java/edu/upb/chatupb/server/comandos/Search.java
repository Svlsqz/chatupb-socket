/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb.server.comandos;

import edu.upb.chatupb.server.SocketClient;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 *
 * @author Sarah
 */
public class Search implements Comando {
    private String tipo;
    private String codigoPersona;
    private String nombre;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
       return null;
    }

    public static Search parseo(String message) {
        Search contacto = new Search();
        String comando = message.substring(0, 3);
        String codigoPersona = message.substring(3, 39);
        String nombre = message.substring(39);

        contacto.tipo = comando;
        contacto.codigoPersona = codigoPersona;
        contacto.nombre = nombre;

        return contacto;
    }

}
