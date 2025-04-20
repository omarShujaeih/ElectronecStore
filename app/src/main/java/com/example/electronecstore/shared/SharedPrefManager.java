package com.example.electronecstore.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.electronecstore.models.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {

    private static final String PREF_NAME = "store_data";
    private static final String KEY_CART = "cart_items";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void saveCart(List<CartItem> cartItems) {
        String json = gson.toJson(cartItems);
        editor.putString(KEY_CART, json);
        editor.apply();
    }

    public List<CartItem> getCart() {
        String json = sharedPreferences.getString(KEY_CART, null);
        Type type = new TypeToken<List<CartItem>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }

    public void clearCart() {
        editor.remove(KEY_CART).apply();
    }

    public void saveLoginDetails(String email, String password) {
        editor.putString(KEY_EMAIL, email); // Save email
        editor.putString(KEY_PASSWORD, password); // Save password
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public boolean isLoginDetailsExist() {
        return !(getEmail().isEmpty() || getPassword().isEmpty());
    }

    public boolean isCartNotEmpty() {
        List<CartItem> cartItems = getCart();
        return cartItems != null && !cartItems.isEmpty();
    }
}
