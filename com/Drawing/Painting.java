package com.Drawing;
import java.awt.Graphics2D;
import com.logic.*;

public interface Painting{
	void setInterpretation( GameInterpretation interpretation);
	void draw(Graphics2D graphics);
}