package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
   EditText email;
   EditText password;
   Button login;
   TextView register;
   private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;
    Button btlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.emailId);
        password=findViewById(R.id.passwordId);
        login=findViewById(R.id.buttonLogin);
        register=findViewById(R.id.registerId);
        progressDialog = new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=email.getText().toString().trim();
                String txt_password=password.getText().toString().trim();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Login.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(Login.this,"Password too short!",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("Please Wait Login ...");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,"Login fail"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,"Loging user succesful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this,MainActivity.class));
                            }
                        }
                    });
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
  //  public void tap(View view){
   //     btlog.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   startActivity(new Intent(new Intent(Login.this,Ticket.class)));
            //}
        //});
//}
}



