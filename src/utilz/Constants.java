package utilz;


import main.Game;

public class Constants {

    public static class Environment {
        public static final int MOON_WIDTH_DEFAULT = 108;
        public static final int MOON_HEIGHT_DEFAULT = 108;

        public static final int MOON_WIDTH = (int) (MOON_WIDTH_DEFAULT * Game.SCALE);
        public static final int MOON_HEIGHT = (int) (MOON_HEIGHT_DEFAULT * Game.SCALE);

    }


    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }
        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }

    }

    public static class Projectiles{
        public static final int BULLET_DEFAULT_WIDTH = 15;
        public static final int BULLET_DEFAULT_HEIGHT = 15;

        public static final int BULLET_WIDTH = (int)(Game.SCALE * BULLET_DEFAULT_WIDTH);
        public static final int BULLET_HEIGHT = (int)(Game.SCALE * BULLET_DEFAULT_HEIGHT);
        public static float SPEED = 2.2f * Game.SCALE;
    }

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
                case ATTACK:
                    return 2;
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