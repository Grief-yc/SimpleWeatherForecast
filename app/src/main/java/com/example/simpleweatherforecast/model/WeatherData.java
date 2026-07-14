package com.example.simpleweatherforecast.model;

import java.util.List;

/**
 * 天气数据整体模型，包含今日天气和多日预报
 */
public class WeatherData {
    private String city;            // 城市名
    private String updateTime;      // 更新时间
    private DailyForecast today;    // 今日天气详情
    private List<DailyForecast> forecastList; // 多日预报列表

    public WeatherData() {}

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public DailyForecast getToday() { return today; }
    public void setToday(DailyForecast today) { this.today = today; }

    public List<DailyForecast> getForecastList() { return forecastList; }
    public void setForecastList(List<DailyForecast> forecastList) { this.forecastList = forecastList; }
}
