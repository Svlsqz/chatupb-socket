/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb.server;

import edu.upb.chatupb.event.SocketEvent;
import edu.upb.chatupb.server.comandos.*;
import lombok.Getter;
import lombok.ToString;

import javax.swing.event.EventListenerList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

@ToString
/**
 *
 *
 * @author Sarah
 */
public class SocketClient extends Thread {

    private final String INVITACION = "001";
    private final String ACEPTAR_INVITACION = "002";
    private final String CHAT = "003";
    private final String EDITAR_MENSAJE = "004";
    private final String CAMBIAR_TEMA = "005";
    private final String BORRAR_HISTORIAL = "006";
    private final String PASAR_CONTACTO = "007";
    private final String SEARCH = "008";
    private final String ZUMBIDO_PANTALLA = "009";
    private final String RECHAZO_INVITACION = "010";
    //
    private final String INVITACION_ARCHIVO = "011"; //tipo nombre
    private final String ARCHIVO = "012";
    private final String TERMINAR_ENVIO_ARCHIVO = "013";

    private Socket socket;
    @Getter
    private String ip;
    private final EventListenerList listenerList = new EventListenerList();
    private Queue<Comando> comands = new LinkedList<>();
    private final DataOutputStream dout;

    public SocketClient(Socket socket, SocketEvent listener) throws IOException {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        dout = new DataOutputStream(socket.getOutputStream());
    }

    public SocketClient(String ip) throws IOException {
        this.socket = new Socket(ip, 1900);
        this.ip = ip;
        dout = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String message;

            while ((message = br.readLine()) != null) {
                //Interpretar mensaje
                message = message.toString();
                String comando = message.substring(0, 3);
                switch (comando) {

                    case INVITACION -> {
                        Comando comand = InvitacionContacto.parseo(message).ip(ip).socketClient(this);
                        Mediador.sendEventIvited(comand);
                    }
                    case ACEPTAR_INVITACION -> {
                        Mediador.sendEventAccept(AceptarInvitacion.parseo(message).ip(ip).socketClient(this));
                    }
                    case CHAT -> {
                        if (message.substring(3, 5).equals("V2")) {
                            Mediador.sendEventChat(ChatV2.parseo(message));
                        } else {
                            Mediador.sendEventChat(Chat.parseo(message));
                        }
                    }
                    case EDITAR_MENSAJE -> {
                        Mediador.sendEventEditMessage(EditarMensaje.parseo(message));
                    }
                    case CAMBIAR_TEMA -> {
                        Mediador.sendEventChangeTheme(CambiarTema.parseo(message));
                    }
                    case BORRAR_HISTORIAL -> {
                        Mediador.sendEventDeleteHistory(BorrarHistorial.parseo(message));
                    }
                    case PASAR_CONTACTO -> {
                        Mediador.sendEventPassContact(PasarContacto.parseo(message));
                    }
//                    case SEARCH -> {
//                        Mediador.sendEventSearch(Search.parseo(message));
//                    }
                    case ZUMBIDO_PANTALLA -> {
                        Mediador.sendEventScreenBuzz(ZumbidoPantalla.parseo(message));
                    }
                    case RECHAZO_INVITACION -> {
                        Mediador.sendEventRejectedInvitation(RechazoInvitacion.parseo(message));
                    }
                    default -> System.out.println("Comando no reconocido" + comando);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void addCommand(Comando command) {
        this.comands.add(command);
    }

    public void sendComands() throws Exception {
        if (dout == null || socket.isClosed()) {
            throw new Exception("La conexión está cerrada");
        }
        while (!comands.isEmpty()) {
            Comando command = this.comands.poll();
            dout.write(command.generateCommand().getBytes("UTF-8"));
            dout.flush();
            System.out.println("Comando enviado....");
        }
    }

}
