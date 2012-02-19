import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMap {
    private int mapWidth;
    private int mapHeight;
    private QuadTreeNode[][] mapTreeRoots = new QuadTreeNode[3][1];
    private LinkedList<BaseUnit> units = new LinkedList<BaseUnit>();
    private boolean[][] fog;
    private TileTypes[][] referenceTiles;
    private double timeOfDay;
    private int multiplier = 20;

    public SimpleMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        fog = new boolean[mapWidth][mapHeight];
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                fog[x][y] = true;
            }
        }
        referenceTiles = new TileTypes[mapWidth][mapHeight];
    }

    /**
     * Takes various parameters and creates a random perlin noise map based on them
     * @param waterLevel the top of the water threshold in the perlin heightmap
     * @param grassLevel the top of the grass threshold in the perlin heightmap
     * @param treeLevel the top of the tree threshold in the perlin heightmap
     * @param mountainLevel the top of the mountain threshold in the perlin heightmap
     */
    public void createMapFromPerlinNoise(int waterLevel, int grassLevel, int treeLevel, int mountainLevel){
        QuadTreeNode[][] baseTreeTiles =  new QuadTreeNode[mapWidth][mapHeight];;
        float[][] finalNoiseArray = PerlinNoise.GeneratePerlinNoise(mapWidth, mapHeight, 8);
        BufferedImage debugImage = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_4BYTE_ABGR);
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                int grey = (int)(255 * finalNoiseArray[x][y]);
                if(grey < waterLevel){
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Water, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(0, 0, 255).getRGB());
                    referenceTiles[x][y] = TileTypes.Water;
                }else if(grey > waterLevel && grey < grassLevel){
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Grass, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(0, 255, 0).getRGB());
                    referenceTiles[x][y] = TileTypes.Grass;
                }else if(grey > grassLevel && grey < treeLevel){
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Trees, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(50, 150, 50).getRGB());
                    referenceTiles[x][y] = TileTypes.Trees;
                }else {
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Mountains, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(150, 100, 25).getRGB());
                    referenceTiles[x][y] = TileTypes.Mountains;
                }
            }
        }
        try{
            ImageIO.write(debugImage, "png", new File("C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/perlinNoise.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        mapTreeRoots = QuadTree.generateQuadTree(baseTreeTiles, mapWidth, mapHeight);
    }

    public void render(Graphics2D g2d, int topLeftX, int topLeftY){
        if((mapTreeRoots[0][0].getX() - topLeftX) < (800) && (mapTreeRoots[0][0].getX() + mapTreeRoots[0][0].getWidth() - topLeftX) >= 0){
            if((mapTreeRoots[0][0].getY() - topLeftY) < (400) && (mapTreeRoots[0][0].getY() + mapTreeRoots[0][0].getHeight() - topLeftY) >= 0){
                QuadTree.quadTreeRender(g2d, mapTreeRoots[0][0], topLeftX, topLeftY);
            }
        }
        if((mapTreeRoots[1][0].getX() - topLeftX) < (800) && (mapTreeRoots[1][0].getX() + mapTreeRoots[1][0].getWidth() - topLeftX) >= 0){
            if((mapTreeRoots[1][0].getY() - topLeftY) < (400) && (mapTreeRoots[1][0].getY() + mapTreeRoots[1][0].getHeight() - topLeftY) >= 0){
                QuadTree.quadTreeRender(g2d, mapTreeRoots[1][0], topLeftX, topLeftY);
            }
        }
        if((mapTreeRoots[2][0].getX() - topLeftX) < (800) && (mapTreeRoots[2][0].getX() + mapTreeRoots[2][0].getWidth() - topLeftX) >= 0){
            if((mapTreeRoots[2][0].getY() - topLeftY) < (400) && (mapTreeRoots[2][0].getY() + mapTreeRoots[2][0].getHeight() - topLeftY) >= 0){
                QuadTree.quadTreeRender(g2d, mapTreeRoots[2][0], topLeftX, topLeftY);
            }
        }

        for(BaseUnit currentUnit : units){
            currentUnit.render(g2d, topLeftX, topLeftY);
        }

        this.calculateFog(topLeftX, topLeftY);
        this.renderFog(g2d, topLeftX, topLeftY);
    }

    public void renderSelection(Graphics2D g2d, int topLeftX, int topLeftY){
        QuadTree.renderSelection(g2d, topLeftX, topLeftY);
    }

    public void renderFog(Graphics2D g2d, int topLeftX, int topLeftY){
        int fogScale = (int)((Math.sin(timeOfDay) + 1) * 100);
        for(int y = topLeftY; y < (topLeftY + 400); y += 20){
            for(int x = topLeftX; x < (topLeftX + 800); x += 20){
                if(fog[Math.round(x / 20.0f)][Math.round(y / 20.0f)]){
                    g2d.setColor(new Color(0, 0, 0, fogScale));
                    g2d.fillRect(x - topLeftX, y - topLeftY, 20, 20);
                }else{
                    g2d.setColor(new Color(0, 255, 0, 0));
                    g2d.fillRect(x - topLeftX, y - topLeftY, 20, 20);
                }
            }
        }
    }

    public void addUnit(BaseUnit newUnit){
        units.add(newUnit);
    }

    public void calculateFog(int topLeftX, int topLeftY){
        for(int y = topLeftY; y < (topLeftY + 400); y += 20){
            for(int x = topLeftX; x < (topLeftX + 800); x += 20){
                if(!fog[Math.round(x / 20.0f)][Math.round(y / 20.0f)]){
                    fog[Math.round(x / 20.0f)][Math.round(y / 20.0f)] = true;
                }
            }
        }
        for(BaseUnit currentUnit : units){
            int radius = currentUnit.getVisionMax();
            for(float degree = 0; degree < 2.0f * Math.PI; degree += Math.PI / 32.0f){
                for(int dist = 0; dist < radius; dist++){
                    float x = (float)(Math.cos(degree) * dist * 20.0f);
                    float y = (float)(Math.sin(degree) * dist * 20.0f);
                    int refX = Math.round((currentUnit.getxPosition() + x) / 20);
                    int refY = Math.round((currentUnit.getyPosition() + y) / 20);
                    if(refX >= 0 && refY >= 0 && refX < 400 && refY < 200){
                        TileTypes temp = referenceTiles[refX][refY];
                        if(temp == TileTypes.Mountains || temp == TileTypes.Trees){
                            break;
                        }else{
                            fog[refX][refY] = false;
                        }
                    }
                }
            }
        }

    }

    public void changeCell(TileTypes newType){
        referenceTiles[QuadTree.getSelectedNode().getX() / 20][QuadTree.getSelectedNode().getY() / 20] = newType;
        QuadTree.changeCell(mapTreeRoots[0][0], newType);
        QuadTree.changeCell(mapTreeRoots[1][0], newType);
        QuadTree.changeCell(mapTreeRoots[2][0], newType);

        QuadTree.compressCells(mapTreeRoots[0][0]);
        QuadTree.compressCells(mapTreeRoots[1][0]);
        QuadTree.compressCells(mapTreeRoots[2][0]);

        // The following three repeats shouldn't be necessary, but it's a test. This can definitely be optimized
        QuadTree.compressCells(mapTreeRoots[0][0]);
        QuadTree.compressCells(mapTreeRoots[1][0]);
        QuadTree.compressCells(mapTreeRoots[2][0]);

        QuadTree.compressCells(mapTreeRoots[0][0]);
        QuadTree.compressCells(mapTreeRoots[1][0]);
        QuadTree.compressCells(mapTreeRoots[2][0]);

        QuadTree.compressCells(mapTreeRoots[0][0]);
        QuadTree.compressCells(mapTreeRoots[1][0]);
        QuadTree.compressCells(mapTreeRoots[2][0]);
    }

    public LinkedList<BaseUnit> getUnits(){
        return units;
    }

    // Deprecated Methods
    public void createMapFromImage(String path){
        try{
            BufferedImage inputMap = ImageIO.read(new File(path));
            for(int y = 0; y < mapHeight; y++){
                for(int x = 0; x < mapWidth; x++){
                    Color mapColor = new Color(inputMap.getRGB(x, y));
                    int r = mapColor.getRed();
                    int g = mapColor.getGreen();
                    int b = mapColor.getBlue();
                    if(g == 150 && r == 100){
                        //mapTiles[x][y] = new Tile(false, true, "C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/mountainSprite.png", TileTypes.Mountains);
                    }else if(g == 150){
                        //mapTiles[x][y] = new Tile(true, false, "C:/Users/Eric/Desktop/AdvanceWarsClone/Sprites/grassSprite.png", TileTypes.Grass);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public QuadTreeNode[][] getMapTreeRoots(){
        return mapTreeRoots;
    }
    
    public double getTimeOfDay(){
        return timeOfDay;
    }
    
    public void setTimeOfDay(double timeOfDay1){
        timeOfDay = timeOfDay1;
    }
}
