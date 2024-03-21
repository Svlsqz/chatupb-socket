package edu.upb.chatupb.ui.temas;

import edu.upb.chatupb.ui.ChatUI;

import javax.swing.*;
import java.awt.*;

public class TemaUno implements Tema{
    @Override
    public String getColor() {
        return null;
    }

    public TemaUno(ChatUI chatUI) {
        chatUI.getChatArea().setOpaque(true);
        chatUI.getChatArea().setBackground( new Color(113, 31, 89, 233));
        chatUI.getChatArea().setForeground(Color.WHITE);

        Color nuevoColorFuerte = new Color(55, 2, 41, 220);
        Color nuevoColorSuave = new Color(233, 100, 100);


        chatUI.getBackground1().setColor1(nuevoColorFuerte);
        chatUI.getBackground1().setColor2(nuevoColorSuave);

        chatUI.getChatArea().repaint();

        chatUI.getChatArea().revalidate();

        SwingUtilities.updateComponentTreeUI(chatUI);
    }
}



