package com.webMobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RemoteMobileChromeWebDriverProvider implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {

        //iPhone XR
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 414);
        deviceMetrics.put("height", 896);
        deviceMetrics.put("pixelRatio", 2);
        deviceMetrics.put("touch", true);
        deviceMetrics.put("mobile", true);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Mobile/15E148 Safari/604.1");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        // не нужно. chromeOptions и так дает browserName=chrome
        // chromeOptions.setCapability("browserName", "chrome");

        // это нужно, но в твоем селеноиде нет 96 хрома. только latest.
        // http://xxxxxxxxx:4444/status
        // "browsers":{"chrome":{"latest":{}},"safari":{"latest":{}}}}
        //chromeOptions.setCapability("browserVersion", "96.0");
        chromeOptions.setCapability("selenoid:options", Map.<String, Boolean>of(
                "enableVideo", true,
                "acceptInsecureCerts", false
        ));

        try {
            return new RemoteWebDriver(new URL(System.getProperty("selenide.remote2")), chromeOptions);
        } catch (final MalformedURLException e) {
            throw new RuntimeException("Unable to create driver", e);
        }
    }
}
