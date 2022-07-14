/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    boolean checkChocolateBoxState = false;
    boolean checkWhippedBoxState = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)

    {

        int price = calculatePrice();
        String name = enterEditText();
        String message = createOrderSummary(price, checkWhippedBoxState, checkChocolateBoxState, name);

        /*
         * Creating an implicit intent to call an email app
         * */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);

        /*
         * Check if the device has at least one app to execute the intent before calling the startActivity method
         * */
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "No app to complete your task", Toast.LENGTH_SHORT).show();


    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice()

    {
        int price = quantity * 5;

        if (checkWhippedBoxState) {
            price = price + 1;
        }
        if (checkChocolateBoxState) {
            price = price + 2;
        }
        return price;
    }

    /*
     * Method called when the box is checked/unchecked
     * */

    public void checkWhippedBoxClicked(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_check_box_view);
        checkWhippedBoxState = checkBox.isChecked();
    }

    /*
     * Method called when the box is checked/unchecked
     * */

    public void checkChocolateBoxClicked(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate_box_view);
        checkChocolateBoxState = checkBox.isChecked();
    }


    /*
     * @param price for the quantities ordered
     * @param whippedCheckBox stores the state of WhippedcheckBox
     * @param chocolateCheckBox stores the state of chocolatecheckBox
     * method returns summaried order
     * */
    private String createOrderSummary(int price, boolean whippedCheckBox, boolean chocolateCheckBox, String name)
    {
        return "Name: " + name + "\nAdd whipped cream? " + whippedCheckBox
                + "\nAdd Chocolate? " + chocolateCheckBox +
                "\nQuantity: " + quantity + "\nTotal: $"
                + calculatePrice() + "\n" + getString(R.string.thank_you);
    }

    /*
     * Method that keep track of the user name entered!
     * */
    private String enterEditText()
    {
        EditText enteredText = (EditText) findViewById(R.id.edit_text_view);

        String myName = enteredText.getText().toString();

        return myName;
    }

    /*
     * Method called when the increment button is pressed
     * */
    public void increment(View view)
    {
        if (quantity >= 1 && quantity <= 99)
            quantity += 1;
        else
            showToastMessage();
        display(quantity);
    }

    /*
     * Method called when the decrement button is pressed
     * */
    public void decrement(View view)
    {
        if (quantity > 1)
            quantity -= 1;
        else
            showToastMessage();
        display(quantity);
    }

    /*
     * Method called to make a toast message
     * */
    public void showToastMessage()
    {
        Context context = getApplicationContext();
        CharSequence text = "You have reached its limit";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }


}
