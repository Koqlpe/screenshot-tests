package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import pages.ToolBar;

import static com.codeborne.selenide.Browsers.CHROME;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public abstract class BaseTest {
    private final static String BASE_URL = "https://ok.ru/";

    @BeforeAll
    public static void setUp() {
        Screenshoter.removeActualScreenshots();
        Screenshoter.removeDiffScreenshots();

        Configuration.browser = CHROME;
        Configuration.baseUrl = BASE_URL;
        open("/");
    }

    @AfterEach
    public void tearDown() {
        new ToolBar().logOut();
        clearBrowserCache();
    }
}
