 package com.example.javaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name_EditText);
        String name = text.getText().toString();
        // checkbox for whipped cream
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        // checkbox for chocolate cream
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String PriceMessage = CreateOrderSummery(name, price, hasWhippedCream, hasChocolate);

        // intent to Email app

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "meraj4382@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Ordered User name :" + name );
            intent.putExtra(Intent.EXTRA_TEXT, PriceMessage );
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        // display the order summery
        displayMessage(PriceMessage);
    }

    //calculate price method
    private int calculatePrice(boolean haswhippedcream, boolean haschocolate) {
        int basePriceCoffee = 5;
        if (haswhippedcream) {
            basePriceCoffee = basePriceCoffee + 1;
        }
        if (haschocolate) {
            basePriceCoffee = basePriceCoffee + 2;
        }
        return quantity * basePriceCoffee;
    }

    /**
     * This method displays the plus  quantity value on the screen.
     */
    public void increament(View view) {
        if (quantity == 100) {
            Toast toast = Toast.makeText(this, R.string.Maximum_orders, Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            quantity = quantity + 1;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void decreament(View view) {
        if (quantity == 0) {
            Toast toast = Toast.makeText(this, R.string.minimum_order, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String CreateOrderSummery(String name, int price, boolean isClicked, boolean isClicked1) {
        String priceMessage = getString(R.string.name1) + name;
        priceMessage += "\n"+getString(R.string.added_whipped) + isClicked;
        priceMessage += "\n" +getString(R.string.added_chocolate) + isClicked1;
        priceMessage += "\n" +getString(R.string.quantity_display) + quantity;
        priceMessage += "\n" + getString(R.string.total_price) + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void displayMessage(String number) {
        TextView orderSummeryTextView = (TextView) findViewById(R.id.ordersummery_text_view);
        orderSummeryTextView.setText("" + number);
    }

}