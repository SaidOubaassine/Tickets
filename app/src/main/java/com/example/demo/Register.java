package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class Register extends AppCompatActivity {
    private EditText firstNameEditText, lastNameEditText;
    private EditText emailEditText, passwordEditText;
    private EditText cinEditText, cneEditText;

    private FirebaseFirestore database;
    private DocumentReference df;
    private static final String USERS = "users";
    private String TAG = "Register";
    private String firstName, lastName, email, password, cin, cne;
    private user etudiant;
    private FirebaseAuth mAuth;
    private Button registerButton;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameEditText =(EditText) findViewById(R.id.firstName);
        lastNameEditText =(EditText) findViewById(R.id.lastName);
        passwordEditText =(EditText) findViewById(R.id.password);
        emailEditText = (EditText) findViewById(R.id.email);
        cinEditText=(EditText)findViewById(R.id.cin);
        cneEditText=(EditText)findViewById(R.id.cne);

        registerButton =(Button) findViewById(R.id.registerId);

        database = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //insert data into firebase database
                if(emailEditText.getText().toString() != null && passwordEditText.getText().toString() != null) {
                    firstName = firstNameEditText.getText().toString();
                    email = emailEditText.getText().toString();
                    lastName = lastNameEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    cin=cinEditText.getText().toString();
                    cne=cneEditText.getText().toString();
                    etudiant = new user(firstName, lastName, email, password, cin, cne);
                    registerUser();
                }
            }
        });

    }

    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            userId=mAuth.getCurrentUser().getUid();
                            Toast.makeText(Register.this, "Authentication créer.", Toast.LENGTH_SHORT).show();
                            updateUI(userId);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void updateUI(String userId) {
        df=database.collection(USERS).document(userId);
        df.set(etudiant);
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

}