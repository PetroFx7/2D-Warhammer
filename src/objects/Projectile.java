package objects;

import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.Projectiles.*;

public class Projectile {
    private BufferedImage bulletImg;
    private Rectangle2D.Float hitBox;
    private int dir;
    private boolean active = true;

    public Projectile(int x, int y, int dir) {
        hitBox = new Rectangle2D.Float(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        this.dir = dir;
        bulletImg = LoadSave.GetSpriteAtlas(LoadSave.BULLET);

    }

    public void updatePos() {
        hitBox.x += dir * SPEED;
    }

    public void setPos(int x, int y) {
        hitBox.x = x;
        hitBox.y = y;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(bulletImg, (int) (hitBox.x - xLvlOffset), (int) hitBox.y, BULLET_WIDTH, BULLET_HEIGHT, null);
    }

}
