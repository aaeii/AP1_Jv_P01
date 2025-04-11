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
//    Position exit;
    private boolean inGame;
    private boolean win;
    private boolean readyToStart;


public GameSession() {
    levels = new ArrayList<>(MAX_LEVEL_NUMBER); // Инициализация списка уровней
    this.inGame = true;
    this.win = false;
    this.readyToStart = false;
    this.currentLevelNumber = 0;
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
    generate_entities();
    level_to_field(currentLevel);
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

    public Character getPlayer(){
    return player;
    }

    public void waitForStart(int input) {
        if (input == 'N' || input == 'n') this.readyToStart = true;
        if (input == 'Q' || input == 'q') {
            this.inGame = false;
            this.readyToStart = true;
        }
    }

    public void  generate_entities()

    {
        generate_player_pos();
        generate_enemies();
        generate_items();
    }

    public void generate_player_pos(){
        int room_index = -1;
        int offset = 0 ;
        Room player_room = new Room();
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        do {
            room_index = (int) (Math.random() * (double) (currentLevel.getRoom_cnt())+1);
            player_room = currentLevel.getRoomsSequence(offset + room_index - 1);
        } while (currentLevel.checkExitInRoom(offset + room_index - 1));
        Position player_pos = new Position();
        player_pos = player_pos.generate_entity_coords(player_room);
        player_room.setVisited(true);
        player.setPosition(player_pos);
    }


    public void generate_enemies(){

        for (int i=0; i < currentLevel.getRoom_cnt(); i++)
        {
            int offset = 0 ;
            while (currentLevel.getRoomsSequence(offset).getSector() == -1)
                ++offset;
            int enemies_cnt = (int)(Math.random() * (MAX_ENEMIES_PER_ROOM) + 1);
            int enemy_type = -1;

            for (int j = 0; j < enemies_cnt; j++){
                enemy_type = (int)(Math.random() * (double) (SNAKE - ZOMBIE + 1) + ZOMBIE );
                Enemy enemy = new Enemy(enemy_type);
                switch (enemy_type){
                    case ZOMBIE -> enemy.setSymbol(ZOMBIE_CHAR);
                    case VAMPIRE -> enemy.setSymbol(VAMPIRE_CHAR);
                    case GHOST -> enemy.setSymbol(GHOST_CHAR);
                    case OGRE -> enemy.setSymbol(OGRE_CHAR);
                    case SNAKE -> enemy.setSymbol(SNAKE_CHAR);
                } Position enemy_pos = new Position();
                enemy_pos = enemy_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                enemy.setPosition(enemy_pos);
                currentLevel.getRoomsSequence(offset + i).setEntities(enemy);
            }
        }
    }

    public void generate_items(){
        int offset = 0 ;
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        for (int i = 0; i < currentLevel.getRoom_cnt(); i++)
        {
            int items_cnt = (int)(Math.random() * (MAX_ITEMS_PER_ROOM) + 1);
            int item_type = -1;
            for (int j = 0; j < items_cnt; j++){
                item_type = (int)(Math.random() * (double) (ELIXIR - GOLD + 1) + GOLD );
                Item newItem;
                switch (item_type){
                    case GOLD: {
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem = new Gold(GOLD, GOLD_CHAR, item_pos);
//                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case FOOD :{
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem = new Food(FOOD, FOOD_CHAR, item_pos);
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case WEAPON: {
                        int weapon_type = (int)(Math.random() * (double) (TWO_HANDED_SWORD - MACE + 1) + MACE );
                        newItem = new Weapon(weapon_type);
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case SCROLL: {
                        int scroll_type = (int)(Math.random() * (double) (CURSED_AGILITY_SCROLL - STRENGTH_SCROLL + 1) + STRENGTH_SCROLL );
                        newItem = new Scroll(scroll_type);
                        Position item_pos = new Position();
                        item_pos = item_pos.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));
                        newItem.setPosition(item_pos);
                        currentLevel.getRoomsSequence(offset + i).setEntities(newItem);
                        break;
                    }
                    case ELIXIR: {
                        int elixir_type = (int)(Math.random() * (double) (AGILITY_ELIXIR - HEALTH_ELIXIR + 1) + HEALTH_ELIXIR);
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

    public void level_to_field (Level level){
        rooms_to_field(level);
        corridors_to_field(level);
        entities_to_field(level);
        player_to_field(level);
        exit_to_field(level);
    }

    private void rooms_to_field(Level level) {
        currentLevel.changeVisibility(player.getPosition());
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++) {
            Position top_room_corner = level.getRoomsSequence(i).getTop_left();
            Position bot_room_corner = level.getRoomsSequence(i).getBot_right();
            if (bot_room_corner.getY() != 0 && top_room_corner.getX() != 0 && top_room_corner.isVisibility()
                    || bot_room_corner.getY() != 0 && top_room_corner.getX() != 0 && level.getRoomsSequence(i).isVisited()) {
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
                if (level.getRoomsSequence(i).checkPlayerInRoom(player.getPosition()))
                    fill_inner_area(top_room_corner, bot_room_corner);
                for (int k=0; k < 4; k++){
                    int xDoor = level.getRoomsSequence(i).getDoors(k).getX();
                    int yDoor = level.getRoomsSequence(i).getDoors(k).getY();
                    if (xDoor != 0 )
                        field[yDoor][xDoor] = CORRIDOR_CHAR;
                }
            }
        }
    }

    private void fill_inner_area(Position top, Position bot){
        for (int i = top.getY() + 1; i < bot.getY(); i++)
            for (int j = top.getX() + 1; j < bot.getX(); j++)
                field[i][j] = INNER_AREA_CHAR;

    }

    private void corridors_to_field(Level level){

        for (int i = 0; i < MAX_ROOMS_NUMBER; i++){
            if (level.getRoomsSequence(i).getSector() != -1) {
                for (int k = 0; k < level.getCorridors_cnt(); k++)
                {
                    if (level.getCorridors(k).getPoints(0).isVisibility()) {
                        switch (level.getCorridors(k).getType()) {
                            case LEFT_TO_RIGHT_CORRIDOR:
                                horizontal_corridor(level.getCorridors(k).getPoints(0), level.getCorridors(k).getPoints(1));
                                vertical_corridor(level.getCorridors(k).getPoints(1), level.getCorridors(k).getPoints(2));
                                horizontal_corridor(level.getCorridors(k).getPoints(2), level.getCorridors(k).getPoints(3));
                                break;
                            case LEFT_TURN_CORRIDOR:
                                vertical_corridor(level.getCorridors(k).getPoints(0), level.getCorridors(k).getPoints(1));
                                horizontal_corridor(level.getCorridors(k).getPoints(1), level.getCorridors(k).getPoints(2));
                                break;
                            case TOP_TO_BOTTOM_CORRIDOR:
                                vertical_corridor(level.getCorridors(k).getPoints(0), level.getCorridors(k).getPoints(1));
                                horizontal_corridor(level.getCorridors(k).getPoints(1), level.getCorridors(k).getPoints(2));
                                vertical_corridor(level.getCorridors(k).getPoints(2), level.getCorridors(k).getPoints(3));
                        }
                    }
                }
            }
        }
    }

    private void horizontal_corridor(Position first_point, Position second_point){
        int y = first_point.getY();
        for (int x = Math.min(first_point.getX(), second_point.getX()); x <= Math.max(first_point.getX(), second_point.getX()); x++)
            field[y][x] = CORRIDOR_CHAR;
    }

    private void vertical_corridor(Position first_point, Position second_point){
        int x = first_point.getX();
        for (int y = Math.min(first_point.getY(), second_point.getY()); y <= Math.max(first_point.getY(), second_point.getY()); y++) {
            field[y][x] = CORRIDOR_CHAR;
        }
    }

    private void entities_to_field(Level level){
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++) {
            if (level.getRoomsSequence(i).getSector()!= UNINITIALIZED){
                for (int k = 0; k < level.getRoomsSequence(i).getEntities_cnt(); k++) {
                    Entity cur_entity = level.getRoomsSequence(i).getEntities(k);
                    if (cur_entity.getType() != PLAYER && cur_entity.getPosition().isVisibility() && cur_entity.getStatus()==ON_FIELD)
                        field[cur_entity.getPosition().getY()][cur_entity.getPosition().getX()] = (char) cur_entity.getSymbol();
                }
            }
        }
    }
    
    private void player_to_field(Level level){
        field[player.getPosition().getY()][player.getPosition().getX()] = PLAYER_CHAR;
    }

    private void exit_to_field(Level level){
    if (currentLevel.getExit_position().isVisibility())
        field[currentLevel.getExit_position().getY()][currentLevel.getExit_position().getX()] = EXIT_CHAR;
    }

    public void gameStep(int action){

        if (action == 'w' || action == 'W') {
            player.move(field, TOP, currentLevel);
            player.setStepCount();
            player.checkElixirDuration();
        }
        if (action == 'd' || action == 'D') {
            player.move(field, RIGHT, currentLevel);
            player.setStepCount();
            player.checkElixirDuration();
        }
        if (action == 's' || action == 'S') {
            player.move(field, BOTTOM, currentLevel);
            player.setStepCount();
            player.checkElixirDuration();
        }
        if (action == 'a' || action == 'A') {
            player.move(field, LEFT, currentLevel);
            player.setStepCount();
            player.checkElixirDuration();
        }
        if (player.getPosition().getX() == currentLevel.getExit_position().getX()
                && player.getPosition().getY() == currentLevel.getExit_position().getY()) {
            generate_next_level();
        }
        else
        {
            currentLevel.moveEnemies(player.getPosition());
            currentLevel.takeItem(player);
        }
//        checkInGame();
        if (action == 'Q' || action == 'q')
        {
            inGame = false;
            readyToStart = false;
        }
        currentLevel.RoomVisit(player.getPosition());
        map_refresh();
        level_to_field(currentLevel);

    }

    public void map_refresh(){
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                field[i][j] = OUTER_AREA_CHAR;
            }
        }
    }

    public void generate_next_level(){

        currentLevelNumber++;
        if (currentLevelNumber < MAX_LEVEL_NUMBER) {
            levels.get(currentLevelNumber).generate_level();
            currentLevel = levels.get(currentLevelNumber);
            generate_entities();
            level_to_field(currentLevel);
        }
        else win = true;
    }

}