package s21.domain.items;

import s21.domain.Character;
import s21.domain.Position;

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

    public void setHealth(int amount) {
        this.health = amount;
    }

    @Override
    public String toString(){
        return  "Food = " + health;
    }
    @Override
    public void action(Character player_pos, Position top_left, Position bot_right) {
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


