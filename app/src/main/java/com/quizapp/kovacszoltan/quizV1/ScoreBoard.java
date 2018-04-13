package com.quizapp.kovacszoltan.quizV1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreBoard extends AppCompatActivity {
    @BindView(R.id.tvuser1) TextView tvuser1;
    @BindView(R.id.tvpont1) TextView tvpont1;
    @BindView(R.id.tvuser2) TextView tvuser2;
    @BindView(R.id.tvpont2) TextView tvpont2;
    @BindView(R.id.tvuser3) TextView tvuser3;
    @BindView(R.id.tvpont3) TextView tvpont3;
    @BindView(R.id.tvuser4) TextView tvuser4;
    @BindView(R.id.tvpont4) TextView tvpont4;
    @BindView(R.id.tvuser5) TextView tvuser5;
    @BindView(R.id.tvpont5) TextView tvpont5;
    @BindView(R.id.first_place_score) TextView first_place_score;
    @BindView(R.id.first_place) CircleImageView first_place;
    @BindView(R.id.clearscore) Button clearscore;
    private com.quizapp.kovacszoltan.quizV1.DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Cursor c;
    List<Integer> pontok_szama = new ArrayList<Integer>();
    List<String> pont_nev = new ArrayList<String>();
    List<String> image = new ArrayList<String>();
    int id, nev, pont, max, profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        ButterKnife.bind(this);
        mDBHelper = new com.quizapp.kovacszoltan.quizV1.DatabaseHelper(this);
        databasetolist();
        listtotable();
        first_place();
        clearscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDb = mDBHelper.getWritableDatabase();
                //mDb.execSQL("TRUNCATE table scoreboard");
                mDb.execSQL("delete from Scoreboard");
                pontok_szama.clear();
                pont_nev.clear();
                first_place.setImageResource(getResources().getIdentifier("com.quizapp.kovacszoltan.quizV1:drawable/profile_placeholder", null, null));
                listtotable();

            }
        });
    }



    private void databasetolist(){
        mDb = mDBHelper.getReadableDatabase();
        this.c = mDb.rawQuery("SELECT * FROM Scoreboard order by Pontok_szama desc", null);
        c.moveToFirst();
        this.max = c.getCount();
        for(int i=0; i < this.max; i++)
        {
            this.id = c.getColumnIndex("ID");
            this.nev = c.getColumnIndex("Username");
            this.pont = c.getColumnIndex("Pontok_szama");
            this.profile_image = c.getColumnIndex("Profile_image");
            pontok_szama.add(c.getInt(pont));
            pont_nev.add(c.getString(nev));
            image.add(c.getString(profile_image));
            c.moveToNext();
        }
    }
    private void first_place(){
        if (image.size()>0)
        {
            byte[] imageAsBytes = Base64.decode(image.get(0).getBytes(), Base64.DEFAULT);
            if (imageAsBytes.length == 0){
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.profile_placeholder);
                first_place.setImageBitmap(bitmap);
            }else{
                first_place.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
            first_place_score.setText(pontok_szama.get(0) + " pont");
        }
    }
    private void listtotable(){
        while (pontok_szama.size()<6)
        {
            pontok_szama.add(0);
            pont_nev.add("");
        }

            tvuser1.setText(pont_nev.get(1).toString());
        if (pontok_szama.get(1).equals(0)){
            tvpont1.setText("");
        } else {
            tvpont1.setText(pontok_szama.get(1).toString());
        }
            tvuser2.setText(pont_nev.get(2).toString());
        if (pontok_szama.get(2).equals(0)){
            tvpont2.setText("");
        } else {
            tvpont2.setText(pontok_szama.get(2).toString());
        }
            tvuser3.setText(pont_nev.get(3).toString());
        if (pontok_szama.get(3).equals(0)){
            tvpont3.setText("");
        } else {
            tvpont3.setText(pontok_szama.get(3).toString());
        }
            tvuser4.setText(pont_nev.get(4).toString());
        if (pontok_szama.get(4).equals(0)){
            tvpont4.setText("");
        } else {
            tvpont4.setText(pontok_szama.get(4).toString());
        }
            tvuser5.setText(pont_nev.get(5).toString());
        if (pontok_szama.get(5).equals(0)){
            tvpont5.setText("");
        } else {
            tvpont5.setText(pontok_szama.get(5).toString());
        }


    }
}
