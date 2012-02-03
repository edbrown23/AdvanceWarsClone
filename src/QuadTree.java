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
                    tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], scalar * 2, (nodes[x][y].getX() / scalar), nodes[x][y].getY() / scalar);
                    if(tempNodes[tempXIndex][tempYIndex].checkIdenticalBranches()){
                        tempNodes[tempXIndex][tempYIndex].setTile(nodes[x][y].getTile());
                    }else{
                        System.out.println("Assigning Null Tile");
                        tempNodes[tempXIndex][tempYIndex].setTile(new Tile());
                    }
                    tempXIndex++;
                }
                tempYIndex++;
                tempXIndex = 0;
            }
            return generateQuadTree(tempNodes, width / 2, height / 2, scalar * scalar);
        }
    }

    public static void quadTreeRender(Graphics2D g2d, QuadTreeNode branch, int topLeftX, int topLeftY){
        AffineTransform transform;
        AffineTransform scaleTransform = new AffineTransform();
        if(!(branch.getTile().getTileType() == TileTypes.NULL)){
            //System.out.println("Drawing NULL tile at " + branch.getX() + " " +  branch.getY());
            transform = g2d.getTransform();
            scaleTransform.scale(branch.getRenderingScalar(), branch.getRenderingScalar());
            //scaleTransform.scale(32, 32);
            g2d.transform(scaleTransform);
            QuadTreeNode[] branches = branch.getBranches();
//            for(int i = 0; i < 4; i++){
//                g2d.drawImage(branches[i].getTile().getTerrainSprite(), null, branches[i].getX(), branches[i].getY());
//            }
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
