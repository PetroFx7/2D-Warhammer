package entities;

import main.Game;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 16;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down, jump;
    private float playerSpeed = 2.4f;
    private float airSpeed = 0f;
    private float gravity = 0.05f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.05f * Game.SCALE;
    private boolean inAir = false;
    private int [][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 21 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x,y,20*Game.SCALE,28*Game.SCALE);
    }

    //TODO add details to loadlvldata(inAir check)
    //TODO change height of the hitbox pixels

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset), (int)(hitbox.y-yDrawOffset), width, height, null);
        drawHitbox(g);
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
        aniIndex = 0;
        aniTick = 0;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir () {
        inAir = false;
        airSpeed = 0;
    }

    private void updatePos() {

        if (jump) {
            jump();
        }
        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        moving = false;
        if(!left && !right && !up && !down)
            return;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        float xSpeed = 0, ySpeed = 0;

        if (left && !right)
            xSpeed -= -playerSpeed;
        else if (right && !left)
            xSpeed = playerSpeed;

        if (up && !down)
            ySpeed = -playerSpeed;
         else if (down && !up)
            ySpeed = playerSpeed;


         if(CanMoveHere(hitbox.x+xSpeed,hitbox.y+ySpeed, hitbox.width, hitbox.height,lvlData)) {
        hitbox.x += xSpeed;
        hitbox.y += ySpeed;
        moving = true;
        }

//        if (!inAir) {
//            if (!IsEntityOnFloor(hitbox, lvlData)) {
//                inAir = true;
//            }
//        }

//        if (inAir) {
//			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
//				hitbox.y += airSpeed;
//				airSpeed += gravity;
//				updateXPos(xSpeed);
//			} else {
//				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
//				if (airSpeed > 0)
//					resetInAir();
//				else
//					airSpeed = fallSpeedAfterCollision;
//				updateXPos(xSpeed);
//			}
//
//		} else
//			updateXPos(xSpeed);
//		moving = true;
	}


//        public void updateXPos ( float XSpeed){
//            if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, lvlData)) {
//                hitbox.x += xSpeed;
//            } else {
//                hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
//            }
//        }

    private void loadAnimations () {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[3][5];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);

    }

    public void loadlvlData(int [][] lvlData) {
this.lvlData = lvlData;
    }

    public void resetDirBooleans () {
        left = right = up = down = false;
    }

    public void setAttacking ( boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft () {
        return left;
    }

    public void setLeft ( boolean left){
        this.left = left;
    }

    public boolean isRight () {
        return right;
    }

    public void setRight ( boolean right){
        this.right = right;
    }

    public boolean isUp () {
        return up;
    }

    public void setUp ( boolean up){
        this.up = up;
    }

    public boolean isDown () {
        return down;
    }

    public void setDown ( boolean down){
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}