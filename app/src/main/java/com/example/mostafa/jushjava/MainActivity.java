package com.example.mostafa.jushjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.id.edit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int numberOfcoffee = 0;
    boolean haswhippedcream;
    boolean haswhippedchocolate;

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (numberOfcoffee == 100) {
            Toast.makeText(this, "you cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfcoffee = numberOfcoffee + 1;
        displaynumberOfcoffee(numberOfcoffee);
    }

    public void decrement(View view) {
        if (numberOfcoffee == 1) {
            Toast.makeText(this, "you cannot have few than 0 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfcoffee = numberOfcoffee - 1;
        displaynumberOfcoffee(numberOfcoffee);
    }

    public void submitOrder(View view) {
        CheckBox whippedcreamcheckbox = (CheckBox) findViewById(R.id.Whipped_cream_checkBox);
        haswhippedcream = whippedcreamcheckbox.isChecked();
        Log.v("MainActivity", "Has whipped cream:" + haswhippedcream);

        CheckBox whippedchocolatecheckbox = (CheckBox) findViewById(R.id.Whipped_chocolate_checkBox);
        haswhippedchocolate = whippedchocolatecheckbox.isChecked();
        Log.v("MainActivity", "Has whipped hocolate:" + haswhippedchocolate);

        EditText nameeditText = (EditText) findViewById(R.id.enter_name);
        String hasnameeditText = nameeditText.getText().toString();
        Log.v("MainActivity", "has nameedit text" + hasnameeditText);

        int price = calculatePrice();
        String priceMessage = createOrderSummery(price, haswhippedcream, haswhippedchocolate, hasnameeditText);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java orderd for" + hasnameeditText);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order
     */
    private int calculatePrice() {
        if (haswhippedcream == true) {
            return 4 + (numberOfcoffee * 5);
        }
        if (haswhippedchocolate == true) {
            return 4 + (numberOfcoffee * 5);
        }
        if (haswhippedchocolate == true && haswhippedcream == true) {
            return 4 + 4 + (numberOfcoffee * 5);
        } else {
            return numberOfcoffee * 5;
        }
    }

    private String createOrderSummery(int price, boolean addwhippedcream, boolean addwhippedchocolate, String addnameeditText) {
            String priceMessage = "Name : " + addnameeditText;
        priceMessage += "\nadd whippedcream?" + addwhippedcream;
        priceMessage += "\nadd whippedcream?" + addwhippedchocolate;
        priceMessage += "\nQuantity : " + numberOfcoffee;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n thank you";
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displaynumberOfcoffee(int number) {
        TextView zeroTextView = (TextView) findViewById(R.id.zero);
        zeroTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView OrderSummeryTextView = (TextView) findViewById(R.id.order_text_view);
        OrderSummeryTextView.setText(message);
    }
}