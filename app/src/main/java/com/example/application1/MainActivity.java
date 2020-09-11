package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextView Lbienvenue;
    ImageView image;
    EditText EditMail;
    EditText EditPassword;
    Button Connexion,creer;


    TextView Lwarning;
    ConstraintLayout Container;
    SQLiteOpenHelper helper ;
    SQLiteDatabase database;
    SimpleCursorAdapter c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Lbienvenue = findViewById(R.id.Lbienvenue);
        image = findViewById(R.id.image);
        EditMail = findViewById(R.id.EditMail);
        EditPassword = findViewById(R.id.EditPassword);
        Connexion = findViewById(R.id.Connexion);
        creer = findViewById(R.id.creer);

        Lwarning = findViewById(R.id.Lwarning);
        Container = findViewById(R.id.Container);

        helper = new SQLiteOpenHelper(MainActivity.this,"Flash.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Client ( Nom TEXT NOT NULL, Pseudo TEXT, Email TEXT PRIMARY KEY NOT NULL,Password TEXT NOT NULL);");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        database = helper.getReadableDatabase();

        Connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EditPassword.getText().toString().isEmpty() || EditMail.getText().toString().isEmpty() ){
                    Lwarning.setText("Tous les champs sont obligatoires");
                    Lwarning.setVisibility(View.VISIBLE);


                }
                 else if (EditPassword.getText().length()<8 ){
                     Lwarning.setText("Password must have at least 8 caracteres");
                     Lwarning.setVisibility(View.VISIBLE);
                }

                 else if ( EditPassword.getText().toString().contentEquals("<")){
                    Lwarning.setText("SQL Injection");
                    Lwarning.setVisibility(View.VISIBLE);
                }
                     else {

                    //Toast.makeText(MainActivity.this,"Send",Toast.LENGTH_LONG).show();
                   String Hashed =  md5(EditPassword.getText().toString());
                    //Cursor c = database.rawQuery("SELECT DISTINCT * FROM Client WHERE Email ='"+EditMail.getText().toString() +"' AND Password = '"+EditPassword.getText()+"';",null);
                    Cursor c = database.rawQuery("SELECT DISTINCT * FROM Client WHERE Email ='"+EditMail.getText().toString() +"' AND Password = '"+Hashed+"';",null);
                    if (c.moveToNext()){
                        Intent second_page = new Intent(MainActivity.this,MainActivity3.class);
                        second_page.putExtra("pseudo",c.getString(1));
                        second_page.putExtra("mail",c.getString(2));
                        startActivity(second_page);
                    } else {
                        Lwarning.setText("Credentials unknown");
                        Lwarning.setVisibility(View.VISIBLE);
                    }


                }



            }


        });

        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });

    }

    // https://mobikul.com/converting-string-md5-hashes-android/

    public String md5(String toString) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(toString.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
