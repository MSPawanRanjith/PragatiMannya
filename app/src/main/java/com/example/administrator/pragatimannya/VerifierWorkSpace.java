package com.example.administrator.pragatimannya;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerifierWorkSpace extends AppCompatActivity {

    FirebaseAuth auth;
    public String uid;
    DatabaseReference mDatabase;
    private RecyclerView recylceview;
    private ArrayList<String> Astatus;
    private ArrayList<Double> Alat;
    private ArrayList<Double> Along;
    private ArrayList<String> AparentNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifier_work_space);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Firebase.setAndroidContext(this);

        Intent intent=getIntent();
        uid=intent.getStringExtra("UID");

        Astatus=new ArrayList<>();
        Alat=new ArrayList<>();
        Along=new ArrayList<>();
        AparentNode=new ArrayList<>();

        Log.d("UID of verifier in work",uid);

        Log.d("Before recycle", uid);
        recylceview = (RecyclerView) findViewById(R.id.list_verify);
        Log.d("After recycle", "");

        recylceview.setHasFixedSize(true);
        recylceview.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        Log.d("Before recycle", "Layoutmanager");


    }
    @Override
    public void onStart(){
        super.onStart();

        Log.d("Where verify", "onStart: ");
        Query userfEvents = mDatabase.orderByChild("type").equalTo(uid);

       userfEvents.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot child: dataSnapshot.getChildren()) {
                   Log.d("MainActivity parent key", child.getKey());
                   AparentNode.add(child.getKey());
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Log.e("MainActivity", "onCancelled", databaseError.toException());
           }
       });

        Log.d("SAomething",userfEvents.toString());
        FirebaseRecyclerAdapter<AdminAddTaskData,VerifierWorkSpace.PostviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AdminAddTaskData, VerifierWorkSpace.PostviewHolder>(
                AdminAddTaskData.class,
                R.layout.list_verify_card,
                VerifierWorkSpace.PostviewHolder.class,
                userfEvents


        ) {
            @Override
            protected void populateViewHolder(VerifierWorkSpace.PostviewHolder viewHolder, AdminAddTaskData model, final int position) {

                viewHolder.setTitle("Task "+(position+1));
                viewHolder.setDesc(model.getDescription());
                viewHolder.setLat(model.getGeolat());
                viewHolder.setLong(model.getGeolong());
                viewHolder.setAddress(model.getAddress());

                //Setting Required stuffs
                Astatus.add(model.getStatus());
                Alat.add(Double.parseDouble(model.getGeolat()));
                Along.add(Double.parseDouble(model.getGeolong()));




                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Astatus.get(position).equals("1")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(VerifierWorkSpace.this);

                            builder.setTitle("Proceed");
                            builder.setMessage("Are you sure?");

                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                    Intent intent=new Intent(VerifierWorkSpace.this,ValidateGPSActivity.class);
                                    intent.putExtra("GEO_LAT",Alat.get(position));
                                    intent.putExtra("GEO_LONG",Along.get(position));
                                    intent.putExtra("PARENT_KEY",AparentNode.get(position));
                                    dialog.dismiss();
                                    startActivity(intent);

                                }
                            });

                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else{
                            Snackbar.make(view,"This task has been already completed",Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                    }
                });



            }
        };
        recylceview.setAdapter(firebaseRecyclerAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.verifier_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
//            case R.id.action_remove:
//            {
//                Intent intent = new Intent(VerifierWorkSpace.this, AdminRemoveTaskActivity.class);
//                startActivity(intent);
//            }
             // return true;
            case R.id.action_signout:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public  void  signout(){
//        auth.signOut();
        Intent intent = new Intent(VerifierWorkSpace.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public static class PostviewHolder extends RecyclerView.ViewHolder{
        View mview;

        public PostviewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String mJobtitle){
            TextView posttitle = (TextView) mview.findViewById(R.id.job_title);
            posttitle.setText(mJobtitle);
        }
        public void setDesc(String mJobdesc){
            TextView posttitle = (TextView) mview.findViewById(R.id.job_desc);
            posttitle.setText(mJobdesc);
        }
        public void setLat(String mJoblat){
            TextView posttitle = (TextView) mview.findViewById(R.id.job_lat);
            posttitle.setText(mJoblat);
        }
        public void setLong(String mJoblang){
            TextView posttitle=(TextView)mview.findViewById(R.id.job_long);
            posttitle.setText(mJoblang);

        }
        public void setAddress(String mJobaddress){
            TextView posttitle=(TextView)mview.findViewById(R.id.job_address);
            posttitle.setText(mJobaddress);

        }


    }
}
