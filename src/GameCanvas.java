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

        if((roots[0][0].getX() - topLeftX) < (800) && (roots[0][0].getX() - topLeftX) >= (topLeftX * -1)){
            if((roots[0][0].getY() - topLeftY) < (400) && (roots[0][0].getY() - topLeftY) >= (topLeftY * -1)){
                QuadTree.quadTreeRender(g2d, roots[0][0], topLeftX, topLeftY);
            }
        }
        if((roots[1][0].getX() - topLeftX) < (800) && (roots[1][0].getX() - topLeftX) >= (topLeftX * -1)){
            if((roots[1][0].getY() - topLeftY) < (400) && (roots[1][0].getY() - topLeftY) >= (topLeftY * -1)){
                QuadTree.quadTreeRender(g2d, roots[1][0], topLeftX, topLeftY);
            }
        }
        if((roots[2][0].getX() - topLeftX) < (800) && (roots[2][0].getX() - topLeftX) >= (topLeftX * -1)){
            if((roots[2][0].getY() - topLeftY) < (400) && (roots[2][0].getY() - topLeftY) >= (topLeftY * -1)){
                QuadTree.quadTreeRender(g2d, roots[2][0], topLeftX, topLeftY);
            }
        }
        
        LinkedList<BaseUnit> tempUnits = map.getUnits();
        Iterator<BaseUnit> iter = tempUnits.iterator();
        while(iter.hasNext()){
            BaseUnit renderableUnit = iter.next();
            renderableUnit.render(g2d);
        }
        
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
        //g2d.setColor(Color.green);
        //g2d.fillRect(selectedX, selectedY, 20, 20);
    }
    
    public void addUnit(BaseUnit unit){
        map.addUnit(unit);
    }
   
    public QuadTreeNode getSelectedNode(){
        return QuadTree.getSelectedNode();
    }

    public void changeCell(TileTypes newType){
        QuadTree.changeCell(roots[0][0], newType);
        QuadTree.changeCell(roots[1][0], newType);
        QuadTree.changeCell(roots[2][0], newType);

        QuadTree.compressCells(roots[0][0]);
        QuadTree.compressCells(roots[1][0]);
        QuadTree.compressCells(roots[2][0]);

        // The following three repeats shouldn't be necessary, but it's a test. This can definitely be optimized
        QuadTree.compressCells(roots[0][0]);
        QuadTree.compressCells(roots[1][0]);
        QuadTree.compressCells(roots[2][0]);

        QuadTree.compressCells(roots[0][0]);
        QuadTree.compressCells(roots[1][0]);
        QuadTree.compressCells(roots[2][0]);

        QuadTree.compressCells(roots[0][0]);
        QuadTree.compressCells(roots[1][0]);
        QuadTree.compressCells(roots[2][0]);
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
