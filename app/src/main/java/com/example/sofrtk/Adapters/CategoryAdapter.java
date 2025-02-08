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
import com.example.sofrtk.Pojos.Category;
import com.example.sofrtk.Pojos.RandomMeal;
import com.example.sofrtk.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categoriesList;
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
        Glide.with(context).load(currentCategory.getCategoryImage()).apply(new RequestOptions().override(250,250)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.categoryImageView);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImageView;
        TextView categoryNameTxt;
        TextView categoryDescriptionTxt;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.categoryImageView);
            categoryNameTxt = itemView.findViewById(R.id.categoryNameTxt);
            categoryDescriptionTxt = itemView.findViewById(R.id.categoryDescriptionTxt);
        }
    }
}
