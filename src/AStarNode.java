/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 2/20/12
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class AStarNode {
    private int x;
    private int y;
    private int pointScale;
    public float G;
    public float H;
    public float F;
    public AStarNode parent;
    
    public AStarNode(int x, int y, int p){
        this.x = x;
        this.y = y;
        this.pointScale = p;
    }

    public int getPointScale(){
        return pointScale;
    }
    
    /**
     * Class stores x and y as real map coordinates. This method returns x and y divided by the map scale, so they can be used as indices in a reference array
     * @return x and y
     */
    public int[] getPointForIndex(){
        int[] temp = new int[2];
        temp[0] = x / pointScale;
        temp[1] = y / pointScale;
        return temp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object other){
        AStarNode a = (AStarNode)other;
        if(a.getX() == this.getX() && a.getY() == this.getY()){
            return true;
        }else{
            return false;
        }
    }
}
