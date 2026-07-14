package com.example.simpleweatherforecast.util;

import com.example.simpleweatherforecast.R;
import com.example.simpleweatherforecast.model.DailyForecast;
import com.example.simpleweatherforecast.model.WeatherData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Mock 天气数据生成器
 * 生成模拟的近期天气预报数据（含宜/不宜建议）
 * 后续可替换为真实 API 调用
 */
public class MockWeatherData {

    // 天气类型常量
    private static final String SUNNY = "晴";
    private static final String CLOUDY = "多云";
    private static final String OVERCAST = "阴";
    private static final String LIGHT_RAIN = "小雨";
    private static final String MODERATE_RAIN = "中雨";
    private static final String THUNDERSTORM = "雷阵雨";
    private static final String LIGHT_SNOW = "小雪";
    private static final String FOG = "雾";

    // 天气类型数组（随机选取）
    private static final String[] WEATHER_TYPES = {
            SUNNY, SUNNY, CLOUDY, CLOUDY, OVERCAST,
            LIGHT_RAIN, MODERATE_RAIN, THUNDERSTORM, FOG
    };

    // 风向数组
    private static final String[] WIND_DIRS = {
            "东北风", "东南风", "西北风", "西南风", "北风", "南风", "东风", "西风"
    };

    private static final String[] WEEK_DAYS = {
            "周日", "周一", "周二", "周三", "周四", "周五", "周六"
    };

    private static final Random random = new Random();

