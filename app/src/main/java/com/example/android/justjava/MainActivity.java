/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        /*String priceMessage ="Total: $" + (quantity*5);
        priceMessage = priceMessage + "\nThank you!";
        displayMessage(priceMessage);*/

        // Methods can be used with strings or ints together to initialize variable
       // String price = "Total: " + calculatePrice() + " kn" + "\nThank you!!!";
        //displayMessage(price);

        boolean isWhipped = CheckWhipped();
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkBox.isChecked();
        String userName = CheckName();
        int basePrice = 5;
        if(isWhipped) {
            basePrice += 1;
        }
        if (hasChocolate){
            basePrice += 2;
        }
        int orderPrice = basePrice * quantity;

        String orderSummary = createOrderSummary(orderPrice, isWhipped, hasChocolate, userName);;
        // displayMessage(orderSummary);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject,userName));
            intent.putExtra(Intent. EXTRA_TEXT,orderSummary);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean isWhipped, boolean hasChocolate, int quantity) {
        int basePrice = 5;
        if(isWhipped) {
            basePrice += 1;
        }
        if (hasChocolate){
            basePrice += 2;
        }
        int orderPrice = basePrice * quantity;
        return orderPrice;
    }

    private String createOrderSummary (int orderPrice, boolean hasWhippedCream, boolean hasChocolate, String userName) {
        String summary = getString(R.string.order_summary_name, userName);
        summary += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        summary += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        summary += "\n" + getString(R.string.order_summary_quantity, quantity);
        summary += "\n" + getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(orderPrice));
        summary += "\n" + getString(R.string.thank_you);
        return summary;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    /*private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
    /**
     * This method displays the given text on the screen.
     */
    public void decrement(View view) {
        if(quantity >= 2) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(MainActivity.this, "You cannot order less than 1 coffe!",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public void increment(View view) {
        if(quantity <= 99) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(MainActivity.this, "You cannot order more than 100 coffe's!",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public boolean CheckWhipped () {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean isWhipped = checkBox.isChecked();
        return isWhipped;
    }

    public String CheckName () {
        EditText editText = (EditText) findViewById(R.id.name_edittext_view);
        return editText.getText().toString();
    }
}