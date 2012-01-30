import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicGUI extends JFrame {
    private GameCanvas game;

    public BasicGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);

        game = new GameCanvas("forestMap.png");
        this.add(game);
        
        while(true){
            game.repaint();
        }
    }
}
