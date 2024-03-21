package edu.upb.chatupb.ui.temas;

import edu.upb.chatupb.ui.ChatUI;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class TemaDefault implements Tema{
    @Override
    public String getColor() {
        return null;
    }

    public TemaDefault(ChatUI chatUI) {

        chatUI.getChatArea().setOpaque(true);
        chatUI.getChatArea().setBackground( new Color(9, 71, 84, 255));
        chatUI.getChatArea().setForeground(Color.WHITE);

        Color nuevoColorFuerte = new Color(4, 34, 59, 255);
        Color nuevoColorSuave = new Color(56, 90, 102, 233);


        chatUI.getBackground1().setColor1(nuevoColorFuerte);
        chatUI.getBackground1().setColor2(nuevoColorSuave);

        chatUI.getChatArea().repaint();

        chatUI.getChatArea().revalidate();

        SwingUtilities.updateComponentTreeUI(chatUI);

    }

}
