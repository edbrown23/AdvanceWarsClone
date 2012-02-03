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
        this.setSize(850, 500);
        this.setVisible(true);

        game = new GameCanvas();
        //game = new GameCanvas("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/forestMap.png");
        this.add(game);

        while(true){
            this.requestFocus();
            game.setTopCoords(topLeftX, topLeftY);
            game.repaint();
            try{
                Thread.sleep(10000);
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
                if(topLeftX < 359){
                    topLeftX++;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                if(topLeftX > 0){
                    topLeftX--;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_UP){
                if(topLeftY > 0){
                    topLeftY--;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                if(topLeftY < 179){
                    topLeftY++;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
