package Drawing;
import java.awt.Graphics2D;
import com.logic.GameInterpretation;

public interface Painting{
	void setInterpretation(GameInterpretation interpretation);
	void draw(Graphics2D graphics);
}