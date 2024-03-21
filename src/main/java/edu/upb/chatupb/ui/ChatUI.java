package edu.upb.chatupb.ui;

import edu.upb.chatupb.db.ControllerDB;
import edu.upb.chatupb.event.SocketEvent;
import edu.upb.chatupb.interpreter.Expression;
import edu.upb.chatupb.interpreter.Interpreter;
import edu.upb.chatupb.interpreter.OperadorAND;
import edu.upb.chatupb.interpreter.PlabraClave;
import edu.upb.chatupb.model.ContactCollection;
import edu.upb.chatupb.server.ChatServer;
import edu.upb.chatupb.server.Contact;
import edu.upb.chatupb.server.Mediador;
import edu.upb.chatupb.server.comandos.*;
import edu.upb.chatupb.ui.components.ChatBox;
import edu.upb.chatupb.ui.model.InitialsIconGenerator;
import edu.upb.chatupb.ui.model.ModelMessage;
import edu.upb.chatupb.ui.swing.ChatEvent;
import edu.upb.chatupb.ui.temas.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@ToString
@Getter
@Setter
public class ChatUI extends javax.swing.JFrame implements SocketEvent, ListSelectionListener {

    private ChatServer chatServer;
    private final ControllerDB db = new ControllerDB();
    private Timer screenBuzzTimer;
    private boolean isScreenBuzzing = false;
    private final String MYID = "19c6e463-7439-4304-bc3d-a8b6de3c8588";

    private Tema temaActual;

    private Contact contactoSeleccionado;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");

