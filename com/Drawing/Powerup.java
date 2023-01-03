package com.Drawing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.images.ImageProvider;
import com.logic.GameInterpretation;
import com.logic.Position;

public class Powerup implements Painting
{
    private static BufferedImage image ;
    private Position position = new Position(0, 0);

    static{
        image = ImageProvider.getImage("heart.png");
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(image, position.x , position.y , null );
    }

    @Override
    public void setInterpretation(GameInterpretation interpretation) {
        position.x = 100;
        position.y = 300;

    }
}
