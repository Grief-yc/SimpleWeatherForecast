# 简易天气预报 Android App

## 功能

- 今日天气：温度、天气描述、空气湿度、风向风力
- 实时时钟：自动更新时间、日期、星期
- 今日宜/不宜：根据天气类型智能生成生活建议
- 近期预报：未来 7 天天气预报列表
- 天气图标：晴、多云、阴、小雨、中雨、雷阵雨、小雪、雾

## 运行环境

| 项目 | 版本 |
|------|------|
| AGP | 7.3.1 |
| Gradle | 7.4 |
| compileSdk | 33 |
| minSdk | 21 |
| targetSdk | 33 |
| Java | 1.8 |

## 打开方式

1. 打开 Android Studio → `File` → `Open` → 选择 `d:\SimpleWeatherForecast` 目录
2. 修改 `local.properties` 中的 `sdk.dir` 为你本机 Android SDK 路径
3. 等待 Gradle Sync 完成
4. 点击运行按钮 ▶️ 即可在模拟器或真机上运行

## 项目结构

```
SimpleWeatherForecast/
├── build.gradle                      # 项目级配置
├── settings.gradle                   # 仓库配置（阿里云镜像）
├── gradle.properties                 # Gradle 属性
├── local.properties                  # SDK 路径（需修改）
├── app/
│   ├── build.gradle                  # 模块配置
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/simpleweatherforecast/
│       │   ├── MainActivity.java          # 主界面
│       │   ├── adapter/
│       │   │   └── ForecastAdapter.java   # 预报列表适配器
│       │   ├── model/
│       │   │   ├── WeatherData.java       # 天气数据模型
│       │   │   └── DailyForecast.java     # 单日预报模型
│       │   └── util/
│       │       └── MockWeatherData.java   # Mock 数据生成器
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml      # 主界面布局
│           │   ├── item_forecast.xml      # 预报列表项
│           │   └── item_suggestion.xml    # 宜/不宜条目
│           ├── drawable/                   # 天气图标 + 背景渐变
│           ├── mipmap/                     # 应用图标
│           └── values/                     # 颜色/字符串/主题
```

## 接入真实天气 API

当前使用 Mock 数据。如需接入真实 API（如和风天气）：

1. 注册和风天气开发者账号，获取 API Key
2. 在 `MockWeatherData.java` 中替换为网络请求逻辑
3. 使用 `HttpURLConnection` 或 OkHttp 发起请求
4. 解析 JSON 响应并填充 `WeatherData` / `DailyForecast` 模型
