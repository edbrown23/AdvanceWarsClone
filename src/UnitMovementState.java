import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/17/12
 * Time: 8:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnitMovementState extends GameState {
    private GameCanvas game;
    private int topLeftX = 0, topLeftY = 0;
    private int width, height;

    public UnitMovementState(int t, GameState previousState) {
        super(t);
        //The following is a hack, it can certainly be improved
        // Essentially transferring everything from the edit state to this state
        WorldEditState prev = (WorldEditState)previousState;
        game = prev.getGame();
        topLeftX = prev.getTopLeftX();
        topLeftY = prev.getTopLeftY();
        
    }

    public void setKeyboardState(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(topLeftX < (width * 20) - 800){
                topLeftX += 20;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(topLeftX > 0){
                topLeftX -= 20;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            if(topLeftY > 0){
                topLeftY -= 20;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(topLeftY < (height * 20) - 400){
                topLeftY += 20;
            }
        }
    }

    public void update(double elapsedTime){
        game.setElapsedTime(elapsedTime);
    }

    public void render(){
        System.out.println("Unit State");
        game.setTopCoords(topLeftX, topLeftY);
        game.repaint();
    }

    public Component getGUIComponent(){
        return game;
    }
}
