package com.quizapp.kovacszoltan.quizV1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    @BindView(R.id.txt_jelenkerdes) TextView txt_jelenkerdes;
    @BindView(R.id.Main_act) RelativeLayout Main_act;
    @BindView(R.id.txt_kerdesekszama) TextView txt_kerdesekszama;

    private com.quizapp.kovacszoltan.quizV1.DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Random r = new Random();
    List<Integer> regikerdes = new ArrayList<Integer>();
    List<String> kerdes = new ArrayList<String>();
    List<String> valasz1 = new ArrayList<String>();
    List<String> valasz2 = new ArrayList<String>();
    List<String> valasz3 = new ArrayList<String>();
    List<String> valasz4 = new ArrayList<String>();
    Cursor c;
    String kerdesselect_sql;
    int idIndex, kerdesIndex, valasz1index, valasz2index, valasz3index, valasz4index, jelenkerdesek, pontok=0;
    int gombsorrend, kerdesekszama = 0, kerdesek = 0;
    private String username, profile_image;
    private AlertDialog.Builder alert_vege, alert_kilep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mDBHelper = new com.quizapp.kovacszoltan.quizV1.DatabaseHelper(this);
        categorieshelper();
        init();
        ujkerdes();
        gombok();
    }
    public void init(){
        alert_vege = new AlertDialog.Builder(MainActivity.this);
        alert_kilep = new AlertDialog.Builder(MainActivity.this);
        alert_vege.setTitle("Elfogytak a kérédések!")
                .setMessage("Szeretnéd folytatni egy másik kategóriával?")
                .setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDBHelper.adatRogzites(username,pontok,profile_image);
                        SharedPreferences sharedPreferences = getSharedPreferences("Scores", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().commit();
                        Intent gomain = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(gomain);
                        finish();
                    }
                })
                .setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent ujkategoria = new Intent(MainActivity.this, CategoriesActivity.class);
                        startActivity(ujkategoria);
                        SharedPreferences sharedPreferences = getSharedPreferences("Scores", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putInt("pontok",pontok);
                        if(kerdesselect_sql.equals("* FROM quiz WHERE kategoria='jatekok'"))
                        {
                            editor.putInt("jatekok", 1);
                        } else if(kerdesselect_sql.equals("* FROM quiz WHERE kategoria='filmek'"))
                        {
                            editor.putInt("filmek", 1);
                        } else if(kerdesselect_sql.equals("* FROM quiz WHERE kategoria='etelital'"))
                        {
                            editor.putInt("etelital", 1);
                        } else if(kerdesselect_sql.equals("* FROM quiz WHERE kategoria='tortenelem'"))
                        {
                            editor.putInt("tortenelem", 1);
                        }

                        editor.commit();
                        finish();
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
                        mDBHelper.adatRogzites(username,pontok,profile_image);
                        Intent gomain = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(gomain);
                        finish();
                    }
                });
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        this.username = sharedPreferences.getString("username", "");
        this.profile_image = sharedPreferences.getString("image", "");
        SharedPreferences sharedPreferences2 = getSharedPreferences("Scores", Context.MODE_PRIVATE);
        this.pontok = sharedPreferences2.getInt("pontok", 0);
        Toast.makeText(MainActivity.this,Integer.toString(this.pontok),Toast.LENGTH_SHORT).show();
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
        disablebuttons();
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.right);
        mediaPlayer.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ujkerdes();
                gombok();
            }
        }, 500);

    }
    public void rosszvalasz(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        disablebuttons();
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(300); // for 500 ms
        }
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.wrong);
        mediaPlayer.start();
        Toast.makeText(MainActivity.this, "A helyes válasz: "+valasz4.get(this.kerdesek), Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ujkerdes();
                gombok();
            }
        }, 300);

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
            txt_kerdesekszama.setText(Integer.toString(c.getCount()));
            txt_jelenkerdes.setText(Integer.toString(kerdesekszama+1));

            kerdesekszama++;
            //gombok();
        } else if (kerdesekszama == c.getCount())
        {
            tv_kerdes.setText("A kérdések elfogytak!");
            alert_vege.show();
            //mDBHelper.adatRogzites(this.username,this.pontok);
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
    } else if (this.kerdesselect_sql.equals("* FROM quiz WHERE kategoria='tortenelem'")) {
        Main_act.setBackgroundResource(R.drawable.history_bg);
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
    public void disablebuttons(){
    btn_valasz1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    btn_valasz2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    btn_valasz3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    btn_valasz4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
}

}

