package com.abeldandi.alitasecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavDrawerActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private TextView textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        textName = findViewById(R.id.name);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser.getEmail()!=null) {
            textName.setText(firebaseUser.getEmail());
        }
        else {
            textName.setText("Login Failed!");
        }

        RelativeLayout logoutLayout = findViewById(R.id.logout);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengarahkan ke halaman login
                Intent intent = new Intent(NavDrawerActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}