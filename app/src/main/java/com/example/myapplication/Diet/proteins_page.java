package com.example.myapplication.Diet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.R;

public class proteins_page extends AppCompatActivity {
    List<meal> meal ;
    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proteins_page);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        meal = new ArrayList<>();
        meal.add(new meal("The Vegitarian","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Wild Robot","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("Maria Semples","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Martian","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Vegitarian","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Wild Robot","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("Maria Semples","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Martian","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Vegitarian","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Wild Robot","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("Maria Semples","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("The Martian","Categorie Book","Description book",R.drawable.hediedwith));
        meal.add(new meal("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);

        myrv.setLayoutManager(new GridLayoutManager(this,3));



    }
}