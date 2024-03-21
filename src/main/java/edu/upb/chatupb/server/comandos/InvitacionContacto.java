/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb.server.comandos;

import edu.upb.chatupb.server.SocketClient;
import lombok.*;

/**
 * @author Sarah
 */


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitacionContacto implements Comando {
    private String tipo;
    private String codigoPersona;
    private String nombre;
    private String mensaje;
    private String ip;
    private SocketClient socketClient;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return this.tipoMensaje() + this.codigoPersona + String.format("%-" + 60 + "s", this.nombre) + this.mensaje + System.lineSeparator();
    }

    public static InvitacionContacto parseo(String message) {
        return InvitacionContacto.builder()
                .tipo(message.substring(0, 3))
                .codigoPersona(message.substring(3, 39))
                .nombre(message.substring(39, 99).trim())
                .mensaje(message.substring(99).trim())
                .build();
    }

    public InvitacionContacto ip(String ip) {
        this.ip = ip;
        return this;
    }

    public InvitacionContacto socketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        return this;
    }

}
