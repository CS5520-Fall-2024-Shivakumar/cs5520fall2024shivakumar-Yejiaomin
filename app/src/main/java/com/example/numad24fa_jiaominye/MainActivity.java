package com.example.numad24fa_jiaominye;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button showToastButton = findViewById(R.id.showToastButton);


        showToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Nama: Amy Ye\nEmail:ye.jiao@northeastern.edu";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}


//public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Nama: Amy Ye\nEmail:ye.jiao@northeastern.edu", Toast.LENGTH_LONG.show());
//            }