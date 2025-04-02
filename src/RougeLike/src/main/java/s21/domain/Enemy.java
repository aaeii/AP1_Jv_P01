package s21.domain;

import s21.domain.Entity;
import s21.domain.Position;

import java.util.Random;

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

    private int initialDirection = TOP;

    /**
     * Движение по диагонали
     */
    public void moveSnake(Position player_pos, Position  top_left, Position bot_right) {
        int x = position.getX();
        int y = position.getY();
        int newX = x, newY = y;
        Random random = new Random();
        int randomNumber = 0;
        for (int i = 0; i < 4; i++) {
            switch (initialDirection) {
                case TOP:
                    randomNumber = random.nextInt(1 + 1);
                    newY = y - 1;
                    newX = randomNumber == 0 ? x + 1 : x - 1;
                    break;
                case RIGHT:
                    newX = x + 1;
                    randomNumber = random.nextInt(1 + 1);
                    newY = randomNumber == 0 ? y + 1 : y - 1;
                    break;
                case BOTTOM:
                    newY = y + 1;
                    randomNumber = random.nextInt(1 + 1);
                    newX = randomNumber == 0 ? x + 1 : x - 1;
                    break;
                case LEFT:
                    randomNumber = random.nextInt(1 + 1);
                    newX = x - 1;
                    newY = randomNumber == 0 ? y + 1 : y - 1;
                    break;
                default:
                    return;
            }
//            System.out.println("dir=" + initialDirection);
            if (newX > top_left.getX() && newX < bot_right.getX()
                    && newY > top_left.getY() && newY < bot_right.getY())
            {
                position.setNew(newX, newY, false);

            } else {
                initialDirection = (initialDirection + 1) % 4;
            }
        }
    }

    /**
     * Движение по вверх-вниз
     */
    public void moveZombie(Position player_pos, Position  top_left, Position bot_right) {
        int x = position.getX();
        int y = position.getY();
        int newX = x, newY = y;
        for (int i = 0; i < 4; i++) {
            switch (initialDirection) {
                case TOP:
                    newY = y - 1;
                    break;

                case BOTTOM:
                    newY = y + 1;
                    break;
                default:
                    return;
            }
            if (newX > top_left.getX() && newX < bot_right.getX()
                    && newY > top_left.getY() && newY < bot_right.getY()) {
                position.setNew(newX, newY, false);

            } else {
                initialDirection = (initialDirection + 2) % 4;
            }
        }
    }

    /**
     * Движение по кругу
     */
    public void moveOgre(Position player_pos, Position  top_left, Position bot_right) {

        int x = position.getX();
        int y = position.getY();
        int newX, newY;
        for (int i = 0; i < 4; i++) {
            switch (initialDirection) {
                case TOP:
                    newX = x;
                    newY = y - 2;
                    break;
                case RIGHT:
                    newX = x + 2;
                    newY = y;
                    break;
                case BOTTOM:
                    newX = x;
                    newY = y + 2;
                    break;
                case LEFT:
                    newX = x - 2;
                    newY = y;
                    break;
                default:
                    return;
            }
//            System.out.println("dir=" + initialDirection);
            if (newX > top_left.getX() && newX < bot_right.getX()
                    && newY > top_left.getY() && newY < bot_right.getY()) {
                position.setNew(newX, newY, false);

            } else {
                initialDirection = (initialDirection + 1) % 4;
            }
        }
    }

    /**
     * Рандомное передвижение
     */
    public void moveGhost(Position player_pos, Position  top_left, Position bot_right) {
        int x = position.getX();
        int y = position.getY();
        int newX = x, newY = y;
        Random random = new Random();
        boolean visible = Math.random() > 0.5;;
            for (int i = 0; i < 4; i++) {
                int direction = random.nextInt(4);
                switch (direction) {
                    case TOP:
                        newY = y - 1;
                        newX = x;
                        break;
                    case RIGHT:
                        newX = x + 1;
                        newY = y;
                        break;
                    case BOTTOM:
                        newY = y + 1;
                        newX = x;
                        break;
                    case LEFT:
                        newX = x - 1;
                        newY = y;
                        break;
                    default:
                        return;
                }
//                System.out.println("dir=" + direction);
                if (newX > top_left.getX() && newX < bot_right.getX()
                        && newY > top_left.getY() && newY < bot_right.getY()) {
                    position.setNew(newX, newY, visible);

                }
        }
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
    
    @Override
    public void action(Position player_pos, Position  top_left, Position bot_right){
        switch (type){
            case ZOMBIE -> moveZombie(player_pos, top_left, bot_right);
            case GHOST -> moveGhost(player_pos, top_left, bot_right);
            case OGRE -> moveOgre(player_pos, top_left, bot_right);
//            case VAMPIRE -> moveVampire(player_pos, top_left, bot_right);
            case SNAKE -> moveSnake(player_pos, top_left, bot_right);

        }
    }


}



