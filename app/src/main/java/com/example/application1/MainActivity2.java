package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

public class MainActivity2 extends AppCompatActivity {


    TextView Ltext;
    EditText Name,Pseudo,mail,Password,npassword;
    Button Valid;
    Button button_retour;
    TextView Lresult;


    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Ltext = findViewById(R.id.Ltext);
        Valid = findViewById(R.id.Valid);
        Lresult = findViewById(R.id.Lresult);
        button_retour =findViewById(R.id.button_retour);
        Name = findViewById(R.id.Name);
        Pseudo = findViewById(R.id.Pseudo);
        mail = findViewById(R.id.mail);
        Password = findViewById(R.id.Password);
        npassword = findViewById(R.id.npassword);


        helper = new SQLiteOpenHelper(MainActivity2.this,"Flash.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Client (Nom TEXT NOT NULL, Pseudo TEXT, Email TEXT NOT NULL PRIMARY KEY,Password TEXT NOT NULL);");

                //CREATE TABLE 'Client' ('id' INTEGER , 'Nom' TEXT NOT NULL, 'Pseudo' TEXT NOT NULL, 'Email' TEXT NOT NULL, 'Password' TEXT NOT NULL, PRIMARY KEY ('Email');
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };


        database = helper.getWritableDatabase();

        Valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Name.getText().toString().isEmpty() || Pseudo.getText().toString().isEmpty() || mail.getText().toString().isEmpty() || Password.getText().toString().isEmpty() || npassword.getText().toString().isEmpty()){
                    Lresult.setText("Tous les champs sont obligatoires");
                    Lresult.setVisibility(View.VISIBLE);
                } else if (!(Password.getText().toString().equals(npassword.getText().toString()))){
                    Lresult.setVisibility(View.INVISIBLE);
                    Lresult.setText("Attention!! Les mots de passe ne sont pas identiques");
                    Lresult.setVisibility(View.VISIBLE);
                } else {

                    if (npassword.getText().length()>=8 && Password.getText().length()>=8 ){
                        String hashed = md5 (Password.getText().toString());
                       try {
                           database.execSQL("INSERT INTO Client (Nom,Pseudo,Email,Password) VALUES ('" + Name.getText().toString() + "','" + Pseudo.getText().toString() + "','" + mail.getText() + "','" + hashed + "');");
                           Intent i = new Intent(MainActivity2.this, MainActivity3.class);
                           i.putExtra("pseudo", Pseudo.getText().toString());
                           startActivity(i);
                       } catch (SQLException e){
                           e.printStackTrace();
                           Toast.makeText(MainActivity2.this, "There is something wrong right there", Toast.LENGTH_LONG).show();
                           Intent i = new Intent (MainActivity2.this, MainActivity.class);
                           startActivity(i);
                       }
                    }
                }


            }

        });

        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
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