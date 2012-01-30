import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: 1/28/12
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseUnit {
    protected int health;
    protected int attackPower;
    protected Facing facingDirection;
    protected BufferedImage sprite;
    protected int xPosition;
    protected int yPosition;

    protected BaseUnit(int health, int attackPower, Facing facingDirection, int xPosition, BufferedImage sprite, int yPosition) {
        this.health = health;
        this.attackPower = attackPower;
        this.facingDirection = facingDirection;
        this.xPosition = xPosition;
        this.sprite = sprite;
        this.yPosition = yPosition;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public Facing getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Facing facingDirection) {
        this.facingDirection = facingDirection;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

}
