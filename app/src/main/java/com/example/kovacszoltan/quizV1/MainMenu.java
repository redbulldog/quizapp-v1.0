package com.example.kovacszoltan.quizV1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenu extends AppCompatActivity {
    @BindView(R.id.btn_newgame) Button btn_newgame;
    @BindView(R.id.btn_scoreboard) Button btn_scoreboard;
    @BindView(R.id.btn_exit) Button btn_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        actions();
    }

    private void actions(){
        btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newgame = new Intent(MainMenu.this,CategoriesActivity.class);
                startActivity(newgame);
            }
        });
        btn_scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scoreboard = new Intent(MainMenu.this,ScoreBoard.class);
                startActivity(scoreboard);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
    public void onBackPressed(){
        finishAffinity();
    }
}
