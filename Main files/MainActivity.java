package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;
import java.util.ArrayList;




    public class MainActivity extends AppCompatActivity {

        double firstnum;
        String operation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Initialize buttons and screen
            Button num0 = findViewById(R.id.num0);
            Button num1 = findViewById(R.id.num1);
            Button num2 = findViewById(R.id.num2);
            Button num3 = findViewById(R.id.num3);
            Button num4 = findViewById(R.id.num4);
            Button num5 = findViewById(R.id.num5);
            Button num6 = findViewById(R.id.num6);
            Button num7 = findViewById(R.id.num7);
            Button num8 = findViewById(R.id.num8);
            Button num9 = findViewById(R.id.num9);

            Button on = findViewById(R.id.on);
            Button off = findViewById(R.id.off);
            Button ac = findViewById(R.id.ac);
            Button del = findViewById(R.id.del);
            Button equal = findViewById(R.id.equal);
            Button plus = findViewById(R.id.plus);
            Button min = findViewById(R.id.min);
            Button div = findViewById(R.id.div);
            Button times = findViewById(R.id.times);
            Button point = findViewById(R.id.point);

            TextView screen = findViewById(R.id.screen);

            // Clear screen on AC
            ac.setOnClickListener(view -> {
                firstnum = 0;
                screen.setText("0");
            });

            // Turn off screen
            off.setOnClickListener(view -> screen.setVisibility(View.GONE));

            // Turn on screen
            on.setOnClickListener(view -> {
                screen.setVisibility(View.VISIBLE);
                screen.setText("0");
            });

            // List of number buttons
            ArrayList<Button> nums = new ArrayList<>();
            nums.add(num0);
            nums.add(num1);
            nums.add(num2);
            nums.add(num3);
            nums.add(num4);
            nums.add(num5);
            nums.add(num6);
            nums.add(num7);
            nums.add(num8);
            nums.add(num9);

            // Number button click listeners
            for (Button b : nums) {
                b.setOnClickListener(view -> {
                    if (!screen.getText().toString().equals("0")) {
                        screen.setText(screen.getText().toString() + b.getText().toString());
                    } else {
                        screen.setText(b.getText().toString());
                    }
                });
            }

            // List of operator buttons
            ArrayList<Button> opers = new ArrayList<>();
            opers.add(div);
            opers.add(min);
            opers.add(plus);
            opers.add(times);

            // Operator button click listeners
            for (Button b : opers) {
                b.setOnClickListener(view -> {
                    // Save the first number from the screen
                    firstnum = Double.parseDouble(screen.getText().toString());

                    // Get the operation (e.g., "+", "-", "*", "/")
                    operation = b.getText().toString();

                    // Append the operation to the screen (e.g., 12 +)
                    screen.setText(screen.getText().toString() + " " + operation + " ");
                });
            }

            // Delete button click listener
            del.setOnClickListener(view -> {
                String num = screen.getText().toString();
                if (num.length() > 1) {
                    screen.setText(num.substring(0, num.length() - 1));
                } else if (num.length() == 1 && !num.equals("0")) {
                    screen.setText("0");
                }
            });

            // Decimal point button click listener
            point.setOnClickListener(view -> {
                String screenContent = screen.getText().toString();

                // Find the last part of the screen content (the current number)
                String[] parts = screenContent.split("[\\+\\-\\*/]"); // Split by any operator
                String currentNumber = parts[parts.length - 1]; // Get the last number

                // Only add the decimal point if the current number doesn't already contain a "."
                if (!currentNumber.contains(".")) {
                    screen.setText(screenContent + ".");
                }
            });


            // Equal button click listener
            equal.setOnClickListener(view -> {
                String screenContent = screen.getText().toString();

                // Split the screen content by the operator with proper escaping for special characters
                String[] parts;
                switch (operation) {
                    case "+":
                        parts = screenContent.split("\\+"); // Escape `+` using `\\+`
                        break;
                    case "-":
                        parts = screenContent.split("\\-");
                        break;
                    case "*":
                        parts = screenContent.split("\\*");
                        break;
                    case "/":
                        parts = screenContent.split("\\/");
                        break;
                    default:
                        screen.setText("Error");
                        return;
                }

                // Ensure there are two parts (first number and second number)
                if (parts.length < 2) {
                    screen.setText("Error");
                    return;
                }

                // Parse the second number
                double secondnum = Double.parseDouble(parts[1].trim());
                double result = 0;

                switch (operation) {
                    case "/":
                        if (secondnum != 0) {
                            result = firstnum / secondnum;
                        } else {
                            screen.setText("Error");
                            return;
                        }
                        break;
                    case "*":
                        result = firstnum * secondnum;
                        break;
                    case "-":
                        result = firstnum - secondnum;
                        break;
                    case "+":
                        result = firstnum + secondnum;
                        break;
                    default:
                        screen.setText("Error");
                        return;
                }

                // Show the result on the screen
                screen.setText(String.valueOf(result));

                // Save the result for further calculations
                firstnum = result;
            });


        }
    }


