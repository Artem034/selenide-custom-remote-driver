package com.webMobile;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;


@WebMobile
public class HomePageTest extends BaseWebMobileTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    void checkSections() {
        open("http://google.com");
    }
}
