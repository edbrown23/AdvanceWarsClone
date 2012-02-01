/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/1/12
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuadTreeNode {
    private QuadTreeNode UL;
    private QuadTreeNode UR;
    private QuadTreeNode BL;
    private QuadTreeNode BR;
    private Tile tile;
    private int renderingScalar;
    
    public QuadTreeNode(QuadTreeNode upL, QuadTreeNode upR, QuadTreeNode botL, QuadTreeNode botR, Tile inputTile, int scalar){
        UL = upL;
        UR = upR;
        BL = botL;
        BR = botR;
        tile = inputTile;
        renderingScalar = scalar;
    }

    public QuadTreeNode(QuadTreeNode upL, QuadTreeNode upR, QuadTreeNode botL, QuadTreeNode botR, int scalar){
        UL = upL;
        UR = upR;
        BL = botL;
        BR = botR;
        renderingScalar = scalar;
    }
    
    public boolean checkIdenticalBranches(){
        TileTypes ulType = UL.getTile().getTileType();
        TileTypes urType = UR.getTile().getTileType();
        TileTypes blType = BL.getTile().getTileType();
        TileTypes brType = BR.getTile().getTileType();
        if(ulType == urType){
            if(urType == blType){
                if(blType == brType){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Tile getTile(){
        return tile;
    }
    
    public void setTile(Tile type){
        tile = type;
    }
}
