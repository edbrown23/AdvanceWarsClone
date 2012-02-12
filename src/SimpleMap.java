import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private int multiplier = 20;

    public SimpleMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
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
                }else if(grey > waterLevel && grey < grassLevel){
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Grass, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(0, 255, 0).getRGB());
                }else if(grey > grassLevel && grey < treeLevel){
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Trees, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(50, 150, 50).getRGB());
                }else {
                    baseTreeTiles[x][y] = new QuadTreeNode(new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), new QuadTreeNode(), TileTypes.Mountains, 20, 20, x * multiplier, y * multiplier);
                    debugImage.setRGB(x, y, new Color(150, 100, 25).getRGB());
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

    public void addUnit(BaseUnit newUnit){
        units.add(newUnit);
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

    public void render(Graphics2D g2d, int topLeftX, int topLeftY){
        for(int y = topLeftY; y < (topLeftY + 20); y++){
            for(int x = topLeftX; x < (topLeftX + 40); x++){
                //mapTiles[x][y].render(g2d, (x - topLeftX) * multiplier, (y - topLeftY) * multiplier);
            }
        }
    }


    public QuadTreeNode[][] getMapTreeRoots(){
        return mapTreeRoots;
    }
}
