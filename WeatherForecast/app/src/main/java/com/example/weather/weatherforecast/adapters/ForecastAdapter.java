package com.example.weather.weatherforecast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.weatherforecast.R;
import com.example.weather.weatherforecast.models.ForecastSingleDay;
import com.example.weather.weatherforecast.utilities.FieldFormatter;
import com.example.weather.weatherforecast.utilities.ImageMapper;

import java.util.ArrayList;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private ArrayList<ForecastSingleDay> forecastList;
    private final ForecastAdapterOnClickHandler forecastClickHandler;

    public interface ForecastAdapterOnClickHandler {
        void onClick(ForecastSingleDay forecast);
    }

    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
        forecastClickHandler = clickHandler;
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView weatherIv;
        public final TextView dayOfWeekTv;
        public final TextView conditionTv;
        public final TextView tempCurrentTv;
        public final TextView tempMinTv;

        public ForecastAdapterViewHolder(View view) {
            super(view);

            weatherIv = (ImageView) view.findViewById(R.id.weather_img);
            dayOfWeekTv = (TextView) view.findViewById(R.id.day_of_week_tv);
            conditionTv = (TextView) view.findViewById(R.id.condition_tv);
            tempCurrentTv = (TextView) view.findViewById(R.id.temp_current_tv);
            tempMinTv = (TextView) view.findViewById(R.id.temp_min_tv);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            ForecastSingleDay clickedForecast = forecastList.get(adapterPosition);
            forecastClickHandler.onClick(clickedForecast);
        }
    }

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        ForecastSingleDay forecast = forecastList.get(position);

        holder.weatherIv.setImageResource(ImageMapper.getIconResourceId(forecast.getIcon()));
        holder.dayOfWeekTv.setText(forecast.getDayOfWeek());
        holder.conditionTv.setText(forecast.getConditionDesc());
        holder.tempCurrentTv.setText(FieldFormatter.getFormattedTemp(forecast.getTemp()));
        holder.tempMinTv.setText(FieldFormatter.getFormattedTemp(forecast.getMinTemp()));
    }

    @Override
    public int getItemCount() {
        if(forecastList == null){
            return 0;
        }else{
            return forecastList.size();
        }
    }

    public void setForecastList(ArrayList<ForecastSingleDay> returnedForecastList) {
        forecastList = returnedForecastList;
        notifyDataSetChanged();
    }
}
