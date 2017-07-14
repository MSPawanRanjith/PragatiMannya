package com.example.administrator.pragatimannya;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class AdminAddTaskActivity extends AppCompatActivity {

    private Button assign;
    private TextInputLayout descriptionoftask;
    private TextInputLayout mGeo_lat,mGeo_long;
   // String title, description, eligibility, contact, imgurl, date,date1;
    private String uid = "";
    private int year_x, month_x, day_x;
    private TextInputLayout addressofsite;
    private DatabaseReference mDataBase;
    private ProgressDialog mProgress;
    static final int DIALOG_ID = 0;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_task);

        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);
        mProgress.setCanceledOnTouchOutside(false);

        assign=(Button) findViewById(R.id.assign);
        descriptionoftask=(TextInputLayout) findViewById(R.id.desc_work);
        mGeo_lat=(TextInputLayout)findViewById(R.id.geo_lat);
        mGeo_long=(TextInputLayout)findViewById(R.id.geo_long);
        addressofsite=(TextInputLayout)findViewById(R.id.address);

    }
}
