import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/28/12
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tile {

    private boolean unitAccessable;
    private boolean blocksLineOfSight;
    private BufferedImage terrainSprite;

    public boolean isUnitAccessable() {
        return unitAccessable;
    }

    public void setUnitAccessable(boolean unitAccessable) {
        this.unitAccessable = unitAccessable;
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
}
