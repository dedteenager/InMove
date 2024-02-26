package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.home.Home_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LogInActivity extends AppCompatActivity {
    private TextView SignUp;
    private EditText Email;
    private EditText Password;
    private FirebaseAuth mAuth;
    private Button LoginBtn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
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
        setContentView(R.layout.login);
        SignUp = findViewById(R.id.btnToSignUp);
        LoginBtn=findViewById(R.id.btnLogIn);
        Email=findViewById(R.id.editEmailIn);
        Password=findViewById(R.id.editPasswordIn);

        SharedPreferences sp= getSharedPreferences("ForEmail", Context.MODE_PRIVATE);
        SharedPreferences sp2= getSharedPreferences("ForPassword",Context.MODE_PRIVATE);

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
                LogInActivity.this.finish();
            }
        };

        View.OnClickListener oclBtnGoToHome = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LogInActivity.this, Home_page.class);
                                    startActivity(intent);
                                    LogInActivity.this.finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Authentication failed.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        });
               //b.collection("Users")
               //       .get()
               //       .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               //           @Override

               //           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               //               if (task.isSuccessful()) {
               //                   for (QueryDocumentSnapshot document : task.getResult()) {
               //                           if(document.getString("email").equals(Email.getText().toString()) ){
               //                               if(document.getString("Password").equals(Password.getText().toString()) ){
               //                                   sp.edit().putString("Email",Email.getText().toString());
               //                                   Intent intent = new Intent(LogInActivity.this, Home_page.class);
               //                                   startActivity(intent);
               //                                   LogInActivity.this.finish();
               //                               }
               //                           }
               //                   }
               //               } else {
               //                   Toast toast = Toast.makeText(getApplicationContext(),
               //                           "Пользователь не найден", Toast.LENGTH_SHORT);
               //                   toast.show();
               //                   Password.setText("");
               //                   Email.setText("");
               //               }
               //           }
               //       });
            }
        };
        LoginBtn.setOnClickListener(oclBtnGoToHome);
    }}
