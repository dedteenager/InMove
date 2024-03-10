package com.example.myapplication.home;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.myapplication.R;
import com.example.myapplication.joining_from;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Home_page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Home_page";

    BottomNavigationView bottomNavigationView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(userId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);




        if (!isconnected()) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Internet Connection Alert")
                .setMessage("please check you internet connection")
                .setPositiveButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })

                .show();
    } else {

    }

}

    private boolean isconnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }



    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this).title("Share").sheet(R.menu.android_bottom_menu_item).listener(new DialogInterface.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case R.id.menu_call:
                        Intent intent = new Intent(getApplicationContext(), joining_from.class);
                        startActivity(intent);
                        break;

                    case R.id.menu_share:

                        Intent shareintent = new Intent();
                        shareintent.setAction(Intent.ACTION_SEND);
                        shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/developer?id=Brandvidiya");
                        shareintent.setType("text/plain");
                        startActivity(Intent.createChooser(shareintent, " share Application"));


                        break;
                }
            }
        }).show();

    }





    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_foreground)
                .setTitle(R.string.app_name)
                .setMessage("Выйти из приложения?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();

    }






    Homes_fragment Homes_fragment = new Homes_fragment();
    Tracker_fragment Tracker_fragment = new Tracker_fragment();
    mealFragment mealFragment = new mealFragment();
    workoutFragment workoutFragment = new workoutFragment();
    moreFragment moreFragment = new moreFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Homes_fragment).commit();
                return true;

            case R.id.navigation_Meal:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Tracker_fragment).commit();
                return true;

            case R.id.navigation_Workout:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, workoutFragment).commit();
                return true;

            case R.id.navigation_More:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, moreFragment).commit();
                return true;



        }

        return false;
    }
}
