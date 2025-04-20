package com.example.electronecstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electronecstore.HomeActivity;
import com.example.electronecstore.R;
import com.example.electronecstore.models.CartItem;
import com.example.electronecstore.shared.SharedPrefManager;
import com.example.electronecstore.models.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        Product product = productList.get(position);

        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productDescription = convertView.findViewById(R.id.productDescription);

        Button addToCartButton = convertView.findViewById(R.id.addToCartButton);

        int resId = context.getResources().getIdentifier(product.getImage(), "drawable", context.getPackageName());
        productImage.setImageResource(resId);

        productName.setText(product.getName());
        productPrice.setText("$" + product.getPrice());
        productDescription.setText(product.getDescription());

        addToCartButton.setOnClickListener(v -> {
            SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
            List<CartItem> cart = sharedPrefManager.getCart();

            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProduct().getName().equals(product.getName())) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(product, 1));
            }

            sharedPrefManager.saveCart(cart);

            ((HomeActivity) context).removeProductFromList(product);

            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
        });


        return convertView;
    }
}
