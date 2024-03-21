
package edu.upb.chatupb;

import edu.upb.chatupb.server.Mediador;
import edu.upb.chatupb.ui.ChatUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Sarah
 */
public class Chatupb {

    private static final Logger log = LogManager.getRootLogger();
    public static void main(String[] args) {

        log.info("Iniciando la aplicación Chatupb");

        Mediador mediador = new Mediador();
        mediador.start();
        log.info("Mediador iniciado");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatUI().setVisible(true);
            }
        });

    }
}
