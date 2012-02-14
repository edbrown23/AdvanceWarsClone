import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/14/12
 * Time: 12:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class InMapState extends GameState {
    public GameCanvas game;

    public InMapState(int t) {
        super(t);
        game = new GameCanvas();
    }

    public void render(Graphics2D g2d, double elapsedTime, int topLeftX, int topLeftY){
        game.setTopCoords(topLeftX, topLeftY);
        game.setElapsedTime(elapsedTime);
        game.repaint();
    }
}
