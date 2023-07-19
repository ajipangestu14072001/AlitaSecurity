package com.abeldandi.alitasecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.abeldandi.alitasecurity.adapter.UserListAdapter;
import com.abeldandi.alitasecurity.model.Message;
import com.abeldandi.alitasecurity.ui.ChatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private List<ListElement> userList;
    private RecyclerView recyclerView;
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userList = new ArrayList<>();
        userList.add(new ListElement("775447", "Abelitho Siahaan", "TT-6A-01"));
        userList.add(new ListElement("775447", "Dandi Kusuma", "TT-6A-02"));

        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userListAdapter = new UserListAdapter(userList, this);
        recyclerView.setAdapter(userListAdapter);

        userListAdapter.setOnItemClickListener(position -> {
            ListElement selectedUser = userList.get(position);

            Intent intent = new Intent(MessageActivity.this, ChatActivity.class);
            intent.putExtra("list_element", selectedUser);
            startActivity(intent);
        });

        loadLastMessages();

        ImageView menuIcon = findViewById(R.id.menu);
        menuIcon.setOnClickListener(view -> startActivity(new Intent(MessageActivity.this, NavDrawerActivity.class)));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_message);

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

    private void loadLastMessages() {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");

        for (ListElement user : userList) {
            String userId = user.getIdnumber();
            Query userMessagesQuery = messagesRef.child(userId).orderByChild("time").limitToLast(1);

            userMessagesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                        Message lastMessage = messageSnapshot.getValue(Message.class);
                        if (lastMessage != null) {
                            user.setLastMessage(lastMessage.getMessage());
                            user.setLastMessageTime(lastMessage.getTime());
                        }
                    }
                    userListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle onCancelled if needed
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLastMessages();
    }
}