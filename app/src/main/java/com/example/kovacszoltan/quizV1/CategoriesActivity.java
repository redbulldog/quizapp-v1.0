package com.example.kovacszoltan.quizV1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {
    @BindView(R.id.ctg_movies) ImageView ctgmovies;
    @BindView(R.id.ctg_games) ImageView ctggames;
    @BindView(R.id.ctg_all) ImageView ctgall;
    @BindView(R.id.ctg_food) ImageView ctgfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        action();
    }
    private void action(){
        ctgmovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kerdesselect", "* FROM quiz WHERE kategoria='filmek'");
                editor.commit();
                Intent startgame = new Intent(CategoriesActivity.this, MainActivity.class);
                startActivity(startgame);
            }
        });
        ctgall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kerdesselect", "* FROM quiz");
                editor.commit();
                Intent startgame = new Intent(CategoriesActivity.this, MainActivity.class);
                startActivity(startgame);
            }
        });
        ctggames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kerdesselect", "* FROM quiz WHERE kategoria='jatekok'");
                editor.commit();
                Intent startgame = new Intent(CategoriesActivity.this, MainActivity.class);
                startActivity(startgame);
            }
        });
        ctgfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kerdesekszama", "SELECT count(kerdes) FROM quiz WHERE kategoria='etelital'");
                editor.putString("kerdesselect", "* FROM quiz WHERE kategoria='etelital'");
                editor.commit();
                Intent startgame = new Intent(CategoriesActivity.this, MainActivity.class);
                startActivity(startgame);
            }
        });
    }
}
