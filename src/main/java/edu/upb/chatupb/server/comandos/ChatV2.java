/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb.server.comandos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

/**
 *
 * @author Sarah
 */
public class ChatV2 implements ComandoChat {
    private String tipo;
    private String version;
    private String codigoMensaje;
    private String codigoPersona;
    private String longitudMensaje;
    private String mensaje;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return this.tipoMensaje() + "V2" + this.codigoMensaje + this.codigoPersona + String.format("%" + 6 + "s", mensaje.length()).replace(" ","0") + this.mensaje + System.lineSeparator();
    }

    public static ChatV2 parseo(String message) {
        ChatV2 contacto = new ChatV2();
        String comando = message.substring(0, 3);
        String version = message.substring(3, 5);
        String codigoMensaje = message.substring(5, 41);
        String codigoPersona = message.substring(41, 77);
        String longitudMensaje = message.substring(77, 83);
        String mensaje = message.substring(83);

        contacto.tipo = comando;
        contacto.version = version;
        contacto.codigoMensaje = codigoMensaje;
        contacto.codigoPersona = codigoPersona;
        contacto.longitudMensaje = longitudMensaje.trim();
        contacto.mensaje = mensaje;

        return contacto;
    }


}
