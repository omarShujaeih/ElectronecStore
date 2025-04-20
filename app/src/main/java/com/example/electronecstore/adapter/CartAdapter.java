package com.example.electronecstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.electronecstore.HomeActivity;
import com.example.electronecstore.R;
import com.example.electronecstore.models.CartItem;
import com.example.electronecstore.models.Product;
import com.example.electronecstore.shared.SharedPrefManager;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<CartItem> cartItems;
    private SharedPrefManager sharedPrefManager;
    private TextView totalTextView;
    List<Product> productList;
    public CartAdapter(Context context, List<CartItem> cartItems, TextView totalTextView) {
        this.context = context;
        this.cartItems = cartItems;
        this.totalTextView = totalTextView;
        sharedPrefManager = new SharedPrefManager(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_itame, parent, false);

        ImageView productImage = view.findViewById(R.id.productImage);
        TextView productName = view.findViewById(R.id.productName);
        TextView productPrice = view.findViewById(R.id.productPrice);
        TextView productQuantity = view.findViewById(R.id.productQuantity);
        Button decreaseButton = view.findViewById(R.id.decreaseButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        CartItem cartItem = cartItems.get(position);

        int resId = context.getResources().getIdentifier(cartItem.getProduct().getImage(), "drawable", context.getPackageName());
        productImage.setImageResource(resId);
        productName.setText(cartItem.getProduct().getName());
        productPrice.setText("$" + cartItem.getProduct().getPrice());
        productQuantity.setText("Qty: " + cartItem.getQuantity());

        decreaseButton.setOnClickListener(v -> {
            int quantity = cartItem.getQuantity();
            if (quantity > 1) {
                cartItem.setQuantity(quantity - 1);
                notifyDataSetChanged();
                sharedPrefManager.saveCart(cartItems);
                updateTotal();
            }
        });

        deleteButton.setOnClickListener(v -> {
            Product deletedProduct = cartItems.get(position).getProduct();

            cartItems.remove(position);
            notifyDataSetChanged();

            if (context instanceof HomeActivity) {
                HomeActivity homeActivity = (HomeActivity) context;

                homeActivity.removeProductFromList(deletedProduct);

                homeActivity.addProductBackToHome(deletedProduct);
            }

            SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
            sharedPrefManager.saveCart(cartItems);

            updateTotal();
        });



        return view;
    }

    private void updateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        totalTextView.setText("Total: $" + String.format("%.2f", total));
    }
}
