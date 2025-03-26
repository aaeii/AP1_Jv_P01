package s21.domain;

import static s21.domain.GameConstants.*;

public class Enemy extends Entity {
    public static final int VERY_HIGH_LVL = 100;
    public static final int HIGH_LVL = 75;
    public static final int MEDIUM_LVL = 50;
    public static final int LOW_LVL = 25;
    private int health;
    private int agility;
    private int strength;
    private int hostility;
    private int type;
    //    private char symbol;
    private int symbol;

    Position position;

    public Enemy(int type) {
        super();
        this.type = type;
        switch (type) {
            case ZOMBIE:
                this.health = HIGH_LVL;
                this.agility = LOW_LVL;
                this.strength = MEDIUM_LVL;
                this.hostility = MEDIUM_LVL;
                setSymbol(ZOMBIE_CHAR);
                break;
            case VAMPIRE:
                this.health = HIGH_LVL;
                this.agility = HIGH_LVL;
                this.strength = MEDIUM_LVL;
                this.hostility = HIGH_LVL;
                setSymbol(VAMPIRE_CHAR);
                break;
            case GHOST:
                this.health = LOW_LVL;
                this.agility = HIGH_LVL;
                this.strength = LOW_LVL;
                this.hostility = LOW_LVL;
                setSymbol(GHOST_CHAR);
                break;
            case OGRE:
                this.health = VERY_HIGH_LVL;
                this.agility = LOW_LVL;
                this.strength = VERY_HIGH_LVL;
                this.hostility = MEDIUM_LVL;
                setSymbol(OGRE_CHAR);
                break;
            case SNAKE:
                this.health = LOW_LVL;
                this.agility = VERY_HIGH_LVL;
                this.strength = LOW_LVL;
                this.hostility = HIGH_LVL;
                setSymbol(SNAKE_CHAR);
                break;
            default:
                break;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAgility() {
        return agility;
    }


    public int getStrength() {
        return strength;
    }

    public int getHostility() {
        return hostility;
    }

    public void attack(Character character) {
        switch (type) {
            case ZOMBIE:
                break;
            case VAMPIRE:
                break;
            case GHOST:
                break;
            case OGRE:
                break;
            case SNAKE:
                break;
            default:
                break;
        }
    }

    public void moveOgre(int[][] field, int direction) {
        int x = position.getX(), y = position.getY();
        int newX = x, newY = y;

        switch (direction) {
            case TOP:
                newY = y - 1;
                break;
            case RIGHT:
                newX = x + 1;
                break;
            case BOTTOM:
                newY = y + 1;
                break;
            case LEFT:
                newX = x - 1;
                break;
        }
        if (isWall(field, newX, newY)) {
            changeDirection(direction);
        } else {
            position.setNew(newX, newY);
        }
    }

    private boolean isWall(int[][] field, int x, int y) {
        if (y < 0 || y >= field.length || x < 0 || x >= field[0].length) {
            return true;
        }
        return field[y][x] == WALL_CHAR;
    }

    private void changeDirection(int direction) {
        switch (direction) {
            case TOP:
                direction = RIGHT;
                break;
            case RIGHT:
                direction = BOTTOM;
                break;
            case BOTTOM:
                direction = LEFT;
                break;
            case LEFT:
                direction = TOP;
                break;
        }
    }
}


