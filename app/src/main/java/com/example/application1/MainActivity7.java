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
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity7 extends AppCompatActivity {

    EditText Nom, username, current_password,new_password;
    TextView  user_mail, Label_error, saved_hash;
    SQLiteOpenHelper helper;
    SQLiteDatabase database,nskg;
    //SimpleCursorAdapter c;
    Button modifier;

    TextView current_hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        Nom = findViewById(R.id.Nom);
        username = findViewById(R.id.username);
        user_mail = findViewById(R.id.user_mail);
        saved_hash = findViewById(R.id.saved_hash);
        Label_error = findViewById(R.id.Label_error);
        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        modifier = findViewById(R.id.modifier);

        current_hash = findViewById(R.id.current_hash);

        Intent x = getIntent();
        user_mail.setText(x.getStringExtra("mail"));
        user_mail.setVisibility(View.VISIBLE);
        username.setText(x.getStringExtra("pseudo"));

        helper = new SQLiteOpenHelper(MainActivity7.this, "Flash.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Client (Nom TEXT NOT NULL, Pseudo TEXT, Email TEXT NOT NULL PRIMARY KEY,Password TEXT NOT NULL);");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        database = helper.getReadableDatabase();
        final Cursor c = database.rawQuery("SELECT DISTINCT * FROM Client WHERE Email ='" + x.getStringExtra("mail") + "' AND Pseudo = '" + x.getStringExtra("pseudo") + "';", null);
        if (c.moveToNext()) {
            Nom.setText(c.getString(0));
            //user_mail.setText(c.getString(2));
            saved_hash.setText(c.getString(3));

            nskg = helper.getWritableDatabase();

            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String current_password_Hash = md5(current_password.getText().toString());
                    String New_pass = md5(new_password.getText().toString());
                    if (current_password.getText().toString().isEmpty() || new_password.getText().toString().isEmpty() || user_mail.getText().toString().isEmpty() || username.getText().toString().isEmpty()) {

                        Label_error.setText("Warning, all fields are necessary");
                        Label_error.setVisibility(View.VISIBLE);
                    }
                    if (saved_hash.getText().toString().equals(current_password_Hash)) {

                            try {
                                nskg.execSQL("UPDATE Client SET Nom ='" + Nom.getText().toString() + "'," + "Pseudo='" + username.getText().toString() + "', Email ='" + user_mail.getText().toString() + "'," + "Password ='" + New_pass + "' " + "WHERE Email ='" + user_mail.getText().toString() + "';");
                                Label_error.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity7.this, "Update Succesfully", Toast.LENGTH_LONG).show();

                                Intent x = new Intent(MainActivity7.this, MainActivity3.class);
                                startActivity(x);
                            } catch (SQLException e){
                                e.printStackTrace();
                                Toast.makeText(MainActivity7.this, "Error, there is something wrong maybe this email is already use", Toast.LENGTH_LONG).show();
                                Intent x = new Intent(MainActivity7.this, MainActivity.class);
                                startActivity(x);
                            }

                    } else {
                        Label_error.setText("Current password is incorrect");
                        Label_error.setVisibility(View.VISIBLE);

                    }

                }


            });


        }
    }

    // https://mobikul.com/converting-string-md5-hashes-android/

    public String md5 (String toString) {
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