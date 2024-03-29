/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.chatupb.ui.swing;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Sarah
 */

@Getter
@Setter
public class Background  extends JPanel{

    private Color color1 = new Color(58, 72, 85);
    private Color color2 = new Color(28, 38, 50);


    public Background(){
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics grphcs){
        Graphics2D g2 = (Graphics2D) grphcs.create();
        int width = getWidth();
        int height = getHeight();
        g2.setPaint(new GradientPaint(0, 0, color1, width, 0,color2 ));
        //g2.setPaint(new GradientPaint(0, 0, new Color(37, 81, 149), width, 0, new Color(9, 35, 75)));
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        super.paintComponent(grphcs);
    }
}
