package com.example.numad24fa_jiaominye;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    // TextView to display the current calculation expression
    TextView display;

    // Buttons for numbers and operations
    Button num1, num2, num3, num4, num5, num6, num7, num8, num9;
    Button add, num0, minus, delete, equal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Initialize the display TextView
        display = findViewById(R.id.display);

        // Initialize buttons for numbers and operations
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

        // Set click listeners for all buttons
        setOnClickListeners();
    }

    // Method to set click listeners for all buttons
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
    // Handle button click events
    @Override
    public void onClick(View view) {
        Button button = (Button) view; // Cast the clicked view to a Button
        String buttonText = button.getText().toString(); // Get the text on the button
        String currentExpression = display.getText().toString(); // Get the current expression from the display

        // Handle the delete button (remove the last character)
        if (buttonText.equals("X")) {
            if (currentExpression.length() > 0) {
                currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
                display.setText(currentExpression);
            }
            return;
        }

        // Handle the equal button (evaluate the expression)
        if (buttonText.equals("=")) {
            String result = evaluateExpression(currentExpression);
            display.setText(result);
        } else {
            // Append the clicked button's text to the current expression
            currentExpression += buttonText;
            display.setText(currentExpression);
        }
    }

    // Evaluate the mathematical expression and return the result
    private String evaluateExpression(String expression) {
        try {
            // Build and evaluate the expression using the exp4j library
            Expression e = new ExpressionBuilder(expression).build();
            double result = e.evaluate();

            // Format the result (remove trailing .0 if the result is an integer)
            String finalResult = String.valueOf(result);
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            // Return error message if the expression is invalid
            return "Invalid Expression";
        }
    }
}
