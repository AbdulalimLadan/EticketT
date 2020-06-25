package com.example.etickett;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

public class ProfileActivity extends AppCompatActivity {
    Button logout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private FirebaseAuth fAuth;
    FirebaseFirestore fStores;
    StorageReference storageReference;
    FirebaseUser users;
    String userIds;
    TextView namedisplay, emaildisplay;
    ImageView goback;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = (Button) findViewById(R.id.logout);
        namedisplay = (TextView)findViewById(R.id.namedisplay);
        emaildisplay =(TextView)findViewById(R.id.emaildisplay);
        goback = (ImageView)findViewById(R.id.goback);

        fAuth = FirebaseAuth.getInstance();
        fStores = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userIds = fAuth.getCurrentUser().getUid();
        users = fAuth.getCurrentUser();

        DocumentReference documentReference = fStores.collection("users").document(userIds);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    namedisplay.setText(documentSnapshot.getString("fName"));
                    emaildisplay.setText(documentSnapshot.getString("email"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();


            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
