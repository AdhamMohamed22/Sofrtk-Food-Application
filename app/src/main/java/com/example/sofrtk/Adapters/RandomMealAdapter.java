package com.example.sofrtk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sofrtk.R;
import com.example.sofrtk.Pojos.RandomMeal;

import java.util.ArrayList;

public class RandomMealAdapter extends RecyclerView.Adapter<RandomMealAdapter.RandomMealViewHolder> {
    Context context;
    ArrayList<RandomMeal> randomMealsList;
    public RandomMealAdapter(Context context, ArrayList<RandomMeal> randomMealsList) {
        this.context = context;
        this.randomMealsList = randomMealsList;
    }

    @NonNull
    @Override
    public RandomMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.random_meal_item,parent,false);
        RandomMealViewHolder myviewHolder = new RandomMealViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RandomMealViewHolder holder, int position) {
        RandomMeal currentRandomMeal = randomMealsList.get(position);
        holder.mealNameTxt.setText(currentRandomMeal.getMealTitle());
        holder.mealCategoryTxt.setText(currentRandomMeal.getMealCategory());
        holder.mealAreaTxt.setText(currentRandomMeal.getMealArea());
        Glide.with(context).load(currentRandomMeal.getMealImage()).apply(new RequestOptions().override(250,250)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.mealImageView);
    }

    @Override
    public int getItemCount() {
        return randomMealsList.size();
    }

    class RandomMealViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImageView;
        TextView mealNameTxt;
        TextView mealCategoryTxt;
        TextView mealAreaTxt;

        public RandomMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.mealImageView);
            mealNameTxt = itemView.findViewById(R.id.mealNameTxt);
            mealCategoryTxt = itemView.findViewById(R.id.mealCategoryTxt);
            mealAreaTxt = itemView.findViewById(R.id.mealAreaTxt);
        }
    }
}
