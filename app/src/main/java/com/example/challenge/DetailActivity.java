package com.example.challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String start;
    String end;
    String image;
    String id;
    String price;
    boolean isFavourite;
    public TextView textStart,textEnd,textPrice;
    public ImageView detiaImage;
    ToggleButton btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedpreferences.edit();

        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        textStart =findViewById(R.id.teStar);
        textEnd =findViewById(R.id.teEnd);
        detiaImage = findViewById(R.id.detiaImg);
        textPrice = findViewById(R.id.price);
        btn = findViewById(R.id.btn);



        if (bundle!= null){
            start = bundle.getString("start");
            end = bundle.getString("end");
            image = bundle.getString("image");
            isFavourite = bundle.getBoolean("isFavourit");
            id = bundle.getString("id");
            price = bundle.getString("price");

        }

        btn.setChecked(sharedpreferences.getString(id,"").equals("fav"));

        if (btn.isChecked()) {
            btn.setButtonDrawable(R.drawable.favor2);
        } else {
            btn.setButtonDrawable(R.drawable.favor1);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(sharedpreferences.getString(id,"")+";';';';'");
                if(sharedpreferences.getString(id,"").equals("fav")){
                    btn.setButtonDrawable(R.drawable.favor1);
                    btn.setChecked(false);
                    myEdit.putString(id,"null");
                    myEdit.commit();
                    System.out.println(sharedpreferences.getString(id,""));

                }
                else {
                    btn.setButtonDrawable(R.drawable.favor2);
                    btn.setChecked(true);
                    myEdit.putString(id,"fav");
                    myEdit.commit();
                    System.out.println(sharedpreferences.getString(id,""));
                }

            }
        });
        textStart.setText(start);
        textEnd.setText(end);
        textPrice.setText(price);
        Glide.with(DetailActivity.this).load(image).into(detiaImage);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}