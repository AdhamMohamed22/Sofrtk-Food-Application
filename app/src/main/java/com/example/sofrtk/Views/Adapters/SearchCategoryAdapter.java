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

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.SearchCategoryViewHolder> {
    Context context;
    ArrayList<Category> categoriesList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchCategoryAdapter(Context context, ArrayList<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public SearchCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.search_category_item,parent,false);
        SearchCategoryViewHolder myviewHolder = new SearchCategoryViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryViewHolder holder, int position) {
        Category currentCategory = categoriesList.get(position);
        holder.searchCategoryNameTxt.setText(currentCategory.getCategoryTitle());
        Glide.with(context).load(currentCategory.getCategoryImage()).apply(new RequestOptions().override(225,225)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.searchCategoryImageView);
        /*
        holder.searchCategoryCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(Integer.parseInt(currentCategory.getCategoryId()));
        });
         */
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

    class SearchCategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView searchCategoryImageView;
        TextView searchCategoryNameTxt;
        CardView searchCategoryCard;
        public SearchCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            searchCategoryImageView = itemView.findViewById(R.id.searchCategoryImageView);
            searchCategoryNameTxt = itemView.findViewById(R.id.searchCategoryNameTxt);
            searchCategoryCard = itemView.findViewById(R.id.searchCategoryCard);
        }
    }

    public interface OnItemClickListener{
        public void onClicks(int id);
    }
}
