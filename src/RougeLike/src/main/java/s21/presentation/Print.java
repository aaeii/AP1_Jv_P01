package s21.presentation;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import s21.datalayer.DataLayer;
import s21.domain.Entity;
import s21.domain.GameSession;
import s21.domain.items.MyComparator;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static s21.domain.GameConstants.*;


public class Print {
    private char[][] field;

    public Print() {
        field = new char[MAP_HEIGHT][MAP_WIDTH];
    }

    public void setField(char[][] playground) {
        this.field = playground;
    }

    public Terminal createTerminal() throws IOException {
//        TerminalSize size = new TerminalSize(MAP_WIDTH+ 20, MAP_HEIGHT );
//        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(size);
//        Terminal terminal = defaultTerminalFactory.createTerminal();
//        terminal.setCursorVisible(false);
//        terminal.flush();
//
//        return terminal;
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);
        terminal.flush();
        return terminal;

    }

    public void printOnlyField(Terminal terminal) throws IOException {
        try{
            terminal.clearScreen();
            for (int i = 0; i < MAP_HEIGHT; i++) {
                for (int j = 0; j < MAP_WIDTH; j++) {
                    terminal.setCursorPosition(j, i);
                    switch (field[i][j]){
                        case ZOMBIE_CHAR:
                            terminal.setForegroundColor(TextColor.ANSI.GREEN);
                            break;
                        case VAMPIRE_CHAR:
                            terminal.setForegroundColor(TextColor.ANSI.RED);
                            break;
                        case GHOST_CHAR:
                            terminal.setForegroundColor(TextColor.ANSI.WHITE);
                            break;
                        case OGRE_CHAR:
                            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
                            break;
                        case SNAKE_CHAR:
                            terminal.setForegroundColor(TextColor.ANSI.WHITE);
                            break;
                    }
                    terminal.putCharacter(field[i][j]);
                    terminal.setForegroundColor(TextColor.ANSI.DEFAULT);

                }
            }
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printGame(Terminal terminal, GameSession game) throws IOException {
     printOnlyField(terminal);
     printInfo(terminal, game);
    }

    private void printStartMenu(Terminal terminal) throws IOException {
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);
        screen.clear();
        String sizeLabel = "Press R to Start ";
        TerminalPosition labelBoxTopLeft = new TerminalPosition(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 4);
        TerminalSize labelBoxSize = new TerminalSize(sizeLabel.length() + 2, 3);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

        textGraphics.drawLine(labelBoxTopLeft.withRelativeColumn(1), labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2), Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1), labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2), Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), sizeLabel);
        textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        screen.refresh();
        screen.readInput();
        screen.stopScreen();
    }

    public void printRules(Terminal terminal) throws IOException {
        final TextGraphics textGraphics = terminal.newTextGraphics();
        try {
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.setBackgroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 1, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2, "Press N to start", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 1, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 2, "                ", SGR.BOLD);
        terminal.flush();
        }
        catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void printItems(Terminal terminal, GameSession game, char input) throws IOException {

        try {
            if (!game.getPlayer().playerInCorridor(game)) {
                switch (input) {
                    case 'h' -> printItemMenu(terminal, game, WEAPON);
                    case 'e' -> printItemMenu(terminal, game, SCROLL);
                    case 'j' -> printItemMenu(terminal, game, HEALTHKIT);
                    case 'k' -> printItemMenu(terminal, game, ELIXIR);
                    case 'i' -> printStatistic(terminal, game);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

public void printItemMenu(Terminal terminal, GameSession game, int type) throws IOException {
                final TextGraphics textGraphics = terminal.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                int number = 0;

                for (int i = 0; i < game.getItemsCount(); i++){
                    Entity cur_entity = game.getItemFromInventory(i);
                    if (cur_entity.getType() == type && cur_entity.getStatus() == IN_INVENTORY && type!= HEALTHKIT){
                        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2 + number, (number) + " " + cur_entity.toString(), SGR.BOLD);
                        number++;
                    }
                    if (type == HEALTHKIT && cur_entity.getStatus() == IN_INVENTORY && cur_entity.getHealth()>0){
                        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2 + number, (number) + " " + cur_entity.toString(), SGR.BOLD);
                        number++;
                    }
                }
                if (number == 0)
                    textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2 + number, "You have 0 Items", SGR.BOLD);
                else textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2 + number, "Choose by pressing 0-" + (number-1), SGR.BOLD);
                textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 1 + number, "Press any Q for exit", SGR.BOLD);
                terminal.flush();
                char choose = 0;
                int int_choose = 0;
                int flag=0;
                do{
                 choose = terminal.readInput().getCharacter();
                 int_choose = Character.getNumericValue(choose);
                    for (int i = 0; i < number; i++) {
                        if (i == int_choose) {
                            game.getPlayer().useItem(i, game, type);
                            terminal.flush();
                            printGame(terminal, game);
                            terminal.flush();
                            flag=1;
                            break;
                        }
                    }
                 if (choose == 'q'|| choose == 'Q') {
                     printGame(terminal, game);
                     terminal.flush();
                 }
                } while (choose != 'q' && choose != 'Q' && flag == 0);

        terminal.flush();
    }

        public void printInfo (Terminal terminal, GameSession game) throws IOException {
            final TextGraphics textGraphics = terminal.newTextGraphics();
            try {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
                textGraphics.putString( 2, MAP_HEIGHT + 1, "Level:     " + (game.getCurrentLevelNumber() + 1), SGR.BOLD);
                textGraphics.putString(15, MAP_HEIGHT + 1, "Health:   " + game.getPlayer().getHealth(), SGR.BOLD);
                textGraphics.putString(30, MAP_HEIGHT + 1, "Agility:   " + game.getPlayer().getAgility(), SGR.BOLD);
                textGraphics.putString(45, MAP_HEIGHT + 1, "Strength:  " + game.getPlayer().getStrength(), SGR.BOLD);
                textGraphics.putString(60, MAP_HEIGHT + 1, "Gold:  " + game.getPlayer().getGold(), SGR.BOLD);

                terminal.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void printResultOfGame (Terminal terminal, GameSession game) throws IOException {
            final TextGraphics textGraphics = terminal.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 3, "                        ", SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2, "      Game is Over      ", SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 1, "     Your level:  " + game.getCurrentLevelNumber() + "     ", SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2, "    Your health: " + game.getPlayer().getHealth() + "     ", SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 1, "Your quantity of Gold: " + game.getPlayer().getGold(), SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 2, "Drunk Elixirs " + game.getPlayer().getDrunkElixirCounter(), SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 3, "Eaten Food " + game.getPlayer().getEatenFoodCounter(), SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 4, "Read Scrolls " + game.getPlayer().getReadScrollCounter(), SGR.BOLD);
            textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 5, "                        ", SGR.BOLD);
            terminal.flush();
        }

    public void printStatistic(Terminal terminal, GameSession game) throws IOException {

        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
        List<s21.domain.Character> players = DataLayer.loadProgress();
        int count = players.size();
        s21.domain.Character.sortListOfCharacter(players);
        players.stream()
                .forEach(p-> textGraphics.putString(MAP_WIDTH/2 - 15, MAP_HEIGHT / 2 + players.indexOf(p)-5,
                        " Gold: " + p.getGold() +
                                "lvl: " +  p.getEndLevel() +
                                " Food: " + p.getEatenFoodCounter() +
                                " Elixirs: " + p.getDrunkElixirCounter() +
                                " Steps: " + p.getStepCount(),
                        SGR.BOLD));
        textGraphics.putString(2, MAP_HEIGHT / 2 + count - 4, "Press--Q--to--exit.", SGR.BOLD);
        terminal.flush();
        char choose = 0;
        do{
            choose = terminal.readInput().getCharacter();
            if (choose == 'q'|| choose == 'Q') {
                printGame(terminal, game);
                terminal.flush();
            }
        } while (choose != 'q' && choose != 'Q');
    }

    public void printInfoMessage(Terminal terminal, List<String> messages, GameSession game) throws IOException {
        if (!messages.isEmpty()) {
            final TextGraphics textGraphics = terminal.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
            printGame(terminal, game); // Отрисовываем поле
            messages.stream()
                    .forEach(m-> textGraphics.putString(MAP_WIDTH/2 - 15, MAP_HEIGHT / 2 + messages.indexOf(m), m,
                            SGR.BOLD));
        }
    }
}



