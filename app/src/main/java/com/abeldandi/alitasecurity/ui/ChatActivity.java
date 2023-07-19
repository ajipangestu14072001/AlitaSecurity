package com.abeldandi.alitasecurity.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abeldandi.alitasecurity.ListElement;
import com.abeldandi.alitasecurity.R;
import com.abeldandi.alitasecurity.adapter.MessageAdapter;
import com.abeldandi.alitasecurity.model.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity {

    private EditText messageEditText;
    private Button sendButton;
    private ListView listView;
    private Toolbar toolbar;
    private TextView nameSec, idSec;
    private MessageAdapter adapter;

    private DatabaseReference databaseReference;
    private List<Message> messageList;
    private String selectedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        listView = findViewById(R.id.listView);
        nameSec = findViewById(R.id.nameSec);
        idSec = findViewById(R.id.idSec);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            ListElement selectedUser = intent.getParcelableExtra("list_element");
            if (selectedUser != null) {
                setTitle(selectedUser.getSecurityname());
                selectedUserId = selectedUser.getIdnumber();
                nameSec.setText(selectedUser.securityname);
                idSec.setText(selectedUser.idnumber);

                databaseReference = FirebaseDatabase.getInstance().getReference("messages").child(selectedUserId);

                messageList = new ArrayList<>();
                adapter = new MessageAdapter(this, R.layout.item_message, messageList);
                listView.setAdapter(adapter);

                sendButton.setOnClickListener(v -> sendMessage());

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                        Message message = snapshot.getValue(Message.class);
                        messageList.add(message);
                        adapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(messageList.size() - 1);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                        // Not implemented
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        // Not implemented
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                        // Not implemented
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Not implemented
                    }
                });
            }
        }
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        String sender = getCurrentTime();

        if (!messageText.isEmpty()) {
            Message message = new Message(sender, messageText, true);
            databaseReference.push().setValue(message);
            messageEditText.setText("");
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Jakarta");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

