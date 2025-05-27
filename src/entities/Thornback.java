package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Thornback extends Enemy {

    public Thornback(float x, float y) {
        super(x, y, THORNBACK_WIDTH, THORNBACK_HEIGHT, THORNBACK);
        initHitbox(x, y, (int)(64 * Game.SCALE), (int)(64 * Game.SCALE));
    }


    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick(player);

        if (enemyState == ATTACK && aniIndex == 2 && !hasDealtDamage) {
            if (hitbox.intersects(player.getHitbox())) {
                player.hurt(20); // Наприклад, 20 HP
                hasDealtDamage = true;
            }
        }
    }


    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(ATTACK);

                    move(lvlData);
                    break;
            }
        }

    }

}
