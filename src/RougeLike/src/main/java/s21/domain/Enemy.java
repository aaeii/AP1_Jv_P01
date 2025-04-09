package s21.domain;


import java.util.Random;

import static s21.domain.GameConstants.*;

public class Enemy extends Entity {
    public static final int VERY_HIGH_LVL = 4;
    public static final int HIGH_LVL = 3;
    public static final int MEDIUM_LVL = 2;
    public static final int LOW_LVL = 1;
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

    private int getDistanceForPursuit(int hostilityLevel) {
        return switch (hostilityLevel) {
            case HIGH_LVL -> 3;
            case MEDIUM_LVL -> 2;
            case LOW_LVL -> 1;
            default -> 0;
        };
    }

    private boolean isValidMove(int x, int y, Position top_left, Position bot_right) {
        return (x > top_left.getX() && x < bot_right.getX()
                && y > top_left.getY() && y < bot_right.getY());
    }

    public void moveSnake(Character playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.position.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.position.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        Random random = new Random();
        int randomNumber = 0;
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, top_left, bot_right, x, y, 1, checkHealth());
        } else {
            for (int i = 0; i < 4; i++) {
                switch (initialDirection) {
                    case TOP:
                        randomNumber = random.nextInt(1 + 1);
                        y = y - 1;
                        x = randomNumber == 0 ? x + 1 : x - 1;
                        break;
                    case RIGHT:
                        x = x + 1;
                        randomNumber = random.nextInt(1 + 1);
                        y = randomNumber == 0 ? y + 1 : y - 1;
                        break;
                    case BOTTOM:
                        y = y + 1;
                        randomNumber = random.nextInt(1 + 1);
                        x = randomNumber == 0 ? x + 1 : x - 1;
                        break;
                    case LEFT:
                        randomNumber = random.nextInt(1 + 1);
                        x = x - 1;
                        y = randomNumber == 0 ? y + 1 : y - 1;
                        break;
                    default:
                        return;
                }
                if (isValidMove(x, y, top_left, bot_right)) {
                    position.setNew(x, y, false);
//                    System.out.println("snake2: (" + x + ", " + y + ")");
                } else {
                    initialDirection = (initialDirection + 1) % 4;
                }
            }
        }
    }

    public void moveVampire(Character playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.position.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.position.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, top_left, bot_right, x, y, 1, checkHealth());
        }
    }

    public void moveZombie(Character playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.position.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.position.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, top_left, bot_right, x, y, 1, checkHealth());

        } else {
//            for (int i = 0; i < 4; i++) {
            switch (initialDirection) {
                case TOP:
                    y--;
                    break;
                case BOTTOM:
                    y++;
                    break;
                default:
                    return;
            }
            if (isValidMove(x, y, top_left, bot_right)) {
                position.setNew(x, y, false);
//                    System.out.println("zombie2: (" + x + ", " + y + ")");
            } else {
                initialDirection = (initialDirection + 2) % 4;
            }
//            }
        }
    }

    public void moveOgre(Character playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.position.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.position.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, top_left, bot_right, x, y, 2, checkHealth());
        }
    }


    public void moveGhost(Character playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        Random random = new Random();
        boolean visible = checkHealth() && Math.random() > 0.6;
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayer = Math.abs(x - playerPosition.position.getX()) + Math.abs(y - playerPosition.position.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayer <= distanceForPursuit) {
            moveToPlayer(playerPosition, top_left, bot_right, x, y, 1, visible);
        } else {
            for (int i = 0; i < 4; i++) {
                int direction = random.nextInt(4);
                switch (direction) {
                    case TOP:
                        y = y - 1;
                        x = x;
                        break;
                    case RIGHT:
                        x = x + 1;
                        y = y;
                        break;
                    case BOTTOM:
                        y = y + 1;
                        x = x;
                        break;
                    case LEFT:
                        x = x - 1;
                        y = y;
                        break;
                    default:
                        return;
                }
                if (isValidMove(x, y, top_left, bot_right)) {
                    position.setNew(x, y, visible);
                }

            }
        }

    }

    private void moveToPlayer(Character playerPosition, Position top_left, Position bot_right, int x, int y, int step, boolean visible) {
        if (x < playerPosition.position.getX()) {
            x = x + step;
        } else if (x > playerPosition.position.getX()) {
            x = x - step;
        } else if (y < playerPosition.position.getY()) {
            y = y + step;
        } else if (y > playerPosition.position.getY()) {
            y = y - step;
        }
        if (isValidMove(x, y, top_left, bot_right)) {
            position.setNew(x, y, visible);
        }
    }


    public void move(Character player_pos, Position top_left, Position bot_right) {
        switch (type) {
            case ZOMBIE -> moveZombie(player_pos, top_left, bot_right, getHostility());
            case GHOST -> moveGhost(player_pos, top_left, bot_right, getHostility());
            case OGRE -> moveOgre(player_pos, top_left, bot_right, getHostility());
            case VAMPIRE -> moveVampire(player_pos, top_left, bot_right, getHostility());
            case SNAKE -> moveSnake(player_pos, top_left, bot_right, getHostility());
        }
//        attack(player_pos);
    }
    private boolean checkHealth(){
        return getHealth() <= 0;
    }

}

