package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.*;

import main.Game;

public abstract class Enemy extends Entity {
    // Анімація
    protected int aniTick, aniIndex, aniSpeed = 25;

    // Стан
    protected int enemyState;
    protected int enemyType;
    protected boolean alive = true;
    private boolean doRemove = false;

    // Фізика
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.9f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;

    // Параметри атаки
    protected float attackDistance = Game.TILES_SIZE;

    // Інше
    protected boolean firstUpdate = true;
    private int health = 100;
    protected boolean hasDealtDamage = false;


    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    // ======= Update логіка =======

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = (walkDir == LEFT) ? -walkSpeed : walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)
                && IsFloor(hitbox, xSpeed, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            changeWalkDir();
        }
    }

    // ======= Атака та гравець =======

    protected void turnTowardsPlayer(Player player) {
        walkDir = (player.hitbox.x > hitbox.x) ? RIGHT : LEFT;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        return (playerTileY == tileY)
                && isPlayerInRange(player)
                && IsSightClear(lvlData, hitbox, player.hitbox, tileY);
    }

    protected boolean isPlayerInRange(Player player) {
        return Math.abs(player.hitbox.x - hitbox.x) <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        return Math.abs(player.hitbox.x - hitbox.x) <= attackDistance;
    }

    // ======= Анімація =======

    protected void updateAnimationTick(Player player) {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            int maxFrames = GetSpriteAmount(enemyType, enemyState);

            // Пошкодження під час атаки
            if (enemyState == ATTACK && aniIndex == 2 && !hasDealtDamage) {
                if (hitbox.intersects(player.getHitbox())) {
                    player.hurt(20); // Скільки завгодно шкоди
                    hasDealtDamage = true;
                }
            }

            if (aniIndex >= maxFrames) {
                switch (enemyState) {
                    case ATTACK -> {
                        aniIndex = 0;
                        enemyState = IDLE;
                        hasDealtDamage = false; // Скидання після завершення атаки
                    }
                    case DEAD -> aniIndex = maxFrames - 1;
                    default -> aniIndex = 0;
                }
            }
        }
    }


    protected void newState(int state) {
        this.enemyState = state;
        aniTick = 0;
        aniIndex = 0;
    }

    // ======= Статус =======

    public void hurt(int damage) {
        if (!alive) return;

        health -= damage;
        if (health <= 0) die();
    }

    public void die() {
        alive = false;
        newState(DEAD);
    }

    protected void changeWalkDir() {
        walkDir = (walkDir == LEFT) ? RIGHT : LEFT;
    }

    // ======= Get-методи =======

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getWalkDir() {
        return walkDir;
    }
    public boolean isDoRemove() {
        return doRemove;
    }

    public void setDoRemove(boolean doRemove) {
        this.doRemove = doRemove;
    }


    public boolean isReadyToDelete() {
        return enemyState == DEAD && aniIndex >= GetSpriteAmount(enemyType, DEAD);
    }
}
