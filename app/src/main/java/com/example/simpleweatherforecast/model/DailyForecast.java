package com.example.simpleweatherforecast.model;

import java.util.List;

/**
 * 单日天气预报数据模型
 */
public class DailyForecast {
    private String date;       // 日期 yyyy-MM-dd
    private String weekDay;    // 星期几 如 "周一"
    private String weather;    // 天气描述 如 "晴"
    private int tempMax;       // 最高温度
    private int tempMin;       // 最低温度
    private int humidity;      // 湿度百分比
    private int windLevel;     // 风力等级
    private String windDir;    // 风向
    private int iconRes;       // 天气图标资源ID
    private List<String> suitable;    // 今日宜做
    private List<String> unsuitable;  // 今日不宜做

    public DailyForecast() {}

    // --- getters & setters ---
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getWeekDay() { return weekDay; }
    public void setWeekDay(String weekDay) { this.weekDay = weekDay; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public int getTempMax() { return tempMax; }
    public void setTempMax(int tempMax) { this.tempMax = tempMax; }

    public int getTempMin() { return tempMin; }
    public void setTempMin(int tempMin) { this.tempMin = tempMin; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public int getWindLevel() { return windLevel; }
    public void setWindLevel(int windLevel) { this.windLevel = windLevel; }

    public String getWindDir() { return windDir; }
    public void setWindDir(String windDir) { this.windDir = windDir; }

    public int getIconRes() { return iconRes; }
    public void setIconRes(int iconRes) { this.iconRes = iconRes; }

    public List<String> getSuitable() { return suitable; }
    public void setSuitable(List<String> suitable) { this.suitable = suitable; }

    public List<String> getUnsuitable() { return unsuitable; }
    public void setUnsuitable(List<String> unsuitable) { this.unsuitable = unsuitable; }
}
