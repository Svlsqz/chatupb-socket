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
public class EditarMensaje implements Comando {
    private String tipo;
    private String codigoMensaje;
    private String mensaje;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return this.tipo + this.codigoMensaje + this.mensaje + System.lineSeparator();
    }

    public static EditarMensaje parseo(String message) {
        EditarMensaje contacto = new EditarMensaje();
        String comando = message.substring(0, 3);
        String codigoMensaje = message.substring(3, 39);
        String mensaje = message.substring(39).trim();

        contacto.tipo = comando;
        contacto.codigoMensaje = codigoMensaje;
        contacto.mensaje = mensaje;

        return contacto;
    }


}
