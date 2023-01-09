package com.Drawing;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.logic.GameInterpretation;

/**
 * A compond painting for Ghosts
 */
public class Ghosts implements Painting{
    List<Ghost> ghosts;

    public Ghosts(int size){
        ghosts = new ArrayList<>();
        IntStream.range(0, size).forEach( index -> ghosts.add(new Ghost(index)));
    }


    @Override
    public void setInterpretation(GameInterpretation interpretation) {
        ghosts.forEach(painting -> painting.setInterpretation(interpretation));
    }

    @Override
    public void draw(Graphics2D graphics) {
        ghosts.forEach(painting -> painting.draw(graphics));
    }
}
