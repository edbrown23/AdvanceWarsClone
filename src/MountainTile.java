import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/30/12
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MountainTile extends Tile {

    public MountainTile(String path) {
        super(true, true);
        try {
            BufferedImage sprite = ImageIO.read(new File(path));

            for(int y = 0; y < sprite.getHeight(); y++){
                for(int x = 0; x < sprite.getWidth(); x++){
                    Color picColor = new Color(sprite.getRGB(x, y));
                    int r = picColor.getRed();
                    int g = picColor.getGreen();
                    int b = picColor.getBlue();
                }
            }
            this.setTerrainSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
