package com.example.myapplication.home;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class Home_page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, Khatkha_fragment.OnFragmentInteractionListener, Wim_fragment.OnFragmentInteractionListener, None_fragment.OnFragmentInteractionListener, Choose_fragment.OnFragmentInteractionListener {

    private static final String TAG = "Home_page";
    private static final String PREFS_FILE = "0";
    SharedPreferences page;
    BottomNavigationView bottomNavigationView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("users").document(userId);

    @Override
    public void onButtonClickedNone() {
        page=getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = page.edit();
        prefEditor.putString(PREFS_FILE, "1");
        prefEditor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Khatkha_fragment())
                .addToBackStack(null)
                .commit();

    }
    @Override
    public void onButtonClickedKhatkha() {
        page=getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = page.edit();
        prefEditor.putString(PREFS_FILE, "2");
        prefEditor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Wim_fragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onButtonClickedWim() {
        page=getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = page.edit();
        prefEditor.putString(PREFS_FILE, "3");
        prefEditor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new None_fragment())
                .addToBackStack(null)
                .commit();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        page=getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);



        if (!isconnected()) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Проблема с интернет-соединением")
                .setMessage("Проверьте интернет-соединение. Оно должно быть включено ")
                .setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Home_page.this, CloseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Home_page.this.finish();
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



  //  public void openAndroidBottomMenu(View view) {

        //new BottomSheet.Builder(this).title("Share").sheet(R.menu.android_bottom_menu_item).listener(new DialogInterface.OnClickListener() {
        //    @SuppressLint("NonConstantResourceId")
        //    @Override
        //    public void onClick(DialogInterface dialog, int which) {
        //        switch (which) {
//
        //            case R.id.menu_call:
        //                Intent intent = new Intent(getApplicationContext(), joining_from.class);
        //                startActivity(intent);
        //                break;
//
        //            case R.id.menu_share:
//
        //                Intent shareintent = new Intent();
        //                shareintent.setAction(Intent.ACTION_SEND);
        //                shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/developer?id=Brandvidiya");
        //                shareintent.setType("text/plain");
        //                startActivity(Intent.createChooser(shareintent, " share Application"));
//
//
        //                break;
        //        }
        //    }
        //}).show();

   // }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        //page=getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        //SharedPreferences.Editor prefEditor = page.edit();
        //prefEditor.putString(PREFS_FILE, "0");
        //prefEditor.apply();
        //getSupportFragmentManager().beginTransaction()
        //        .replace(R.id.container, new Choose_fragment())
        //        .addToBackStack(null)
        //        .commit();
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon)
                .setTitle(R.string.app_name)
                .setMessage("Выйти из приложения?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Home_page.this, CloseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Home_page.this.finish();
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
    None_fragment None_fragment = new None_fragment();
    Khatkha_fragment Khatkha_fragment = new Khatkha_fragment();
    Wim_fragment Wim_fragment = new Wim_fragment();
    Tracker_fragment Tracker_fragment = new Tracker_fragment();
    Choose_fragment Choose_fragment = new Choose_fragment();
    Knowledges_fragment Knowledges_fragment = new Knowledges_fragment();
    More_fragment moreFragment = new More_fragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int value = Integer.parseInt(page.getString(PREFS_FILE,"1"));
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (value==2){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Wim_fragment).commit();
                } else if (value==3){
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, None_fragment).commit();
                } else if(value==1){
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Khatkha_fragment).commit();
                }else{
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Choose_fragment).commit();
                }
                return true;

            case R.id.navigation_Meal:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Tracker_fragment).commit();
                return true;

            case R.id.navigation_Workout:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, Knowledges_fragment).commit();
                return true;

            case R.id.navigation_More:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, moreFragment).commit();
                return true;
        }

        return false;
    }
}
