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
import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Models.DTOs.FilterMeal;
import com.example.sofrtk.R;

import java.util.ArrayList;
import java.util.List;

public class PlanMealsAdapter extends RecyclerView.Adapter<PlanMealsAdapter.PlanMealsViewHolder> {
    Context context;
    List<PlanMeal> planMealsList;
    PlanMealsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PlanMealsAdapter(Context context, List<PlanMeal> planMealsList) {
        this.context = context;
        this.planMealsList = planMealsList;
    }

    @NonNull
    @Override
    public PlanMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.plan_meal_item, parent, false);
        PlanMealsViewHolder myviewHolder = new PlanMealsViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanMealsViewHolder holder, int position) {
        PlanMeal currentPlanMeal = planMealsList.get(position);
        holder.planMealNameTxt.setText(currentPlanMeal.getMeal().getStrMeal());
        Glide.with(context).load(currentPlanMeal.getMeal().getStrMealThumb()).apply(new RequestOptions().override(200, 200)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.planMealImageView);

        holder.unPlanCardView.setOnClickListener(v -> {
            onItemClickListener.onClicks(currentPlanMeal);
        });
    }

    @Override
    public int getItemCount() {
        return planMealsList.size();
    }

    public void updateData(List<PlanMeal> newPlanMeals) {
        this.planMealsList.clear();
        this.planMealsList.addAll(newPlanMeals);
        notifyDataSetChanged();
    }

    public void removeItem(PlanMeal planMeal) {
        planMealsList.remove(planMeal);
        notifyDataSetChanged();
    }

    class PlanMealsViewHolder extends RecyclerView.ViewHolder {
        ImageView planMealImageView;
        TextView planMealNameTxt;
        CardView unPlanCardView;

        public PlanMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            planMealImageView = itemView.findViewById(R.id.planMealImageView);
            planMealNameTxt = itemView.findViewById(R.id.planMealNameTxt);
            unPlanCardView = itemView.findViewById(R.id.unPlanCardView);
        }
    }

    public interface OnItemClickListener {
        void onClicks(PlanMeal planMeal);
    }
}
