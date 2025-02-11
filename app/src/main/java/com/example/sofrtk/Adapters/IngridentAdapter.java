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
import com.example.sofrtk.R;

import java.util.ArrayList;

public class IngridentAdapter extends RecyclerView.Adapter<IngridentAdapter.IngridentViewHolder> {
    Context context;
    ArrayList<String> ingridentsList;
    ArrayList<String> measuresList;

    public IngridentAdapter(Context context, ArrayList<String> ingridentsList, ArrayList<String> measuresList) {
        this.context = context;
        this.ingridentsList = ingridentsList;
        this.measuresList = measuresList;
    }

    @NonNull
    @Override
    public IngridentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ingredient_item,parent,false);
        IngridentViewHolder myviewHolder = new IngridentViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngridentViewHolder holder, int position) {
        String currentIngrident = ingridentsList.get(position);
        holder.ingridentNameTxt.setText(currentIngrident);

        String currentMeasure = measuresList.get(position);
        holder.measureNameTxt.setText(currentMeasure);

        String imageUrl = "https://www.themealdb.com/images/ingredients/" + currentIngrident + "-Small.png";
        Glide.with(context)
                .load(imageUrl)
                .into(holder.ingredientImageView);
    }

    @Override
    public int getItemCount() {
        return ingridentsList.size();
    }

    class IngridentViewHolder extends RecyclerView.ViewHolder{
        ImageView ingredientImageView;
        TextView ingridentNameTxt;
        TextView measureNameTxt;

        public IngridentViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImageView = itemView.findViewById(R.id.planMealImageView);
            ingridentNameTxt = itemView.findViewById(R.id.planMealNameTxt);
            measureNameTxt = itemView.findViewById(R.id.measureNameTxt);
        }
    }
}
