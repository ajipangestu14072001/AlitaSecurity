package com.abeldandi.alitasecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abeldandi.alitasecurity.adapter.EmployeeAdapter;
import com.abeldandi.alitasecurity.callback.FetchRecyclerViewItems;
import com.abeldandi.alitasecurity.model.DataObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EmployeeActivity extends AppCompatActivity implements FetchRecyclerViewItems {

    List<ListElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        init();

        ImageView menuIcon = findViewById(R.id.menu);
        menuIcon.setOnClickListener(view -> startActivity(new Intent(EmployeeActivity.this, NavDrawerActivity.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_employee);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_employee) {
                startActivity(new Intent(getApplicationContext(), EmployeeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_abssence) {
                startActivity(new Intent(getApplicationContext(), AbssenceActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_notifications) {
                startActivity(new Intent(getApplicationContext(), NotifActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_message) {
                startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    public void init() {
        elements = new ArrayList<>();
        elements.add(new ListElement("775447","Abelitho Siahaan","TT-6A-01"));
        elements.add(new ListElement("775447","Dandi Kusuma","TT-6A-02"));

        EmployeeAdapter listAdapter = new EmployeeAdapter(elements,this, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onIntent(DataObject dataObject) {

    }

    @Override
    public void onIntentEmployee(ListElement element) {
        Intent intent = new Intent(this, AbssenceActivity.class);
        intent.putExtra("element", element);
        startActivity(intent);
    }
}


