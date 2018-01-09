package com.example.kovacszoltan.designanddatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    private Button btn_newgame, btn_resoult, btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        actions();
    }
    private void init(){
        btn_newgame = (Button) findViewById(R.id.btn_newgame);
        btn_resoult = (Button) findViewById(R.id.btn_scoreboard);
        btn_exit = (Button) findViewById(R.id.btn_exit);
    }
    private void actions(){
        btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newgame = new Intent(MainMenu.this,MainActivity.class);
                startActivity(newgame);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
