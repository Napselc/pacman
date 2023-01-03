package com.Drawing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.Direction;
import com.images.ImageProvider;
import com.logic.GameInterpretation;
import com.logic.Position;

public class Ghost implements Painting{
    private static BufferedImage image ;
    private Position position = new Position(40, 140);

   static{
    image = ImageProvider.getImage("ghost.gif");
   }


    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(image, position.x , position.y , null );
    }

    @Override
    public void setInterpretation(GameInterpretation interpretation) {
        position.setDirection(Direction.UP);
        
    }

    
}
