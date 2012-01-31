import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/30/12
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaterTile extends Tile{

    public WaterTile(String path) {
        super(false, false);
        try {
            BufferedImage sprite = ImageIO.read(new File(path));
            this.setTerrainSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
