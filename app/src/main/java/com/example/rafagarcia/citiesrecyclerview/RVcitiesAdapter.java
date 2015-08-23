package com.example.rafagarcia.citiesrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rafael Garcia on 23/08/15.
 */

public class RVcitiesAdapter extends RecyclerView.Adapter<RVcitiesAdapter.CitiesViewHolder> {

    List<City> cityList;
    Context context;

    public RVcitiesAdapter(Context context, List<City> cities){

        this.context = context;
        cityList = cities;
    }

    @Override
    public CitiesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_card, viewGroup, false);
        CitiesViewHolder cityViewHolder = new CitiesViewHolder(view);
        return cityViewHolder;
    }

    @Override
    public void onBindViewHolder(CitiesViewHolder citiesViewHolder, int i) {

        citiesViewHolder.cityTextView.setText(cityList.get(i).getName());

        //Setting tag to avoid crazy list views
        citiesViewHolder.cityImageView.setTag(cityList.get(i).getUrlImage());

        //Downloading image from internet in background
        ImageDownloader.getInstance().download(cityList.get(i).getUrlImage(),
                citiesViewHolder.cityImageView, context);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public static class CitiesViewHolder extends RecyclerView.ViewHolder{

        ImageView cityImageView;
        TextView cityTextView;

        CitiesViewHolder(View view){

            super(view);
            cityImageView = (ImageView)view.findViewById(R.id.cityImageView);
            cityTextView = (TextView)view.findViewById(R.id.cityTextView);
        }
    }
}
