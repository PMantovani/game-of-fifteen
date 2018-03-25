package com.monetovani.fifteenpuzzle;

import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by pmant on 19-Mar-18.
 */

class Game {
    private final int BOARD_LENGTH = 4;
    private final int INITIAL_REVERSAL_MOVES = 10;
    private int mPlayCount = 0;
    private Date mStartTime;
    private HashMap<Integer, Tile> mTilesMap = new HashMap<>();
    private Tile[][] mIndexedTiles = new Tile[BOARD_LENGTH][BOARD_LENGTH];
    private boolean mWon = false;
    private int mEmptyRow = BOARD_LENGTH-1;
    private int mEmptyCol = BOARD_LENGTH-1;

    Game(ArrayList<View> buttonArray) {
        int counter = 0;

        for (int i=0; i<BOARD_LENGTH; i++) {
            for (int j=0; j<BOARD_LENGTH; j++) {
                View button = buttonArray.get(counter);
                Tile tile = new Tile(button, counter+1, i, j);
                mIndexedTiles[i][j] = tile; // Insert to Tile matrix
                mTilesMap.put(button.getId(), tile); // Insert to Tile map
                counter++;
            }
        }
    }

    void restartGame() {
        initializeTiles();
        shuffleTiles();
        mWon = false;
        mPlayCount = 0;
    }

    boolean executeMove(View view) {
        // Gets Tile corresponding to the view
        Tile tile = mTilesMap.get(view.getId());

        if (mWon) {
            return false; // If game is finished, don't do anything
        }

        // Checks if tile is adjacent to empty tile
        if (isTileAdjacentToEmpty(tile)) {
            Tile emptyTile = mIndexedTiles[mEmptyRow][mEmptyCol];
            swapTileValues(tile, emptyTile);
        }

        // Checks if after this move, the game has been finished
        if (!hasWon()) {
            mPlayCount++;
        }

        return true;
    }

    private void initializeTiles() {
        int counter = 1;
        for (int i=0; i<BOARD_LENGTH; i++) {
            for (int j=0; j<BOARD_LENGTH; j++) {
                if (i==BOARD_LENGTH-1 && j==BOARD_LENGTH-1) {
                    mIndexedTiles[i][j].setValue(0);
                }
                else {
                    mIndexedTiles[i][j].setValue(counter);
                }
                counter++;
            }
        }

        mEmptyRow = BOARD_LENGTH-1;
        mEmptyCol = BOARD_LENGTH-1;
    }

    private void swapTileValues(Tile tile1, Tile tile2) {
        int value1 = tile1.getValue();
        int value2 = tile2.getValue();

        tile1.setValue(value2);
        tile2.setValue(value1);

        if (tile1.isEmpty()) {
            mEmptyRow = tile1.getRow();
            mEmptyCol = tile1.getColumn();
        }
        else if (tile2.isEmpty()) {
            mEmptyRow = tile2.getRow();
            mEmptyCol = tile2.getColumn();
        }
    }

    private boolean isTileAdjacentToEmpty(Tile tile) {
        int row = tile.getRow();
        int column = tile.getColumn();

        if ((row == mEmptyRow-1 && column == mEmptyCol) ||
                (row == mEmptyRow+1 && column == mEmptyCol) ||
                (row == mEmptyRow && column == mEmptyCol-1) ||
                (row == mEmptyRow && column == mEmptyCol+1)) {
            return true;
        }

        return false;
    }

    private void shuffleTiles() {
        int movesLeft = INITIAL_REVERSAL_MOVES;
        Random rnd = new Random();
        // These are just initial values that will stop the initial random move to go right.
        boolean lastMoveWasRow = true;
        boolean lastMoveWasIncrement = false;

        while (movesLeft > 0) {
            boolean incrementRow = rnd.nextBoolean();
            boolean incrementCol = rnd.nextBoolean();
            boolean swapRow = rnd.nextBoolean();
            Tile tileToSwap;

            // Stops the program from repeating last move
            if (lastMoveWasRow && lastMoveWasIncrement) incrementRow = true;
            else if (!lastMoveWasRow && lastMoveWasIncrement) incrementCol = true;
            else if (lastMoveWasRow && !lastMoveWasIncrement) incrementRow = false;
            else if (!lastMoveWasRow && !lastMoveWasIncrement) incrementCol = false;
            // Validations of possible moves
            if (mEmptyRow == BOARD_LENGTH-1) incrementRow = false;
            else if (mEmptyRow == 0 ) incrementRow = true;
            if (mEmptyCol == BOARD_LENGTH-1) incrementCol = false;
            else if (mEmptyCol == 0) incrementCol = true;

            // Checks which tile will be swapped
            if (swapRow) {
                if (incrementRow) {
                    tileToSwap = mIndexedTiles[mEmptyRow+1][mEmptyCol];
                    lastMoveWasIncrement = true;
                }
                else {
                    tileToSwap = mIndexedTiles[mEmptyRow-1][mEmptyCol];
                    lastMoveWasIncrement = false;
                }
                lastMoveWasRow = true;
            }
            else {
                if (incrementCol) {
                    tileToSwap = mIndexedTiles[mEmptyRow][mEmptyCol+1];
                    lastMoveWasIncrement = true;
                }
                else {
                    tileToSwap = mIndexedTiles[mEmptyRow][mEmptyCol-1];
                    lastMoveWasIncrement = false;
                }
                lastMoveWasRow = false;
            }

            // Actually swap tile and subtract number of moves left
            swapTileValues(mIndexedTiles[mEmptyRow][mEmptyCol], tileToSwap);
            movesLeft--;
        }
    }

    private boolean hasWon() {
        int checkValue = 1;

        for (int i=0; i<BOARD_LENGTH; i++) {
            for (int j=0; j<BOARD_LENGTH; j++) {
                if (mIndexedTiles[i][j].getValue() != checkValue &&
                        !(i==BOARD_LENGTH-1 && j==BOARD_LENGTH-1)) {
                    mWon = false;
                    return false;
                }

                checkValue++;
            }
        }
        mWon = true;
        return true;
    }

    public boolean checkPuzzleFinished() {
        return mWon;
    }


}
