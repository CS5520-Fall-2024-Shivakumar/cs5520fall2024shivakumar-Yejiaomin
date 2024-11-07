package com.example.numad24fa_jiaominye;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    private EditText nameInput, phoneInput;
    private Button addButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameInput = findViewById(R.id.name_input);
        phoneInput = findViewById(R.id.phone_input);
        addButton = findViewById(R.id.add_contact_button);
        cancelButton = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        if (name != null && phone != null) {
            nameInput.setText(name);
            phoneInput.setText(phone);
            addButton.setText("Update Contact");
        }

        addButton.setOnClickListener(v -> {
            String newName = nameInput.getText().toString().trim();
            String newPhone = phoneInput.getText().toString().trim();

            if (!newName.isEmpty() && !newPhone.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", newName);
                resultIntent.putExtra("phone", newPhone);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close this activity and return to ContactsActivity
            } else {
                nameInput.setError("Name is required");
                phoneInput.setError("Phone is required");
            }
        });

        cancelButton.setOnClickListener(v -> finish());
    }
}
