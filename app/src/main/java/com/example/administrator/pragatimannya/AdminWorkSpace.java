package com.example.administrator.pragatimannya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class AdminWorkSpace extends AppCompatActivity {
    private Button signoutButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work_space);

        signoutButton=(Button)findViewById(R.id.signout);
        auth=FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(AdminWorkSpace.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
