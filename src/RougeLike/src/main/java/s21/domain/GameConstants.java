package s21.domain;

public class GameConstants {

    public static final int  UNINITIALIZED  = -1;

    public static final int CONNECTED = 0;
    public static final int NOT_CONNECTED = 1;
    public static final int MAP_HEIGHT = 24;
    public static final int MAP_WIDTH  = 80;
    public static final int ROOMS_PER_SIDE  = 3;
    public static final int SECTOR_WIDTH = MAP_WIDTH / ROOMS_PER_SIDE;
    public static final int SECTOR_HEIGHT = MAP_HEIGHT / ROOMS_PER_SIDE;
    public static final int MAX_ROOMS_NUMBER = ROOMS_PER_SIDE * ROOMS_PER_SIDE;
    public static final int MAX_CORRIDORS_NUMBER  = 12;
    public static final int CORNER_VERT_RANGE = (SECTOR_HEIGHT - 6) / 2; // для 3д графики
    public static final int CORNER_HOR_RANGE = (SECTOR_WIDTH - 6) / 2; // для 3д графики
    public static final double ROOM_CHANCE = 0.5;
    public static final double SPAWN_SET_CHANCE = 0.5;
    public static final int MAX_ENEMIES_PER_ROOM = 2;
    public static final int MAX_ITEMS_PER_ROOM = 2;
    public static final int MAX_ENTITIES_PER_ROOM = MAX_ENEMIES_PER_ROOM + MAX_ITEMS_PER_ROOM + 2;
    public static final int MAX_ENEMIES_TOTAL = MAX_ENEMIES_PER_ROOM * ROOMS_PER_SIDE * ROOMS_PER_SIDE;
    public static final int MAX_ITEMS_TOTAL = MAX_ITEMS_PER_ROOM * ROOMS_PER_SIDE * ROOMS_PER_SIDE;
    public static final int MAX_ENTITIES_TOTAL = MAX_ENEMIES_TOTAL + MAX_ITEMS_TOTAL + 2;
    public static final int ENEMY_POOL_LEN  = 26;
    public static final int ITEM_POOL_LEN  = 5;

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    public static final int LEFT_TO_RIGHT_CORRIDOR = 0;
    public static final int LEFT_TURN_CORRIDOR = 1;
    public static final int RIGHT_TURN_CORRIDOR = 2;
    public static final int TOP_TO_BOTTOM_CORRIDOR = 3;

    public static final int UNOCCUPIED = 0;
    public static final int OCCUPIED = 1;

    public static final int  PLAYER = 0;
    public static final int PLAYER_CHAR = '@';
    public static final int ENEMY_CHAR = 'X';
    public static final int EXIT = 1;
    public static final int EXIT_CHAR  = '|';
    public static final int ITEM = 2;

    public static final int  WALL_CHAR = '#';
    public static final int CORRIDOR_CHAR = '+';
    public static final int CORRIDOR_CHAR2 = '1';


    public static final int  OUTER_AREA_CHAR = '.';
    public static final int  INNER_AREA_CHAR = ' ';
    public static final int EMPTY_CHAR = ' ';


    //TYPE OF ENEMIES
    public static final int ZOMBIE = 3;
    public static final int ZOMBIE_CHAR = 'Z';
    public static final int VAMPIRE = 4;
    public static final int VAMPIRE_CHAR = 'V';
    public static final int GHOST = 5;
    public static final int GHOST_CHAR = 'G';
    public static final int OGRE = 6;
    public static final int OGRE_CHAR = 'O';
    public static final int SNAKE = 7;
    public static final int SNAKE_CHAR = 'S';

//            #define IS_OUTER    0
//            #define IS_INNER    1
//            #define IS_WALL     2

}
