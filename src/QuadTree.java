import javax.imageio.ImageIO;
import java.awt.*;
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
    private static QuadTreeNode selectedNode;

    /**
     * The basic sprites are loaded, to save memory. These sprites are used to render the world
     */
    public static void setupSprites(){
        try{
            waterSprite = toCompatibleImage(ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/waterSprite.png")));
            grassSprite = toCompatibleImage(ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/grassSprite.png")));
            treeSprite = toCompatibleImage(ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/treeSprite.png")));
            mountainSprite = toCompatibleImage(ImageIO.read(new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/mountainSprite.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Optimizes BufferedImage for current Context
     * Borrowed from http://stackoverflow.com/questions/196890/java2d-performance-issues, Consty's solution
     * @param image The input image
     * @return the output image
     */
    private static BufferedImage toCompatibleImage(BufferedImage image)
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }

    /**
     * Takes a given 2 dimensional array of nodes at the lowest level and fills them into a Quad tree structure
     * @param nodes The input nodes
     * @param width The tree width
     * @param height The tree height
     * @return Returns 3 base nodes, which are the most compressed nodes of the list. Essentially 3 unique roots
     */
    public static QuadTreeNode[][] generateQuadTree(QuadTreeNode[][] nodes, int width, int height){
        if(width == 3 && height == 1){
            return nodes;
        }else{
            // Next level up the has a fourth the number of nodes as the level below
            QuadTreeNode[][] tempNodes = new QuadTreeNode[(width / 2)][(height / 2)];
            int tempXIndex = 0;
            int tempYIndex = 0;
            for(int y = 0; y < height - 1; y += 2){
                for(int x = 0; x < width - 1; x += 2){
                    tempNodes[tempXIndex][tempYIndex] = new QuadTreeNode(nodes[x][y], nodes[x+1][y], nodes[x][y+1], nodes[x+1][y+1], nodes[x][y].getWidth() * 2, nodes[x][y].getHeight() * 2, nodes[x][y].getX(), nodes[x][y].getY());
                    // If all the branches are identical, then the node can be simplified to the same type as it's constituents
                    if(tempNodes[tempXIndex][tempYIndex].checkIdenticalBranches()){
                        tempNodes[tempXIndex][tempYIndex].setTile(nodes[x][y].getTile());
                    }else{
                        // If branches are not identical, the Null type tells the renderer there are unique branches
                        tempNodes[tempXIndex][tempYIndex].setTile(TileTypes.NULL);
                    }
                    tempXIndex++;
                }
                tempYIndex++;
                tempXIndex = 0;
            }
            // Recursively runs the method with the next level of tree
            return generateQuadTree(tempNodes, width / 2, height / 2);
        }
    }

    /**
     * Renders the quad tree, compressing the process into as few nodes as possible
     * @param g2d The rendering graphics object
     * @param branch The root branch for rendering
     * @param topLeftX The top left x coordinate, for panning
     * @param topLeftY The top left y coordinate, for panning
     */
    public static void quadTreeRender(Graphics2D g2d, QuadTreeNode branch, int topLeftX, int topLeftY){
        if(!(branch.getTile() == TileTypes.NULL)){
            switch(branch.getTile()){
                case Water:
                    g2d.drawImage(waterSprite, branch.getX() - topLeftX, branch.getY() - topLeftY, branch.getWidth(), branch.getHeight(), null);
                    break;
                case Grass:
                    g2d.drawImage(grassSprite, branch.getX() - topLeftX, branch.getY() - topLeftY, branch.getWidth(), branch.getHeight(), null);
                    break;
                case Trees:
                    g2d.drawImage(treeSprite, branch.getX() - topLeftX, branch.getY() - topLeftY, branch.getWidth(), branch.getHeight(), null);
                    break;
                case Mountains:
                    g2d.drawImage(mountainSprite, branch.getX() - topLeftX, branch.getY() - topLeftY, branch.getWidth(), branch.getHeight(), null);
                    break;
            }
        }else{
            try {
                QuadTreeNode[] branches = branch.getBranches();
                for(int i = 0; i < 4; i++){
                    if((branches[i].getX() - topLeftX) < (800) && (branches[i].getX() + branches[i].getWidth() - topLeftX) >= 0){
                        if((branches[i].getY() - topLeftY) < (400) && (branches[i].getY() + branches[i].getHeight() - topLeftY) >= 0){
                            quadTreeRender(g2d, branches[i], topLeftX, topLeftY);
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        if(selectedNode != null){
            g2d.setColor(Color.green);
            g2d.fillRect(selectedNode.getX() - topLeftX, selectedNode.getY() - topLeftY, 20, 20);
        }
    }

    /**
     * Barrels down through branch to attempt to find the cell the user selected
     * @param branch The root node
     * @param mouseX The mouse x coord
     * @param mouseY The mouse y coord
     */
    public static void selectTile(QuadTreeNode branch, int mouseX, int mouseY){
        // If the width and height = 20, then a leaf has been found, so check if it's the right one
        if(branch.getWidth() == 20 && branch.getHeight() == 20){
            if(mouseX >= branch.getX() && mouseX <= (branch.getX() + branch.getWidth())){
                if(mouseY >= branch.getY() && mouseY <= (branch.getY() + branch.getHeight())){
                    selectedNode = branch;
                }
            }
        }else{
            // Still in a branch, so find sub branches and recurse with appropriate sub branch
            try{
                QuadTreeNode[] branches = branch.getBranches();
                for(int i = 0; i < 4; i++){
                    if(mouseX >= branches[i].getX() && mouseX <= (branches[i].getX() + branches[i].getWidth())){
                        if(mouseY >= branches[i].getY() && mouseY <= (branches[i].getY() + branches[i].getHeight())){
                            selectTile(branches[i], mouseX, mouseY);
                        }
                    }
                }
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }
    
    public static void changeCell(QuadTreeNode branch, TileTypes newType){
        if(branch.getWidth() == 20 && branch.getHeight() == 20){
            branch.setTile(newType);
        }else{
            try{
                QuadTreeNode[] branches = branch.getBranches();
                for(int i = 0; i < 4; i++){
                    if(selectedNode.getX() >= branches[i].getX() && selectedNode.getX() < (branches[i].getX() + branches[i].getWidth())){
                        if(selectedNode.getY() >= branches[i].getY() && selectedNode.getY() < (branches[i].getY() + branches[i].getHeight())){
                            if(branches[i].getTile() != TileTypes.NULL){
                                branches[i].setTile(TileTypes.NULL);
                                changeCell(branches[i], newType);
                            }else{
                                changeCell(branches[i], newType);
                            }
                        }
                    }
                }
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Compresses cells around the selected cell, but only up 1 level. This can probably be optimized
     * @param branch The branch being compressed
     */
    public static void compressCells(QuadTreeNode branch){
        if(branch.checkIdenticalBranches()){
            branch.setTile(branch.getBranchType());
        }else{
            try{
                QuadTreeNode[] branches = branch.getBranches();
                for(int i = 0; i < 4; i++){
                    if(selectedNode.getX() >= branches[i].getX() && selectedNode.getX() < (branches[i].getX() + branches[i].getWidth())){
                        if(selectedNode.getY() >= branches[i].getY() && selectedNode.getY() < (branches[i].getY() + branches[i].getHeight())){
                            compressCells(branches[i]);
                        }
                    }
                }
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    public static TileTypes getTileType(QuadTreeNode branch, int desiredX, int desiredY){
        // If the width and height = 20, then a leaf has been found, so check if it's the right one
        if(branch.getWidth() == 20 && branch.getHeight() == 20){
            if(desiredX >= branch.getX() && desiredX <= (branch.getX() + branch.getWidth())){
                if(desiredY >= branch.getY() && desiredY <= (branch.getY() + branch.getHeight())){
                    return branch.getTile();
                }
            }
        }else{
            // Still in a branch, so find sub branches and recurse with appropriate sub branch
            try{
                QuadTreeNode[] branches = branch.getBranches();
                for(int i = 0; i < 4; i++){
                    if(desiredX >= branches[i].getX() && desiredX <= (branches[i].getX() + branches[i].getWidth())){
                        if(desiredY >= branches[i].getY() && desiredY <= (branches[i].getY() + branches[i].getHeight())){
                            selectTile(branches[i], desiredX, desiredY);
                        }
                    }
                }
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
        return TileTypes.NULL;
    }

    public static QuadTreeNode getSelectedNode(){
        return selectedNode;
    }
}
