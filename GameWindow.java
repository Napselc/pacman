
import javax.swing.JFrame;

public class GameWindow extends JFrame{
    
    public GameWindow(){
        add(new GamePannel());
        setTitle("Pac Man");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 540);
        setLocationRelativeTo(null);
        setVisible(true);     
    }
    
}
