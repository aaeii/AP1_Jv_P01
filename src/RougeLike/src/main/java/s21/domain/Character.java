package s21.domain;

import com.googlecode.lanterna.SGR;
import s21.domain.items.Inventory;
import s21.domain.items.Item;
import s21.domain.items.Weapon;

import java.util.List;

import static s21.domain.GameConstants.*;

public class Character {

    Position position;
    private int maxHealth;
    private int health;
    private int agility;
    private int strength;
    private int gold;
    private boolean sleep;
    private int eatenFoodCounter;
    private int drunkElixirCounter;
    private int readScrollsCounter;
    private int stepCount;
    private int attackCounter;
    private int enemiesAttackCounter;
    private Inventory inventory;
    private int moveDirection;
    private Position[][] view_area;

    Character(Position pos) {
        this.position = pos;
        this.maxHealth = 20;
        this.health = maxHealth;
        this.agility = 2;
        this.strength = 2;
        this.gold = 0;
        this.sleep = false;
        this.eatenFoodCounter = 0;
        this.drunkElixirCounter = 0;
        this.attackCounter = 0;
        this.readScrollsCounter = 0;
        this.inventory = new Inventory();
        this.stepCount = 0;
        this.moveDirection = UNINITIALIZED;
        this.view_area = new Position[VIEW_AREA_SIZE][VIEW_AREA_SIZE];
        for (int i = 0; i < VIEW_AREA_SIZE; i++)
            for (int j = 0; j < VIEW_AREA_SIZE; j++){
                view_area[i][j] = new Position();
        }
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
                if (field[y - 1][x] != WALL_CHAR && field[y - 1][x] != OUTER_AREA_CHAR) position.setNew(x, y - 1, true);
                fillInViewArea();
                break;
            case (RIGHT):
                if (field[y][x + 1] != WALL_CHAR && field[y][x + 1] != OUTER_AREA_CHAR) position.setNew(x + 1, y, true);
                fillInViewArea();
                break;
            case (BOTTOM):
                if (field[y + 1][x] != WALL_CHAR && field[y + 1][x] != OUTER_AREA_CHAR) position.setNew(x, y + 1, true);
                fillInViewArea();
                break;
            case (LEFT):
                if (field[y][x - 1] != WALL_CHAR && field[y][x - 1] != OUTER_AREA_CHAR) position.setNew(x - 1, y, true);
                fillInViewArea();
                break;
        }

    }

    public List<Entity> getAllItems() {
        return inventory.getAllItems();
    }

    public List<Entity> getItemsByType(int itemType) {
        return inventory.getItemsByType(itemType);
    }

    public void addItem(Entity item) {
        inventory.addItem(item);
    }

    public int getItemsCount() {
        return inventory.getSize();
    }

    public Entity getItemFromInventory(int i) {
        return inventory.getItem(i);
    }

    public void useItem(int number, GameSession game, int type){
        int count = 0;
        for (int i = 0; i < getItemsCount(); i++){
            Entity cur_entity = getItemFromInventory(i);
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
        for (int i = 0; i < getItemsCount(); i++){
            Entity cur_entity = getItemFromInventory(i);
            if ((cur_entity.getType() == WEAPON
                    && cur_entity.getStatus() == ON_FIELD)
                    || (cur_entity.getType() == type
                    && cur_entity.getStatus() == USED
                    && cur_entity.getType() != WEAPON
                    && cur_entity.getType()!=ELIXIR)){
            getAllItems().remove(i);
            }
        }
       printInventary();
    }

    public void setNewCharacters(int i, GameSession game){
        if (getItemFromInventory(i).getType() == WEAPON) freeCurrentWeapon(game);
        if (getItemFromInventory(i).getType() == ELIXIR){
            int duration = (int) ( Math.random() * 10 + 10);
            getItemFromInventory(i).setDuration(duration);
            System.out.println("duration" + getItemFromInventory(i).toString() + " " + getItemFromInventory(i).getDuration());
        }
        getItemFromInventory(i).setStatus(USED);
        int newStrength = getStrength() + getItemFromInventory(i).getStrength();
        if (newStrength >= 0) setStrength(newStrength);
        else setStrength(0);
        int newAgility = getAgility() + getItemFromInventory(i).getAgility();
        if (newAgility >= 0) setAgility(newAgility);
        else setAgility(0);
        int newHealth = getHealth() + getItemFromInventory(i).getHealth();
        if (newHealth <= MAX_HEALTH) setHealth(newHealth);
        else setHealth(MAX_HEALTH);
        if (getItemFromInventory(i).getType()==ELIXIR) drunkElixirCounter++;
        if (getItemFromInventory(i).getType()==SCROLL) setReadScrollCounter();
    }

    public void freeCurrentWeapon(GameSession game){
        for (int i = 0; i < getItemsCount(); i++){
            Entity cur_entity = getItemFromInventory(i);
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
                setStrength(getStrength() - getItemFromInventory(i).getStrength());
            }
        }
        game.level_to_field(game.currentLevel);
        printInventary();
    }

    public void printInventary(){
        inventory.print();
    }

    public void checkElixirDuration(){
        for (int i=0; i<getItemsCount(); i++){
            if (getItemFromInventory(i).getType()==ELIXIR) {
                getItemFromInventory(i).reduceDuration();
                if (getItemFromInventory(i).getDuration() == 0) {
                    int newStrength = getStrength() - getItemFromInventory(i).getStrength();
                    if (newStrength >= 0) setStrength(newStrength);
                    else setStrength(0);
                    int newAgility = getAgility() - getItemFromInventory(i).getAgility();
                    if (newAgility >= 0) setAgility(newAgility);
                    else setAgility(0);
                    int newHealth = getHealth() - getItemFromInventory(i).getHealth();
                    if (newHealth >= 0) setAgility(newHealth);
                    else setAgility(0);
                    getAllItems().remove(i);
                }
            }
        }
    }

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
}
