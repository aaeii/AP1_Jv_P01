package s21.domain.items;

import s21.domain.Character;
import s21.domain.Position;
import s21.domain.Room;

public class Food extends Item {

    private int strength;
    private int agility;
    private int health;

    public Food(){
        super();
        this.strength = 0;
        this.agility = 0;
        this.health = 0;
    }

    public Food(int type, int symbol, Position position) {
        super(type,  symbol, position);
        this.strength = 0;
        this.agility = 0;
        this.health = (int)(Math.random() * (10) + 1);;
    }

    @Override
    public String toString(){
        return  "Food = " + health;
    }
    @Override
    public void action(Character player, Room room) {
    }
    @Override
    public int getAgility() {
        return agility;
    }
    @Override
    public int getStrength() {
        return strength;
    }
    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public void setHealth(int health) {
        this.health = health;
    }
    @Override
    public int getCountHit() {
        return 0;
    }

    @Override
    public void setCountHit(int countHit) {

    }
}


