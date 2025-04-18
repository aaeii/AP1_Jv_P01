package s21.domain;

import com.googlecode.lanterna.SGR;
import s21.domain.items.Inventory;
import s21.domain.items.Item;
import s21.domain.items.Weapon;

import java.util.List;
import java.util.Random;

import static s21.domain.GameConstants.*;

public class Character {

    private int gold;
    private int endLevel;
    private int attackCounter;
    private int eatenFoodCounter;
    private int drunkElixirCounter;
    private int readScrollsCounter;
    private int stepCount;
    private int health;
    private int agility;
    private int strength;
    private int maxHealth;
    private boolean sleep;
    private int enemiesAttackCounter;
    private int moveDirection;
    private Position[][] view_area;

    private Position position;

    Character(Position pos) {
        this.position = pos;
        this.maxHealth = 20;
        this.health = maxHealth;
        this.agility = 2;
        this.strength = 10;
        this.gold = 0;
        this.sleep = false;
        this.eatenFoodCounter = 0;
        this.drunkElixirCounter = 0;
        this.attackCounter = 0;
        this.readScrollsCounter = 0;
        this.stepCount = 0;
        this.moveDirection = UNINITIALIZED;
        this.endLevel=0;
        this.view_area = new Position[VIEW_AREA_SIZE][VIEW_AREA_SIZE];
        for (int i = 0; i < VIEW_AREA_SIZE; i++)
            for (int j = 0; j < VIEW_AREA_SIZE; j++){
                view_area[i][j] = new Position();
            }
    }

    public void setEndLevel(int endLevel) {
        this.endLevel = endLevel;
    }

    public int getEndLevel(){
        return endLevel;
    }

    public int getStepCount(){
        return stepCount;
    }

    public Position getViewArea(int i, int j){
        return view_area[i][j];
    }

    public Position getPosition() {
        return position;
    }

    public int getMoveDirection(){
        return moveDirection;
    }

    public int getDrunkElixirCounter(){
        return drunkElixirCounter;
    }

    public int getEatenFoodCounter(){
        return eatenFoodCounter;
    }

    public void setEatenFoodCounter(){
        this.eatenFoodCounter++;
    }

    public void setStepCount(){
        this.stepCount++;
    }

    public int getReadScrollCounter(){
        return readScrollsCounter;
    }

    public void setReadScrollCounter(){
        this.readScrollsCounter++;
    }

    public int getHealth() {
        return health;
    }

    public int getAgility() {
        return agility;
    }

