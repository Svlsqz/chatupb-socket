/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb;

import edu.upb.chatupb.server.comandos.Comando;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author rlaredo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String ip;
    private Comando command;
    
}
