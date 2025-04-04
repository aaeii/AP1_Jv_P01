package s21.domain.items;

import s21.domain.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static s21.domain.GameConstants.MAX_COUNT_TYPE_ITEM;

public class Inventory {
    private List<Entity> items;

    public List<Entity> getAllItems() {
        return items;
    }

    public Inventory(){
        items = new ArrayList<>();
    }

    public void addItem(Entity item) {
        long countTypeItem = items.stream()
                .filter(elem -> elem.getType() == item.getType())
                .count();

        if (countTypeItem < (MAX_COUNT_TYPE_ITEM )) {
            items.add(item);
        }
    }

    public List<Entity> getItemsByType(int itemType) {
        List<Entity> itemsList = items.stream()
                .filter(elem -> elem.getType() == itemType)
                .collect(Collectors.toList());
        return itemsList;
    }

    public int getSize(){
        return items.size();
    }

    public Entity getItem( int i){
        return items.get(i);
    }
}


