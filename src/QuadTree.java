import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/2/12
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuadTree {

    public static QuadTreeNode[][] generateQuadTree(QuadTreeNode[][] nodes, int width, int height, int scalar){
        int multiplier = 10;
        if(width == 2 && height == 1){
            return nodes;
        }else{
            QuadTreeNode[][] tempNodes = new QuadTreeNode[(width / 2)][(height / 2)];
            int tempXIndex = 0;
            int tempYIndex = 0;
            for(int y = 0; y < height - 1; y += 2){
                for(int x = 0; x < width - 1; x += 2){
                    if(y == 0){
                        if(x == 0){
                            tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], scalar, (x * scalar * multiplier), (y * scalar * multiplier));
                        }else{
                            tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], scalar, (x * scalar * multiplier) - 1, (y * scalar * multiplier));
                        }
                    }else{
                        if(x == 0){
                            tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], scalar, (x * scalar * multiplier), (y * scalar * multiplier) - 1);
                        }else{
                            tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], scalar, (x * scalar * multiplier) - 1, (y * scalar * multiplier) - 1);
                        }
                    }
                    if(tempNodes[tempXIndex][tempYIndex].checkIdenticalBranches()){
                        tempNodes[tempXIndex][tempYIndex].setTile(nodes[x][y].getTile());
                    }else{
                        tempNodes[tempXIndex][tempYIndex].setTile(new Tile());
                    }
                    tempXIndex++;
                }
                tempYIndex++;
                tempXIndex = 0;
            }
            scalar++;
            return generateQuadTree(tempNodes, width / 2, height / 2, scalar);
        }
    }
    
    public static void quadTreeRender(Graphics2D g2d, QuadTreeNode branch, int topLeftX, int topLeftY){
        AffineTransform transform;
        AffineTransform scaleTransform = new AffineTransform();
        if((branch.getTile().getTileType() == TileTypes.NULL)){
            transform = g2d.getTransform();
            scaleTransform.scale(branch.getRenderingScalar(), branch.getRenderingScalar());
            g2d.transform(scaleTransform);
            g2d.drawImage(branch.getTile().getTerrainSprite(), null, branch.getX(), branch.getY());
            g2d.setTransform(transform);
        }else{
            QuadTreeNode[] branches = branch.getBranches();
            for(int i = 0; i < 4; i++){
                //if(branches[i].getX() > (topLeftX * branches[i].getRenderingScalar()) && branches[i].getX() < ((topLeftX * 40) * branches[i].getRenderingScalar())){
                    //if(branches[i].getY() > (topLeftY * branches[i].getRenderingScalar()) && branches[i].getY() < ((topLeftY * 20) * branches[i].getRenderingScalar())){
                        quadTreeRender(g2d, branches[i], topLeftX, topLeftY);
                   //}
                //}
            }
        }
    }
}
