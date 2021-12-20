package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile1 extends AppCompatActivity {
    private static final String TAG ="ChangeFirstAndLastName";;

    EditText et_nom;
    EditText et_prenom;
    Button btn_modifier_1;
    Button btn_anuller_1;

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DocumentReference df;
    private String userId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        df=fStore.collection("users").document(userId);


        et_nom=(EditText)findViewById(R.id.et_nom);
        et_prenom=(EditText)findViewById(R.id.et_prenom);

        btn_modifier_1=(Button) findViewById(R.id.btn_modifier_1);
        btn_anuller_1=(Button)findViewById(R.id.btn_annuler_1);

        btn_modifier_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFirstAndLastName(et_nom.getText().toString(), et_prenom.getText().toString());
                Intent intent = new Intent(v.getContext(), Profile.class);
                v.getContext().startActivity(intent);
            }
        });
        btn_anuller_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Profile.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void changeFirstAndLastName(String nvFirstName, String nvLastName) {
        Map<String,Object> userFirstAndLastName =new HashMap<>();
        userFirstAndLastName.put("firstName", nvFirstName);
        userFirstAndLastName.put("lastName", nvLastName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        df.update(userFirstAndLastName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                Toast.makeText(Profile1.this,"Successfuly update",Toast.LENGTH_SHORT).show();
            }
        });

    }
}