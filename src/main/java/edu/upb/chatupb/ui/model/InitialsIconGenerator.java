package edu.upb.chatupb.ui.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InitialsIconGenerator {

    public static BufferedImage generateIcon(String initials, int size) {
        BufferedImage icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();

        // Configurar la calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Rellenar el fondo con un color
        g2d.setColor(new Color(204, 0, 153));  // Puedes ajustar el color del fondo
        g2d.fillRect(0, 0, size, size);

        // Configurar la fuente y el color del texto
        g2d.setFont(new Font("Arial", Font.BOLD, size / 2));
        g2d.setColor(Color.WHITE);  // Puedes ajustar el color del texto

        // Calcular la posición del texto
        int x = size / 4;
        int y = size / 2 + size / 8;

        // Dibujar las iniciales
        g2d.drawString(initials, x, y);

        g2d.dispose();  // Liberar recursos gráficos

        return icon;
    }

    public static String extractInitials(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }

        // Obtener las iniciales del nombre (primeras letras de cada palabra)
        StringBuilder initials = new StringBuilder();
        String[] words = name.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                initials.append(word.charAt(0));
            }
        }

        return initials.toString().toUpperCase();
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        String name = "John Doe";
        String initials = extractInitials(name);
        BufferedImage icon = generateIcon(initials, 50);

        // Ahora, 'icon' contiene la imagen generada con las iniciales
        // Puedes utilizar 'icon' en tu aplicación
    }
}
