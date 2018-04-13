package com.quizapp.kovacszoltan.quizV1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {
    @BindView(R.id.ctg_movies) ImageView ctgmovies;
    @BindView(R.id.ctg_games) ImageView ctggames;
    @BindView(R.id.ctg_food) ImageView ctgfood;
    @BindView(R.id.ctg_history) ImageView ctghistory;
    @BindView(R.id.txt_movies) TextView txt_movies;
    @BindView(R.id.txt_games) TextView txt_games;
    @BindView(R.id.txt_food) TextView txt_food;
    @BindView(R.id.txt_history) TextView txt_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        action();
        hideused();
    }
    private void action(){
        ctgmovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickaction( "filmek");

            }
        });
        ctggames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickaction("jatekok");
            }
        });
        ctgfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickaction("etelital");
            }
        });
        ctghistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickaction("tortenelem");
            }
        });
    }
    public void onBackPressed(){
        Intent mainmenu = new Intent(CategoriesActivity.this,MainMenu.class);
        startActivity(mainmenu);
    }
    private void onclickaction(String sqlcommand){
        SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("kerdesselect", "* FROM quiz WHERE kategoria='"+sqlcommand+"'");
        editor.commit();
        Intent startgame = new Intent(CategoriesActivity.this, MainActivity.class);
        startActivity(startgame);
        finish();
    }
    public void hideused(){
        SharedPreferences sharedPreferences = getSharedPreferences("Scores", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("jatekok")){
            ctggames.setVisibility(View.GONE);
            txt_games.setVisibility(View.GONE);
        }
        if (sharedPreferences.contains("filmek")){
            ctgmovies.setVisibility(View.GONE);
            txt_movies.setVisibility(View.GONE);
        }
        if (sharedPreferences.contains("etelital")){
            ctgfood.setVisibility(View.GONE);
            txt_food.setVisibility(View.GONE);
        }
        if (sharedPreferences.contains("tortenelem")){
            ctghistory.setVisibility(View.GONE);
            txt_history.setVisibility(View.GONE);
        }
    }
}
