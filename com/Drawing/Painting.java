package com.Drawing;
import java.awt.Graphics2D;
import com.logic.*;

/**
 * This is a parent class to draw the different components of the game.
 */
public interface Painting{
	void setInterpretation( GameInterpretation interpretation);
	void draw(Graphics2D graphics);
}