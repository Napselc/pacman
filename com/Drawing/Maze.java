package com.Drawing;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import com.Constants;
import com.logic.GameInterpretation;
import java.awt.Color;

public class Maze implements Painting {


    private static final int scrsize = Constants.scrsize;
    private static final int BLOCK_SIZE = Constants.BLOCK_SIZE;
    private static final Color mazecolor = new Color(5, 100, 5);
    //TODO: move dots to their own class
    private final Color dotcolor = new Color(192, 192, 0);
    private short[] screendata;

    @Override
    public void draw(Graphics2D graphics) {
        short i = 0;
        int x, y;

        for (y = 0; y < scrsize; y += BLOCK_SIZE) {
            for (x = 0; x < scrsize; x += BLOCK_SIZE) {

                graphics.setColor(mazecolor);
                graphics.setStroke(new BasicStroke(2));

                if ((screendata[i] & 1) != 0) { 
                    graphics.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screendata[i] & 2) != 0) { 
                    graphics.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screendata[i] & 4) != 0) { 
                    graphics.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screendata[i] & 8) != 0) { 
                    graphics.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screendata[i] & 16) != 0) { 
                    graphics.setColor(dotcolor);
                    graphics.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
        
    }

    @Override
    public void setInterpretation(GameInterpretation interpretation) {
        screendata= interpretation.getLeveldata();
    }
    
}
