package com.example.sofrtk.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofrtk.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngridentViewHolder> {
    Context context;
    ArrayList<String> ingredientsList;
    ArrayList<String> measuresList;

    public IngredientAdapter(Context context, ArrayList<String> ingredientsList, ArrayList<String> measuresList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
        this.measuresList = measuresList;
    }

    @NonNull
    @Override
    public IngridentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        IngridentViewHolder myviewHolder = new IngridentViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngridentViewHolder holder, int position) {
        String currentIngredient = ingredientsList.get(position);
        holder.ingredientNameTxt.setText(currentIngredient);

        String currentMeasure = measuresList.get(position);
        holder.measureNameTxt.setText(currentMeasure);

        String imageUrl = "https://www.themealdb.com/images/ingredients/" + currentIngredient + "-Small.png";
        Glide.with(context)
                .load(imageUrl)
                .into(holder.ingridientImageView);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class IngridentViewHolder extends RecyclerView.ViewHolder {
        ImageView ingridientImageView;
        TextView ingredientNameTxt;
        TextView measureNameTxt;

        public IngridentViewHolder(@NonNull View itemView) {
            super(itemView);
            ingridientImageView = itemView.findViewById(R.id.ingridientImageView);
            ingredientNameTxt = itemView.findViewById(R.id.ingridientNameTxt);
            measureNameTxt = itemView.findViewById(R.id.measureNameTxt);
        }
    }
}
