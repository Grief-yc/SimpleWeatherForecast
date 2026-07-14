# SimpleWeatherForecast 项目记忆

## 项目概述
简易天气预报 Android 应用，位于 d:\SimpleWeatherForecast\
- 包名: com.example.simpleweatherforecast
- AGP 7.3.1 / Gradle 7.4 / compileSdk 33 / minSdk 21
- 使用阿里云/腾讯云镜像
- 当前使用 Mock 数据，可后续接入和风天气 API

## 功能模块
- 今日天气卡片（温度、天气描述、湿度、风力）
- 实时时钟（每秒更新时间/日期/星期）
- 今日宜/不宜建议（根据天气类型智能生成）
- 未来7天天气预报列表（RecyclerView）
- 8种天气图标（矢量 drawable）

## 关键文件
- MainActivity.java: 主界面，含时钟刷新 Handler
- MockWeatherData.java: Mock 数据生成器，含宜/不宜逻辑
- ForecastAdapter.java: 预报列表适配器
- WeatherData.java / DailyForecast.java: 数据模型
- activity_main.xml: 主界面布局（渐变天空背景）
