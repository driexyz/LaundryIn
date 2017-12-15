package com.drpro.laundryin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drpro.laundryin.Common.Common;
import com.drpro.laundryin.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class SignInActivity extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    MaterialFancyButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please Wait ...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Check if user is not in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                            //Get User info
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(SignInActivity.this, "Sign In Successfully !", Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(SignInActivity.this, HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(home);
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, "Sign In Failed !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "User Not Exist !!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
