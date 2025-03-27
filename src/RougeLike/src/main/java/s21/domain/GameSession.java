package s21.domain;

import s21.controller.UserInput;
import s21.presentation.Print;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static s21.controller.UserInput.*;
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
    for (int i = 0; i < 21; i++) {
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
        generate_exit();
        generate_enemies();
//        generate_items();
    }

    public void generate_player_pos(){
        int room_index = (int) (Math.random() * (double) (currentLevel.getRoom_cnt())+1);
        int offset = 0 ;
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        Room player_room = currentLevel.getRoomsSequence(offset + room_index - 1);
        Entity player_entity = new Entity();
        Position player_pos = new Position();
        player_pos = player_entity.generate_entity_coords(player_room);
        player_room.setVisited(true);
        player.setPosition(player_pos);
    }

    public void generate_exit(){
        int room_index = (int) (Math.random() * (double) (currentLevel.getRoom_cnt())+1);
        int offset = 0 ;
        while (currentLevel.getRoomsSequence(offset).getSector() == -1)
            ++offset;
        Room exit_room = currentLevel.getRoomsSequence(offset + room_index - 1);
        Entity exit = new Entity();
        Position exit_pos = new Position();
        exit_pos = exit.generate_entity_coords(exit_room);
        exit.setSymbol(EXIT_CHAR);
        exit.setType(EXIT);
        exit.setPosition(exit_pos);
        exit_room.setEntities(exit);
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
                Entity enemy = new Entity();
                enemy_type = (int)(Math.random() * (SNAKE - ZOMBIE) + ZOMBIE+ 1);
                enemy.setType(enemy_type);
                switch (enemy_type){
                    case ZOMBIE -> enemy.setSymbol(ZOMBIE_CHAR);
                    case VAMPIRE -> enemy.setSymbol(VAMPIRE_CHAR);
                    case GHOST -> enemy.setSymbol(GHOST_CHAR);
                    case OGRE -> enemy.setSymbol(OGRE_CHAR);
                    case SNAKE -> enemy.setSymbol(SNAKE_CHAR);
                }
                Position enemy_pos = new Position();
                enemy_pos = enemy.generate_entity_coords(currentLevel.getRoomsSequence(offset + i));//нужна случайная генерация
                enemy.setPosition(enemy_pos);
                currentLevel.getRoomsSequence(offset + i).setEntities(enemy);
            }
        }
    }

    public void level_to_field (Level level){
        rooms_to_field(level);
        corridors_to_field(level);
        entities_to_field(level);
        player_to_field(level);
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
                if (cur_entity.getType() != PLAYER && cur_entity.getPosition().isVisibility())
                     field[cur_entity.getPosition().getY()][cur_entity.getPosition().getX()] = (char) cur_entity.getSymbol();
            }
            }
        }
    }

    private void player_to_field(Level level){
        field[player.getPosition().getY()][player.getPosition().getX()] = PLAYER_CHAR;
    }

    public void gameStep(int action){

        if (action == 'w' || action == 'W') {
            player.move(field, TOP, currentLevel);
            currentLevel.moveEnemies(player.getPosition());
        }
        if (action == 'd' || action == 'D') {
            player.move(field, RIGHT, currentLevel);
        }
        if (action == 's' || action == 'S') {
            player.move(field, BOTTOM, currentLevel);
        }
        if (action == 'a' || action == 'A') {
            player.move(field, LEFT, currentLevel);
        }
        if (currentLevel.isItExit(player.getPosition())) {
            generate_next_level();
            System.out.println("new level " + currentLevelNumber);
        }
        else
            currentLevel.moveEnemies(player.getPosition());
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
        if (currentLevelNumber < 21) {
            levels.get(currentLevelNumber).generate_level();
            currentLevel = levels.get(currentLevelNumber);
            generate_entities();
            level_to_field(currentLevel);
        }
        else win = true;
    }

}