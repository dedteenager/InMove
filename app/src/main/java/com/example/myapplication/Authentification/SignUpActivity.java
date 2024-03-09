package com.example.myapplication.Authentification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.home.Home_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private TextView Login;
    private Button SignUp;
    private EditText Email;
    private EditText Password;
    private EditText RePassword;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(SignUpActivity.this, Home_page.class);
            startActivity(intent);
            SignUpActivity.this.finish();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        SharedPreferences sp =  getSharedPreferences("ForEmail", MODE_PRIVATE);
        SharedPreferences sp2 =  getSharedPreferences("ForPassword", MODE_PRIVATE);
        ///
        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ///
        Login=findViewById(R.id.login);
        View.OnClickListener oclBtnGoToReg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                SignUpActivity.this.finish();

            }
        };

        Login.setOnClickListener(oclBtnGoToReg);
        SignUp = findViewById(R.id.btnLogIn);
        Email = findViewById(R.id.editEmail);
        Password = findViewById(R.id.editPassword);
      //  RePassword = findViewById(R.id.repeatPassword);
        View.OnClickListener oclBtnSignUp= new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("email", Email.getText().toString());
                                    userMap.put("status", "active");

                                    // Add user data to Firestore Database
                                    db.collection("users").document(user.getUid()).set(userMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                                    startActivity(intent);
                                                    SignUpActivity.this.finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SignUpActivity.this, "Failed to add user to Firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                    startActivity(intent);
                                    SignUpActivity.this.finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Authentication failed.", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        });


            //        if(TextUtils.isEmpty(Email.getText().toString()) || Email.getText().toString()==" " || !Email.getText().toString().contains("@")||Email.getText().toString().length()<5){
            //            Toast toast = Toast.makeText(getApplicationContext(),
            //                   "Ошибка в поле Email!", Toast.LENGTH_SHORT);
            //          toast.show();
            //        } else if (TextUtils.isEmpty(Password.getText().toString()) || Password.getText().toString().length()<8) {
            //            Toast toast = Toast.makeText(getApplicationContext(),
            //                    "Поле пароля должно быть пустым и менее 8 символов", Toast.LENGTH_SHORT);
            //            toast.show();
            //        } else if (!Password.getText().toString().equals(RePassword.getText().toString())) {
            //            Toast toast = Toast.makeText(getApplicationContext(),
            //                    "Пароли не совпадают!", Toast.LENGTH_SHORT);
            //            toast.show();
            //        }
            //        else {
            //            FirebaseFirestore db = FirebaseFirestore.getInstance();
            //            // Create a new user with a first and last name
            //            Map<String, Object> user = new HashMap<>();
            //            user.put("name"," ");
            //            user.put("email", Email.getText().toString());
            //            user.put("password", Password.getText().toString());
//
// Add a new// document with a generated ID
            //            db.collection("Users")
            //                    .add(user)
            //                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            //                        @Override
            //                        public void onSuccess(DocumentReference documentReference) {
            //                            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
            //                            startActivity(intent);
            //                            SignUpActivity.this.finish();
            //                        }
            //                    })
            //                    .addOnFailureListener(new OnFailureListener() {
            //                        @Override
            //                        public void onFailure(@NonNull Exception e) {
            //                            Toast toast = Toast.makeText(getApplicationContext(),
            //                                    "Пользователь не найден", Toast.LENGTH_SHORT);
            //                            toast.show();
            //                        }
            //                    });
            //        }
//
            };

        };
        SignUp.setOnClickListener(oclBtnSignUp);
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

}
