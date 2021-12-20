package com.example.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private TextView nomTxtView, emailTxtView,passwordTxtView,cnaTxtView, cneTxtView;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DocumentReference df;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        drawerLayout=findViewById(R.id.drawer_layout);
        txt1=findViewById(R.id.tv_modifier_1);
        txt2=findViewById(R.id.tv_modifier_3);
        txt3=findViewById(R.id.tv_modifier_4);
        fAuth =FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        df=fStore.collection("users").document(userId);
        nomTxtView=(TextView)findViewById(R.id.tv_nomUser);
        emailTxtView=(TextView)findViewById(R.id.tv_emailUser);
        passwordTxtView=(TextView)findViewById(R.id.tv_passwordUser);

        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nomTxtView.setText(value.getString("firstName")+" "+value.getString("lastName"));
                emailTxtView.setText(value.getString("email"));
                //cnaTxtView.setText(value.getString("cna"));
                //cneTxtView.setText(value.getString("cin"));
              // passwordTxtView.setText(value.getString("password"));
            }
        });

    }
    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){MainActivity.redirectActivity(this,MainActivity.class);
    }
    public void ClickDashboard(View view){
        recreate();
    }
    public void ClickAboutus(View view){
        MainActivity.redirectActivity(this, Profile.class);
    }
    public void ClickInfo(View view){
        MainActivity.redirectActivity(this, Profile.class);
    }
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
    protected void onPause(){
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
    public void Clicktxt1(View view){
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Profile1.class));
            }
        });
    }
    public void Clicktxt2(View view){
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Profile2.class));
            }
        });
    }
    public void Clicktxt3(View view){
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Profile3.class));
            }
        });
    }
}