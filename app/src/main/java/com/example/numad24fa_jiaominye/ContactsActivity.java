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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private SharedPreferences sharedPreferences;

    private final ActivityResultLauncher<Intent> addContactLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String name = result.getData().getStringExtra("name");
                    String phone = result.getData().getStringExtra("phone");
                    Log.d("ContactsActivity", "Received name: " + name + ", phone: " + phone);

                    if (name != null && phone != null) {
                        Contact newContact = new Contact(name, phone);
                        contactList.add(newContact);
                        adapter.notifyItemInserted(contactList.size() - 1);
                        saveContacts(); // Save the updated contact list
                        Log.d("ContactsActivity", "Contact added and saved: " + newContact);

                    }
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    // Log and show a Snackbar only when contact addition is canceled.
                    Log.d("ContactsActivity", "Contact addition cancelled by user.");
                    Snackbar.make(recyclerView, "Contact addition cancelled", Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.d("ContactsActivity", "Result data was null or incomplete.");
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        sharedPreferences = getSharedPreferences("contacts_prefs", MODE_PRIVATE);
        contactList = new ArrayList<>();

        loadContacts();

        adapter = new ContactsAdapter(contactList, this::onContactClick);
        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton fab = findViewById(R.id.add_contact);
        fab.extend();
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
            addContactLauncher.launch(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
        adapter.notifyDataSetChanged(); // Refresh list when returning to this activity
        Log.d("ContactsActivity", "onResume: Contacts reloaded and adapter notified.");

    }

    private final ActivityResultLauncher<Intent> contactDetailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    boolean isDeleted = result.getData().getBooleanExtra("delete_contact", false);
                    if (isDeleted) {
                        String nameToDelete = result.getData().getStringExtra("name");
                        contactList.removeIf(contact -> contact.getName().equals(nameToDelete));
                        adapter.notifyDataSetChanged();
                        saveContacts();
                        Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
                    }
                    boolean isEdit = result.getData().getBooleanExtra("update_contact", false);
                    int position = result.getData().getIntExtra("position", -1);
                    String name = result.getData().getStringExtra("name");
                    String phone = result.getData().getStringExtra("phone");
                    Log.d("debug", "position loaded: " + position);
                    Log.d("debug", "name loaded: " + name);
                    Log.d("debug", "phone loaded: " + phone);
                    if(isEdit && position >=0) {
                        contactList.get(position).setName(name);
                        contactList.get(position).setPhone(phone);
                        adapter.notifyDataSetChanged();
                        saveContacts();
                        Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
                    }


                }
            });
    private void onContactClick(Contact contact) {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("name", contact.getName());
        intent.putExtra("phone", contact.getPhone());
        intent.putExtra("position", contact.getPosition());
        contactDetailLauncher.launch(intent);
    }

    private void saveContacts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contactList);
        editor.putString("contacts_list", json);
        editor.apply(); // Commit changes

        Log.d("ContactsActivity", "Loaded contacts: " + contactList.toString());

    }

    private void loadContacts() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("contacts_list", null);
        Log.d("ContactsActivity", "Loaded contacts JSON: " + json);

        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
        List<Contact> loadedContacts = gson.fromJson(json, type);


        if (loadedContacts == null) {
            loadedContacts = new ArrayList<>(); // Initialize an empty list if null
            Log.d("ContactsActivity", "No contacts found, initializing empty list.");

        }
        contactList.clear();  // 确保每次重新加载清空列表
        contactList.addAll(loadedContacts);
        for (int i=0; i<contactList.size(); i++){
            contactList.get(i).setPosition(i);
        }
        Log.d("ContactsActivity", "Contacts loaded: " + contactList);

    }
}
