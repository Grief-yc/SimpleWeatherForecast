package com.example.simpleweatherforecast.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleweatherforecast.R;
import com.example.simpleweatherforecast.model.DailyForecast;

import java.util.List;

/**
 * 未来天气预报列表适配器
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<DailyForecast> forecastList;

    public ForecastAdapter(List<DailyForecast> forecastList) {
        this.forecastList = forecastList;
    }

    public void updateData(List<DailyForecast> newList) {
        this.forecastList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecast forecast = forecastList.get(position);

        holder.tvWeekDay.setText(forecast.getWeekDay());
        holder.tvDate.setText(forecast.getDate());
        holder.ivIcon.setImageResource(forecast.getIconRes());
        holder.tvWeather.setText(forecast.getWeather());
        holder.tvTemp.setText(forecast.getTempMin() + "° / " + forecast.getTempMax() + "°");
        holder.tvWind.setText(forecast.getWindDir() + " " + forecast.getWindLevel() + "级");
        holder.tvHumidity.setText("湿度 " + forecast.getHumidity() + "%");
    }

    @Override
    public int getItemCount() {
        return forecastList == null ? 0 : forecastList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWeekDay;
        TextView tvDate;
        ImageView ivIcon;
        TextView tvWeather;
        TextView tvTemp;
        TextView tvWind;
        TextView tvHumidity;

        ViewHolder(View itemView) {
            super(itemView);
            tvWeekDay = itemView.findViewById(R.id.tv_week_day);
            tvDate = itemView.findViewById(R.id.tv_date);
            ivIcon = itemView.findViewById(R.id.iv_weather_icon);
            tvWeather = itemView.findViewById(R.id.tv_weather_desc);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvWind = itemView.findViewById(R.id.tv_wind);
            tvHumidity = itemView.findViewById(R.id.tv_humidity);
        }
    }
}
