package com.example.administrator.pragatimannya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class AdminAddTaskActivity extends AppCompatActivity {

    private Button assign;
    private TextInputLayout descriptionoftask;
    private TextInputLayout mGeo_lat,mGeo_long;

    private String uid = "";

    private TextInputLayout addressofsite;
    private DatabaseReference mDataBase;
    private ProgressDialog mProgress;
    static final int DIALOG_ID = 0;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private String mType;
    private String mDesc;
    private String mGeolat;
    private String mGeolong;
    private String mAddress;
    private String mStatus;
    private String mAdminUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_task);

        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mProgress=new ProgressDialog(this);
        mProgress.setCanceledOnTouchOutside(false);

        assign=(Button) findViewById(R.id.assign);
        descriptionoftask=(TextInputLayout) findViewById(R.id.desc_work);
        mGeo_lat=(TextInputLayout)findViewById(R.id.geo_lat);
        mGeo_long=(TextInputLayout)findViewById(R.id.geo_long);
        addressofsite=(TextInputLayout)findViewById(R.id.address);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AdminAddTaskActivity.this, AdminLoginActivity.class));
                    finish();
                }
            }
        };
        //Get UID of the user
        mAdminUid = user.getUid();

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAssignment();
            }
        });
    }

    public void uploadAssignment(){

        mProgress.setMessage("Validating task...");
        mProgress.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            }}, 2000);
        if(!mGeo_lat.getEditText().getText().equals(null) && !mGeo_long.getEditText().getText().equals(null)){


                    mProgress.setMessage("Assigning task...");



            handler.postDelayed(new Runnable() {
                public void run() {

                }}, 3000);
                  //  DatabaseReference newPost=mDataBase.push();

                    mDesc=descriptionoftask.getEditText().getText().toString();
                    mGeolat = mGeo_lat.getEditText().getText().toString();
                    mGeolong= mGeo_long.getEditText().getText().toString();
                    mAddress =addressofsite.getEditText().getText().toString();
//                    date1 = ""+day_x;
//                    date1 = date1 + "/" + month_x;
//                    date1 = date1 + "/" + year_x;
                    //description = description + "\n" + "date :" + date1;
                    // get selected radio button from radioGroup
                    mType=getIntent().getStringExtra("EMP_UID");
                    mStatus="1";
//



                    AdminAddTaskData obj = new AdminAddTaskData(mDesc,mGeolat,mGeolong,mAddress,mType,mStatus,mAdminUid);
                    Random rand = new Random();


                    mDataBase.push().setValue(obj);

                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Successfully task assigned ",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AdminAddTaskActivity.this, AdminWorkSpace.class));
                    finish();
                }
            else{
            Toast.makeText(getApplicationContext(),"Please enter the geo-location for minimum information",Toast.LENGTH_LONG).show();
        }


    }
}
