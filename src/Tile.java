import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/28/12
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tile {

    private boolean unitAccessible;
    private boolean blocksLineOfSight;
    private BufferedImage terrainSprite;

    public Tile(boolean unitAccessible, boolean blocksLineOfSight) {
        this.unitAccessible = unitAccessible;
        this.blocksLineOfSight = blocksLineOfSight;
    }

    public boolean isUnitAccessible() {
        return unitAccessible;
    }

    public void setUnitAccessible(boolean unitAccessible) {
        this.unitAccessible = unitAccessible;
    }

    public boolean isBlocksLineOfSight() {
        return blocksLineOfSight;
    }

    public void setBlocksLineOfSight(boolean blocksLineOfSight) {
        this.blocksLineOfSight = blocksLineOfSight;
    }

    public BufferedImage getTerrainSprite() {
        return terrainSprite;
    }

    public void setTerrainSprite(BufferedImage terrainSprite) {
        this.terrainSprite = terrainSprite;
    }
    
    public void render(Graphics2D g2d, int x, int y){
        AffineTransform transform = new AffineTransform();
        transform.scale(1, 1);
        //g2d.setTransform(transform);
        g2d.drawImage(terrainSprite, null, x, y);
    }
}
