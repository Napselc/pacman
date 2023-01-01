package com.Drawing;

import java.awt.Graphics2D;

import com.logic.GameInterpretation;

public class Maze implements Painting {

    

    @Override
    public void draw(Graphics2D graphics) {
        short i = 0;
        int x, y;

        // for (y = 0; y < scrsize; y += BLOCK_SIZE) {
        //     for (x = 0; x < scrsize; x += BLOCK_SIZE) {

        //         g2d.setColor(mazecolor);
        //         g2d.setStroke(new BasicStroke(2));

        //         if ((screendata[i] & 1) != 0) { 
        //             g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
        //         }

        //         if ((screendata[i] & 2) != 0) { 
        //             g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
        //         }

        //         if ((screendata[i] & 4) != 0) { 
        //             g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
        //                     y + BLOCK_SIZE - 1);
        //         }

        //         if ((screendata[i] & 8) != 0) { 
        //             g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
        //                     y + BLOCK_SIZE - 1);
        //         }

        //         if ((screendata[i] & 16) != 0) { 
        //             g2d.setColor(dotcolor);
        //             g2d.fillRect(x + 11, y + 11, 2, 2);
        //         }

        //         i++;
        //     }
        // }
        
    }

    @Override
    public void setInterpretation(GameInterpretation interpretation) {
        // TODO Auto-generated method stub
        
    }
    
}
