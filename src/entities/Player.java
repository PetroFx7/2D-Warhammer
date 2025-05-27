package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Gamestates;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
    // Анімація
    private BufferedImage[][] animations;
    private int aniTick, aniIndex;
    private int aniSpeed = 16;
    private int playerAction = IDLE;

    // Стан
    private boolean moving = false;
    private boolean attacking = false;
    private boolean inAir = false;

    // Керування
    private boolean left, right, up, down, jump;

    // Фізика
    private float playerSpeed = 2.4f;
    private float airSpeed = 0f;
    private float gravity = 0.05f * Game.SCALE;
    private float jumpSpeed = -2.6f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.05f * Game.SCALE;

    // Малювання
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 21 * Game.SCALE;

    // Рівень
    private int[][] lvlData;

    // Інше
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private boolean alive = true;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);

    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();


    }

    public void render(Graphics g, int lvlOffset) {
        if (left) {
            g.drawImage(animations[playerAction][aniIndex],
                    (int)(hitbox.x - xDrawOffset) - lvlOffset + width, (int)(hitbox.y - yDrawOffset),
                    -width, height, null);
        } else {
            g.drawImage(animations[playerAction][aniIndex],
                    (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset),
                    width, height, null);
        }
//        drawHitbox(g, lvlOffset);


    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;

            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if (attacking)
            playerAction = ATTACK;

        if (startAni != playerAction)
            resetAniTick();
    }


    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (IsEntityOnFloor(hitbox, lvlData) && airSpeed >= 0) {
            resetInAir();
        } else {
            inAir = true;
        }


        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }

    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[5][5];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);

    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;

    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void hurt(int damage) {
        if (!alive)
            return;

        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            die();
        }
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private void die() {
        alive = false;
        Gamestates.state = Gamestates.GAME_OVER; // Зупиняємо гру
    }



    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public float getX() {
        return hitbox.x;
    }

    public float getY() {
        return hitbox.y;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isAlive() {
        return alive;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}