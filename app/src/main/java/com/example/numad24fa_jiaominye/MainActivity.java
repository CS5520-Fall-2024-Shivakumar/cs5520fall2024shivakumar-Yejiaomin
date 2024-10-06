package com.example.numad24fa_jiaominye;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView display;

    MaterialButton num1, num2, num3, num4, num5, num6, num7, num8, num9;
    MaterialButton add, num0, minus, multiply, equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
        assign(num1, R.id.num1);
        assign(num2, R.id.num2);
        assign(num3, R.id.num2);
        assign(num4, R.id.num4);
        assign(num5, R.id.num5);
        assign(num6, R.id.num6);
        assign(num7, R.id.num7);
        assign(num8, R.id.num8);
        assign(num9, R.id.num9);
        assign(num0, R.id.num0);
        assign(add, R.id.add);
        assign(minus, R.id.minus);
        assign(multiply, R.id.multiply);
        assign(equal, R.id.equal);
    }
    void assign (MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        display.setText(buttonText);
    }
}