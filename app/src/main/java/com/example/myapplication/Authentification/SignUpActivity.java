package com.example.myapplication.Authentification;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private TextView Login;
    private Button SignUp;
    private EditText Email;
    private EditText Password;
    private EditText editName;
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
        editName = findViewById(R.id.editName);
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
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String userId = user.getUid();

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    DocumentReference wim = db.collection("users").document(userId).collection("Progress").document("WimMethod");
                                    DocumentReference khatkha = db.collection("users").document(userId).collection("Progress").document("Khatkha");
                                    DocumentReference detka = db.collection("users").document(userId).collection("Progress").document("Detka");

                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("nick", editName.getText().toString());
                                    userMap.put("email", Email.getText().toString());
                                    userMap.put("status", "active");

                                    Map<String, Object> progressMapWim = new HashMap<>();
                                    progressMapWim.put("days","8");

                                    Map<String, Object> progressMapKhatkha = new HashMap<>();
                                    progressMapKhatkha.put("days","1");

                                    Map<String, Object> progressMapDetka = new HashMap<>();
                                    progressMapDetka.put("days","3");
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

                                    wim.set(progressMapWim).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });

                                    khatkha.set(progressMapKhatkha).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });
                                    detka.set(progressMapDetka).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
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
