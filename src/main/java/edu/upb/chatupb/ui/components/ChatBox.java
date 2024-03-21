package edu.upb.chatupb.ui.components;

import edu.upb.chatupb.server.Mediador;
import edu.upb.chatupb.server.comandos.EditarMensaje;
import edu.upb.chatupb.ui.ChatUI;
import edu.upb.chatupb.ui.model.ModelMessage;
import edu.upb.chatupb.ui.swing.AutoWrapText;
import edu.upb.chatupb.ui.swing.ImageAvatar;
import lombok.Builder;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;



public class ChatBox extends JComponent {

    private ChatUI chatUI;

    @Getter
    private final BoxType boxType;
    @Getter
    private final ModelMessage message;

    public ChatBox(ChatUI chatUI, BoxType boxType, ModelMessage message) {
        this.chatUI = chatUI;
        this.boxType = boxType;
        this.message = message;
        init();
    }

    private void init() {
        initBox();
    }

    JTextPane text = new JTextPane();

    private void initBox() {
        String rightToLeft = boxType == BoxType.RIGHT ? ",rtl" : "";
        setLayout(new MigLayout("inset 5" + rightToLeft, "[40!]5[]", "[top]"));
        ImageAvatar avatar = new ImageAvatar();
        avatar.setBorderSize(1);
        avatar.setBorderSpace(1);
        avatar.setImage(message.getIcon());

        text.setEditorKit(new AutoWrapText());
        text.setText(message.getMessage());
        text.setBackground(new Color(0, 0, 0, 0));
        text.setForeground(new Color(0, 3, 6));
        text.setSelectionColor(new Color(200, 200, 200, 100));
        text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.setOpaque(false);
        text.setEditable(false);
        JLabel labelDate = new JLabel(message.getName() + " | " + message.getDate());
        labelDate.setForeground(new Color(127, 127, 127));
        add(avatar, "height 40,width 40");
        add(text, "gapy 20, wrap");
        add(labelDate, "gapx 20,span 2");

        // Menu

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(e.getX(), e.getY());
                }
            }
        });


    }

    public void updateMessage(String message) {
        text.setText(message);
    }

    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editarItem = new JMenuItem("Editar mensaje");
        editarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí necesitas obtener los datos del mensaje y abrir un cuadro de diálogo para la edición
                // Puedes usar el método getMessage() para obtener el mensaje de este ChatBox
                String mensajeActual = getMessage().getMessage();
                // Luego puedes abrir un cuadro de diálogo para editar el mensaje

                System.out.println("HERE1");
                // Por ejemplo:
                String nuevoMensaje = JOptionPane.showInputDialog(ChatBox.this, "Editar mensaje:", mensajeActual);
                System.out.println("HERE");
                // Ahora puedes actualizar el mensaje si el usuario hizo cambios
                if (nuevoMensaje != null && !nuevoMensaje.isEmpty()) {
                    System.out.println(" HEre 2");

                    try {
                        text.setText(nuevoMensaje);
                        getMessage().setMessage(nuevoMensaje);
                       chatUI.editMessage(nuevoMensaje, getMessage().getId());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        popupMenu.add(editarItem);
        popupMenu.show(this, x, y);
    }

    //TODO: MEnsaje


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        if (boxType == BoxType.LEFT) {
            Area area = new Area(new RoundRectangle2D.Double(25, 25, width - 25, height - 25 - 16 - 10, 5, 5));
            area.subtract(new Area(new Ellipse2D.Double(5, 5, 45, 45)));
            g2.setPaint(new GradientPaint(0, 0, new Color(255, 94, 98, 240), width, 0, new Color(255, 153, 102, 240)));
            g2.fill(area);
        } else {
            Area area = new Area(new RoundRectangle2D.Double(0, 25, width - 25, height - 25 - 16 - 10, 5, 5));
            area.subtract(new Area(new Ellipse2D.Double(width - 50, 5, 45, 45)));
            g2.setPaint(new GradientPaint(0, 0, new Color(115, 15, 85, 240), width, 0, new Color(219, 136, 198, 220)));
//            g2.setColor(new Color(255, 255, 255, 20));
            g2.fill(area);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    public static enum BoxType {
        LEFT, RIGHT
    }
}
