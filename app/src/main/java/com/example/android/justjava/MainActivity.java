package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    final static int priceWhippedCream = 1;
    final static int priceChocolate = 2;
    int quantity;
    boolean hasWhippedCream;
    boolean hasChocolate;
    String name;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();

        EditText nameEditText = findViewById(R.id.customer_name_view);
        name = nameEditText.getText().toString();

        price = calculatePrice();
        displayMessage(createOrderSummary());

    }

    public void sendMail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_title) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, getString(R.string.send_mail)));
        }
    }

    private int calculatePrice() {
        int basePrice = 5;
        if(hasWhippedCream) {
            basePrice += priceWhippedCream;
        }
        if (hasChocolate) {
            basePrice += priceChocolate;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary() {
        String priceMessage = getString(R.string.name) + " " + name;
        priceMessage = priceMessage + "\n" + getString(R.string.add_whipped_cream) + " " + hasWhippedCream;
        priceMessage = priceMessage + "\n" + getString(R.string.add_chocolate) + " " + hasChocolate;
        priceMessage = priceMessage + "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage = priceMessage + "\n" + getString(R.string.total) + ": $" + price;
        priceMessage = priceMessage + "\n" + getString((R.string.thank_you));
        return priceMessage;
    }

    public void increment(View view) {
        if(quantity <= 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else Toast.makeText(MainActivity.this, getString(R.string.too_many_coffees),
                Toast.LENGTH_LONG).show();
    }

    public void decrement(View view) {
        if (quantity >= 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else Toast.makeText(MainActivity.this, getString(R.string.no_negative_coffees),
                Toast.LENGTH_LONG).show();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}