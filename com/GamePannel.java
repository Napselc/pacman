package com;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.logic.GameInterpretation;

public class GamePannel extends JPanel {
    
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
    public void paint(Graphics graphics){
        super.paint(graphics);
        this.gameInterpretation.setUserInputDirection(controls.getDirection());
        this.gameInterpretation.draw((Graphics2D) graphics);
    }

    
    
}
