package com;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.logic.GameInterpretation;

public class GamePannel extends JPanel implements ActionListener{
    
    private final Controls controls;
    private GameInterpretation gameInterpretation;

    

    public GamePannel(){
        controls = new Controls();
        addKeyListener(controls);
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // this.gameInterpretation.draw(graphics);
        graphics.dispose();
    }

    public void actionPerformed(ActionEvent e){
        repaint();
    }
    
}
