import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/14/12
 * Time: 12:04 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameState {
    private int timeOut;
    
    public GameState(int t){
        timeOut = t;
    }
    
    public int getTimeOut(){
        return timeOut;
    }

    public void render(Graphics2D g2d, double elapsedTime, int topLeftX, int topLeftY){

    }
}
