package com.covid19.coronarg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputLayout mName, mEmail, mPassword, mPhone;
    Button mRegisterBtn, mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.name_field);
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.password_field);
        mPhone = findViewById(R.id.phone_field);
        mRegisterBtn = findViewById(R.id.register_button); //registerUser
        mLoginBtn = findViewById(R.id.login_button);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                String name = mName.getEditText().getText().toString();
                String phone = mPhone.getEditText().getText().toString();

                if(name.isEmpty()){
                    mEmail.setError("이름은 필수 입력입니다.");
                    return;
                }
                
                if(email.isEmpty()){
                    mEmail.setError("이메일은 필수 입력입니다.");
                    return;
                }

                if(password.isEmpty()){
                    mPassword.setError("비밀번호는 필수 입력입니다.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be >= 6 Characters");
                    return;
                } 

                if(phone.isEmpty()){
                    mPassword.setError("휴대폰번호는 필수 입력입니다.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, phone, email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }else{
                                        Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

}