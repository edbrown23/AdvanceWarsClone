import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameCanvas extends JPanel {
    private SimpleMap map = new SimpleMap(768, 256);
    private int topLeftX, topLeftY;
    private int selectedX, selectedY = 0;
    private double elapsedTime;
    private QuadTreeNode[][] roots;
    
    public GameCanvas(String path){
        this.setSize(400, 200);
        //map.createMapFromImage(path);
        topLeftX = 0;
        topLeftY = 0;
    }
    
    public GameCanvas(){
        this.setSize(400, 200);
        map.createMapFromPerlinNoise(100, 150, 200, 255);
        QuadTree.setupSprites();
        roots = map.getMapTreeRoots();

        MouseHandler mHandler = new MouseHandler();
        this.addMouseListener(mHandler);
    }
    
    public void setTopCoords(int topX, int topY){
        topLeftX = topX;
        topLeftY = topY;
    }
    
    public int getTopLeftX(){
        return topLeftX;
    }
    
    public int getTopLeftY(){
        return topLeftY;
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        map.render(g2d, topLeftX, topLeftY);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 400, 800, 400);
        g2d.fillRect(800, 0, 400, 800);
        g2d.setColor(Color.black);
        for(int x = 0; x <= 800; x += 20){
            g2d.drawLine(x, 0, x, 400);
        }
        for(int y = 0; y <= 400; y += 20){
            g2d.drawLine(0, y, 800, y);
        }
    }
    
    public void addUnit(BaseUnit unit){
        map.addUnit(unit);
    }
   
    public QuadTreeNode getSelectedNode(){
        return QuadTree.getSelectedNode();
    }

    public void changeCell(TileTypes newType){
        map.changeCell(newType);
    }

    public void setElapsedTime(double elapsedTime){
        this.elapsedTime = elapsedTime;
    }

    private class MouseHandler implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            selectedX = topLeftX + x;
            selectedY = topLeftY + y;
            QuadTree.selectTile(roots[0][0], selectedX, selectedY);
            QuadTree.selectTile(roots[1][0], selectedX, selectedY);
            QuadTree.selectTile(roots[2][0], selectedX, selectedY);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
