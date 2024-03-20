/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.upb.chatupb.event;

import edu.upb.chatupb.server.comandos.Comando;

import java.util.EventListener;

/**
 * @author Sarah
 */
public interface SocketEvent extends EventListener {
    void onInvited(Comando comando);

    void onAcceptedInvitation(Comando comando);

    void onChat(Comando comando);

    void onEditMessage(Comando comando);

    void onChangeTheme(Comando comando);

    void onDeleteHistory(Comando comando);

    void onPassContact(Comando comando);

    void onScreenBuzz(Comando comando);

    void onRejectedInvitation(Comando comando);

    void onSearch(Comando comando);
}