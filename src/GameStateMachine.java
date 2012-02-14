import java.awt.*;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/14/12
 * Time: 12:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameStateMachine {
    private GameState currentState;
    private double currentStateElapsedTime;
    private LinkedList<GameState> stateQueue = new LinkedList<GameState>();
    
    public void addState(GameState newState){
        stateQueue.add(newState);
    }
    
    public void renderCurrentState(Graphics2D g2d, double elapsedTime, int topLeftX, int topLeftY){
        currentStateElapsedTime += elapsedTime;
        if(currentState.getTimeOut() == -1){
            currentState.render(g2d, elapsedTime, topLeftX, topLeftY);
        }
        if(currentStateElapsedTime >= currentState.getTimeOut()){
            stateQueue.removeFirst();
            currentState = stateQueue.peek();
            currentState.render(g2d, elapsedTime, topLeftX, topLeftY);
        }
    }
}
