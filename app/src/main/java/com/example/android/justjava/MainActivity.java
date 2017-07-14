
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitOrder = (Button) findViewById(R.id.submitOrder);
        Button increment = (Button) findViewById(R.id.increment);
        Button decrement = (Button) findViewById(R.id.decrement);

        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isWhipped = CheckWhipped();
                boolean hasChocolate = CheckChocolate();
                String userName = CheckName();

                if(TextUtils.isEmpty(userName)) {
                    editText.setError("This field cannot be empty.");
                    return;
                }

                int basePrice = 5;

                if(isWhipped) {
                    basePrice += 1;
                }

                if (hasChocolate){
                    basePrice += 2;
                }

                int orderPrice = basePrice * quantity;

                String orderSummary = createOrderSummary(orderPrice, isWhipped, hasChocolate, userName);

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject,userName));
                intent.putExtra(Intent. EXTRA_TEXT,orderSummary);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity <= 99) {
                    quantity = quantity + 1;
                    displayQuantity(quantity);
                }
                else {
                    Toast.makeText(MainActivity.this, "You cannot order more than 100 coffe's!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity >= 2) {
                    quantity = quantity - 1;
                    displayQuantity(quantity);
                }
                else {
                    Toast.makeText(MainActivity.this, "You cannot order less than 1 coffe!",Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    public boolean CheckWhipped () {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return checkBox.isChecked();
    }

    public boolean CheckChocolate () {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return checkBox.isChecked();
    }

    public String CheckName () {
        editText = (EditText) findViewById(R.id.name_edittext_view);
        return editText.getText().toString();
    }
}