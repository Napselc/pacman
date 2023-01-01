package com;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.logic.GameInterpretation;

public class GamePannel extends JPanel implements ActionListener{
    
    private final Controls controls;
    private GameInterpretation gameInterpretation = new GameInterpretation();

    

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
        this.gameInterpretation.draw((Graphics2D) graphics);
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    
    
}
