package com.example.electronecstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.electronecstore.adapter.CartAdapter;
import com.example.electronecstore.models.CartItem;
import com.example.electronecstore.shared.SharedPrefManager;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    Button checkoutButton, confirmPurchaseButton;
    TextView totalTextView;
    CheckBox termsCheckbox;
    RadioGroup paymentRadioGroup;
    RadioButton prepaidRadio, codRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView         = findViewById(R.id.cartListView);
        checkoutButton       = findViewById(R.id.checkoutButton);
        confirmPurchaseButton= findViewById(R.id.confirmPurchaseButton);
        totalTextView        = findViewById(R.id.totalTextView);
        termsCheckbox        = findViewById(R.id.termsCheckbox);
        paymentRadioGroup    = findViewById(R.id.paymentRadioGroup);
        prepaidRadio         = findViewById(R.id.prepaidRadio);
        codRadio             = findViewById(R.id.codRadio);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        List<CartItem> cartItems = sharedPrefManager.getCart();

        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty 🛒", Toast.LENGTH_SHORT).show();
        }

        CartAdapter adapter = new CartAdapter(this, cartItems, totalTextView);
        cartListView.setAdapter(adapter);
        updateTotal(cartItems);


        checkoutButton.setOnClickListener(v -> finish());


        confirmPurchaseButton.setOnClickListener(v -> {
            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this,
                        "Please agree to the terms and conditions.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = paymentRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this,
                        "Please select a payment method.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton chosen = findViewById(selectedId);
            String method = chosen.getText().toString();

            Toast.makeText(this,
                    "Purchase confirmed (" + method + ")! Delivery in 2 days.",
                    Toast.LENGTH_LONG).show();

            sharedPrefManager.clearCart();

            finish();
        });
    }

    private void updateTotal(List<CartItem> cartItems) {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        totalTextView.setText("Total: $" + String.format("%.2f", total));
    }
}
