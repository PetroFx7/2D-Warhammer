package utilz;


import main.Game;

public class Constants {

    public static class Projectiles{
        public static final int BULLET_DEFAULT_WIDTH = 15;
        public static final int BULLET_DEFAULT_HEIGHT = 15;

        public static final int BULLET_WIDTH = (int)(Game.SCALE * BULLET_DEFAULT_WIDTH);
        public static final int BULLET_HEIGHT = (int)(Game.SCALE * BULLET_DEFAULT_HEIGHT);
        public static float SPEED = 0.5f * Game.SCALE;
    }

    //для анімації прижку та падіння використовуй значення IDLE
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int JUMP = 3;
        public static final int FALLING = 4;
//        public static final int GROUND = 4;
//        public static final int HIT = 5;
//        public static final int ATTACK_1 = 6;
//        public static final int ATTACK_JUMP_1 = 7;
//        public static final int ATTACK_JUMP_2 = 8;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 5;
                case IDLE:
                    return 5;
//                case HIT:
//                    return 4;
                case JUMP:
//                case ATTACK_1:
//                case ATTACK_JUMP_1:
//                case ATTACK_JUMP_2:
//                    return 3;
//                case GROUND:
//                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}