package s21.presentation;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import s21.domain.GameSession;

import java.io.IOException;

import static s21.domain.GameConstants.*;


public class Print {
    private char[][] field;
//    private Character player;

    public Print() {
        field = new char[MAP_HEIGHT][MAP_WIDTH];
    }

    public void setField(char[][] playground) {
        this.field = playground;
    }

    public Terminal createTerminal() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        TerminalSize screenSize = terminal.getTerminalSize();
        terminal.setCursorVisible(false);

        terminal.flush();

        return terminal;
    }

    public void printOnlyField(Terminal terminal) throws IOException {
        terminal.clearScreen();
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++)
                terminal.putCharacter(field[i][j]);
        }
        terminal.flush();
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
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.setBackgroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 1, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2, "Press N to start", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 1, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 2, "                ", SGR.BOLD);
        terminal.flush();
    }

    public void printInfo(Terminal terminal, GameSession game) throws IOException {
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.setBackgroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(1, 0, "Level  " + (game.getCurrentLevelNumber()+1), SGR.UNDERLINE);
        terminal.flush();
    }
    public void printResultOfGame(Terminal terminal, GameSession game) throws IOException {
        final TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.setBackgroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 2, "  Game is Over  ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 - 1, "Your level: " + game.getCurrentLevelNumber(), SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2, "Your health: " + game.getPlayer().getHealth(), SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 1, "Your quantity of Gold: " + game.getPlayer().getHealth(), SGR.BOLD);
        textGraphics.putString(MAP_WIDTH / 2 - 10, MAP_HEIGHT / 2 + 2, "                ", SGR.BOLD);
        terminal.flush();
    }


}

