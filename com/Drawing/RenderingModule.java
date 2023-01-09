package com.Drawing;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.logic.GameInterpretation;

/**
 * This is an compund painting, contain subPaintings.
 */
public class RenderingModule implements Painting{
    private List<Painting> paintings = new ArrayList<>();

    public RenderingModule(){
        paintings.add(new Maze());
        paintings.add(new Ghost());
    }

    @Override
    public void setInterpretation(GameInterpretation interpretation) {
        paintings.forEach(painting -> painting.setInterpretation(interpretation));
    }

    @Override
    public void draw(Graphics2D graphics) {
        paintings.forEach(painting -> painting.draw(graphics));
    }
    
}
