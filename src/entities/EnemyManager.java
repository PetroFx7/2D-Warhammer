package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;
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
        System.out.println("size of thornbacks: " + thornbacks.size());
    }

    public void update(int[][] lvlData) {
        for (Thornback t : thornbacks) {
            t.update(lvlData);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawThornB(g, xLvlOffset);
    }

    private void drawThornB(Graphics g, int xLvlOffset) {
        for (Thornback t : thornbacks) {
            g.drawImage(
                    thornbackArr[t.getEnemyState()][t.getAniIndex()],
                    (int) t.getHitbox().x - xLvlOffset,
                    (int) t.getHitbox().y,
                    THORNBACK_WIDTH, THORNBACK_HEIGHT, null
            );
        }
    }

    private void loadEnemyImgs() {
        thornbackArr = new BufferedImage[3][4];
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
