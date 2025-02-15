package com.example.sofrtk.Views.Adapters;

import static com.example.sofrtk.Views.UI.Main.Search.SearchFragment.getFlagResourceByName;

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
import com.example.sofrtk.Models.DTOs.Country;
import com.example.sofrtk.R;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    Context context;
    ArrayList<Country> countriesList;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CountryAdapter(Context context, ArrayList<Country> countriesList) {
        this.context = context;
        this.countriesList = countriesList;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.country_item, parent, false);
        CountryViewHolder myviewHolder = new CountryViewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country currentCountry = countriesList.get(position);
        holder.countryNameTxt.setText(currentCountry.getCountryName());
        holder.countryImageView.setImageResource(getFlagResourceByName(holder.countryImageView.getContext(), currentCountry.getCountryName()));

        holder.countryCard.setOnClickListener(v -> {
            onItemClickListener.onClicks(currentCountry.getCountryName());
        });

    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public void updateData(ArrayList<Country> newCountriesList) {
        this.countriesList.clear();
        this.countriesList.addAll(newCountriesList);
        notifyDataSetChanged();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView countryImageView;
        TextView countryNameTxt;
        CardView countryCard;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImageView = itemView.findViewById(R.id.countryImageView);
            countryNameTxt = itemView.findViewById(R.id.countryNameTxt);
            countryCard = itemView.findViewById(R.id.countryCard);
        }
    }

    public interface OnItemClickListener {
        void onClicks(String filterName);
    }
}
