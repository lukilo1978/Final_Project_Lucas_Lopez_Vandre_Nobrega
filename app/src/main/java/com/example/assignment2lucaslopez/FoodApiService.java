package com.example.assignment2lucaslopez;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodApiService {
    // Fetch food IDs based on a query
    @GET("/food/ingredients/search")
    Call<FoodIdResponse> getFoodIds(
            @Query("query") String query,
            @Query("apiKey") String apiKey
    );

    // Fetch nutritional information for a food ID
    @GET("food/ingredients/{id}/information")
    Call<NutritionalInfoResponse> getNutritionalInfo(
            @Path("id") int id,
            @Query("apiKey") String apiKey,
            @Query("amount") int amount,
            @Query("unit") String unit
    );
}
