package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity3 extends AppCompatActivity {

    TextView up;
    TextView Lerror;

    Button button_retour,ajouter_anonce,Consulter_annonce,supprimer_annonce,modifier_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        up = findViewById(R.id.up);
        Lerror = findViewById(R.id.Lerror);
        button_retour = findViewById(R.id.button_retour);
        ajouter_anonce = findViewById(R.id.ajouter_anonce);
        Consulter_annonce = findViewById(R.id.Consulter_annonce);
        supprimer_annonce = findViewById(R.id.supprimer_annonce);
        modifier_profil = findViewById(R.id.modifier_profil);


        final Intent x = getIntent();

        up.setText("Welcome   "+x.getStringExtra("pseudo"));

        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(i);
            }
        });

        ajouter_anonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (MainActivity3.this,MainActivity5.class);
                i.putExtra("pseudo",x.getStringExtra("pseudo"));
                startActivity(i);
            }
        });

        Consulter_annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (MainActivity3.this,MainActivity8.class);
                startActivity(i);
            }
        });

        supprimer_annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        modifier_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity3.this,MainActivity7.class);
                i.putExtra("pseudo",x.getStringExtra("pseudo"));
                i.putExtra("mail",x.getStringExtra("mail"));
                startActivity(i);
            }
        });
    }
}