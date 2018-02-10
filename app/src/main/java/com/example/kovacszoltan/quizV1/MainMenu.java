package com.example.kovacszoltan.quizV1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenu extends AppCompatActivity {
    @BindView(R.id.btn_newgame) Button btn_newgame;
    @BindView(R.id.btn_scoreboard) Button btn_scoreboard;
    @BindView(R.id.btn_exit) Button btn_exit;
    @BindView(R.id.btn_felh_nev) Button btn_felh_nev;
    @BindView(R.id.profile_image) de.hdodenhof.circleimageview.CircleImageView profile_image;
    private AlertDialog.Builder felh_nev_input;
    private String usernameexists, profilRaw;
    private int PICK_IMAGE_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        actions();
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        this.usernameexists = sharedPreferences.getString("username", "");
        if (this.usernameexists.isEmpty())
        {
            felhasznalonev(false);
        }

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
        btn_felh_nev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                felhasznalonev(true);
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();

            }

        });
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                choosePhotoFromGallary();

                        }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    public void onBackPressed(){
        finishAffinity();
    }
    public void felhasznalonev(Boolean cancelable){
        felh_nev_input = new AlertDialog.Builder(MainMenu.this);
        View mView = getLayoutInflater().inflate(R.layout.felhasznalonev, null);
        final EditText edt_felhnev = (EditText) mView.findViewById(R.id.edt_felhnev);
        Button btn_mentes = (Button) mView.findViewById(R.id.btn_felhnev);


        felh_nev_input.setView(mView);
        final AlertDialog dialog = felh_nev_input.create();
        dialog.setCancelable(cancelable);
        dialog.show();
        btn_mentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edt_felhnev.getText().toString().isEmpty())
                {
                    Toast.makeText(MainMenu.this, "A mentés sikeres", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", edt_felhnev.getText().toString());
                    editor.commit();
                    dialog.dismiss();

                } else {
                    Toast.makeText(MainMenu.this, "Adj meg egy felhasználónevet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


