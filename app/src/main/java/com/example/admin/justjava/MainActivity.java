package com.example.admin.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        //To capture the name written by user
        EditText nameInput = (EditText)findViewById(R.id.name_field);
        String name = nameInput.getText().toString();
        //....Check if code works to display name entered
       // Log.v ("MainActivity", "Name: " + name);


        // To add whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        // To add Chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        // to display results on my log files
        //Log.v ("MainActivity", "Has Whipped Cream: " + hasWhippedCream);


        int price = calculatePrice(hasChocolate, hasWhippedCream);
        //Saving all order summary to a string variable called priceMessage
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);



      // FINALLY creates an intent to sendout order to an email app **********
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(priceMessage);
    }

    /**
     * Method to Calculate the price of the order.
     *@return total price
     * @param Chocolate whether the user wants chocolate
     * @param Cream  whether the user wants whipped cream
     */
    private int calculatePrice(boolean Cream, boolean Chocolate) {

        //price of 1 cup of coffee
        int basePrice = 5;

       // Add 1$ if user want whipped Cream
        if (Cream){
            basePrice = basePrice + 1;
        }

        //Add 2$ if user wants Chocolate
        if(Chocolate){
         basePrice = basePrice + 2;
        }


        return quantity * basePrice ;
    }

    /**
     * Method to create the order summary
     * @param price of the order
     * @param addWhippedCream whether the user wants whipped cream topping
     * @return text summary
     */

    private String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate){
         //price = calculatePrice();
        return "Name: " + name
                + "\nAdd Whipped Cream? " + addWhippedCream
                + "\nAdd Chocolate? " + addChocolate
                + "\nQuantity: " + quantity
                + "\nTotal: $ " + price + "\n" + getString(R.string.thank_you);
    }



    /**
     * This metheod is called when the plus button is clicked
     */
    public void increment(View view) {

        if(quantity == 100){
            //show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees" , Toast.LENGTH_SHORT).show();
            //Exit the method early since there is nothing else to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked
     */
    public void decrement(View view) {

        if (quantity == 1){
            // show an error message
            Toast.makeText(this, "You cannot have less than one cup of coffee", Toast.LENGTH_SHORT).show();
            //Exit the method early since there is nothing to do
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /***************************************************************************
     * This method displays the given price on the screen.

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
     *********************************************************************************/


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}