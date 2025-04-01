package s21.domain;

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
    private int type;

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

    private int initialDirection = TOP;

    public void moveSnake(char[][] field, Position enemyPosition, Position playerPosition, int hostilityLevel) {
        int x = enemyPosition.getX();
        int y = enemyPosition.getY();
        int newX = x, newY = y;
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            int playerX = playerPosition.getX();
            int playerY = playerPosition.getY();
            if (x < playerX) {
                newX++;
            } else if (x > playerX) {
                newX--;
            }
            if (y < playerY) {
                newY++;
            } else if (y > playerY) {
                newY--;
            }

            if (isValidMove(newX, newY, field)) {
                enemyPosition.setNew(newX, newY, false);
                System.out.println("snake: (" + newX + ", " + newY + ")");
            }
        }
    }

    public void moveVampire(char[][] field, Position enemy, Position player, int hostility) {
        moveZombie(field, enemy, player, hostility);
    }

    public void moveZombie(char[][] field, Position zombiePosition, Position playerPosition, int hostilityLevel) {
        int x = zombiePosition.getX();
        int y = zombiePosition.getY();
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

            if (isValidMove(x, y, field)) {
                zombiePosition.setNew(x, y, false);
                System.out.println("zombie: (" + x + ", " + y + ")");
            }
        }
    }

    private int getDistanceForPursuit(int hostilityLevel) {
        return switch (hostilityLevel) {
//            case VERY_HIGH_LVL -> 5;
            case HIGH_LVL -> 5;
            case MEDIUM_LVL -> 3;
            case LOW_LVL -> 1;
            default -> 0;
        };
    }


    private boolean isValidMove(int newX, int newY, char[][] field) {
        return field[newY][newX] != WALL_CHAR &&
                field[newY][newX] != OUTER_AREA_CHAR &&
                field[newY][newX] != CORRIDOR_CHAR;
    }

    public void moveOgre(char[][] field, Position enemyPosition, Position playerPosition, int hostilityLevel) {
        int x = enemyPosition.getX();
        int y = enemyPosition.getY();
        int newX = x, newY = y;
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

            if (isValidMove(x, y, field)) {
                enemyPosition.setNew(x, y, false);
                System.out.println("ogr: (" + x + ", " + y + ")");
            }
        }
    }


    public void moveGhost(char[][] field, Position ghostPosition, Position playerPosition, int hostilityLevel, int visibilityChance) {
        Random random = new Random();
        int x = ghostPosition.getX();
        int y = ghostPosition.getY();
        int newX = x;
        int newY = y;

        if (random.nextInt(100) < visibilityChance) {
            System.out.println("Призрак исчез");
        }

        int distanceToPlayer = Math.abs(x - playerPosition.getX()) + Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayer <= distanceForPursuit) {
            if (x < playerPosition.getX()) {
                newX++;
            } else if (x > playerPosition.getX()) {
                newX--;
            }

            if (y < playerPosition.getY()) {
                newY++;
            } else if (y > playerPosition.getY()) {
                newY--;
            }
        } else {
            newX = x + random.nextInt(3) - 1;
            newY = y + random.nextInt(3) - 1;
        }

        if (isValidMove(newX, newY, field)) {
            ghostPosition.setNew(newX, newY, false);
            System.out.println("ghost: (" + newX + ", " + newY + ")");
        }
    }

    public void move(int type, char[][] field, Position position, Position ch) {
        this.type = type;
        switch (type) {
            case ZOMBIE:
                moveZombie(field, position, ch, getHostility());
                break;
            case VAMPIRE:
                moveVampire(field, position, ch, getHostility());
                break;
            case GHOST:
                moveGhost(field, position, ch, getHostility(), 50);
//                moveGhost(field, true, position);
                break;
            case OGRE:
                moveOgre(field, position, ch, getHostility());
                break;
            case SNAKE:
                moveSnake(field, position, ch, getHostility());
                break;
            default:
                break;
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


}


