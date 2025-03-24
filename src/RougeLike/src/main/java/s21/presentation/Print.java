package s21.presentation;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import s21.controller.UserInput;

import java.io.IOException;

import static s21.domain.GameConstants.*;


public class Print {
    private char[][] field;
//    private Character player;

    public  Print() {
        field = new char[MAP_HEIGHT][MAP_WIDTH];
    }

    public void setField(char[][] playground) {
        this.field = playground;
    }

//    public void printField() throws IOException {
//        Terminal terminal = new DefaultTerminalFactory().createTerminal();
////        terminal.setBackgroundColor(TextColor.ANSI.BLUE);
////        terminal.setForegroundColor(TextColor.ANSI.YELLOW);
//        Screen screen = new TerminalScreen(terminal);
//        screen.startScreen();
//        screen.setCursorPosition(null);
//        screen.clear();
//        TerminalPosition startPosition = terminal.getCursorPosition();
//        for (int i=0; i < MAP_HEIGHT; i++ ){
//            for (int j=0; j < MAP_WIDTH; j++ ) {
//                if (field[i][j] == WALL_CHAR) {
//                    screen.setCharacter(j, i, new TextCharacter(
//                            ' ',
//                            TextColor.ANSI.DEFAULT,
//                            TextColor.ANSI.BLUE));
//                }
//                else if ( field[i][j] == CORRIDOR_CHAR){
//                    screen.setCharacter(j, i, new TextCharacter(
//                            ' ',
//                            TextColor.ANSI.DEFAULT,
//                            TextColor.ANSI.WHITE));
//                }
//                else screen.setCharacter(j, i, new TextCharacter(field[i][j]));
//            }
//        }
////        printStartMenu(terminal);
//        screen.refresh();
//        screen.readInput();
//        screen.stopScreen();
//    }

public Terminal printField() throws IOException {
    Terminal terminal = new DefaultTerminalFactory().createTerminal();
    TerminalSize screenSize = terminal.getTerminalSize();
    terminal.setCursorVisible(false);
//    Screen screen = new TerminalScreen(terminal);
//    screen.startScreen();
//    screen.clear();
//    UserInput Action = new UserInput();
//    Action.waitForInput(terminal);
//    System.out.println((Action.getAction()));
    for (int i=0; i < MAP_HEIGHT; i++ ){
        for (int j=0; j < MAP_WIDTH; j++ )
            terminal.putCharacter(field[i][j]);
    }
    terminal.flush();
//    screen.refresh();
//    screen.readInput();
//    screen.stopScreen();
    return terminal;
}


    private void  printStartMenu(Terminal terminal) throws IOException {
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

        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
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
        final  TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.setBackgroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(MAP_WIDTH/2-10, MAP_HEIGHT/2-2, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH/2-10, MAP_HEIGHT/2-1, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH/2-10, MAP_HEIGHT/2, "Press N to start", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH/2-10, MAP_HEIGHT/2+1, "                ", SGR.BOLD);
        textGraphics.putString(MAP_WIDTH/2-10, MAP_HEIGHT/2+2, "                ", SGR.BOLD);
        terminal.flush();
    }
}


