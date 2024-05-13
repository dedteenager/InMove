package com.example.myapplication.Authentification;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.home.CloseActivity;
import com.example.myapplication.home.Home_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {
    private TextView SignUp;
    private EditText Email;
    private EditText Password;
    private FirebaseAuth mAuth;
    private Button LoginBtn;
    private ImageView btnGoogleLogin;
    private TextView btnToForgotPassword;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LogInActivity.this, Home_page.class);
            startActivity(intent);
            LogInActivity.this.finish();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout._login);
        SignUp = findViewById(R.id.btnToSignUp);
        LoginBtn=findViewById(R.id.btnLogIn);
        btnGoogleLogin=findViewById(R.id.btnGoogleLogin);
        Email=findViewById(R.id.editEmailIn);
        Password=findViewById(R.id.editPasswordIn);
        btnToForgotPassword=findViewById(R.id.btnToForgotPasswordr);

        if (!isconnected()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Проблема с интернет-соединением")
                    .setMessage("Проверьте интернет-соединение. Оно должно быть включено ")
                    .setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LogInActivity.this, CloseActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            LogInActivity.this.finish();
                        }
                    })

                    .show();
        } else {

        }
        View.OnClickListener oclBtnGoToReg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                LogInActivity.this.finish();

            }
        };

        SignUp.setOnClickListener(oclBtnGoToReg);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LogInActivity.this, Home_page.class);
            startActivity(intent);
            LogInActivity.this.finish();
        }

        View.OnClickListener oclBtnGoToForgotPassword = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

            }
        };

        View.OnClickListener oclBtnGoToHome = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String pas = Password.getText().toString().trim();

                if (!TextUtils.isEmpty(email)&!TextUtils.isEmpty(pas)) {
                    mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(LogInActivity.this, Home_page.class);
                                        startActivity(intent);
                                        LogInActivity.this.finish();
                                    } else {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                "Проверьте данные. Неудачная попытка входа.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            });

                } else if(TextUtils.isEmpty(email)){
                    Email.setError("Поле Email не может быть пустым");
                }else if(TextUtils.isEmpty(pas)){
                    Password.setError("Поле Пароля не может быть пустым");
                }else{
                    Email.setError("Поле Email не может быть пустым");
                    Password.setError("Поле Пароля не может быть пустым");
                }

            }
        };
        LoginBtn.setOnClickListener(oclBtnGoToHome);
        btnToForgotPassword.setOnClickListener(oclBtnGoToForgotPassword);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon)
                .setTitle(R.string.app_name)
                .setMessage("Выйти из приложения?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LogInActivity.this, CloseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        LogInActivity.this.finish();
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
    private boolean isconnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
