/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb.server;

import edu.upb.chatupb.event.SocketEvent;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author rlaredo
 */
public class ChatServer extends Thread {

    private static final int port = 1900;
    private ServerSocket server;
    private SocketEvent listener;

    public ChatServer(SocketEvent listener) throws IOException {
       this.server = new ServerSocket(port);
       this.listener = listener;
    }

    @Override
    public void run() {
        
        System.out.println("Servidor en ejecucion");
        
        while (true) {
            try {
                SocketClient socketClient = new SocketClient(this.server.accept(), this.listener);
                socketClient.start();
                Mediador.addClient(socketClient);
                System.out.println("Nuevo cliente!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
