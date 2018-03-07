package com.example.kovacszoltan.quizV1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard extends AppCompatActivity {
    private com.example.kovacszoltan.quizV1.DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Cursor c;
    List<Integer> pontok_szama = new ArrayList<Integer>();
    List<String> pont_nev = new ArrayList<String>();
    int id, nev, pont, max;
    String teszt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        mDBHelper = new com.example.kovacszoltan.quizV1.DatabaseHelper(this);
        databasetolist();
        Toast.makeText(ScoreBoard.this,pontok_szama.get(0).toString()+ " + " + pont_nev.get(0).toString() , Toast.LENGTH_SHORT).show();
        //teszt = pontok_szama.get(0).toString();
    }



    private void databasetolist(){
        mDb = mDBHelper.getReadableDatabase();
        this.c = mDb.rawQuery("SELECT * FROM Scoreboard", null);
        c.moveToFirst();
        this.max = c.getCount();
        for(int i=0; i < this.max; i++)
        {
            this.id = c.getColumnIndex("ID");
            this.nev = c.getColumnIndex("Username");
            this.pont = c.getColumnIndex("Pontok_szama");
            pontok_szama.add(c.getInt(pont));
            pont_nev.add(c.getString(nev));
            c.moveToNext();
        }
    }
}
