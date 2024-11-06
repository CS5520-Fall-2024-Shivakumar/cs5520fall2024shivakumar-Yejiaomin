package com.example.numad24fa_jiaominye;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView display;

    Button num1, num2, num3, num4, num5, num6, num7, num8, num9;
    Button add, num0, minus, delete, equal;

    Button quickCalcButton;
    GridLayout calculatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quickCalcButton = findViewById(R.id.quickcalc_button);
        calculatorLayout = findViewById(R.id.gridLayout);
        display = findViewById(R.id.display);

        quickCalcButton.setOnClickListener(view -> {
            quickCalcButton.setVisibility(View.GONE);
            display.setVisibility(View.VISIBLE);
            calculatorLayout.setVisibility(View.VISIBLE);
        });

        // Assign buttons directly to avoid issues with the assign method
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        num0 = findViewById(R.id.num0);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        delete = findViewById(R.id.delete);
        equal = findViewById(R.id.equal);

        // Set onClick listeners for buttons
        setOnClickListeners();
    }

    // Method to set click listeners
    private void setOnClickListeners() {
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);
        delete.setOnClickListener(this);
        equal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String expression = display.getText().toString();

        // Check button type and perform actions
        if (buttonText.equals("X")) {
            if (expression.length() > 0) {
                expression = expression.substring(0, expression.length() - 1); // Remove last character
                display.setText(expression);
            }
            return;
        }

        if (buttonText.equals("=")) {
            String result = getResult(expression);
            display.setText(result);
        } else {
            expression += buttonText;
            display.setText(expression);
        }
    }

    // Method to evaluate the expression and get the result
    private String getResult(String expression) {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            double result = e.evaluate();
            String finalResult = String.valueOf(result);
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Invalid Expression";
        }
    }
}