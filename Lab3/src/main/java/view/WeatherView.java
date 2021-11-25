package view;

import lombok.Getter;
import models.WeatherInfo;

import javax.swing.*;

public class WeatherView {

    @Getter
    private final JPanel panel;

    private final JLabel jWeatherInfo;

    public WeatherView() {
        panel = new JPanel();
        jWeatherInfo = new JLabel();
    }

    private String formatWeatherInfo(WeatherInfo weatherInfo) {
        return String.format(
            "<html>" +
            "<b>Temperature:</b> %s<br>" +
            "</html>",
            weatherInfo.parameters.temperature
        );
    }

    public void update(WeatherInfo weatherInfo) {
        jWeatherInfo.setText(formatWeatherInfo(weatherInfo));
        panel.revalidate();
    }
}