    public int getStrength() {
        return strength;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGold(){
        return gold;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAgility(int value) {
        this.agility = value;
    }

    public void setStrength(int value) {
        this.strength = value;
    }
    private void fillInViewArea(){
        for (int i = 0;  i < VIEW_AREA_SIZE; i++) {
            for (int j = 0; j < VIEW_AREA_SIZE; j++) {
                view_area[i][j].setY(position.getY() - VIEW_DISTANCE + i);
                view_area[i][j].setX(position.getX() - VIEW_DISTANCE + j);
                view_area[i][j].setVisibility(false);
            }
        }
        drawRound();
    }

    private void drawRound(){
        int R = VIEW_DISTANCE;
        int x = 0;
        int y = R;
        int x1 = VIEW_DISTANCE + 1;
        int y1 = VIEW_DISTANCE + 1;
        int delta = 1 - 2 * R;
        int error = 0;
        while (y >= x) {
            for (int i = y1 - y - 1; i <= y1 + y-1; i++)
                view_area[x1 + x - 1][i].setVisibility(true);
            for (int i = y1 - y - 1; i <= y1 + y - 1; i++)
                view_area[x1 - x - 1][i].setVisibility(true);
            for (int i = y1 - x - 1; i <= y1 + x - 1; i++)
                view_area[x1 + y - 1][i].setVisibility(true);
            for (int i = y1 - x - 1; i <= y1 + x - 1; i++)
                view_area[x1 - y - 1][i].setVisibility(true);
            error = 2 * (delta + y) - 1;
            if ((delta < 0) && (error <= 0))
                delta += 2 * ++x + 1;
            if ((delta > 0) && (error > 0))
                delta -= 2 * --y + 1;
            delta += 2 * (++x - --y);
        }
        for (int i=1; i<VIEW_AREA_SIZE-1; i++){
            for (int j=0; j<VIEW_AREA_SIZE-1; j++){
                if (view_area[i-1][j].isVisibility() && view_area[i+1][j].isVisibility())
                    view_area[i][j].setVisibility(true);
            }
        }
    }


    public void move(char[][] field, int direction, Level level) {
        int x = position.getX();
        int y = position.getY();
        moveDirection=direction;
        switch (direction) {
            case (TOP):
                if (field[y - 1][x] == SNAKE_CHAR
                        || field[y - 1][x] == ZOMBIE_CHAR
                        || field[y - 1][x] == VAMPIRE_CHAR
                        ||  field[y - 1][x] == GHOST_CHAR)
                    fight(new Position(x, y - 1, true), level);
                else
                if (field[y-1][x ] != WALL_CHAR && field[y-1][x] != OUTER_AREA_CHAR) position.setNew(x , y-1, true);
                fillInViewArea();
                break;
            case (RIGHT):
                if (field[y][x + 1] == SNAKE_CHAR
                        || field[y][x + 1] == ZOMBIE_CHAR
                        || field[y][x + 1] == VAMPIRE_CHAR
                        ||  field[y][x + 1] == GHOST_CHAR
                        ||  field[y][x + 1] == OGRE_CHAR)
                    fight(new Position(x + 1, y, true), level);
                else
                if (field[y][x + 1] != WALL_CHAR && field[y][x + 1] != OUTER_AREA_CHAR) position.setNew(x + 1, y, true);
                fillInViewArea();
                break;
            case (BOTTOM):
                if (field[y + 1][x] == SNAKE_CHAR
                        || field[y + 1][x] == ZOMBIE_CHAR
                        || field[y + 1][x] == VAMPIRE_CHAR
                        ||  field[y + 1][x] == GHOST_CHAR
                        ||  field[y + 1][x] == OGRE_CHAR)
                    fight(new Position(x, y + 1, true), level);
                else
                if (field[y + 1][x] != WALL_CHAR && field[y + 1][x] != OUTER_AREA_CHAR) position.setNew(x, y + 1, true);
                fillInViewArea();
                break;
            case (LEFT):
                if (field[y][x - 1] == SNAKE_CHAR
                        || field[y][x - 1] == ZOMBIE_CHAR
                        || field[y][x - 1] == VAMPIRE_CHAR
                        ||  field[y][x - 1] == GHOST_CHAR
                        ||  field[y][x - 1] == OGRE_CHAR)
                    fight(new Position(x - 1, y, true), level);
                else
                if (field[y][x - 1] != WALL_CHAR && field[y][x - 1] != OUTER_AREA_CHAR) position.setNew(x - 1, y, true);
                fillInViewArea();
                break;
        }

    }
    public void fight(Position enemyPos, Level level) {
        int offset = 0,  roomNumber = 0;
        while (level.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        for (int j = offset; j < MAX_ROOMS_NUMBER; j++){
            System.out.println(level.getRoomsSequence(j).checkInRoomEntities(enemyPos));
            if (level.getRoomsSequence(j).checkInRoomEntities(enemyPos)) {
                roomNumber = j;
                break;
            }
        }
        System.out.println(roomNumber);
        for (int i = 0; i < level.getRoomsSequence(roomNumber).getEntities().size(); i++){
            System.out.println(i + level.getRoomsSequence(roomNumber).getEntities(i).toString());
        }
        int entity_num = -1;
        for (int i = 0; i < level.getRoomsSequence(roomNumber).getEntities_cnt(); i++) {
            if (enemyPos.getX() == level.getRoomsSequence(roomNumber).getEntities(i).getPosition().getX()
                    && enemyPos.getY() == level.getRoomsSequence(roomNumber).getEntities(i).getPosition().getY()
//                    && enemyRoom.getEntities(i).getType() <= ZOMBIE && enemyRoom.getEntities(i).getType() >= SNAKE
            ) {
                entity_num = i;
                System.out.println("enemy" + i);
                break;
            }
        }
        if (entity_num != UNINITIALIZED){
            attack(level.getRoomsSequence(roomNumber).getEntities(entity_num));
            if (level.getRoomsSequence(roomNumber).getEntities(entity_num).getHealth() == 0)
            {
                level.getRoomsSequence(roomNumber).getEntities().remove(entity_num);
                int Entities_cnt = level.getRoomsSequence(roomNumber).getEntities_cnt() - 1;
                level.getRoomsSequence(roomNumber).setEntities_cnt(Entities_cnt);
            }
        }
    }

    public void attack(Entity enemy) {
        int hitChance = calculateHitChance(enemy);
        Random random = new Random();
        boolean hits = random.nextInt(100) < BASE_FIGHT_CHANCE; // Проверка шанса попадания
        if (hits) {
            takeDamage(enemy);
            System.out.println(" hits " + enemy.getSymbol() + " dealing ");
        } else {
            System.out.println(" misses " + enemy.getSymbol());
        }
    }

    private int calculateHitChance(Entity enemy) {
        return (int) (Math.min(BASE_FIGHT_CHANCE + (agility - enemy.getAgility()), 100));
    }

    public void takeDamage(Entity enemy) {
        health = health - enemy.getStrength();
        int enemy_health = enemy.getHealth()-strength;
        System.out.println("enemy_health " + enemy_health);
        if (health < 0) {
            health = 0;
            System.out.println("GAME OVER");
        }
        enemy.setHealth(enemy_health);
        System.out.println("enemy_health after" + enemy.getHealth());
        if (enemy.getHealth() < 0) {
            enemy.setHealth(0);
            System.out.println("Enemy dead");
        }
    }

    public void useItem(int number, GameSession game, int type){
        int count = 0;
        for (int i = 0; i < game.getItemsCount(); i++){
            Entity cur_entity = game.getItemFromInventory(i);
            if (cur_entity.getType() == type
                    && cur_entity.getStatus() == IN_INVENTORY){
                count++;
                if (number == (count - 1)) {
                    setNewCharacters(i, game);
                }
            }
            if (type == HEALTHKIT
                    && cur_entity.getStatus() == IN_INVENTORY
                    && cur_entity.getHealth()>0){
                count++;
                if (number == (count - 1)) {
                    setNewCharacters(i, game);
                }
            }
        }
        for (int i = 0; i < game.getItemsCount(); i++){
            Entity cur_entity = game.getItemFromInventory(i);
            if ((cur_entity.getType() == WEAPON
                    && cur_entity.getStatus() == ON_FIELD)
                    || (cur_entity.getType() == type
                    && cur_entity.getStatus() == USED
                    && cur_entity.getType() != WEAPON
                    && cur_entity.getType()!=ELIXIR)){
                game.getAllItems().remove(i);
            }
        }

        game.getInventory().print();
    }

    public void setNewCharacters(int i, GameSession game){
        if (game.getItemFromInventory(i).getType() == WEAPON) freeCurrentWeapon(game);
        if (game.getItemFromInventory(i).getType() == ELIXIR){
            int duration = (int) ( Math.random() * 10 + 10);
            game.getItemFromInventory(i).setDuration(duration);
        }
        game.getItemFromInventory(i).setStatus(USED);
        int newStrength = getStrength() + game.getItemFromInventory(i).getStrength();
        if (newStrength >= 0) setStrength(newStrength);
        else setStrength(0);
        int newAgility = getAgility() + game.getItemFromInventory(i).getAgility();
        if (newAgility >= 0) setAgility(newAgility);
        else setAgility(0);
        int newHealth = getHealth() + game.getItemFromInventory(i).getHealth();
        if (newHealth <= MAX_HEALTH) setHealth(newHealth);
        else setHealth(MAX_HEALTH);
        if (game.getItemFromInventory(i).getType()==ELIXIR) drunkElixirCounter++;
        if (game.getItemFromInventory(i).getType()==SCROLL) setReadScrollCounter();
    }

    public void freeCurrentWeapon(GameSession game){
        for (int i = 0; i < game.getItemsCount(); i++){
            Entity cur_entity = game.getItemFromInventory(i);
            if (cur_entity.getType() == WEAPON
                    && cur_entity.getStatus() == USED)
            {
                int offset = 0 ;
                while (game.currentLevel.getRoomsSequence(offset).getSector() == -1)
                    ++offset;
                for (int j = offset; j < MAX_ROOMS_NUMBER; j++){
                    Room current_room = game.currentLevel.getRoomsSequence(j);
                    if (current_room.checkRoom(position)) {
                        Position newWeaponPos = new Position();
                        do {
                            int x = position.getX();
                            int y = position.getY();
                            int direction = (int) ((Math.random() * 5));
                            switch (direction) {
                                case TOP -> y--;
                                case BOTTOM -> y++;
                                case LEFT -> x--;
                                case RIGHT -> x++;
                            }
                            newWeaponPos.setNew(x, y, false);
                        }
                        while ((newWeaponPos.check_unoccupied(current_room, newWeaponPos) == OCCUPIED) && (newWeaponPos.check_walls(current_room, newWeaponPos) == OCCUPIED));
                        cur_entity.setStatus(ON_FIELD);
                        cur_entity.setPosition(newWeaponPos);
                        current_room.setEntities(cur_entity);
                    }
                }
                setStrength(getStrength() - game.getItemFromInventory(i).getStrength());
            }
        }
        game.level_to_field(game.currentLevel);
        game.getInventory().print();
    }

//    public void printInventary(){
//        inventory.print();
//    }

//    public void checkElixirDuration(Inventory inventory){
//        for (int i=0; i< game.getItemsCount(); i++){
//            if (getItemFromInventory(i).getType()==ELIXIR) {
//                getItemFromInventory(i).reduceDuration();
//                if (getItemFromInventory(i).getDuration() == 0) {
//                    int newStrength = getStrength() - getItemFromInventory(i).getStrength();
//                    if (newStrength >= 0) setStrength(newStrength);
//                    else setStrength(0);
//                    int newAgility = getAgility() - getItemFromInventory(i).getAgility();
//                    if (newAgility >= 0) setAgility(newAgility);
//                    else setAgility(0);
//                    int newHealth = getHealth() - getItemFromInventory(i).getHealth();
//                    if (newHealth >= 0) setAgility(newHealth);
//                    else setAgility(0);
//                    getAllItems().remove(i);
//                }
//            }
//        }
//    }

    public boolean playerInCorridor(GameSession game){
        boolean result = true;
        int offset = 0 ;
        while (game.currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        for (int j = offset; j < MAX_ROOMS_NUMBER; j++){
            if (game.currentLevel.getRoomsSequence(j).checkPlayerInRoom(game.getPlayer().getPosition())) {
                result=false;
                return result;
            }
        }
        return result;
    }

    public static void sortListOfCharacter(List <Character> players){
        for (int i = 0; i < players.size()-1; i++){
            for (int j=0; j < players.size()-1-i; j++){
                if (players.get(j+1).getGold() < players.get(j).getGold()) {
                    Character swap = players.get(j);
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, swap);
                }
            }
        }
        for ( int i = 0; i < players.size()-1; i++) {
            for (int j = 0; j < players.size() - 1 - i; j++) {
                if (players.get(j + 1).getGold() == players.get(j).getGold())
                    if (players.get(j + 1).getEndLevel() < players.get(j).getEndLevel()) {
                        Character swap = players.get(j);
                        players.set(j, players.get(j + 1));
                        players.set(j + 1, swap);
                    }
            }
        }

        for ( int i = 0; i < players.size()-1; i++) {
            for (int j = 0; j < players.size() - 1 - i; j++) {
                if ((players.get(j + 1).getGold() == players.get(j).getGold())
                        && (players.get(j + 1).getEndLevel() == players.get(j).getEndLevel()))
                    if (players.get(j + 1).getEatenFoodCounter() < players.get(j).getEatenFoodCounter()) {
                        Character swap = players.get(j);
                        players.set(j, players.get(j + 1));
                        players.set(j + 1, swap);
                    }
            }
        }
        for ( int i = 0; i < players.size()-1; i++) {
            for (int j = 0; j < players.size() - 1 - i; j++) {
                if ((players.get(j + 1).getGold() == players.get(j).getGold())
                        && (players.get(j + 1).getEndLevel() == players.get(j).getEndLevel())
                        && (players.get(j + 1).getEatenFoodCounter() == players.get(j).getEatenFoodCounter()))
                    if (players.get(j + 1).getDrunkElixirCounter() < players.get(j).getDrunkElixirCounter()) {
                        Character swap = players.get(j);
                        players.set(j, players.get(j + 1));
                        players.set(j + 1, swap);
                    }
            }
        }
    }
}
