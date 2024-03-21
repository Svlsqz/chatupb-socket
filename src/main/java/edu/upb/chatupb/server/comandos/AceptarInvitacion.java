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
public class AceptarInvitacion implements Comando {
    private String tipo;
    private String codigoPersona;
    private String nombre;
    private String ip;
    private SocketClient socketClient;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return this.tipo + this.codigoPersona + String.format("%-" + 60 + "s", this.nombre)  + System.lineSeparator();
    }

    public static AceptarInvitacion parseo(String message) {
        AceptarInvitacion contacto = new AceptarInvitacion();
        String comando = message.substring(0, 3);
        String codigoPersona = message.substring(3, 39);
        String nombre = message.substring(39);

        contacto.tipo = comando;
        contacto.codigoPersona = codigoPersona;
        contacto.nombre = nombre;

        return contacto;
    }

    public AceptarInvitacion ip(String ip) {
        this.ip = ip;
        return this;
    }

    public AceptarInvitacion socketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        return this;
    }
}
