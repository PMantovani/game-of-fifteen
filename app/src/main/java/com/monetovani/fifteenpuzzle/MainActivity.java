package com.monetovani.fifteenpuzzle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<View> buttonsArray = new ArrayList<>();
        buttonsArray.add(findViewById(R.id.btTile00));
        buttonsArray.add(findViewById(R.id.btTile01));
        buttonsArray.add(findViewById(R.id.btTile02));
        buttonsArray.add(findViewById(R.id.btTile03));
        buttonsArray.add(findViewById(R.id.btTile10));
        buttonsArray.add(findViewById(R.id.btTile11));
        buttonsArray.add(findViewById(R.id.btTile12));
        buttonsArray.add(findViewById(R.id.btTile13));
        buttonsArray.add(findViewById(R.id.btTile20));
        buttonsArray.add(findViewById(R.id.btTile21));
        buttonsArray.add(findViewById(R.id.btTile22));
        buttonsArray.add(findViewById(R.id.btTile23));
        buttonsArray.add(findViewById(R.id.btTile30));
        buttonsArray.add(findViewById(R.id.btTile31));
        buttonsArray.add(findViewById(R.id.btTile32));
        buttonsArray.add(findViewById(R.id.btTile33));

        mGame = new Game(buttonsArray);
        mGame.restartGame();
    }

    public void onTileClick(View view) {
        if (mGame.executeMove(view)) {
            if (mGame.checkPuzzleFinished()) {
                showWinAlert();
            }
        }
    }

    public void onRestartClick(View view) {
        mGame.restartGame();
    }


    private void showWinAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Congratulations");
        alertDialog.setMessage("Congratulations! You have finished the puzzle!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
