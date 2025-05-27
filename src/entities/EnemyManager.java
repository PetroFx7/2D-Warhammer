package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import objects.Projectile;
import utilz.LoadSave;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] thornbackArr;
    private ArrayList<Thornback> thornbacks = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    public void addEnemies() {
        thornbacks = LoadSave.GetThornB(); // виправлено назву методу
    }

    public void update(int[][] lvlData, Player player) {
        for (Thornback t : thornbacks) {
            if (t.isAlive()) {
                t.update(lvlData, player);
            }else {
                t.updateAnimationTick(player);
            }
        }
        thornbacks.removeIf(e -> !e.isAlive() && e.isReadyToDelete());

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawThornB(g, xLvlOffset);
    }

    private void drawThornB(Graphics g, int xLvlOffset) {
        for (Thornback t : thornbacks) {
            int x = (int) t.getHitbox().x - xLvlOffset;
            int y = (int) t.getHitbox().y;
            int width = THORNBACK_WIDTH;
            int height = THORNBACK_HEIGHT;

            BufferedImage img = thornbackArr[t.getEnemyState()][t.getAniIndex()];

            if (t.getWalkDir() == LEFT) {
                g.drawImage(img, x, y, width, height, null);
            } else {
                g.drawImage(img, x + width, y, -width, height, null);
            }
        }
    }





    public void checkProjectileHit(ArrayList<Projectile> projectiles) {
        for (Enemy e : thornbacks) {
            for (Projectile p : projectiles) {
                if (p.isActive() && e.isAlive() && p.getHitBox().intersects(e.getHitbox())) {
                    e.hurt(20);
                    p.setActive(false);
                    break;
                }
            }
        }
    }

    private void loadEnemyImgs() {
        thornbackArr = new BufferedImage[4][4];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.FOUR_LEGS_ENEMY); // ймовірно цей спрайт
        for (int j = 0; j < thornbackArr.length; j++) {
            for (int i = 0; i < thornbackArr[j].length; i++) {
                thornbackArr[j][i] = temp.getSubimage(
                        i * THORNBACK_WIDTH_DEFAULT,
                        j * THORNBACK_HEIGHT_DEFAULT,
                        THORNBACK_WIDTH_DEFAULT,
                        THORNBACK_HEIGHT_DEFAULT
                );
            }
        }
    }
}
