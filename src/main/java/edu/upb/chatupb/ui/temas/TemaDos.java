package edu.upb.chatupb.ui.temas;

import edu.upb.chatupb.ui.ChatUI;

import javax.swing.*;
import java.awt.*;

public class TemaDos implements Tema{
    @Override
    public String getColor() {
        return null;
    }

    public TemaDos(ChatUI chatUI) {
        chatUI.getChatArea().setOpaque(true);
        chatUI.getChatArea().setBackground( new Color(39, 108, 26, 118));
        chatUI.getChatArea().setForeground(Color.WHITE);

        Color nuevoColorFuerte = new Color(31, 83, 31, 255);
        Color nuevoColorSuave = new Color(173, 212, 134, 105);


        chatUI.getBackground1().setColor1(nuevoColorFuerte);
        chatUI.getBackground1().setColor2(nuevoColorSuave);

        chatUI.getChatArea().repaint();

        chatUI.getChatArea().revalidate();

        SwingUtilities.updateComponentTreeUI(chatUI);
    }
}
