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
public class Chat implements ComandoChat {
    private String tipo;
    private String codigoMensaje;
    private String codigoPersona;
    private String mensaje;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return this.tipoMensaje()+ this.codigoMensaje + this.codigoPersona +  this.mensaje+System.lineSeparator();
    }

    public static Chat parseo(String message) {
        Chat contacto = new Chat();
        String comando = message.substring(0, 3);
        String codigoMensaje = message.substring(3, 39);
        String codigoPersona = message.substring(39, 75);
        String mensaje = message.substring(75);

        contacto.tipo = comando;
        contacto.codigoMensaje = codigoMensaje;
        contacto.codigoPersona = codigoPersona;
        contacto.mensaje = mensaje;

        return contacto;
    }


}
