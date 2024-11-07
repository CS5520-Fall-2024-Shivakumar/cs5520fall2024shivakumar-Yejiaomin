package com.example.numad24fa_jiaominye;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView phoneTextView;
    private String contactName;
    private String contactPhone;

    private ActivityResultLauncher<Intent> editContactLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        nameTextView = findViewById(R.id.detail_name);
        phoneTextView = findViewById(R.id.detail_phone);

        Button editButton = findViewById(R.id.edit_button);
        Button callButton = findViewById(R.id.call_button);
        Button deleteButton = findViewById(R.id.delete_button);
        Button returnButton = findViewById(R.id.return_button);


        // Get contact details from intent
        Intent intent = getIntent();
        contactName = intent.getStringExtra("name");
        contactPhone = intent.getStringExtra("phone");
        int contactPosition = intent.getIntExtra("position", -1); // 获取联系人索引


        // Display contact details
        nameTextView.setText(contactName);
        phoneTextView.setText(contactPhone);

        // Initialize the ActivityResultLauncher for editing contact
        editContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        contactName = result.getData().getStringExtra("name");
                        contactPhone = result.getData().getStringExtra("phone");
                        nameTextView.setText(contactName);
                        phoneTextView.setText(contactPhone);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("update_contact", true);
                        resultIntent.putExtra("position", contactPosition); // 传递被更新联系人的位置
                        resultIntent.putExtra("name", contactName);
                        resultIntent.putExtra("phone", contactPhone);
                        setResult(RESULT_OK, resultIntent);
                        Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Edit contact
        editButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(ContactDetailActivity.this, AddContactActivity.class);
            editIntent.putExtra("name", contactName);
            editIntent.putExtra("phone", contactPhone);
            editContactLauncher.launch(editIntent);
        });

        returnButton.setOnClickListener(v -> finish());

        // Call contact
        callButton.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactPhone));
            startActivity(callIntent);
        });

        // Delete contact and return to ContactsActivity
        deleteButton.setOnClickListener(v -> {
            Intent deleteIntent = new Intent();
            deleteIntent.putExtra("delete_contact", true);
            deleteIntent.putExtra("name", contactName);
            setResult(RESULT_OK, deleteIntent);
            finish();
        });

    }
}