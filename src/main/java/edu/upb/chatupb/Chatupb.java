
package edu.upb.chatupb;

import edu.upb.chatupb.db.ConnectionDB;
import edu.upb.chatupb.db.MyProperties;
import edu.upb.chatupb.server.Mediador;
import edu.upb.chatupb.ui.ChatUI;

/**
 * @author Sarah
 */
public class Chatupb {

    public static void main(String[] args) {


//        final MyProperties myProperties = new MyProperties();


        Mediador mediador = new Mediador();
        mediador.start();
        System.out.println("Mediador iniciado");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatUI().setVisible(true);
            }
        });

    }
}