    /**
     * Creates new form ChatUI
     */
    public ChatUI() {
        initComponents();

        temaActual = new TemaDefault(this);

        if (chatServer == null) {
            try {
                this.chatServer = new ChatServer(this);
                this.chatServer.start();
            } catch (Exception e) {
                System.exit(-1);

            }
        }

        chatArea.setChatUI(this);

        Mediador.addSocketEventListener(this);
        cargarContactos();

        ListaContactos.addListSelectionListener(this);


        chatArea.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) {
                sendMessages();
            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem pasarContacto = new JMenuItem("Pasar Contacto");

                pasarContacto.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        ContactCollection<Contact> contactos = new ContactCollection<>();

                        for (Contact c : db.obtenerContactos()) {
                            contactos.addContact(c);
                        }

                        if (!contactos.isEmpty()) {

                            Contact seleccionarContacto = seleccionarContacto(contactos);

                            if (seleccionarContacto != null) {
                                PasarContacto contacto = PasarContacto.builder()
                                        .tipo("007")
                                        .codigoPersona(seleccionarContacto.getCode())
                                        .nombre(seleccionarContacto.getName())
                                        .ip(seleccionarContacto.getIp())
                                        .build();

                                Mediador.sendMessage(contactoSeleccionado.getIp(), contacto);
                            }
                        }
                    }
                });

                popup.add(pasarContacto);
                JButton sourceButton = (JButton) evt.getSource();
                popup.show(sourceButton, 0, sourceButton.getHeight());


            }

            @Override
            public void keyTyped(KeyEvent evt) {
            }

            @Override
            public void keyEnterSend(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessages();
                }
            }
        });


        screenBuzzTimer = new Timer(50, new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) {
                    setLocation(getX() + 10, getY()); // Simula una vibración moviendo la ventana
                } else {
                    setLocation(getX() - 10, getY());
                }
                count++;

                if (count >= 10) {
                    screenBuzzTimer.stop();
                    setLocationRelativeTo(null);// Restaura la posición original de la ventana

                }
            }
        });

        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llama a la función showInviteDialog cuando se hace clic en el botón
                showInviteDialog(true, "");  // El segundo parámetro podría ser la IP inicial
            }
        });

        moreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem zumbido = new JMenuItem("Zumbido");
                    JMenuItem cambiarTema = new JMenuItem("Cambiar Tema");
                    JMenuItem temaDefault = new JMenuItem("Tema Default");
                    JMenuItem temaUno = new JMenuItem("Tema Uno");
                    JMenuItem temaDos = new JMenuItem("Tema Dos");
                    JMenuItem temaTres = new JMenuItem("Tema Tres");

                    zumbido.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ZumbidoPantalla zumbidoPantalla = ZumbidoPantalla.builder()
                                    .tipo("008")
                                    .build();
                            Mediador.sendMessage(contactoSeleccionado.getIp(), zumbidoPantalla);
                        }
                    });

                    cambiarTema.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Agrega los elementos de menú a popupMenuTema
                            popupMenu.add(temaDefault);
                            popupMenu.add(temaUno);
                            popupMenu.add(temaDos);
                            popupMenu.add(temaTres);

                            // Muestra el menú emergente en la posición del botón
                            popupMenu.show(moreButton, 0, moreButton.getHeight());
                        }
                    });

                    // Agrega el elemento de menú "Cambiar Tema" al menú emergente principal
                    popupMenu.add(zumbido);

                    popupMenu.add(cambiarTema);

                    // Agrega el listener para cada tema
                    temaDefault.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sendTheme("0");
                        }
                    });

                    temaUno.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sendTheme("1");
                        }
                    });

                    temaDos.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sendTheme("2");
                        }
                    });

                    temaTres.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sendTheme("3");
                        }
                    });

                    // Muestra el menú emergente principal en la posición del botón
                    popupMenu.show(moreButton, 0, moreButton.getHeight());
                }
            }

            public void sendTheme(String tema) {
                CambiarTema cambiarTema = CambiarTema.builder()
                        .tipo("005")
                        .codigoTema(tema)
                        .codigoPersonaOrg(MYID)
                        .build();
                Mediador.sendMessage(contactoSeleccionado.getIp(), cambiarTema);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BorrarHistorial borrarHistorial = BorrarHistorial.builder()
                        .tipo("006")
                        .codigoPersona(MYID)
                        .build();
                Mediador.sendMessage(contactoSeleccionado.getIp(), borrarHistorial);

                db.eliminarMensajes(contactoSeleccionado.getCode());
                chatArea.clearChatBox();
            }
        });


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        background1 = new edu.upb.chatupb.ui.swing.Background();
        chatArea = new edu.upb.chatupb.ui.components.ChatArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListaContactos = new javax.swing.JList<>();
        clearButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        moreButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        inviteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ListaContactos.setModel(new javax.swing.AbstractListModel<Contact>() {
            Contact[] contacto = {};

            public int getSize() {
                return contacto.length;
            }

            public Contact getElementAt(int i) {
                return contacto[i];
            }
        });
        jScrollPane1.setViewportView(ListaContactos);

        clearButton.setText("Limpiar Chat");

        jTextField1.setText("jTextField1");

        moreButton.setText("Más");


        jLabel1.setText("jLabel1");

        inviteButton.setText("Agregar Contacto");

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
                background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(background1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(background1Layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29)
                                                .addComponent(inviteButton))
                                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(background1Layout.createSequentialGroup()
                                                .addComponent(chatArea, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31))
                                        .addGroup(background1Layout.createSequentialGroup()
                                                .addComponent(clearButton)
                                                .addGap(26, 26, 26)
                                                .addComponent(moreButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29))))
        );
        background1Layout.setVerticalGroup(
                background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(background1Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                        .addGroup(background1Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(inviteButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)))
                                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(clearButton)
                                        .addComponent(moreButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(chatArea, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(background1Layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(background1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatUI().setVisible(true);
            }
        });
    }

    @Override
    public void onInvited(Comando comando) {


        InvitacionContacto contacto = (InvitacionContacto) comando;

        Object[] options = {"Aceptar", "Rechazar"};
        int n = JOptionPane.showOptionDialog(this,
                contacto.getNombre() +
                        "\n\n\n\n" + "Te ha invitado a chatear",
                "Invitación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        switch (n) {
            case JOptionPane.YES_OPTION -> {

                try {

                    Contact c = Contact.builder()
                            .code(contacto.getCodigoPersona())
                            .name(contacto.getNombre())
                            .ip(contacto.getIp())
                            .socketClient(contacto.getSocketClient())
                            .stateConnect(true)
                            .build();

                    db.agregarContacto(c.getCode(), c.getName(), c.getIp());

                    AceptarInvitacion aceptar = AceptarInvitacion.builder()
                            .tipo("002")
                            .codigoPersona("19c6e463-7439-4304-bc3d-a8b6de3c8588")
                            .nombre("Sarah")
                            .build();

                    Mediador.sendMessage(contacto.getIp(), aceptar);

                    cargarContactos();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            default -> { // En caso de rechazo y cerrar la invitacion
                onRejectedInvitation(comando);

            }
        }

    }


    @Override
    public void onAcceptedInvitation(Comando comando) {

        AceptarInvitacion aceptar = (AceptarInvitacion) comando;

        Contact contactoNuevo = Contact.builder()
                .code(aceptar.getCodigoPersona())
                .name(aceptar.getNombre())
                .ip(aceptar.getIp())
                .socketClient(aceptar.getSocketClient())
                .stateConnect(true)
                .build();


        String mensaje = aceptar.getNombre().toString() + " se agrego a tu lista de contactos";

        showInvitationDialog(mensaje, "InvitaciónAceptada");

        db.agregarContacto(contactoNuevo.getCode(), contactoNuevo.getName(), contactoNuevo.getIp());

        cargarContactos();

    }

    @Override
    public void onChat(Comando comando) {

        ComandoChat chat = (ComandoChat) comando;

        if (chat instanceof ChatV2) {
            System.out.println("ChatV2");
            ChatV2 chatV2 = (ChatV2) chat;
            String mensaje = chatV2.getMensaje();
            String codigoEmisor = chatV2.getCodigoPersona();
            String codigoReceptor = "19c6e463-7439-4304-bc3d-a8b6de3c8588";
            String idMensaje = chatV2.getCodigoMensaje();

            db.guardarMensaje(codigoEmisor, codigoReceptor, mensaje, idMensaje);

            String nombre = db.obtenerNombre(codigoEmisor);

            String initials = InitialsIconGenerator.extractInitials(nombre);
            BufferedImage initialsIcon = InitialsIconGenerator.generateIcon(initials, 35);
            Icon icon = new ImageIcon(initialsIcon);

            ModelMessage message = ModelMessage.builder()
                    .icon(icon)
                    .name(nombre)
                    .date(df.format(db.obtenerFechaMensaje(idMensaje)))
                    .message(chatV2.getVersion() + " " + mensaje + " longitud: " + chatV2.getLongitudMensaje())
                    .id(idMensaje)
                    .codEmisor(codigoEmisor)
                    .build();
            chatArea.addChatBox(message, ChatBox.BoxType.LEFT);
            chatArea.clearTextAndGrabFocus();

            try {
                autoRespondClima(mensaje);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (chat instanceof Chat) {
            System.out.println("ChatV1");
            Chat chatV1 = (Chat) chat;
            String mensaje = chatV1.getMensaje();
            String codigoEmisor = chatV1.getCodigoPersona();
            String codigoReceptor = "19c6e463-7439-4304-bc3d-a8b6de3c8588";
            String idMensaje = chatV1.getCodigoMensaje();

            db.guardarMensaje(codigoEmisor, codigoReceptor, mensaje, idMensaje);

            String nombre = db.obtenerNombre(codigoEmisor);

            String initials = InitialsIconGenerator.extractInitials(nombre);
            BufferedImage initialsIcon = InitialsIconGenerator.generateIcon(initials, 35);
            Icon icon = new ImageIcon(initialsIcon);
            ModelMessage message = ModelMessage.builder()
                    .icon(icon)
                    .name(nombre)
                    .date(df.format(db.obtenerFechaMensaje(idMensaje)))
                    .message(mensaje)
                    .id(idMensaje)
                    .codEmisor(codigoEmisor)
                    .build();
            chatArea.addChatBox(message, ChatBox.BoxType.LEFT);
            chatArea.clearTextAndGrabFocus();

            try {
                autoRespondClima(mensaje);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


//        Chat chat = (Chat) comando;
//
//        String mensaje = chat.getMensaje();
//        String codigoEmisor = chat.getCodigoPersona();
//        String codigoReceptor = "19c6e463-7439-4304-bc3d-a8b6de3c8588";
//        String idMensaje = chat.getCodigoMensaje();
//
//        db.guardarMensaje(codigoEmisor, codigoReceptor, mensaje, idMensaje);
//
//        String nombre = db.obtenerNombre(codigoEmisor);
//
//        String initials = InitialsIconGenerator.extractInitials(nombre);
//        BufferedImage initialsIcon = InitialsIconGenerator.generateIcon(initials, 35);
//        Icon icon = new ImageIcon(initialsIcon);
//        ModelMessage message = new ModelMessage(icon, nombre, df.format(db.obtenerFechaMensaje(idMensaje)), mensaje);
//        chatArea.addChatBox(message, ChatBox.BoxType.LEFT);
//        chatArea.clearTextAndGrabFocus();
//
//        autoRespondClima(Arrays.asList(chat));

    }

    @Override
    public void onEditMessage(Comando comando) {

        EditarMensaje editarMensaje = (EditarMensaje) comando;

        String mensaje = editarMensaje.getMensaje();
        String codigoMensaje = editarMensaje.getCodigoMensaje();

        db.editarMensaje(codigoMensaje, mensaje);

        ChatBox chatBox = chatArea.getChatBoxById(codigoMensaje);

        chatBox.updateMessage(mensaje);


        JDialog dialog = createDialog(this, "Editar Mensaje",
                ((EditarMensaje) comando).getMensaje().toString());
        dialog.show();

    }


    @Override
    public void onChangeTheme(Comando comando) {

        CambiarTema cambiarTema = (CambiarTema) comando;
        String tema = cambiarTema.getCodigoTema();

        switch (tema) {
            case "0" -> {
                TemaDefault temaDefault = new TemaDefault(this);
                temaActual = temaDefault;
            }
            case "1" -> {
                TemaUno temaUno = new TemaUno(this);
                temaActual = temaUno;
            }
            case "2" -> {
                TemaDos temaDos = new TemaDos(this);
                temaActual = temaDos;
            }
            case "3" -> {
                TemaTres temaTres = new TemaTres(this);
                temaActual = temaTres;
            }
        }

        JDialog dialog = createDialog(this, "Cambio de tema",
                "Tema cambiado a " + tema);
        dialog.show();
    }

    @Override
    public void onDeleteHistory(Comando comando) {

        BorrarHistorial borrarHistorial = (BorrarHistorial) comando;

        System.out.println(borrarHistorial.getCodigoPersona());

        db.eliminarMensajes(borrarHistorial.getCodigoPersona());
        //falta

        JDialog dialog = createDialog(this, "Borrar Chat",
                "Chat borrado");
        dialog.show();

    }

    @Override
    public void onPassContact(Comando comando) {

        PasarContacto contacto = (PasarContacto) comando;

        Object[] options = {"Aceptar", "Rechazar"};
        int n = JOptionPane.showOptionDialog(this,
                contacto.getNombre() +
                        "\n\n\n\n" + "Te ha pasado un contacto",
                "Pasando Contacto",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        switch (n) {
            case JOptionPane.YES_OPTION -> {

                if (contacto != null) {

                    InvitacionContacto invitacion = InvitacionContacto.builder()
                            .tipo("001")
                            .codigoPersona("19c6e463-7439-4304-bc3d-a8b6de3c8588")
                            .nombre("Sarah")
                            .mensaje("Holi")
                            .build();

                    Mediador.sendMessage(contacto.getIp(), invitacion);

                } else {
                    System.err.println("Contacto es nulo");
                }

            }
            default -> { // En caso de rechazo y cerrar la invitacion
                onRejectedInvitation(comando);

            }
        }


    }

    @Override
    public void onScreenBuzz(Comando comando) {

        screenBuzzTimer.start();

        try {
            Thread.sleep(2000); // Espera 2000 milisegundos (2 segundos)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JDialog dialog = createDialog(this, "Notificacion",
                " Nuevo Mensaje");
        dialog.show();

    }


    @Override
    public void onRejectedInvitation(Comando comando) {
        JDialog dialog = createDialog(this, "Rechazo de invitacion",
                ((RechazoInvitacion) comando).toString());
        dialog.show();

    }

    @Override
    public void onSearch(Comando comando) {

    }

    private void cargarContactos() {
        //Obtener contactos de la base de datos
        List<Contact> contactos = db.obtenerContactos();

        DefaultListModel<Contact> model = new DefaultListModel<>();
        for (Contact contacto : contactos) {
            model.addElement(contacto);
        }

        if (ListaContactos != null) {
            ListaContactos.setModel(model);
        } else {
            System.err.println("Lista de contactos es nula");
        }
    }

    private void showInvitationDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private static JDialog createDialog(final JFrame frame, String origin, String message) {
        final JDialog modelDialog = new JDialog(frame, origin,
                Dialog.ModalityType.DOCUMENT_MODAL);

        modelDialog.setBounds(132, 132, 300, 200);
        Container dialogContainer = modelDialog.getContentPane();

        dialogContainer.setLayout(new BorderLayout());
        dialogContainer.add(new JLabel(message),
                BorderLayout.CENTER);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelDialog.setVisible(false);
            }
        });

        panel1.add(okButton);
        dialogContainer.add(panel1, BorderLayout.SOUTH);

        modelDialog.setLocationRelativeTo(null);

        return modelDialog;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            this.contactoSeleccionado = ListaContactos.getSelectedValue();
            if (ListaContactos.getSelectedIndex() >= 0) {
                contactoSeleccionado = db.obtenerContactos().get(ListaContactos.getSelectedIndex());
                if (contactoSeleccionado == null || !contactoSeleccionado.isStateConnect()) {
                    System.out.println("HI");
                    // Obtener mensajes del contacto seleccionado
                    System.out.println(contactoSeleccionado.getCode());
                    List<ModelMessage> mensajes = db.obtenerMensajesPorContacto(contactoSeleccionado.getCode());
                    System.out.println(contactoSeleccionado.getCode());
                    System.out.println("Hello");
                    System.out.println(mensajes.size());
                    // Limpiar los mensajes actuales en ChatArea
                   chatArea.clearChatBox();
                    // Agregar los mensajes del contacto seleccionado a ChatArea
                    for (ModelMessage mensaje : mensajes) {
                        try {
                            System.out.println(" si");
                            // Determinar si el mensaje es del contacto seleccionado o del usuario actual
                            ChatBox.BoxType tipo = (mensaje.getCodEmisor().equals(contactoSeleccionado.getCode())) ? ChatBox.BoxType.RIGHT : ChatBox.BoxType.LEFT;
                            // Agregar el mensaje a ChatArea
                            chatArea.addChatBox(mensaje, tipo);
                            chatArea.clearTextAndGrabFocus();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                }
                }
            }
        }
    }


    private static JDialog createDialog(final JFrame frame, String message) {
        final JDialog modelDialog = new JDialog(frame, "Chat",
                Dialog.ModalityType.DOCUMENT_MODAL);
        modelDialog.setBounds(132, 132, 300, 200);
        Container dialogContainer = modelDialog.getContentPane();
        dialogContainer.setLayout(new BorderLayout());
        dialogContainer.add(new JLabel(message),
                BorderLayout.CENTER);
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelDialog.setVisible(false);
            }
        });

        panel1.add(okButton);
        dialogContainer.add(panel1, BorderLayout.SOUTH);

        return modelDialog;
    }

    private Contact seleccionarContacto(ContactCollection<Contact> contacts) {

        Contact[] contactosArr = new Contact[contacts.size()];
        int i = 0;

        while (i < contacts.size()) {
            contactosArr[i] = contacts.getNext();
            i++;
        }


        Contact contactoSeleccionado = (Contact) JOptionPane.showInputDialog(
                ChatUI.this,
                "Seleccione un contacto",
                "Contactos",
                JOptionPane.PLAIN_MESSAGE,
                null,
                contactosArr,
                contactosArr.length > 0 ? contactosArr[0] : null
        );
        return contactoSeleccionado;
    }

    private void showInviteDialog(boolean isInvite, String contactIP) {
        JPanel panel;
        if (isInvite)
            panel = new JPanel(new GridLayout(3, 2));
        else
            panel = new JPanel(new GridLayout(2, 2));

        String name = "Sarah Aranibar";

        JTextField ipAddressField = new JTextField();
        JTextField messageField = new JTextField();

        panel.add(new JLabel("Message:"));
        panel.add(messageField);

        if (isInvite) {
            panel.add(new JLabel("IP Address:"));
            panel.add(ipAddressField);
        }

        JButton okButton = new JButton("OK");
        panel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        panel.add(cancelButton);


        JDialog dialog = new JDialog(this, "Invite", Dialog.ModalityType.APPLICATION_MODAL);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipAddress;
                if (isInvite) ipAddress = ipAddressField.getText();
                else ipAddress = contactIP;

                String message = messageField.getText();

                if (!ipAddress.isEmpty() && !message.isEmpty()) {

                    InvitacionContacto contacto = InvitacionContacto.builder()
                            .tipo("001")
                            .codigoPersona("19c6e463-7439-4304-bc3d-a8b6de3c8588")
                            .nombre(name)
                            .mensaje(message)
                            .build();
                    //String toSend = "001" + myInfo.getId() + String.format("%-" + 60 + "s", name) + message;
                    Mediador.sendMessage(ipAddress, contacto);
                    //sendMessage(Contacto.builder().ip(ipAddress).sc(null).build(), toSend);
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.pack();
        //dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void autoRespondClima(String message) throws Exception {

        String pClima = "clima";
        String pLugar = "Santa Cruz";
        // Crear expresiones para las palabras clave "clima" y "lugar"
        Expression climaExpression = new PlabraClave(pClima);
        Expression lugarExpression = new PlabraClave(pLugar);

        // Crear una expresión compuesta para ambas palabras clave (AND)
        Expression operadorY = new OperadorAND(climaExpression, lugarExpression);

        // Crear el intérprete de oración con la expresión compuesta
        Interpreter interpreter = new Interpreter(operadorY);

        String lowerCaseMessage = message.toLowerCase();
        String codigoReceptor = "19c6e463-7439-4304-bc3d-a8b6de3c8588";

        if (interpreter.interpret(lowerCaseMessage)) {
            String response = "El clima en " + pLugar + " es Calido";
            String idMensaje = UUID.randomUUID().toString();
            String codigoEmisor = "19c6e463-7439-4304-bc3d-a8b6de3c8588";

            Chat rspChat = Chat.builder()
                    .tipo("003")
                    .codigoMensaje(idMensaje)
                    .codigoPersona(codigoEmisor)
                    .mensaje(response)
                    .build();

            db.guardarMensaje(codigoEmisor, codigoReceptor, response, idMensaje);

            Mediador.sendMessage(contactoSeleccionado.getIp(), rspChat);

            String rutaImagen = "C:\\Users\\Sarah\\Documents\\NetBeansProjects\\chatupb\\src\\main\\java\\edu\\upb\\chatupb\\ui\\resources\\bot.png";
            File image = new File(rutaImagen);

            Icon icon = new ImageIcon(image.getAbsolutePath());
            ModelMessage messageSend = ModelMessage.builder()
                    .icon(icon)
                    .name("ChatBot")
                    .date(df.format(db.obtenerFechaMensaje(idMensaje)))
                    .message(response)
                    .id(idMensaje)
                    .codEmisor(codigoEmisor)
                    .build();

            chatArea.addChatBox(messageSend, ChatBox.BoxType.RIGHT);
            chatArea.clearTextAndGrabFocus();
        }
    }

    private void sendMessages() {
        String mensaje = chatArea.getText();

        String codMensaje = UUID.randomUUID().toString();


        if (!mensaje.isEmpty() && contactoSeleccionado != null) {

            if (mensaje.substring(0, 2).equals("V2")) {

                ChatV2 chatV2 = ChatV2.builder()
                        .tipo("003")
                        .version("V2")
                        .codigoPersona("19c6e463-7439-4304-bc3d-a8b6de3c8588")
                        .codigoMensaje(codMensaje)
                        .longitudMensaje(String.valueOf(mensaje.length()))
                        .mensaje(mensaje)
                        .build();

                System.out.println("Mensaje V2: " + chatV2.toString());
                Mediador.sendMessage(contactoSeleccionado.getIp(), chatV2);

                String nombre = db.obtenerNombre("19c6e463-7439-4304-bc3d-a8b6de3c8588");

                String initials = InitialsIconGenerator.extractInitials(nombre);
                BufferedImage initialsIcon = InitialsIconGenerator.generateIcon(initials, 40);
                Icon icon = new ImageIcon(initialsIcon);
                ModelMessage message = ModelMessage.builder()
                        .icon(icon)
                        .name(nombre)
                        .date(df.format(new Date()))
                        .message(chatArea.getText())
                        .id(codMensaje)
                        .codEmisor("19c6e463-7439-4304-bc3d-a8b6de3c8588")
                        .build();
                chatArea.addChatBox(message, ChatBox.BoxType.RIGHT);
                chatArea.clearTextAndGrabFocus();

            } else {

                Chat chat = Chat.builder()
                        .tipo("003")
                        .codigoPersona("19c6e463-7439-4304-bc3d-a8b6de3c8588")
                        .codigoMensaje(codMensaje)
                        .mensaje(mensaje)
                        .build();

                Mediador.sendMessage(contactoSeleccionado.getIp(), chat);

                String nombre = db.obtenerNombre("19c6e463-7439-4304-bc3d-a8b6de3c8588");

                String initials = InitialsIconGenerator.extractInitials(nombre);
                BufferedImage initialsIcon = InitialsIconGenerator.generateIcon(initials, 40);
                Icon icon = new ImageIcon(initialsIcon);

                ModelMessage message = ModelMessage.builder()
                        .icon(icon)
                        .name(nombre)
                        .date(df.format(new Date()))
                        .message(chatArea.getText())
                        .id(codMensaje)
                        .codEmisor(MYID)
                        .build();

                chatArea.addChatBox(message, ChatBox.BoxType.RIGHT);
                chatArea.clearTextAndGrabFocus();
            }


        }
    }

    private void deleteHistory() {
        BorrarHistorial borrarHistorial = BorrarHistorial.builder()
                .tipo("006")
                .codigoPersona(MYID)
                .build();

        Mediador.sendMessage(contactoSeleccionado.getIp(), borrarHistorial);
    }


    public void editMessage(String mensaje, String codigoMensaje) {

        try {
            EditarMensaje editarMensaje = EditarMensaje.builder()
                    .tipo("004")
                    .codigoMensaje(codigoMensaje)
                    .mensaje(mensaje)
                    .build();

            db.editarMensaje(codigoMensaje, mensaje);

            Mediador.sendMessage(contactoSeleccionado.getIp(), editarMensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Variables declaration - do not modify
    private edu.upb.chatupb.ui.swing.Background background1;
    private edu.upb.chatupb.ui.components.ChatArea chatArea;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton inviteButton;
    private javax.swing.JButton moreButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<Contact> ListaContactos;
    //    private javax.swing.JList<Contact> ListaContactos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration

}