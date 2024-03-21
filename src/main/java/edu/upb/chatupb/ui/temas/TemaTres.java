package edu.upb.chatupb.ui.temas;

import edu.upb.chatupb.ui.ChatUI;

import javax.swing.*;
import java.awt.*;

public class TemaTres implements Tema{
    @Override
    public String getColor() {
        return null;
    }

    public TemaTres(ChatUI chatUI) {

        chatUI.getChatArea().setOpaque(true);
        chatUI.getChatArea().setBackground( new Color(139, 51, 51, 167));
        chatUI.getChatArea().setForeground(Color.WHITE);

        Color nuevoColorFuerte = new Color(120, 4, 4, 220);
        Color nuevoColorSuave = new Color(157, 116, 105, 214);


        chatUI.getBackground1().setColor1(nuevoColorFuerte);
        chatUI.getBackground1().setColor2(nuevoColorSuave);

        chatUI.getChatArea().repaint();

        chatUI.getChatArea().revalidate();

        SwingUtilities.updateComponentTreeUI(chatUI);
    }
}
