package com.example.numad24fa_jiaominye;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView display;

    MaterialButton num1, num2, num3, num4, num5, num6, num7, num8, num9;
    MaterialButton add, num0, minus, delete, equal;

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
        assign(delete, R.id.delete);
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
        String expression = display.getText().toString();
        expression = expression + buttonText;
        display.setText(expression);
        if (buttonText.equals("X")) {
            expression = display.getText().toString();
            expression = expression.substring(0, expression.length() - 2);
            display.setText(expression);
            return;
        }
        if (buttonText.equals("=")) {
            String result = getResult(expression.substring(0, expression.length()-1));
            display.setText(result);
        }
    }
    String getResult(String expression) {
        try{
            Expression e = new ExpressionBuilder(expression).build();
            // Evaluate the expression and get the result
            double result = e.evaluate();
            // Return the result as a string
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