    /**
     * 生成完整的天气数据（含今日 + 未来6天预报）
     */
    public static WeatherData generateWeatherData(String city) {
        WeatherData data = new WeatherData();
        data.setCity(city);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm 更新", java.util.Locale.CHINA);
        data.setUpdateTime(sdf.format(Calendar.getInstance().getTime()));

        List<DailyForecast> forecastList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFmt = new SimpleDateFormat("MM月dd日", java.util.Locale.CHINA);

        // 当前季节判断（影响温度范围）
        int month = calendar.get(Calendar.MONTH) + 1;
        int[] tempRange = getSeasonalTempRange(month);

        for (int i = 0; i < 7; i++) {
            DailyForecast forecast = new DailyForecast();

            // 日期
            forecast.setDate(dateFmt.format(calendar.getTime()));

            // 星期
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Calendar.SUNDAY=1
            if (i == 0) {
                forecast.setWeekDay("今天");
            } else if (i == 1) {
                forecast.setWeekDay("明天");
            } else {
                forecast.setWeekDay(WEEK_DAYS[dayOfWeek]);
            }

            // 天气（今天用固定的，后面随机）
            String weather;
            if (i == 0) {
                weather = CLOUDY; // 今天默认多云
            } else {
                weather = WEATHER_TYPES[random.nextInt(WEATHER_TYPES.length)];
            }
            forecast.setWeather(weather);

            // 温度
            int tempMax = tempRange[1] - random.nextInt(5);
            int tempMin = tempRange[0] + random.nextInt(3);
            forecast.setTempMax(tempMax);
            forecast.setTempMin(tempMin);

            // 湿度
            int humidity = getHumidityByWeather(weather);
            forecast.setHumidity(humidity);

            // 风力 & 风向
            forecast.setWindLevel(random.nextInt(4) + 1);
            forecast.setWindDir(WIND_DIRS[random.nextInt(WIND_DIRS.length)]);

            // 天气图标
            forecast.setIconRes(getIconByWeather(weather));

            // 宜/不宜建议
            generateSuggestions(forecast);

            forecastList.add(forecast);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // 今日天气
        data.setToday(forecastList.get(0));
        data.setForecastList(forecastList);

        return data;
    }

    /**
     * 根据月份获取季节性温度范围
     */
    private static int[] getSeasonalTempRange(int month) {
        // 返回 {最低温, 最高温}
        if (month >= 3 && month <= 5) {
            return new int[]{10, 25}; // 春季
        } else if (month >= 6 && month <= 8) {
            return new int[]{22, 35}; // 夏季
        } else if (month >= 9 && month <= 11) {
            return new int[]{8, 22};  // 秋季
        } else {
            return new int[]{-5, 8};  // 冬季
        }
    }

    /**
     * 根据天气类型返回湿度
     */
    private static int getHumidityByWeather(String weather) {
        switch (weather) {
            case SUNNY: return 30 + random.nextInt(20);   // 30-50%
            case CLOUDY: return 50 + random.nextInt(20);   // 50-70%
            case OVERCAST: return 65 + random.nextInt(20); // 65-85%
            case LIGHT_RAIN:
            case MODERATE_RAIN: return 80 + random.nextInt(15); // 80-95%
            case THUNDERSTORM: return 75 + random.nextInt(20); // 75-95%
            case LIGHT_SNOW: return 70 + random.nextInt(20);  // 70-90%
            case FOG: return 85 + random.nextInt(10);          // 85-95%
            default: return 50 + random.nextInt(30);
        }
    }

    /**
     * 根据天气类型返回图标资源ID
     */
    private static int getIconByWeather(String weather) {
        switch (weather) {
            case SUNNY: return R.drawable.ic_weather_sunny;
            case CLOUDY: return R.drawable.ic_weather_cloudy;
            case OVERCAST: return R.drawable.ic_weather_overcast;
            case LIGHT_RAIN: return R.drawable.ic_weather_light_rain;
            case MODERATE_RAIN: return R.drawable.ic_weather_rain;
            case THUNDERSTORM: return R.drawable.ic_weather_thunder;
            case LIGHT_SNOW: return R.drawable.ic_weather_snow;
            case FOG: return R.drawable.ic_weather_fog;
            default: return R.drawable.ic_weather_cloudy;
        }
    }

    /**
     * 根据天气类型生成"宜"和"不宜"建议
     */
    private static void generateSuggestions(DailyForecast forecast) {
        List<String> suitable = new ArrayList<>();
        List<String> unsuitable = new ArrayList<>();
        String weather = forecast.getWeather();
        int humidity = forecast.getHumidity();
        int tempMax = forecast.getTempMax();

        switch (weather) {
            case SUNNY:
                suitable.addAll(Arrays.asList("户外运动", "晒太阳", "晾晒衣物", "出游赏景"));
                unsuitable.addAll(Arrays.asList("长时间暴晒", "高温下剧烈运动"));
                if (tempMax > 30) {
                    suitable.add("游泳消暑");
                    unsuitable.add("高温时段外出");
                }
                break;
            case CLOUDY:
                suitable.addAll(Arrays.asList("户外散步", "轻度运动", "出行办事", "拍照摄影"));
                unsuitable.addAll(Arrays.asList("晾晒衣物", "长时间户外暴晒"));
                break;
            case OVERCAST:
                suitable.addAll(Arrays.asList("室内活动", "阅读看书", "轻度运动"));
                unsuitable.addAll(Arrays.asList("户外长时间活动", "晾晒衣物"));
                break;
            case LIGHT_RAIN:
                suitable.addAll(Arrays.asList("室内运动", "读书学习", "品茶放松"));
                unsuitable.addAll(Arrays.asList("户外运动", "晾晒衣物", "骑自行车"));
                break;
            case MODERATE_RAIN:
                suitable.addAll(Arrays.asList("室内休息", "看电影追剧", "泡茶养生"));
                unsuitable.addAll(Arrays.asList("户外活动", "驾车远行", "晾晒衣物"));
                break;
            case THUNDERSTORM:
                suitable.addAll(Arrays.asList("室内休息", "关好门窗", "拔掉不必要的电器插头"));
                unsuitable.addAll(Arrays.asList("户外活动", "使用有线电话", "树下避雨", "户外运动"));
                break;
            case LIGHT_SNOW:
                suitable.addAll(Arrays.asList("赏雪景", "喝热饮暖身", "室内运动"));
                unsuitable.addAll(Arrays.asList("驾车远行", "长时间户外活动", "骑行"));
                break;
            case FOG:
                suitable.addAll(Arrays.asList("室内活动", "佩戴口罩外出"));
                unsuitable.addAll(Arrays.asList("驾车出行", "户外剧烈运动", "长时间户外逗留"));
                break;
            default:
                suitable.addAll(Arrays.asList("室内活动", "适度运动"));
                unsuitable.addAll(Arrays.asList("长时间户外活动"));
                break;
        }

        // 根据湿度调整建议
        if (humidity > 80) {
            unsuitable.add("存放易受潮物品");
            unsuitable.add("木地板打蜡");
        }
        if (humidity < 40) {
            suitable.add("使用加湿器");
            unsuitable.add("长时间开窗");
        }

        forecast.setSuitable(suitable);
        forecast.setUnsuitable(unsuitable);
    }
}
