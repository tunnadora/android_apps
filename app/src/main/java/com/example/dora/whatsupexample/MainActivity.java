package com.example.dora.whatsupexample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText uname , passwd;
    Button signup;
    private FirebaseAuth mauth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname=(EditText) findViewById(R.id.uname);
        passwd=(EditText) findViewById(R.id.paaswd);
        signup = (Button)findViewById(R.id.signup);
        mauth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });


    }

    private void signUp() {
       final String email = uname.getText().toString();
        final String password = passwd.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this , "PLEASE ENTER EMAIL ....",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this , "PLEASE ENTER A VALID PASSWORD ....",Toast.LENGTH_LONG).show();
            return;
        }

        mauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String user_id = mauth.getCurrentUser().getUid();



                            DatabaseReference current_u_id = mDatabase.child(user_id);


                            current_u_id.child(email);


                            uname.setText("");
                            passwd.setText("");
                            Toast.makeText(MainActivity.this, "Sign up successful..",Toast.LENGTH_LONG ).show();




                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

}
