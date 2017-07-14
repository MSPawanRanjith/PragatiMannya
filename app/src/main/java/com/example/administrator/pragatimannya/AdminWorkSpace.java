package com.example.administrator.pragatimannya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminWorkSpace extends AppCompatActivity {
    //private Button signoutButton;
    FirebaseAuth auth;
    public String uid;
    private FirebaseAuth.AuthStateListener authListener;
    private RecyclerView recylceview;
    private DatabaseReference mDatabase;
    private Long contactnum;
    public String emailadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_work_space);

        //signoutButton=(Button)findViewById(R.id.signout);
        auth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Firebase.setAndroidContext(this);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AdminWorkSpace.this, AdminLoginActivity.class));
                    finish();
                }
            }
        };
        //Get UID of the user
        uid = user.getUid();

        //Query userfEvents = mDatabase.orderByChild("type").equalTo("event");
        Log.d("Before recycle", uid);
        recylceview = (RecyclerView) findViewById(R.id.list);
        Log.d("After recycle", "");

        recylceview.setHasFixedSize(true);
        recylceview.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        Log.d("Before recycle", "Layoutmanager");

    }
    @Override
    public void onStart() {
        super.onStart();


         Log.d("Where", "onStart: ");
        Query userfEvents = mDatabase.orderByChild("type").equalTo(uid);

        Log.d("SAomething",userfEvents.toString());
        FirebaseRecyclerAdapter<AdminWorkSpaceData,PostviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AdminWorkSpaceData, PostviewHolder>(
                AdminWorkSpaceData.class,
                R.layout.list_card,
                PostviewHolder.class,
                userfEvents


        ) {
            @Override
            protected void populateViewHolder(PostviewHolder viewHolder, AdminWorkSpaceData model, int position) {

                viewHolder.setEmpname(model.getEmpname().toUpperCase());
                viewHolder.setEmpid(model.getEmpid());
                viewHolder.setConatct(model.getContact());
                viewHolder.setEmail(model.getEmail());
                contactnum=Long.parseLong(model.getContact());
                emailadd=model.getEmail();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminWorkSpace.this);
                        builder.setTitle("Actions");
                        builder.setMessage("Choose your action : ");

                        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",contactnum.toString(), null));
                                dialog.dismiss();
                                try {
                                    startActivity(intent);
                                }
                                catch (Exception e){
                                    Log.d("Exception","Call exception");
                                }
                            }
                        });
//                        builder.setPositiveButton("Mail", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Do nothing but close the dialog
//                                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                                        "mailto",emailadd , null));
//                                dialog.dismiss();
//                                startActivity(Intent.createChooser(i, "Send email"));
//                            }
//                        });

                        builder.setNegativeButton("AddTask", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                Intent intent =new Intent(AdminWorkSpace.this,AdminAddTaskActivity.class);

                                dialog.dismiss();
                                startActivity(intent);
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                Log.d("Before recycle", "Inside populate");
            }
        };
        recylceview.setAdapter(firebaseRecyclerAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
           // case R.id.action_remove:
               // delete();
             //   return true;
            case R.id.action_signout:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public  void  signout(){
        auth.signOut();
        Intent intent = new Intent(AdminWorkSpace.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public static class PostviewHolder extends RecyclerView.ViewHolder{
        View mview;

        public PostviewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setEmpname(String empname){
            TextView posttitle = (TextView) mview.findViewById(R.id.emp_name);
            posttitle.setText(empname);
        }
        public void setEmpid(String empid){
            TextView posttitle = (TextView) mview.findViewById(R.id.emp_id);
            posttitle.setText(empid);
        }
        public void setConatct(String empconatct){
            TextView posttitle = (TextView) mview.findViewById(R.id.emp_contact);
            posttitle.setText(empconatct);
        }
        public void setEmail(String empemail){
            TextView posttitle=(TextView)mview.findViewById(R.id.emp_email);
            posttitle.setText(empemail);

        }


    }
}
