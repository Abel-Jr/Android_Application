package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity5 extends AppCompatActivity {

    TextView Lajouter,Ltext,saler;
    EditText Nom,Description,Prix,categories;
    Button enregistrer;
    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Lajouter = findViewById(R.id.Lajouter);
        Nom = findViewById(R.id.Nom);

        Description = findViewById(R.id.Description);
        Prix = findViewById(R.id.Prix);
        categories = findViewById(R.id.categories);
        Ltext = findViewById(R.id.Ltext);
        enregistrer = findViewById(R.id.enregistrer);
        saler = findViewById(R.id.saler);

        final Intent x = getIntent();
        saler.setText(x.getStringExtra("pseudo"));

        helper = new SQLiteOpenHelper(MainActivity5.this,"Flash.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Market (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, Product TEXT NOT NULL,Categories TEXT NOT NULL, Description TEXT NOT NULL, Price INTEGER NOT NULL ,Vendeur TEXT NOT NULL );");

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                //onCreate(sqLiteDatabase);
            }
        };
        database = helper.getWritableDatabase();

        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Nom.getText().toString().isEmpty() || Description.getText().toString().isEmpty() || Prix.getText().toString().isEmpty()){
                    Ltext.setVisibility(View.INVISIBLE);
                    Ltext.setText("Tous les champs sont obligatoires");
                    Ltext.setVisibility(View.VISIBLE);
                } else {
                    try {
                        Ltext.setVisibility(View.INVISIBLE);
                        database.execSQL("INSERT INTO Market (Product,Categories,Description,Price,Vendeur) VALUES ('" + Nom.getText().toString() + "','" + categories.getText().toString() + "','" + Description.getText().toString() + "','" + Integer.parseInt(Prix.getText().toString()) + "','" + saler.getText().toString() + "');");
                        Toast.makeText(MainActivity5.this, "Succesfull operation", Toast.LENGTH_LONG).show();
                        Intent x = new Intent(MainActivity5.this, MainActivity3.class);
                        x.putExtra("pseudo",saler.getText().toString());
                        startActivity(x);
                    } catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity5.this, "There is something wrong right there", Toast.LENGTH_LONG).show();
                        Intent x = new Intent(MainActivity5.this, MainActivity.class);
                        //x.putExtra("pseudo",x.getStringExtra("pseudo"));
                        startActivity(x);
                    }
                }

            }
        });


    }
}