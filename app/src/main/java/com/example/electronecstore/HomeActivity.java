package com.example.electronecstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.electronecstore.adapter.ProductAdapter;
import com.example.electronecstore.models.Product;
import com.example.electronecstore.shared.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView productListView;
    Button goToCartBtn;
    SharedPrefManager prefManager;
    ProductAdapter adapter;
    List<Product> productList;
    List<Product> filtereProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        productListView = findViewById(R.id.productListView);
        goToCartBtn = findViewById(R.id.cartButton);

        SearchView searchView = findViewById(R.id.searchView);

        productList = new ArrayList<>();
        filtereProductList = new ArrayList<>();

        productList.add(new Product("iphon 12 pro", 699.99, "iphon","They are the fourteenth-generation iPhones, succeeding the iPhone 11. They were unveiled at a virtually held Apple Special Event at Apple Park in Cupertino, California"));
        productList.add(new Product("airpods", 359.99, "airpods"," H2 chip is the force behind the advanced audio performance of AirPods Pro 2, working with the driver and amplifier to create crisp, high-definition sound. "));
        productList.add(new Product("Camera soni", 1999.99, "camera","Alpha 7 IV - Full-frame Interchangeable Lens Camera 33MP, 10FPS, 4K/60p"));
        productList.add(new Product("iphon 13 pro", 879.99, "iphon","They are the fourteenth-generation iPhones, succeeding the iPhone 11. They were unveiled at a virtually held Apple Special Event at Apple Park in Cupertino, California"));
        productList.add(new Product("iphon 12 ", 449.99, "iphon","They are the fourteenth-generation iPhones, succeeding the iPhone 11. They were unveiled at a virtually held Apple Special Event at Apple Park in Cupertino, California"));
        productList.add(new Product("iphon 12 pro", 699.99, "iphon","They are the fourteenth-generation iPhones, succeeding the iPhone 11. They were unveiled at a virtually held Apple Special Event at Apple Park in Cupertino"));

        filtereProductList.addAll(productList);

        adapter = new ProductAdapter(this, filtereProductList);
        productListView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        goToCartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void filterProducts(String query) {
        filtereProductList.clear();

        if (query.isEmpty()) {
            filtereProductList.addAll(productList);
        } else {
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filtereProductList.add(product);
                }
            }
        }

        adapter.notifyDataSetChanged();

    }
    public void removeProductFromList(Product product) {
        productList.remove(product);
        filtereProductList.remove(product);
        adapter.notifyDataSetChanged();
    }

    public void addProductBackToHome(Product product) {
        productList.add(product);
        filtereProductList.add(product);
        adapter.notifyDataSetChanged();
    }





}
