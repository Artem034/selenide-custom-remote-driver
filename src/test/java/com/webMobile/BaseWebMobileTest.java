package com.webMobile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.TextReportExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@ExtendWith(TextReportExtension.class)
abstract class BaseWebMobileTest {

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    public static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        //Configuration.browserCapabilities = chromeCaps(); //good
        Configuration.browser = RemoteMobileChromeWebDriverProvider.class.getName(); //Empty caps in selenoid
    }

    private static ChromeOptions chromeCaps() {
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
        chromeOptions.setHeadless(Boolean.parseBoolean(System.getProperty("selenide.headless")));
        chromeOptions.setCapability(CapabilityType.HAS_TOUCHSCREEN, true);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        chromeOptions.setCapability("browserName", "chrome");
        chromeOptions.setCapability("browserVersion", "latest");
        chromeOptions.setCapability("selenoid:options", Map.<String, Boolean>of(
                "enableVideo", false,
                "acceptInsecureCerts", false
        ));
        return chromeOptions;
    }

}
