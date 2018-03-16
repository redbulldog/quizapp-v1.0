package com.example.kovacszoltan.quizV1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_kerdes) TextView tv_kerdes;
    @BindView(R.id.btn_valasz1) Button btn_valasz1;
    @BindView(R.id.btn_valasz2) Button btn_valasz2;
    @BindView(R.id.btn_valasz3) Button btn_valasz3;
    @BindView(R.id.btn_valasz4) Button btn_valasz4;
    @BindView(R.id.Main_act) RelativeLayout Main_act;
    @BindView(R.id.txt_kerdesekszama) TextView txt_kerdesekszama;

    private com.example.kovacszoltan.quizV1.DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Random r = new Random();
    List<Integer> regikerdes = new ArrayList<Integer>();
    List<String> kerdes = new ArrayList<String>();
    List<String> valasz1 = new ArrayList<String>();
    List<String> valasz2 = new ArrayList<String>();
    List<String> valasz3 = new ArrayList<String>();
    List<String> valasz4 = new ArrayList<String>();
    Cursor c;
    String kerdesekszama_sql, kerdesselect_sql;
    int idIndex, kerdesIndex, valasz1index, valasz2index, valasz3index, valasz4index, jelenkerdesek, pontok=0;
    int gombsorrend, life = 5, elozogombsorrend = 0, kerdesekszama = 0, kerdesek = 0;
    private String username;
    private AlertDialog.Builder alert_vesztett, alert_kilep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDBHelper = new com.example.kovacszoltan.quizV1.DatabaseHelper(this);
        categorieshelper();
        init();
        ujkerdes();
        gombok();
    }
    public void init(){
        alert_vesztett = new AlertDialog.Builder(MainActivity.this);
        alert_kilep = new AlertDialog.Builder(MainActivity.this);
        alert_vesztett.setTitle("Vesztettél!")
                .setMessage("Újra akarod kezdeni a játékot?")
                .setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setCancelable(false)
                .create();
        alert_kilep.setTitle("Biztosan kilép?")
                .setMessage("A jelenlegi állás elveszik!")
                .setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setCancelable(true)
                .setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent gomain = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(gomain);
                        finish();
                    }
                });
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        this.username = sharedPreferences.getString("username", "");
        //Toast.makeText(MainActivity.this,this.username.toString(),Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        alert_kilep.show();
    }
    public void gombok(){
        switch (this.gombsorrend){
            case 1:
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz1.setText(valasz1.get(this.kerdesek));
                btn_valasz2.setText(valasz2.get(this.kerdesek));
                btn_valasz3.setText(valasz3.get(this.kerdesek));
                btn_valasz4.setText(valasz4.get(this.kerdesek));
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ujkerdes();
                        helyesvalasz();

                    }
                });
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
            case 2:
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz4.setText(valasz1.get(this.kerdesek));
                btn_valasz3.setText(valasz2.get(this.kerdesek));
                btn_valasz1.setText(valasz3.get(this.kerdesek));
                btn_valasz2.setText(valasz4.get(this.kerdesek));
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ujkerdes();
                        helyesvalasz();

                    }
                });
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
            case 3:
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz2.setText(valasz1.get(this.kerdesek));
                btn_valasz3.setText(valasz2.get(this.kerdesek));
                btn_valasz4.setText(valasz3.get(this.kerdesek));
                btn_valasz1.setText(valasz4.get(this.kerdesek));
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ujkerdes();
                        helyesvalasz();

                    }
                });
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
            case 4:
                tv_kerdes.setText(kerdes.get(this.kerdesek));
                btn_valasz2.setText(valasz1.get(this.kerdesek));
                btn_valasz4.setText(valasz2.get(this.kerdesek));
                btn_valasz1.setText(valasz3.get(this.kerdesek));
                btn_valasz3.setText(valasz4.get(this.kerdesek));
                btn_valasz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ujkerdes();
                        helyesvalasz();

                    }
                });
                btn_valasz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                btn_valasz4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rosszvalasz();
                    }
                });
                break;
        }
    }
    public void helyesvalasz(){
        pontok++;
    }
    public void rosszvalasz(){
        /*if (life > 1){
            life--;
        } else{
            alert_vesztett.show();
        }*/
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(300); // for 500 ms
        }

        ujkerdes();
        pontok--;
    }
    public void ujkerdes() {

        this.gombsorrend = r.nextInt(4) + 1;
        mDb = mDBHelper.getWritableDatabase();
        this.c = mDb.rawQuery("SELECT " + this.kerdesselect_sql, null);
        jelenkerdesek = c.getCount();
        //Toast.makeText(MainActivity.this, Integer.toString(c.getCount()), Toast.LENGTH_SHORT).show();
        databasetolist();
        if (kerdesekszama < c.getCount()) {
            this.kerdesek = r.nextInt(c.getCount());
            do {
                if (regikerdes.contains(kerdesek)) {
                    this.kerdesek = r.nextInt(c.getCount());
                }
            } while (regikerdes.contains(this.kerdesek));

            //Toast.makeText(MainActivity.this, Integer.toString(this.kerdesek), Toast.LENGTH_SHORT).show();
            regikerdes.add(this.kerdesek);
            txt_kerdesekszama.setText(Integer.toString(kerdesekszama+1) +" / " + Integer.toString(c.getCount()));

            kerdesekszama++;
            gombok();
        } else if (kerdesekszama == c.getCount())
        {
            tv_kerdes.setText("A kérdések elfogytak!");
            mDBHelper.adatRogzites(this.username,this.pontok);
            Toast.makeText(MainActivity.this,Integer.toString(pontok),Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(MainActivity.this, Integer.toString(c.getCount()), Toast.LENGTH_SHORT).show();






    }
private void categorieshelper() {
    SharedPreferences sharedPreferences = getSharedPreferences("Categories", Context.MODE_PRIVATE);
    this.kerdesselect_sql = sharedPreferences.getString("kerdesselect", "");
    if (this.kerdesselect_sql.equals("* FROM quiz WHERE kategoria='jatekok'")) {
        Main_act.setBackgroundResource(R.drawable.games_bg);
    } else if (this.kerdesselect_sql.equals("* FROM quiz WHERE kategoria='etelital'")) {
        Main_act.setBackgroundResource(R.drawable.food_bg);
    }
}

private void databasetolist(){
    c.moveToFirst();
    for(int i=0; i < this.jelenkerdesek; i++)
    {
        this.idIndex = c.getColumnIndex("id");
        this.kerdesIndex = c.getColumnIndex("kerdes");
        this.valasz1index = c.getColumnIndex("valasz1");
        this.valasz2index = c.getColumnIndex("valasz2");
        this.valasz3index = c.getColumnIndex("valasz3");
        this.valasz4index = c.getColumnIndex("valasz4");
        kerdes.add(c.getString(kerdesIndex));
        valasz1.add(c.getString(valasz1index));
        valasz2.add(c.getString(valasz2index));
        valasz3.add(c.getString(valasz3index));
        valasz4.add(c.getString(valasz4index));

        c.moveToNext();
    }
}

}

