package s21.domain;

import s21.domain.items.*;

import java.util.ArrayList;
import java.util.List;

import static s21.domain.GameConstants.*;

public class GameSession {
    private final char[][] field;
    public List<Level> levels;
    public Level currentLevel;
    private int currentLevelNumber;
    Character player;
    private boolean inGame;
    private boolean win;
    private boolean readyToStart;
    private Inventory inventory;


    public GameSession() {
        levels = new ArrayList<>(MAX_LEVEL_NUMBER); // инициализация списка уровней
        this.inGame = true;
        this.win = false;
        this.readyToStart = false;
        this.currentLevelNumber = 0;
        this.inventory = new Inventory();
        field = new char[MAP_HEIGHT][MAP_WIDTH]; // Заполняем поле пустотой
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                field[i][j] = OUTER_AREA_CHAR;
            }
        }
        for (int i = 0; i < MAX_LEVEL_NUMBER; i++) {
            levels.add(new Level());
        }
        Position StartPoint = new Position();
        this.player = new Character(StartPoint);

        levels.get(currentLevelNumber).generate_level();
        this.currentLevel = levels.get(currentLevelNumber);
        generateEntities();
        levelToField(currentLevel);
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

    public Inventory getInventory() {
        return inventory;
    }


    public char[][] getField() {
        return field;
    }

    public boolean isInGame() {
        return inGame;
    }

    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public boolean isReadyToStart() {
        return readyToStart;
    }

    public Character getPlayer() {
        return player;
    }

    public void waitForStart(int input) {
        if (input == 'N' || input == 'n') this.readyToStart = true;
        if (input == 'Q' || input == 'q') {
            this.inGame = false;
            this.readyToStart = true;
        }
    }

    public void generateEntities() {
        generatePlayerPos();
        generateEnemies();
        generateItems();
    }

    public void generatePlayerPos() {
        int room_index = -1;
        int offset = 0;
        Room player_room = new Room();
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        do {
            room_index = (int) (Math.random() * (double) (currentLevel.getRoom_cnt()) + 1);
            player_room = currentLevel.getRoomsSequence(offset + room_index - 1);
        } while (currentLevel.checkExitInRoom(offset + room_index - 1));
        Position player_pos = new Position();
        player_pos = player_pos.generate_entity_coords(player_room);
        player_room.setVisited(true);
        player.setPosition(player_pos);
    }


    public void generateEnemies() {
        int offset = 0;
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        for (int i = 0; i < currentLevel.getRoom_cnt(); i++) {
            if (!currentLevel.getRoomsSequence(offset + i).checkRoom(player.getPosition())) {
                int enemies_cnt = (int) (Math.random() * (MAX_ENEMIES_PER_ROOM) + 1);
                int enemy_type = -1;
                for (int j = 0; j < enemies_cnt; j++) {
                    enemy_type = (int) (Math.random() * (double) (MIMIK - ZOMBIE + 1) + ZOMBIE);
                    Enemy enemy = new Enemy(enemy_type, currentLevelNumber);
                    Position enemy_pos = new Position();
                    enemy_pos = enemy_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                    enemy.setPosition(enemy_pos);
                    currentLevel.getRoomsSequence(offset + i).setEntities(enemy);
                }
            }
        }
    }

    public void generateItems() {
        int offset = 0;
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        for (int i = 0; i < currentLevel.getRoom_cnt(); i++) {
            int items_cnt = (int) (Math.random() * (MAX_ITEMS_PER_ROOM) + 1);
            int item_type = -1;
            for (int j = 0; j < items_cnt; j++) {
                item_type = (int) (Math.random() * (double) (ELIXIR - GOLD + 1) + GOLD);
                Item newItem;
                switch (item_type) {
                    case GOLD: {
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem = new Gold(GOLD, GOLD_CHAR, item_pos);
//                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case FOOD: {
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem = new Food(FOOD, FOOD_CHAR, item_pos);
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case WEAPON: {
                        int weapon_type = (int) (Math.random() * (double) (TWO_HANDED_SWORD - MACE + 1) + MACE);
                        newItem = new Weapon(weapon_type);
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case SCROLL: {
                        int scroll_type = (int) (Math.random() * (double) (CURSED_AGILITY_SCROLL - STRENGTH_SCROLL + 1) + STRENGTH_SCROLL);
                        newItem = new Scroll(scroll_type);
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case ELIXIR: {
                        int elixir_type = (int) (Math.random() * (double) (AGILITY_ELIXIR - HEALTH_ELIXIR + 1) + HEALTH_ELIXIR);
                        newItem = new Elixir(elixir_type);
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                }
            }
        }
    }

    public void levelToField(Level level) {
        roomsToField(level);
        corridorsToField(level);
        entitiesToField(level);
        playerToField(level);
        exitToField(level);
        if (!currentLevel.isPlayerInRoom(getPlayer().getPosition()))
            viewAreaToField();
    }

    private void viewAreaToField() {

        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {

                if (field[i][j] != OUTER_AREA_CHAR
                        && field[i][j] != CORRIDOR_CHAR
                        && field[i][j] != WALL_CHAR
                        && field[i][j] != PLAYER_CHAR
                        && field[i][j] != INNER_AREA_CHAR_ROOM
                        && field[i][j] != EXIT_CHAR
                )
                    if (!inViewArea(i, j)) field[i][j] = '.';
            }
        }
    }

    public boolean inViewArea(int i, int j) {
        boolean result = false;
        for (int k = 0; k < VIEW_AREA_SIZE; k++) {
            for (int m = 0; m < VIEW_AREA_SIZE; m++) {
                if (getPlayer().getViewArea(k, m).isVisibility()) {
                    int y = getPlayer().getViewArea(k, m).getY();
                    int x = getPlayer().getViewArea(k, m).getX();
                    if (y >= 0 && x >= 0 && x < MAP_WIDTH && y < MAP_HEIGHT) {
                        boolean isInViewArea = (i == y) && (j == x);
                        if (isInViewArea) {
                            return true;
                        }
                    }
                }
            }

        }
        return result;
    }

    private void roomsToField(Level level) {
        currentLevel.changeVisibility(player.getPosition(), player.getMoveDirection());
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++) {
            Position top_room_corner = level.getRoomsSequence(i).getTopLeft();
            Position bot_room_corner = level.getRoomsSequence(i).getBotRight();
            if (bot_room_corner.getY() != 0 && top_room_corner.getX() != 0 && top_room_corner.isVisibility()
                    || bot_room_corner.getY() != 0 && top_room_corner.getX() != 0 && level.getRoomsSequence(i).isVisited()) {
                fillInnerAreaVisited(top_room_corner, bot_room_corner);
                field[top_room_corner.getY()][top_room_corner.getX()] = WALL_CHAR;
                int j = top_room_corner.getX() + 1;
                for (; j < bot_room_corner.getX(); j++)
                    field[top_room_corner.getY()][j] = WALL_CHAR;
                field[top_room_corner.getY()][j] = WALL_CHAR;

                for (j = top_room_corner.getY() + 1; j < bot_room_corner.getY(); j++) {
                    field[j][top_room_corner.getX()] = WALL_CHAR;
                    field[j][bot_room_corner.getX()] = WALL_CHAR;
                }
                field[bot_room_corner.getY()][top_room_corner.getX()] = WALL_CHAR;
                j = top_room_corner.getX() + 1;
                for (; j < bot_room_corner.getX(); j++)
                    field[bot_room_corner.getY()][j] = WALL_CHAR;
                field[bot_room_corner.getY()][j] = WALL_CHAR;

                if (level.getRoomsSequence(i).checkPlayerInRoom(player.getPosition())) {
                    fillInnerArea(top_room_corner, bot_room_corner);
                } else {
                    if (level.getRoomsSequence(i).isPlayerOnConnection(getPlayer().getPosition(), getPlayer().getMoveDirection())
                            && level.getRoomsSequence(i).distance(getPlayer().getPosition(), getPlayer().getMoveDirection()) < VIEW_DISTANCE
                            && !level.getRoomsSequence(i).checkPlayerInRoom(player.getPosition()))
                        fillInnerArea(top_room_corner, bot_room_corner);
                }
                for (int k = 0; k < 4; k++) {
                    int xDoor = level.getRoomsSequence(i).getDoors(k).getX();
                    int yDoor = level.getRoomsSequence(i).getDoors(k).getY();
                    if (xDoor != 0)
                        field[yDoor][xDoor] = CORRIDOR_CHAR;
                }
            }
//            else {
//                if (level.getRoomsSequence(i).isPlayerOnConnection(getPlayer().getPosition(), getPlayer().getMoveDirection())) {
//                    int distance = level.getRoomsSequence(i).distance(getPlayer().getPosition(), getPlayer().getMoveDirection());
//                    if (distance < VIEW_DISTANCE)
//                        fogEffectToField(level.getRoomsSequence(i));
//                }
//            }
        }
    }


    private void fillInnerArea(Position top, Position bot) {
        for (int i = top.getY() + 1; i < bot.getY(); i++)
            for (int j = top.getX() + 1; j < bot.getX(); j++)
                field[i][j] = INNER_AREA_CHAR;

    }

    private void fillInnerAreaVisited(Position top, Position bot) {
        for (int i = top.getY() + 1; i < bot.getY(); i++)
            for (int j = top.getX() + 1; j < bot.getX(); j++)
                field[i][j] = INNER_AREA_CHAR_ROOM;

    }

    private void corridorsToField(Level level) {
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++) {
            if (level.getRoomsSequence(i).getSector() != -1) {
                for (int k = 0; k < level.getCorridors_cnt(); k++) {
                    if (level.getCorridors(k).getPoints(0).isVisibility()) {
                        switch (level.getCorridors(k).getType()) {
                            case LEFT_TO_RIGHT_CORRIDOR:
                                horizontalCorridor(level.getCorridors(k).getPoints(0), level.getCorridors(k).getPoints(1));
                                verticalCorridor(level.getCorridors(k).getPoints(1), level.getCorridors(k).getPoints(2));
                                horizontalCorridor(level.getCorridors(k).getPoints(2), level.getCorridors(k).getPoints(3));
                                break;
                            case LEFT_TURN_CORRIDOR:
                                verticalCorridor(level.getCorridors(k).getPoints(0), level.getCorridors(k).getPoints(1));
                                horizontalCorridor(level.getCorridors(k).getPoints(1), level.getCorridors(k).getPoints(2));
                                break;
                            case TOP_TO_BOTTOM_CORRIDOR:
                                verticalCorridor(level.getCorridors(k).getPoints(0), level.getCorridors(k).getPoints(1));
                                horizontalCorridor(level.getCorridors(k).getPoints(1), level.getCorridors(k).getPoints(2));
                                verticalCorridor(level.getCorridors(k).getPoints(2), level.getCorridors(k).getPoints(3));
                        }
                    }
                }
            }
        }
    }

    private void horizontalCorridor(Position first_point, Position second_point) {
        int y = first_point.getY();
        for (int x = Math.min(first_point.getX(), second_point.getX()); x <= Math.max(first_point.getX(), second_point.getX()); x++)
            field[y][x] = CORRIDOR_CHAR;
    }

    private void verticalCorridor(Position first_point, Position second_point) {
        int x = first_point.getX();
        for (int y = Math.min(first_point.getY(), second_point.getY()); y <= Math.max(first_point.getY(), second_point.getY()); y++) {
            field[y][x] = CORRIDOR_CHAR;
        }
    }

    private void entitiesToField(Level level) {
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++) {
            if (level.getRoomsSequence(i).getSector() != UNINITIALIZED) {
                for (int k = 0; k < level.getRoomsSequence(i).getEntitiesCnt(); k++) {
                    Entity cur_entity = level.getRoomsSequence(i).getEntities(k);
                    if (cur_entity.getType() == MIMIK && cur_entity.getStatus() == FIGHT) {
                        field[cur_entity.getPosition().getY()][cur_entity.getPosition().getX()] = MIMIK_CHAR;
                    } else if (cur_entity.getType() != PLAYER
                            && cur_entity.getPosition().isVisibility()
                            && (cur_entity.getStatus() == ON_FIELD
                            || cur_entity.getStatus() == FIGHT))
                        field[cur_entity.getPosition().getY()][cur_entity.getPosition().getX()] = (char) cur_entity.getSymbol();
                }
            }
        }
    }

    private void playerToField(Level level) {
        field[player.getPosition().getY()][player.getPosition().getX()] = PLAYER_CHAR;
    }

    private void exitToField(Level level) {
        if (currentLevel.getExit_position().isVisibility())
            field[currentLevel.getExit_position().getY()][currentLevel.getExit_position().getX()] = EXIT_CHAR;
    }

    public List<String> gameStep(int action) {
        List<String> message = new ArrayList<>();

        if (action == 'w' || action == 'W') {
            message = player.move(field, TOP, currentLevel);
            player.setStepCount();
            checkElixirDuration();
        }
        if (action == 'd' || action == 'D') {
            message = player.move(field, RIGHT, currentLevel);
            player.setStepCount();
            checkElixirDuration();
        }
        if (action == 's' || action == 'S') {
            message = player.move(field, BOTTOM, currentLevel);
            player.setStepCount();
            checkElixirDuration();
        }
        if (action == 'a' || action == 'A') {
            message = player.move(field, LEFT, currentLevel);
            player.setStepCount();
            checkElixirDuration();
        }
        if (player.getPosition().getX() == currentLevel.getExit_position().getX()
                && player.getPosition().getY() == currentLevel.getExit_position().getY()) {
            generateNextLevel();
        } else {
            if (!isFight()) currentLevel.moveEnemies(player);
            currentLevel.takeItem(player, inventory);
        }
        checkInGame();
        if (action == 'Q' || action == 'q') {
            inGame = false;
            readyToStart = true;
        }
        currentLevel.RoomVisit(player.getPosition());
        mapRefresh();
        levelToField(currentLevel);

        return message;
    }

    private boolean isFight() {
        boolean result = false;
        int offset = 0;
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        for (int j = 0; j < currentLevel.getRoom_cnt() && !result; j++) {
            for (int i = 0; i < currentLevel.getRoomsSequence(j + offset).getEntitiesCnt() && !result; i++) {
                if (currentLevel.getRoomsSequence(j + offset).getEntities(i).getStatus() == FIGHT
                ) {
                    result = true;
                }
            }
        }
        return result;
    }

    private void checkInGame() {
        if (player.getHealth() <= 0) {
            this.inGame = false;
            this.win = false;
        }
    }

    public void checkElixirDuration() {
        for (int i = 0; i < getItemsCount(); i++) {
            if (getItemFromInventory(i).getType() == ELIXIR) {
                getItemFromInventory(i).reduceDuration();
                if (getItemFromInventory(i).getDuration() == 0) {
                    int newStrength = player.getStrength() - getItemFromInventory(i).getStrength();
                    if (newStrength >= 0) {
                        player.setStrength(newStrength);
                    } else {
                        player.setStrength(0);
                    }
                    int newAgility = player.getAgility() - getItemFromInventory(i).getAgility();
                    if (newAgility >= 0) {
                        player.setAgility(newAgility);
                    } else {
                        player.setAgility(0);
                    }
                    int newHealth = player.getHealth() - getItemFromInventory(i).getHealth();
                    if (newHealth >= 0) {
                        player.setAgility(newHealth);
                    } else {
                        player.setAgility(0);
                    }
                    getAllItems().remove(i);
                }
            }
        }
    }

    public void mapRefresh() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                field[i][j] = OUTER_AREA_CHAR;
            }
        }
    }

    public void generateNextLevel() {
        currentLevelNumber++;
        if (currentLevelNumber < MAX_LEVEL_NUMBER) {
            levels.get(currentLevelNumber).generate_level();
            currentLevel = levels.get(currentLevelNumber);
            generateEntities();
            levelToField(currentLevel);
        } else win = true;
    }

}