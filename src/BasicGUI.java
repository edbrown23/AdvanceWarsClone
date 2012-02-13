import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicGUI extends JFrame {
    private GameCanvas game;
    private int topLeftX = 0;
    private int topLeftY = 0;

    public BasicGUI(){
        KeyHandler kHandler = new KeyHandler();
        this.addKeyListener(kHandler);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setVisible(true);

        game = new GameCanvas();
        this.add(game);

        boolean first = true;
        while(true){
            if(first){
                this.setSize(901, 501);
                first = false;
            }
            this.requestFocus();
            game.setTopCoords(topLeftX, topLeftY);
            game.repaint();
            try{
                Thread.sleep(30);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private class KeyHandler implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                if(topLeftX < (768 * 20) - 800){
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
                if(topLeftY < (256 * 20) - 400){
                    topLeftY += 20;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_G){
                game.changeCell(TileTypes.Grass);
            }else if(e.getKeyCode() == KeyEvent.VK_M){
                game.changeCell(TileTypes.Mountains);
            }else if(e.getKeyCode() == KeyEvent.VK_W){
                game.changeCell(TileTypes.Water);
            }else if(e.getKeyCode() == KeyEvent.VK_T){
                game.changeCell(TileTypes.Trees);
            }else if(e.getKeyCode() == KeyEvent.VK_B){
                game.addUnit(new Infantry(10, 10, Facing.East, game.getSelectedNode().getX(), "Sprites/Infantry.png", game.getSelectedNode().getY(), 5));
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
