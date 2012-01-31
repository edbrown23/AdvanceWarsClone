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
            this.setTerrainSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
