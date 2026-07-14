package com.example.simpleweatherforecast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleweatherforecast.adapter.ForecastAdapter;
import com.example.simpleweatherforecast.model.DailyForecast;
import com.example.simpleweatherforecast.model.WeatherData;
import com.example.simpleweatherforecast.util.MockWeatherData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // 今日天气卡片控件
    private TextView tvCity;
    private TextView tvTime;
    private TextView tvDateWeek;
    private TextView tvCurrentTemp;
    private TextView tvWeatherDesc;
    private TextView tvHumidity;
    private TextView tvWind;
    private TextView tvUpdateTime;

    // 宜/不宜容器
    private LinearLayout layoutSuitable;
    private LinearLayout layoutUnsuitable;

    // 未来天气预报
    private RecyclerView rvForecast;
    private ForecastAdapter adapter;

    // 时间刷新
    private final Handler timeHandler = new Handler(Looper.getMainLooper());
    private final Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            timeHandler.postDelayed(this, 1000);
        }
    };

    // 天气数据
    private WeatherData weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadWeatherData();
        startClock();
    }

    private void initViews() {
        tvCity = findViewById(R.id.tv_city);
        tvTime = findViewById(R.id.tv_time);
        tvDateWeek = findViewById(R.id.tv_date_week);
        tvCurrentTemp = findViewById(R.id.tv_current_temp);
        tvWeatherDesc = findViewById(R.id.tv_weather_desc);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWind = findViewById(R.id.tv_wind);
        tvUpdateTime = findViewById(R.id.tv_update_time);
        layoutSuitable = findViewById(R.id.layout_suitable);
        layoutUnsuitable = findViewById(R.id.layout_unsuitable);
        rvForecast = findViewById(R.id.rv_forecast);
    }

    private void loadWeatherData() {
        // 使用 Mock 数据（后续可替换为真实 API）
        weatherData = MockWeatherData.generateWeatherData("北京");

        // 更新今日天气
        DailyForecast today = weatherData.getToday();
        tvCity.setText(weatherData.getCity());
        tvCurrentTemp.setText(today.getTempMax() + "°");
        tvWeatherDesc.setText(today.getWeather());
        tvHumidity.setText("空气湿度 " + today.getHumidity() + "%");
        tvWind.setText(today.getWindDir() + " " + today.getWindLevel() + "级");
        tvUpdateTime.setText(weatherData.getUpdateTime());

        // 更新宜/不宜
        updateSuggestions(today.getSuitable(), today.getUnsuitable());

        // 更新未来天气预报列表
        rvForecast.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForecastAdapter(weatherData.getForecastList());
        rvForecast.setAdapter(adapter);
    }

    /**
     * 动态生成"宜"和"不宜"条目
     */
    private void updateSuggestions(List<String> suitable, List<String> unsuitable) {
        layoutSuitable.removeAllViews();
        layoutUnsuitable.removeAllViews();

        if (suitable != null) {
            for (String item : suitable) {
                View itemView = getLayoutInflater().inflate(R.layout.item_suggestion, layoutSuitable, false);
                TextView tvText = itemView.findViewById(R.id.tv_suggestion_text);
                tvText.setText(item);
                layoutSuitable.addView(itemView);
            }
        }

        if (unsuitable != null) {
            for (String item : unsuitable) {
                View itemView = getLayoutInflater().inflate(R.layout.item_suggestion, layoutUnsuitable, false);
                TextView tvText = itemView.findViewById(R.id.tv_suggestion_text);
                tvText.setText(item);
                layoutUnsuitable.addView(itemView);
            }
        }
    }

    /**
     * 实时更新时间和日期/星期
     */
    private void updateDateTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);

        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK) - 1;

        tvTime.setText(timeFmt.format(now.getTime()));
        tvDateWeek.setText(dateFmt.format(now.getTime()) + " " + weekDays[dayOfWeek]);
    }

    private void startClock() {
        timeRunnable.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeHandler.removeCallbacks(timeRunnable);
    }
}
