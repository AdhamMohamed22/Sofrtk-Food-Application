package com.example.sofrtk.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;
import com.example.sofrtk.R;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.rxjava3.core.Single;

public class RandomMealAdapter extends RecyclerView.Adapter<RandomMealAdapter.RandomMealViewHolder> {
    Context context;
    ArrayList<RandomMeal> randomMealsList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
        holder.mealNameTxt.setText(currentRandomMeal.getStrMeal());
        holder.mealCategoryTxt.setText(currentRandomMeal.getStrCategory());
        holder.mealAreaTxt.setText(currentRandomMeal.getStrArea());
        Glide.with(context).load(currentRandomMeal.getStrMealThumb()).apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.mealImageView);
        holder.randomMealCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(currentRandomMeal);
        });
    }

    @Override
    public int getItemCount() {
        return randomMealsList.size();
    }

    public void updateData(ArrayList<RandomMeal> newMealList) {
        this.randomMealsList.clear();
        this.randomMealsList.addAll(newMealList);
        notifyDataSetChanged();
    }

    class RandomMealViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImageView;
        TextView mealNameTxt;
        TextView mealCategoryTxt;
        TextView mealAreaTxt;
        CardView randomMealCard;

        public RandomMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.mealImageView);
            mealNameTxt = itemView.findViewById(R.id.mealNameTxt);
            mealCategoryTxt = itemView.findViewById(R.id.mealCategoryTxt);
            mealAreaTxt = itemView.findViewById(R.id.mealAreaTxt);
            randomMealCard = itemView.findViewById(R.id.randomMealCard);
        }
    }

    public interface OnItemClickListener{
        void onClicks(RandomMeal randomMeal);
    }
}
