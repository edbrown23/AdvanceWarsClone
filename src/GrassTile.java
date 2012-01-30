import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/12
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class GrassTile extends Tile{

    // Same point as the Forest Tile. Refactor this business
    public GrassTile(String path){
        super(true, false);
        try {
            BufferedImage sprite = ImageIO.read(new File(path));
            this.setTerrainSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
