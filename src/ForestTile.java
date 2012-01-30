import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Wrapper for forest tile, handles loading terrain sprite, makes things more organized
 * User: Eric
 * Date: 1/29/12
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForestTile extends Tile {
    
    // Specific tiles should probably load their own sprites. It's cleaner
    public ForestTile(String path){
        super(true, false);
        try {
            BufferedImage sprite = ImageIO.read(new File(path));

            for(int y = 0; y < sprite.getHeight(); y++){
                for(int x = 0; x < sprite.getWidth(); x++){
                    Color picColor = new Color(sprite.getRGB(x, y));
                    int r = picColor.getRed();
                    int g = picColor.getGreen();
                    int b = picColor.getBlue();
                    if(r == 255 && b == 255){
                       // sprite.setRGB(x, y, transparentColor.getRGB());
                    }
                }
            }
            this.setTerrainSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
