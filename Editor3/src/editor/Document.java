/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.display.CharacterDisplay;


import java.util.*;

/**
 * This class represents the document being edited. Using a 2d array to hold the
 * document content is probably not a very good choice. Fixing this class is the
 * main focus of the exercise. In addition to designing a better data model, you
 * must add methods to do at least basic editing: write and delete text, and
 * moving the cursor
 *
 * @author evenal
 */
public class Document {

    private CharacterDisplay display;
    private int cursorRow;
    private int cursorCol;
    private char[][] data;
    private LinkedList<LinkedList> linkedColumn = new LinkedList<>();


    public Document(CharacterDisplay display) {
        this.display = display;
        cursorCol = cursorRow = 0;

        initializeLinky();
    }

    // Initialize the Linked Lists
    private void initializeLinky() {

        // Create 20 rows to the board
        for (int i = 0; i < 20; i++) {
            linkedColumn.add(new LinkedList<Character>());
        }

        // First access the outer list, then fill the inner lists with Chars.
        for (int i = 0; i < 20; i++) {
            LinkedList row = linkedColumn.get(i);

            for (int j = 0; j < 40; j++) {
                row.add(j, ' ');


            }
        }

    }


    private void shiftChars() {

        // Keeps track of total number of iterations
        int iterations = 0;

        for(int i = cursorCol; i < 40; i= i+3){

            Character current = (Character) linkedColumn.get(cursorRow).get(i);
            Character next = (Character) linkedColumn.get(cursorRow).get(i + 1);

            linkedColumn.get(cursorRow).add(i + 1, current);
            linkedColumn.get(cursorRow).add(i + 2, next);


            linkedColumn.get(cursorRow).removeLast();


        }
        for(int i = cursorCol; i < 40; i++) {
            display.displayChar((Character) linkedColumn.get(cursorRow).get(cursorCol), cursorRow, i);
        }
    }

    public void insertChar(char c) {
        shiftChars();

        linkedColumn.get(cursorRow).add(cursorCol, c);


        display.displayChar(c, cursorRow, cursorCol);
        display.displayCursor(' ', cursorRow, cursorCol);


        if (cursorCol == 39 && cursorRow == 19) {
        } else {
            cursorCol++;
            if (cursorCol >= CharacterDisplay.WIDTH) {
                cursorCol = 0;
                cursorRow++;
            }
        }
    }


    public void removeChar(char c) {
        if (linkedColumn.get(cursorRow).contains(cursorCol)) {
            linkedColumn.get(cursorRow).remove();
        }

        display.displayChar(' ', cursorRow, cursorCol);

        if (cursorCol == 0 && cursorRow == 0) {

        } else if (cursorCol == 0 && cursorRow >= 0) {
            cursorCol = 39;
            cursorRow--;
        } else {
            cursorCol--;
        }
        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void shiftChar(char c) {
        linkedColumn.get(cursorRow).add(c);

        display.displayChar(c, cursorRow, cursorCol);
        if (cursorRow < 19) {
            int ant = CharacterDisplay.WIDTH - cursorCol;
            System.out.println(ant);
            for (int i = 0; i < ant; i++) {
                linkedColumn.get(cursorRow).add('c');
            }
            System.out.println(linkedColumn.get(cursorRow).size());
            cursorCol = 0;
            cursorRow++;
        }
        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void moveCursorUp() {
        if (cursorRow == 0) {

        } else if (cursorRow > 0) {
            cursorRow--;
        }
        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void moveCursorDown() {
        if (cursorCol == 19) {

        } else if (cursorRow < 19) {
            cursorRow++;
        }
        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void moveCursorRight() {

        if (cursorCol == 39 && cursorRow == 19) {
        } else {
            cursorCol++;
            if (cursorCol >= CharacterDisplay.WIDTH) {
                cursorCol = 0;
                cursorRow++;
            }
        }

        display.displayCursor(' ', cursorRow, cursorCol);
    }

    public void moveCursorLeft() {
        if (cursorCol == 0 && cursorRow == 0) {

        } else if (cursorCol == 0 && cursorRow >= 0) {
            cursorCol = 39;
            cursorRow--;
        } else {
            cursorCol--;
        }
        display.displayCursor(' ', cursorRow, cursorCol);
    }
}

