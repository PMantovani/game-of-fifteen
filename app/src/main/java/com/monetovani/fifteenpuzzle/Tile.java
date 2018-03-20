package com.monetovani.fifteenpuzzle;

import android.view.View;
import android.widget.Button;

/**
 * Created by pmant on 19-Mar-18.
 */

public class Tile {
    private int mRow;
    private int mColumn;
    private int mValue;
    private Button mView;

    Tile(View view, int value, int row, int column) {
        this.mValue = value;
        this.mView = (Button) view;
        this.mRow = row;
        this.mColumn = column;

        setValue(value);
    }

    public boolean isEmpty() {
        return mValue == 0;
    }

    public void setValue(int value) {
        this.mValue = value;

        if (this.mValue == 0) {
            mView.setText("");
        }
        else {
            mView.setText(Integer.toString(value));
        }
    }


    public int getValue() {
        return this.mValue;
    }

    public int getRow() {
        return this.mRow;
    }

    public int getColumn() {
        return this.mColumn;
    }
}
