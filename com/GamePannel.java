package com;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.Drawing.RenderingModule;
import com.logic.GameInterpretation;

public class GamePannel extends JPanel {
    
    private final Controls controls;
    private RenderingModule renderingModule = new RenderingModule();

    

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
        renderingModule.setInterpretation(GameInterpretation.gameInterpretation);
        renderingModule.draw((Graphics2D) graphics);
       
    }

    
    
}
