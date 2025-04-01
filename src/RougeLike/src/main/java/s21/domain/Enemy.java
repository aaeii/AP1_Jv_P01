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


    public void moveSnake(Position playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int newX = x, newY = y;
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        Random random = new Random();
        int randomNumber = 0;
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            int playerX = playerPosition.getX();
            int playerY = playerPosition.getY();
            if (x < playerX) {
                x++;
            } else if (x > playerX) {
                x--;
            }
            if (y < playerY) {
                y++;
            } else if (y > playerY) {
                y--;
            }
            if (isValidMove(x, y, top_left, bot_right)) {
                position.setNew(x, y, false);
//                System.out.println("snake: (" + x + ", " + y + ")");
            }
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

    public void moveVampire(Position playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            int playerX = playerPosition.getX();
            int playerY = playerPosition.getY();
            if (x < playerX) {
                x++;
            } else if (x > playerX) {
                x--;
            } else if (y < playerY) {
                y++;
            } else if (y > playerY) {
                y--;
            }
            if (isValidMove(x, y, top_left, bot_right)) {
                position.setNew(x, y, false);
            }
        }
    }

    public void moveZombie(Position playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int newX = x, newY = y;
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            int playerX = playerPosition.getX();
            int playerY = playerPosition.getY();
            if (x < playerX) {
                x++;
            } else if (x > playerX) {
                x--;
            } else if (y < playerY) {
                y++;
            } else if (y > playerY) {
                y--;
            }
            if (isValidMove(x, y, top_left, bot_right)) {
                position.setNew(x, y, false);
//                System.out.println("zombie: (" + x + ", " + y + ")");
            }
        } else {
            for (int i = 0; i < 4; i++) {
                switch (initialDirection) {
                    case TOP:
                        y = y - 1;
                        break;
                    case BOTTOM:
                        y = y + 1;
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
            }
        }
    }

    private int getDistanceForPursuit(int hostilityLevel) {
        return switch (hostilityLevel) {
            case HIGH_LVL -> 4;
            case MEDIUM_LVL -> 3;
            case LOW_LVL -> 1;
            default -> 0;
        };
    }


    private boolean isValidMove(int x, int y, Position top_left, Position bot_right) {
        return (x > top_left.getX() && x < bot_right.getX()
                && y > top_left.getY() && y < bot_right.getY());
    }

    public void moveOgre(Position playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        int x = position.getX();
        int y = position.getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            int playerX = playerPosition.getX();
            int playerY = playerPosition.getY();
            if (x < playerX) {
                x = x + 2;
            } else if (x > playerX) {
                x = x - 2;
            } else if (y < playerY) {
                y = y + 2;
            } else if (y > playerY) {
                y = y - 2;
            }
            if (isValidMove(x, y, top_left, bot_right)) {
                position.setNew(x, y, false);
//                System.out.println("ogr: (" + x + ", " + y + ")");
            }
        }
    }


    public void moveGhost(Position playerPosition, Position top_left, Position bot_right, int hostilityLevel) {
        Random random = new Random();
        boolean visible = Math.random() > 0.5;
        int x = position.getX();
        int y = position.getY();
        int newX = x;
        int newY = y;

        int distanceToPlayer = Math.abs(x - playerPosition.getX()) + Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayer <= distanceForPursuit) {
            if (x < playerPosition.getX()) {
                x++;
            } else if (x > playerPosition.getX()) {
                x--;
            }

            if (y < playerPosition.getY()) {
                y++;
            } else if (y > playerPosition.getY()) {
                y--;
            }
            if (isValidMove(x, y, top_left, bot_right)) {
                position.setNew(x, y, visible);
            }
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
                    position.setNew(x, newY, visible);
                }

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


    public void move(Position player_pos, Position top_left, Position bot_right) {
        switch (type) {
            case ZOMBIE -> moveZombie(player_pos, top_left, bot_right, getHostility());
            case GHOST -> moveGhost(player_pos, top_left, bot_right, getHostility());
            case OGRE -> moveOgre(player_pos, top_left, bot_right, getHostility());
            case VAMPIRE -> moveVampire(player_pos, top_left, bot_right, getHostility());
            case SNAKE -> moveSnake(player_pos, top_left, bot_right, getHostility());
        }
    }

}

