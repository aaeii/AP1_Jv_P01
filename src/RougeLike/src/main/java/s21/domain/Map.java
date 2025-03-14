package s21.domain;

import static s21.domain.GameConstants.*;

 //отображение для печати
public class Map {
     public char [][] playground;
     private Entity player_spawn;
     private Entity exit;
     private Entity [] items; //MAX_ENEMIES_TOTAL
     private int items_cnt;
     private Entity [] enemies; //MAX_ITEMS_TOTAL
     private int enemies_cnt;

     public Map(){
         playground = new char[MAP_HEIGHT][MAP_WIDTH];
         for (int i = 0; i < MAP_HEIGHT; i++) {
             for (int j = 0; j < MAP_WIDTH; j++) {
                 playground[i][j] = OUTER_AREA_CHAR;
             }
         }
         enemies_cnt = 0;
         items_cnt = 0;
     }

     public char[][] getPlayground(){
         return playground;
     }

     public void level_to_map ( Level level, Map map){
         rooms_to_map(level, map);
     }

     public void rooms_to_map(Level level, Map map){
         for (int i = 0; i < level.getRoom_cnt(); i++){
             Position top_room_corner = level.getRoomsSequence(i).getTop_left();
             Position bot_room_corner = level.getRoomsSequence(i).getBot_right();
             if (bot_room_corner.getY()!=0 && top_room_corner.getX()!=0) {
                 playground[top_room_corner.getY()][top_room_corner.getX()] = WALL_CHAR1;
                 int j = top_room_corner.getX() + 1;
                 for (; j < bot_room_corner.getX(); j++)
                     playground[top_room_corner.getY()][j] = WALL_CHAR2;
                 playground[top_room_corner.getY()][j] = WALL_CHAR3;

                 for (j = top_room_corner.getY() + 1; j < bot_room_corner.getY(); j++) {
                     playground[j][top_room_corner.getX()] = WALL_CHAR4;
                     playground[j][bot_room_corner.getX()] = WALL_CHAR5;
                 }
                 playground[bot_room_corner.getY()][top_room_corner.getX()] = WALL_CHAR6;
                 j = top_room_corner.getX() + 1;
                 for (; j < bot_room_corner.getX(); j++)
                     playground[bot_room_corner.getY()][j] = WALL_CHAR7;
                 playground[bot_room_corner.getY()][j] = WALL_CHAR8;
             }
         }
     }
//             Position top_room_corner = level.getRoomsSequence(i).getTop_left();
//             Position bot_room_corner = level.getRoomsSequence(i).getBot_right();
//
//             rectangle_to_map(&top_room_corner, &bot_room_corner, map);
//             fill_rectangle(&top_room_corner, &bot_room_corner, map);

//             for (int j = 0; j < dungeon->sequence[i]->entities_cnt; j++)
//             {
//                 entity_t cur_entity = dungeon->sequence[i]->entities[j];
//
//                 switch (cur_entity.type)
//                 {
//                     case PLAYER:
//                         map->player_spawn = cur_entity;
//                         break;
//                     case EXIT:
//                         map->exit = cur_entity;
//                         break;
//                     case ENEMY:
//                         map->enemies[map->enemies_cnt++] = cur_entity;
//                         break;
//                     case ITEM:
//                         map->items[map->items_cnt++] = cur_entity;
//                         break;
//                 }
//
//                 map->playground[(int)cur_entity.pos.y][(int)cur_entity.pos.x] = cur_entity.symbol;
//             }


 }
