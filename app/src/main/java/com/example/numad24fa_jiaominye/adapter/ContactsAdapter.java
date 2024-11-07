package com.example.numad24fa_jiaominye.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad24fa_jiaominye.ContactsActivity;
import com.example.numad24fa_jiaominye.R;
import com.example.numad24fa_jiaominye.model.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final List<Contact> contactList;
    private final OnContactClickListener clickListener;


    public ContactsAdapter(List<Contact> contactList, OnContactClickListener clickListener) {
        this.contactList = contactList;
        this.clickListener = clickListener;
    }

    public interface OnContactClickListener {
        void onContactClick(Contact contact);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhone());
        Log.d("ContactsAdapter", "Binding contact at position " + position + ": " + contact.getName() + ", " + contact.getPhone());

        holder.itemView.setOnClickListener(v -> clickListener.onContactClick(contact));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView phoneTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.contact_name);
            phoneTextView = itemView.findViewById(R.id.contact_phone);

        }
    }
}
