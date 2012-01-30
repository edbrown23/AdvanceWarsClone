import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameCanvas extends JPanel {
    private SimpleMap map = new SimpleMap(40, 40);
    
    public GameCanvas(String path){
        this.setSize(400, 400);
        map.createMapFromImage(path);
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        map.render(g2d);
        g2d.setColor(Color.black);
        for(int x = 0; x < 400; x += 10){
            g2d.drawLine(x, 0, x, 400);
        }
        for(int y = 0; y < 400; y += 10){
            g2d.drawLine(0, y, 400, y);
        }
    }    
}
