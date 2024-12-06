package com.example.assignment2lucaslopez;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;


public class MainActivity3 extends AppCompatActivity {
    private FoodApiService apiService;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> foodList = new ArrayList<>();
    private String apiKey = "54819a5d91204d96b9b21e2a5e52a0ec";
    private RecyclerView foodRecyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        apiService = ApiClient.getRetrofitInstance().create(FoodApiService.class);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);

        // Get data from Intent
        Intent intent = getIntent();
        String query = intent.getStringExtra("FOOD");
        int amount = intent.getIntExtra("GRAMS", 100); // Default amount is 100g

        // Fetch food data
        fetchFoodData(query, amount);
    }

    private void fetchFoodData(String query, int amount) {
        apiService.getFoodIds(query, apiKey).enqueue(new Callback<FoodIdResponse>() {
            @Override
            public void onResponse(Call<FoodIdResponse> call, Response<FoodIdResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> foodIds = response.body().results.stream()
                            .map(result -> result.id)
                            .collect(Collectors.toList());
                    fetchNutritionalInfo(foodIds, amount);
                } else {
                    Toast.makeText(MainActivity3.this, "Failed to fetch food IDs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodIdResponse> call, Throwable t) {
                Log.e("API_ERROR", "Error fetching food IDs: " + t.getMessage());
            }
        });
    }

    private void fetchNutritionalInfo(List<Integer> foodIds, int amount) {
        List<Call<NutritionalInfoResponse>> calls = new ArrayList<>();
        for (int id : foodIds) {
            calls.add(apiService.getNutritionalInfo(id, apiKey, amount, "g"));
        }

        // Execute calls and update RecyclerView
        for (Call<NutritionalInfoResponse> call : calls) {
            call.enqueue(new Callback<NutritionalInfoResponse>() {
                @Override
                public void onResponse(Call<NutritionalInfoResponse> call, Response<NutritionalInfoResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        NutritionalInfoResponse data = response.body();
                        FoodItem item = new FoodItem(
                                data.name,
                                data.image,
                                data.nutrition.nutrients.get(0).amount,  // Calories
                                data.nutrition.nutrients.get(1).amount,  // Proteins
                                data.nutrition.nutrients.get(2).amount,  // Carbs
                                data.nutrition.nutrients.get(3).amount   // Fats
                        );
                        foodList.add(item);
                        foodAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("API_ERROR", "Failed to fetch nutritional info");
                    }
                }

                @Override
                public void onFailure(Call<NutritionalInfoResponse> call, Throwable t) {
                    Log.e("API_ERROR", "Error fetching nutritional info: " + t.getMessage());
                }
            });
            foodRecyclerView = findViewById(R.id.recyclerView);
            foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Initialize food list
            foodList = new ArrayList<>();

            // Example: Add some sample data
            foodList.add(new FoodItem("Apple", "https://example.com/apple.jpg", 52, 14, 0.3, 0.2));

            // Set up the adapter
            foodAdapter = new FoodAdapter(foodList);
            foodRecyclerView.setAdapter(foodAdapter);

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}