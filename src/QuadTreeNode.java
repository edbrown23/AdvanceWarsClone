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
    private Tile tile = new Tile();
    private int renderingScalar;
    private int x, y;
    
    public QuadTreeNode(QuadTreeNode upL, QuadTreeNode upR, QuadTreeNode botL, QuadTreeNode botR, Tile inputTile, int scalar, int x, int y){
        UL = upL;
        UR = upR;
        BL = botL;
        BR = botR;
        tile = inputTile;
        renderingScalar = scalar;
        this.x = x;
        this.y = y;
    }

    public QuadTreeNode(QuadTreeNode upL, QuadTreeNode upR, QuadTreeNode botL, QuadTreeNode botR, int scalar, int x, int y){
        UL = upL;
        UR = upR;
        BL = botL;
        BR = botR;
        renderingScalar = scalar;
        this.x = x;
        this.y = y;
    }
    
    public boolean checkIdenticalBranches(){
        // Better way to do this?
        if(UL.getTile().getTileType() == TileTypes.NULL){
            return false;
        }else if(UR.getTile().getTileType() == TileTypes.NULL){
            return false;
        }else if(BL.getTile().getTileType() == TileTypes.NULL){
            return false;
        }else if(BR.getTile().getTileType() == TileTypes.NULL){
            return false;
        }else{
            return true;
        }
//        TileTypes ulType = UL.getTile().getTileType();
//        TileTypes urType = UR.getTile().getTileType();
//        TileTypes blType = BL.getTile().getTileType();
//        TileTypes brType = BR.getTile().getTileType();
//        if(ulType == urType){
//            if(urType == blType){
//                if(blType == brType){
//                    return true;
//                }
//            }
//        }
//        return false;
    }
    
    public Tile getTile(){
        return tile;
    }
    
    public void setTile(Tile type){
        tile = type;
    }
    
    public QuadTreeNode[] getBranches(){
        QuadTreeNode[] branchArray = new QuadTreeNode[4];
        
        branchArray[0] = UL;
        branchArray[1] = UR;
        branchArray[2] = BL;
        branchArray[3] = BR;
        
        return branchArray;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getRenderingScalar(){
        return renderingScalar;
    }
}
