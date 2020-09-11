package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity8 extends AppCompatActivity {

    TextView consult;
    ListView list;
    //Button plus;

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        consult = findViewById(R.id.consult);
        list = findViewById(R.id.list);
        //plus = findViewById(R.id.plus);


        helper = new SQLiteOpenHelper(MainActivity8.this,"Flash.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Market (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, Product TEXT NOT NULL,Categories TEXT NOT NULL, Description TEXT NOT NULL, Price INTEGER NOT NULL ,Vendeur TEXT NOT NULL );");

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE Market;");
                onCreate(sqLiteDatabase);
            }
        };
        database = helper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT DISTINCT * FROM Market ;",null);
        c.moveToNext();
        String [] from = {"Product","Categories","Description","Price","Vendeur"};
        int [] to = {R.id.product,R.id.categorie,R.id.description_produit,R.id.price,R.id.vendeur};

        adapter = new SimpleCursorAdapter(MainActivity8.this,R.layout.view,c,from,to,0);
        list.setAdapter(adapter);



    }




}