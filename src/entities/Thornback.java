package entities;

import main.Game;

import static utilz.Constants.EnemyConstants.*;
import static entities.Enemy.*;

public class Thornback extends Enemy {

    public Thornback(float x, float y) {
        super(x, y, THORNBACK_WIDTH, THORNBACK_HEIGHT, THORNBACK);
        initHitbox(x, y, (int)(64 * Game.SCALE), (int)(64 * Game.SCALE));
        this.enemyState = RUNNING;
    }

    @Override
    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
        updateHitbox();
    }
}
