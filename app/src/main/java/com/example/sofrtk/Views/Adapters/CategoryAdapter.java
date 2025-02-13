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
import com.example.sofrtk.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categoriesList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CategoryAdapter(Context context, ArrayList<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.category_item,parent,false);
        CategoryViewHolder myviewHolder = new CategoryViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category currentCategory = categoriesList.get(position);
        holder.categoryNameTxt.setText(currentCategory.getCategoryTitle());
        holder.categoryDescriptionTxt.setText(currentCategory.getCategoryDescription());
        Glide.with(context).load(currentCategory.getCategoryImage()).apply(new RequestOptions().override(225,225)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.categoryImageView);
        holder.categoryCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(Integer.parseInt(currentCategory.getCategoryId()));
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void updateData(ArrayList<Category> newCategoriesList) {
        this.categoriesList.clear();
        this.categoriesList.addAll(newCategoriesList);
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImageView;
        TextView categoryNameTxt;
        TextView categoryDescriptionTxt;
        CardView categoryCard;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.categoryImageView);
            categoryNameTxt = itemView.findViewById(R.id.categoryNameTxt);
            categoryDescriptionTxt = itemView.findViewById(R.id.categoryDescriptionTxt);
            categoryCard = itemView.findViewById(R.id.categoryCard);
        }
    }

    public interface OnItemClickListener{
        public void onClicks(int id);
    }
}
