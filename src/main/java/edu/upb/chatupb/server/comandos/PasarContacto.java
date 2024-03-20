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
public class PasarContacto implements Comando {
    private String tipo;
    private String codigoPersona;
    private String nombre;
    private String ip;

    @Override
    public String tipoMensaje() {
        return this.tipo;
    }

    @Override
    public String generateCommand() {
        return this.tipo + this.codigoPersona + String.format("%-" + 60 + "s", this.nombre) + String.format("%-" + 15 + "s", this.ip) + System.lineSeparator();
    }

    public static PasarContacto parseo(String message) {
        PasarContacto contacto = new PasarContacto();


        String comando = message.substring(0, 3);
        String codigoPersona = message.substring(3, 39);
        String nombre = message.substring(39, 99).trim();
        String ip = message.substring(99).trim();

        // Validar IP con expresión regular
//        if (!ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
//            // Manejar error de IP no válida
//            return null;
//        }

        contacto.tipo = comando;
        contacto.codigoPersona = codigoPersona;
        contacto.nombre = nombre;
        contacto.ip = ip;

        return contacto;
    }


    @Override
    public String toString() {
        return "PasarContacto{" +
                "tipo='" + tipo + '\'' +
                ", codigoPersona='" + codigoPersona + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
