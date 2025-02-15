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
import com.example.sofrtk.Models.DTOs.Country;
import com.example.sofrtk.Models.DTOs.Ingredient;
import com.example.sofrtk.R;

import java.util.ArrayList;

public class MainIngredientAdapter extends RecyclerView.Adapter<MainIngredientAdapter.MainIngridentViewHolder> {
    Context context;
    ArrayList<Ingredient> ingredientsList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MainIngredientAdapter(Context context, ArrayList<Ingredient> ingridentsList) {
        this.context = context;
        this.ingredientsList = ingridentsList;
    }

    @NonNull
    @Override
    public MainIngridentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_ingredient_item,parent,false);
        MainIngridentViewHolder myviewHolder = new MainIngridentViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainIngridentViewHolder holder, int position) {
        Ingredient currentIngredient = ingredientsList.get(position);
        holder.mainIngredientNameTxt.setText(currentIngredient.getIngredientName());

        String imageUrl = "https://www.themealdb.com/images/ingredients/" + currentIngredient.getIngredientName() + "-Small.png";
        Glide.with(context)
                .load(imageUrl)
                .into(holder.mainIngredientImageView);

        holder.mainIngredientCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(currentIngredient.getIngredientName());
        });
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public void updateData(ArrayList<Ingredient> newIngredientsList) {
        this.ingredientsList.clear();
        this.ingredientsList.addAll(newIngredientsList);
        notifyDataSetChanged();
    }

    class MainIngridentViewHolder extends RecyclerView.ViewHolder{
        ImageView mainIngredientImageView;
        TextView mainIngredientNameTxt;
        CardView mainIngredientCard;

        public MainIngridentViewHolder(@NonNull View itemView) {
            super(itemView);
            mainIngredientImageView = itemView.findViewById(R.id.mainIngredientImageView);
            mainIngredientNameTxt = itemView.findViewById(R.id.mainIngredientNameTxt);
            mainIngredientCard = itemView.findViewById(R.id.mainIngredientCard);
        }
    }

    public interface OnItemClickListener {
        public void onClicks(String filterName);
    }
}


