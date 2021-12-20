package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile2 extends AppCompatActivity {

    private static final String TAG ="ChangeEmail";

    EditText et_email;
    Button btn_modifier_2;
    Button btn_anuller_2;

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DocumentReference df;
    private String userId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        df=fStore.collection("users").document(userId);


        et_email=(EditText)findViewById(R.id.et_email);

        btn_modifier_2=(Button) findViewById(R.id.btn_modifier_2);
        btn_anuller_2=(Button)findViewById(R.id.btn_annuler_2);

        btn_modifier_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail(et_email.getText().toString());
                Intent intent = new Intent(v.getContext(), Profile.class);
                v.getContext().startActivity(intent);
            }
        });
        btn_anuller_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Profile.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void changeEmail(String email) {
        Map<String,Object> userEmail =new HashMap<>();
        userEmail.put("email", email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            df.update(userEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(Profile2.this,"Successfuly update",Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d(TAG, "User email updated.");
                        }
                    }
                });


    }


}