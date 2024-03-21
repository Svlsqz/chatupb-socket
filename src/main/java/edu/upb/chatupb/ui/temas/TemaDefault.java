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

        Color nuevoColorHeader = new Color(255, 0, 0);  // Ejemplo: Rojo brillante
        Color nuevoColorBody = new Color(0, 255, 0);    // Ejemplo: Verde brillante
        Color nuevoColorBottom = new Color(0, 0, 255);  // Ejemplo: Azul brillante

        UIManager.put("Panel.header", new ColorUIResource(nuevoColorHeader));

        UIManager.put("Panel.body", new ColorUIResource(nuevoColorBody));

        UIManager.put("Panel.bottom", new ColorUIResource(nuevoColorBottom));

        SwingUtilities.updateComponentTreeUI(chatUI);

    }

}
