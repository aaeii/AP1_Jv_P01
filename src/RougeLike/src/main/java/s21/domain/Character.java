package s21.domain;

import com.googlecode.lanterna.SGR;
import s21.domain.items.Inventory;
import s21.domain.items.Item;

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
    private int attackCounter;
    private int enemiesAttackCounter;
    private Inventory inventory;

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
        this.inventory = new Inventory();
    }


    public Position getPosition() {
        return position;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMaxHealth(int value) {
        this.maxHealth = this.maxHealth + value;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAgility(int value) {
        this.agility = value;
    }

    public void setStrength(int value) {
        this.strength = this.strength + value;
    }

    public int getGold() {
        return gold;
    }

    public void move(char[][] field, int direction, Level level) {
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case (TOP):
                if (field[y - 1][x] != WALL_CHAR && field[y - 1][x] != OUTER_AREA_CHAR) position.setNew(x, y - 1, true);
                break;
            case (RIGHT):
                if (field[y][x + 1] != WALL_CHAR && field[y][x + 1] != OUTER_AREA_CHAR) position.setNew(x + 1, y, true);
                break;
            case (BOTTOM):
                if (field[y + 1][x] != WALL_CHAR && field[y + 1][x] != OUTER_AREA_CHAR) position.setNew(x, y + 1, true);
                break;
            case (LEFT):
                if (field[y][x - 1] != WALL_CHAR && field[y][x - 1] != OUTER_AREA_CHAR) position.setNew(x - 1, y, true);
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

    public void useWeapon(int number){
        int count = 0;
        for (int i = 0; i < getItemsCount(); i++){
            Entity cur_entity = getItemFromInventory(i);
            if (cur_entity.getType() == WEAPON
                    && cur_entity.getStatus() == IN_INVENTORY
                    && number == count){
                count++;
                freeCurrentWeapon();
                getItemFromInventory(i).setStatus(USED);
                setStrength(getItemFromInventory(i).getStrength());
            }
        }

    }

    public void freeCurrentWeapon(){
        for (int i = 0; i < getItemsCount(); i++){
            Entity cur_entity = getItemFromInventory(i);
            if (cur_entity.getType() == WEAPON
                    && cur_entity.getStatus() == USED)
            {
                getItemFromInventory(i).setStatus(ON_FIELD);
                int newStrenght = getItemFromInventory(i).getStrength() *(-1);
                setStrength(newStrenght);
            }
        }
    }
}
