package com.example.administrator.pragatimannya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.messaging.FirebaseMessaging;
public class MainActivity extends AppCompatActivity {

    private Button adminLogin;
    private Button verfierLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        adminLogin=(Button)findViewById(R.id.admin_button_id);
        verfierLogin=(Button)findViewById(R.id.verfier_button_id);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminLoginIntent = new Intent(getBaseContext(),AdminLoginActivity.class);
                startActivity(adminLoginIntent);
            }
        });

        verfierLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verfierLoginIntent = new Intent(getBaseContext(),VerifierLoginActivity.class);
                startActivity(verfierLoginIntent);
            }
        });

    }
}
