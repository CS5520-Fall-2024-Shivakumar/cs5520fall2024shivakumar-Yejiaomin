package com.example.numad24fa_jiaominye;

import android.annotation.SuppressLint;
import android.app.sdksandbox.SandboxedSdk;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad24fa_jiaominye.adapter.ContactsAdapter;
import com.example.numad24fa_jiaominye.model.Contact;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        loadContacts();

        recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setHasFixedSize(true); // Improve performance for fixed-size list

        adapter = new ContactsAdapter(contactList, this::onContactClick);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ExtendedFloatingActionButton fab = findViewById(R.id.add_contact);
        fab.setOnClickListener(view -> {
            addNewContact();
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveContacts();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
        adapter.notifyDataSetChanged();
    }

    private void addNewContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new contact");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        // inset name
        final EditText nameInput = new EditText(this);
        nameInput.setHint("Enter Name");
        layout.addView(nameInput);

        // insert phone
        final EditText phoneInput = new EditText(this);
        phoneInput.setHint("Enter Phone");
        phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
        layout.addView(phoneInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name =  nameInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    Contact newContact = new Contact(name, phone);
                    contactList.add(newContact);
                    adapter.notifyItemInserted(contactList.size() - 1);
                    saveContacts(); // Save contacts after adding new one

                    Snackbar snackbar = Snackbar.make(recyclerView, "Contact Added", Snackbar.LENGTH_LONG)
                            .setAction("Undo", v -> {
                                contactList.remove(newContact);
                                adapter.notifyItemRemoved(contactList.size());
                                saveContacts();
                            });
                    snackbar.show();
                } else {
                    Snackbar.make(recyclerView, "Both fields are required", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialoge, int which) {
                dialoge.dismiss();
            }
        });
        builder.show();
    }

    private void onContactClick(Contact contact) {
        // Start a phone call when contact is clicked
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));
        startActivity(intent);
    }

    private void saveContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(contactList);
        editor.putString("contacts_list", json);
        editor.apply(); // Commit changes

        Log.d("ContactsActivity", "Loaded contacts: " + contactList.toString());

    }

    private void loadContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences("contacts_prefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("contacts_list", null);

        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
        contactList = gson.fromJson(json, type);

        if (contactList == null) {
            contactList = new ArrayList<>(); // Initialize an empty list if null
        }

        Log.d("ContactsActivity", "Loaded contacts: " + contactList.toString());

    }
}
