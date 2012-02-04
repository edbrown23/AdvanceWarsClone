import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/2/12
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuadTree {
    private static BufferedImage waterSprite;
    private static BufferedImage grassSprite;
    private static BufferedImage treeSprite;
    private static BufferedImage mountainSprite;

    public static void setupSprites(){
        try{
            waterSprite = ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/waterSprite.png"));
            grassSprite = ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/grassSprite.png"));
            treeSprite = ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/treeSprite.png"));
            mountainSprite = ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/mountainSprite.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static QuadTreeNode[][] generateQuadTree(QuadTreeNode[][] nodes, int width, int height){
        int multiplier = 10;
        if(width == 2 && height == 1){
            return nodes;
        }else{
            QuadTreeNode[][] tempNodes = new QuadTreeNode[(width / 2)][(height / 2)];
            int tempXIndex = 0;
            int tempYIndex = 0;
            for(int y = 0; y < height - 1; y += 2){
                for(int x = 0; x < width - 1; x += 2){
                    tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], nodes[x][y].getWidth() * 2, nodes[x][y].getHeight() * 2, nodes[x][y].getX(), nodes[x][y].getY());
                    if(tempNodes[tempXIndex][tempYIndex].checkIdenticalBranches()){
                        tempNodes[tempXIndex][tempYIndex].setTile(nodes[x][y].getTile());
                    }else{
                        System.out.println("Assigning Null Tile");
                        tempNodes[tempXIndex][tempYIndex].setTile(TileTypes.NULL);
                    }
                    tempXIndex++;
                }
                tempYIndex++;
                tempXIndex = 0;
            }
            return generateQuadTree(tempNodes, width / 2, height / 2);
        }
    }

    public static void quadTreeRender(Graphics2D g2d, QuadTreeNode branch, int topLeftX, int topLeftY){
        if(!(branch.getTile() == TileTypes.NULL)){
            switch(branch.getTile()){
                case Water:
                    g2d.drawImage(waterSprite, branch.getX(), branch.getY(), branch.getWidth(), branch.getHeight(), null);
                    break;
                case Grass:
                    g2d.drawImage(grassSprite, branch.getX(), branch.getY(), branch.getWidth(), branch.getHeight(), null);
                    break;
                case Trees:
                    g2d.drawImage(treeSprite, branch.getX(), branch.getY(), branch.getWidth(), branch.getHeight(), null);
                    break;
                case Mountains:
                    g2d.drawImage(mountainSprite, branch.getX(), branch.getY(), branch.getWidth(), branch.getHeight(), null);
                    break;
            }
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
