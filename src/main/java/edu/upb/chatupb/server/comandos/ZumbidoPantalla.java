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
public class ZumbidoPantalla implements Comando {
    private String tipo;
    
    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return null;
    }

    public static ZumbidoPantalla parseo(String message) {
        ZumbidoPantalla contacto = new ZumbidoPantalla();
        String comando = message.substring(0,3);

        contacto.tipo = comando;

        return contacto;
    }


}
