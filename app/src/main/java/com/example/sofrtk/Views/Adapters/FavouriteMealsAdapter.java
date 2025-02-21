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
import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.R;

import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealsViewHolder> {
    Context context;
    List<FavouriteMeal> favouriteMealsList;
    FavouriteMealsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FavouriteMealsAdapter(Context context, List<FavouriteMeal> favouriteMealsList) {
        this.context = context;
        this.favouriteMealsList = favouriteMealsList;
    }

    @NonNull
    @Override
    public FavouriteMealsAdapter.FavouriteMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.favourite_meal_item, parent, false);
        FavouriteMealsViewHolder myviewHolder = new FavouriteMealsViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealsAdapter.FavouriteMealsViewHolder holder, int position) {
        FavouriteMeal currentFavouriteMeal = favouriteMealsList.get(position);
        holder.favouriteMealNameTxt.setText(currentFavouriteMeal.getMeal().getStrMeal());
        Glide.with(context).load(currentFavouriteMeal.getMeal().getStrMealThumb()).apply(new RequestOptions().override(200, 200)).into(holder.favouriteMealImageView);

        holder.unFavouriteCardView.setOnClickListener(v -> {
            onItemClickListener.onRemoveClicks(currentFavouriteMeal);
        });
        holder.favouriteMealCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(currentFavouriteMeal.randomMeal);
        });
    }

    @Override
    public int getItemCount() {
        return favouriteMealsList.size();
    }

    public void updateData(List<FavouriteMeal> newFavouriteMeals) {
        this.favouriteMealsList.clear();
        this.favouriteMealsList.addAll(newFavouriteMeals);
        notifyDataSetChanged();
    }

    public void removeItem(FavouriteMeal favouriteMeal) {
        favouriteMealsList.remove(favouriteMeal);
        notifyDataSetChanged();
    }

    class FavouriteMealsViewHolder extends RecyclerView.ViewHolder {
        ImageView favouriteMealImageView;
        TextView favouriteMealNameTxt;
        CardView unFavouriteCardView;
        CardView favouriteMealCard;

        public FavouriteMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            favouriteMealImageView = itemView.findViewById(R.id.favouriteMealImageView);
            favouriteMealNameTxt = itemView.findViewById(R.id.favouriteMealNameTxt);
            unFavouriteCardView = itemView.findViewById(R.id.unFavouriteCardView);
            favouriteMealCard = itemView.findViewById(R.id.favouriteMealCard);
        }
    }

    public interface OnItemClickListener {
        void onRemoveClicks(FavouriteMeal favouriteMeal);
        void onClicks(RandomMeal randomMeal);
    }
}
