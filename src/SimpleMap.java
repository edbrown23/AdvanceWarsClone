import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private Tile[][] mapTiles;
    private int multiplier = 10;

    public SimpleMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        mapTiles = new Tile[mapWidth][mapHeight];
    }

    public void createMapFromImage(String path){
        try{
        BufferedImage inputMap = ImageIO.read(new File(path));
        for(int y = 0; y < inputMap.getHeight(); y++){
            for(int x = 0; x < inputMap.getWidth(); x++){
                Color mapColor = new Color(inputMap.getRGB(x, y));
                int r = mapColor.getRed();
                int g = mapColor.getGreen();
                int b = mapColor.getBlue();
                if(g == 150 && r == 100){
                    mapTiles[x][y] = new ForestTile("treeSprite.png");
                }else if(g == 150){
                    mapTiles[x][y] = new GrassTile("grassSprite.png");
                }
            }
        }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void render(Graphics2D g2d){
        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){
                mapTiles[x][y].render(g2d, x * multiplier, y * multiplier);
            }
        }
    }
}
