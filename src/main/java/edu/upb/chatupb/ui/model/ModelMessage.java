package edu.upb.chatupb.ui.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
@Builder
public class ModelMessage {

//    public void setMessage(String message) {
//        this.message = message;
//    }

//    public ModelMessage(Icon icon, String name, String date, String message) {
//        this.icon = icon;
//        this.name = name;
//        this.date = date;
//        this.message = message;
//    }

//    public ModelMessage() {
//    }

    @Getter
    private Icon icon;
    private String name;
    private String date;
    private String message;
    private String id;
    private String codEmisor;
}
