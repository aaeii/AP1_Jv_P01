package s21.controller;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static s21.domain.GameConstants.*;

public class UserInput {
    private int Action;

    public UserInput() {
        Action = ' ';
    }

    public void waitForInput(Terminal terminal) throws IOException {
        try {
            KeyStroke keyStroke = terminal.readInput();
            Action = (int) keyStroke.getCharacter();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getAction() {
        return Action;
    }

    public void waitForResumeOrExit(Terminal terminal) throws IOException {
        try {
            KeyStroke keyStroke = terminal.readInput();
            Action = (int) keyStroke.getCharacter();
            while (Action != 'Q' && Action != 'q' && Action != 'n' && Action != 'N') {
                keyStroke = terminal.readInput();
                Action = (int) keyStroke.getCharacter();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //    public void waitForExit() {
//        InputChar inputChar = Toolkit.readCharacter();
//        userInput = inputChar.getCode();
//        while (userInput != 'Q' && userInput != 'q') {
//            inputChar = Toolkit.readCharacter();
//            userInput = inputChar.getCode();
//        }
//    }
//
//    public void waitForResumeOrExit() {
//        InputChar inputChar = Toolkit.readCharacter();
//        userInput = inputChar.getCode();
//        while (userInput != 'Q' && userInput != 'q' && userInput != 'n' && userInput != 'N') {
//            inputChar = Toolkit.readCharacter();
//            userInput = inputChar.getCode();
//        }
//    }
//
//    public int returnInput() {
//        return userInput;
//    }
}