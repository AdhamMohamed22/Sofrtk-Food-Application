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
import com.example.sofrtk.Models.DTOs.FilterMeal;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.R;

import java.util.ArrayList;
import java.util.Collection;

public class FilterMealsAdapter extends RecyclerView.Adapter<FilterMealsAdapter.FilterMealsViewHolder>{
    Context context;
    ArrayList<FilterMeal> filterMealsList;
    FilterMealsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(FilterMealsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FilterMealsAdapter(Context context, ArrayList<FilterMeal> filterMealsList) {
        this.context = context;
        this.filterMealsList = filterMealsList;
    }

    @NonNull
    @Override
    public FilterMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.filtered_meal_item,parent,false);
        FilterMealsViewHolder myviewHolder = new FilterMealsViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterMealsViewHolder holder, int position) {
        FilterMeal currentFilterMeal = filterMealsList.get(position);
        holder.filteredMealNameTxt.setText(currentFilterMeal.getFilterMealName());
        Glide.with(context).load(currentFilterMeal.getFilterMealImage()).apply(new RequestOptions().override(200,200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.filteredMealImageView);

        holder.filteredMealCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(filterMealsList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return filterMealsList.size();
    }

    public void updateData(ArrayList<FilterMeal> newFilterMeals) {
        this.filterMealsList.clear();
        this.filterMealsList.addAll(newFilterMeals);
        notifyDataSetChanged();
    }

    class FilterMealsViewHolder extends RecyclerView.ViewHolder{
        ImageView filteredMealImageView;
        TextView filteredMealNameTxt;
        CardView favouriteCardView;
        CardView bookmarkCardView;
        CardView filteredMealCard;

        public FilterMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            filteredMealImageView = itemView.findViewById(R.id.filteredMealImageView);
            filteredMealNameTxt = itemView.findViewById(R.id.filteredMealNameTxt);
            favouriteCardView = itemView.findViewById(R.id.favouriteCardView);
            bookmarkCardView = itemView.findViewById(R.id.bookmarkCardView);
            filteredMealCard = itemView.findViewById(R.id.filteredMealCard);
        }
    }

    public interface OnItemClickListener{
        void onClicks(FilterMeal filterMeal);
    }
}
