package com.example.assignment2lucaslopez;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>  {
    private List<FoodItem> foodList;

    // Constructor to receive the list of food items
    public FoodAdapter(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    // This method creates a new ViewHolder instance from the item_food.xml layout
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item_food layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    // This method binds data from the FoodItem to the ViewHolder
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        // Get the current food item
        FoodItem foodItem = foodList.get(position);

        // Set the food data into the views
        holder.foodName.setText(foodItem.getName());
        Log.e("FoodAdapter", "foodName is null: " + (holder.foodName == null));
        holder.calories.setText(String.valueOf(foodItem.getCalories()));
        Log.e("FoodAdapter", "foodCalories is null: " + (holder.calories == null));
        holder.carbs.setText(String.valueOf(foodItem.getCarbs()));
        Log.e("FoodAdapter", "foodCalories is null: " + (holder.carbs == null));
        holder.proteins.setText(String.valueOf(foodItem.getProteins()));
        Log.e("FoodAdapter", "foodCalories is null: " + (holder.proteins == null));
        holder.fats.setText(String.valueOf(foodItem.getFats()));
        Log.e("FoodAdapter", "foodCalories is null: " + (holder.fats == null));

        // Use Picasso (or any image loading library) to load the image
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    // ViewHolder class to hold the views for each item
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, calories, carbs, proteins, fats;
        ImageView foodImage;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food);
            calories = itemView.findViewById(R.id.calories);
            foodImage = itemView.findViewById(R.id.foodImage);
            carbs = itemView.findViewById(R.id.carbs);
            proteins = itemView.findViewById(R.id.proteins);
            fats = itemView.findViewById(R.id.fats);
        }
    }
}