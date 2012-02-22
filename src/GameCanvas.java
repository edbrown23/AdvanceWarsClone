import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameCanvas extends JPanel {
    private SimpleMap map;
    private int topLeftX, topLeftY;
    private LinkedList<AStarNode> unitPath;
    
    public GameCanvas(String path){
        this.setSize(400, 200);
        //map.createMapFromImage(path);
        topLeftX = 0;
        topLeftY = 0;
    }
    
    public GameCanvas(int width, int height){
        map = new SimpleMap(width, height);
        this.setSize(400, 200);
        map.createMapFromPerlinNoise(100, 150, 200, 255);
        QuadTree.setupSprites();
        map.getMapTreeRoots();
    }
    
    public void setTopCoords(int topX, int topY){
        topLeftX = topX;
        topLeftY = topY;
    }
    
    public void setUnitPath(BaseUnit startingUnit, AStarNode goal){
        if(goal != null){
            unitPath = map.calculatePath(startingUnit, goal);
            map.informUnitOfNewPath(startingUnit, unitPath);
        }else{
            unitPath = null;
        }
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
        g2d.setColor(Color.black);
        for(int x = 0; x <= 800; x += 20){
            g2d.drawLine(x, 0, x, 400);
        }
        for(int y = 0; y <= 400; y += 20){
            g2d.drawLine(0, y, 800, y);
        }

        map.renderSelection(g2d, topLeftX, topLeftY);
        
        if(unitPath != null){
            g2d.setColor(Color.blue);
            for(AStarNode currentPoint : unitPath){
                g2d.fillRect((currentPoint.x * 20) + 5 - topLeftX, (currentPoint.y * 20) + 5 - topLeftY, 10, 10);
            }
        }
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 400, 800, 400);
        g2d.fillRect(800, 0, 400, 800);
    }
    
    public void addUnit(BaseUnit unit){
        map.addUnit(unit);
    }

    public void mouseSelect(int x, int y){
        int selectedX = x + topLeftX;
        int selectedY = y + topLeftY;

        QuadTree.selectTile(map.getMapTreeRoots()[0][0], selectedX, selectedY);
        QuadTree.selectTile(map.getMapTreeRoots()[1][0], selectedX, selectedY);
        QuadTree.selectTile(map.getMapTreeRoots()[2][0], selectedX, selectedY);
    }

    public QuadTreeNode getSelectedNode(){
        return QuadTree.getSelectedNode();
    }

    public void changeCell(TileTypes newType){
        map.changeCell(newType);
    }

    public void setElapsedTime(double elapsedTime){
        map.updateUnitFrames(elapsedTime);
        map.setTimeOfDay((map.getTimeOfDay() + elapsedTime / 100) % (2 * Math.PI));
    }

    public SimpleMap getMap(){
        return map;
    }
}
