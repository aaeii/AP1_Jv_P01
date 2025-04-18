package s21.domain.items;

import s21.domain.Character;
import s21.domain.Position;
import s21.domain.Room;

public class Gold extends Item {

    private int strength;
    private int agility;
    private int health;

    public Gold(){
        super();
        this.strength = 0;
        this.agility = 0;
        this.health = 0;
    }

    public Gold(int type, int symbol, Position position) {
        super(type,  symbol, position);
        this.strength = 0;
        this.agility = 0;
        this.health = 0;
    }

    public void setStrength(int amount) {
        this.strength = amount;
    }
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString(){
        return  "Gold = " + strength;
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
    }

