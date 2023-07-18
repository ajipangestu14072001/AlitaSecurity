package com.abeldandi.alitasecurity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abeldandi.alitasecurity.R;
import com.abeldandi.alitasecurity.model.DataObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    public static final String Database_Path = "AlitaSecurity";

    private EditText userIDEditText, statusEditText, roomIDEditText, securityNameEditText;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        userIDEditText = findViewById(R.id.userID);
        statusEditText = findViewById(R.id.status);
        roomIDEditText = findViewById(R.id.roomID);
        securityNameEditText = findViewById(R.id.securityName);
        btnUpload = findViewById(R.id.btnUpload);

        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        btnUpload.setOnClickListener(v -> {
            String userID = userIDEditText.getText().toString().trim();
            String status = statusEditText.getText().toString().trim();
            String roomID = roomIDEditText.getText().toString().trim();
            String securityName = securityNameEditText.getText().toString().trim();

            uploadDataToDatabase(userID, status, roomID, securityName);

            userIDEditText.setText("");
            statusEditText.setText("");
            roomIDEditText.setText("");
            securityNameEditText.setText("");
        });
    }

    private void uploadDataToDatabase(String userID, String status, String roomID, String securityName) {
        String uploadId = databaseReference.push().getKey();

        DataObject dataObject = new DataObject(userID, status, roomID);

        assert uploadId != null;
        databaseReference.child(uploadId).setValue(dataObject);

        Toast.makeText(this, "Data uploaded successfully!", Toast.LENGTH_SHORT).show();
    }
}