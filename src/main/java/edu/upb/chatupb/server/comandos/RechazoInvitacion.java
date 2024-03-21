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

/**
 *
 * @author Sarah
 */
public class RechazoInvitacion implements Comando {
    private String tipo;
    private String codigoPersona;

    @Override
    public String tipoMensaje() {
      return this.tipo;
    }

    @Override
    public String generateCommand() {
        return tipo + codigoPersona + System.lineSeparator();
    }

    public static RechazoInvitacion parseo(String message) {
        RechazoInvitacion contacto = new RechazoInvitacion();
        String comando = message.substring(0,3);
        String codigoPersona = message.substring(3,39);

        contacto.tipo = comando;
        contacto.codigoPersona = codigoPersona;


        return contacto;
    }

}
