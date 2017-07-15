package com.example.administrator.pragatimannya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class VerifierLoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private FirebaseAuth auth;
    private FirebaseApp finestayApp;
    public static int  i=1;
    public static int   hasBeenInitialized=0;

    //
    FirebaseAuth.AuthStateListener authListener;
    String uid;
//    final Firebase ref1 = new Firebase("https://post-it-81fe6.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Log.d("on create hasbeen",hasBeenInitialized+"  first time");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:128918692988:android:e238b802c3c5c2fa") // Required for Analytics.
                .setApiKey("AIzaSyC9XTPwkaZO7cx2bswrgvYYfEPAMXjfTXE") // Required for Auth.
                .setDatabaseUrl("https://post-it-81fe6.firebaseio.com/")// Required for RTDB.
                .build();

        Log.d("firebase option "," added ");
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(getApplicationContext());
        for(FirebaseApp app : firebaseApps){
            if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
               // hasBeenInitialized = false;
                Log.d("Times inside for list","1+  "+app.getName());
            }
        }



                Log.d("before hasbeen",hasBeenInitialized+" ");
                finestayApp = FirebaseApp.initializeApp(VerifierLoginActivity.this,options,"secondary"+i);
                auth = FirebaseAuth.getInstance(finestayApp);
                i++;
                hasBeenInitialized=1;
                Log.d("After hasbeen",hasBeenInitialized+" ");


      //  FirebaseApp application = FirebaseApp.getInstance("secondary");


       // ref1.setAndroidContext(this);//auth = FirebaseAuth.getInstance();
       // Log.d("which authentication",finestayApp.getName().toString());

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(VerifierLoginActivity.this, VerifierWorkSpace.class));
//            finish();
//        }
        setContentView(R.layout.activity_admin_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //auth = FirebaseAuth.getInstance();


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifierLoginActivity.this, ResetPasswordVerifierActivity.class);
                intent.putExtra("firebase_initialise",i);
                startActivity(intent);
                finish();

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(VerifierLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Password length less than 6");
                                    } else {
                                        Toast.makeText(VerifierLoginActivity.this, "Email or password incorrect", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Log.v("wierd","as");
                                    final FirebaseUser user = FirebaseAuth.getInstance(finestayApp).getCurrentUser();
                                    authListener = new FirebaseAuth.AuthStateListener() {
                                        @Override
                                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                            //FirebaseUser user = firebaseAuth.getCurrentUser();
                                            if (user == null) {
                                                // user auth state is changed - user is null
                                                // launch login activity
                                                startActivity(new Intent(VerifierLoginActivity.this, VerifierLoginActivity.class));
                                                finish();
                                            }
                                        }
                                    };
                                    //Get UID of the user
                                    uid = user.getUid();

                                    Log.d("onCreate: ","uid : of verifier " + uid);

                                    Intent intent = new Intent(VerifierLoginActivity.this, VerifierWorkSpace.class);
                                  //  intent.putExtra("firebase_initialise",true);
                                    intent.putExtra("UID",uid);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }
}
