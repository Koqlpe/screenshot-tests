package tests;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.*;
import pages.FeedPage;
import pages.LeftNavigationColumn;
import pages.LoginPage;
import pages.ToolBar;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginTest extends BaseTest {
    private TestBot testBot;
    private int allowableDiff = 20;

    @BeforeEach
    public void login() {
        testBot = TestBot.newBuilder().buildDefault();

        new LoginPage()
                .setUsername(testBot.getBotUsername())
                .setPassword(testBot.getBotPassword())
                .clickLoginButton();
    }

    @Disabled
    @Test
    @DisplayName("Скриншот-тест: вход пользователя (скриншот экрана целиком)")
    public void loginWithExistUserTest(TestInfo testInfo) {
        String screenshotName = testInfo.getTestMethod().get().getName();
        Screenshoter.makePageScreenshot(screenshotName);
        boolean comparingResult = ScrenshotsComparer.compareScreenshots(screenshotName, allowableDiff);
        assertFalse(comparingResult, "Скриншоты различны");
    }

    @Test
    @DisplayName("Скриншот-тест: вход пользователя (скриншот экрана без элементов)")
    public void loginWithExistUserIgnoredAreasTest(TestInfo testInfo) {
        FeedPage feedPage = new FeedPage();
        String screenshotName = testInfo.getTestMethod().get().getName();
        Screenshoter.makePageScreenshotWithIgnoredElements(screenshotName,
                feedPage.getFeedArea(), feedPage.getAdditionalColumn(), feedPage.getRightColumn());

        boolean comparingResult = ScrenshotsComparer.compareScreenshots(screenshotName, allowableDiff);
        assertFalse(comparingResult, "Скриншоты различны");
    }

    @Test
    @DisplayName("Скриншот-тест: вход пользователя (скриншот элемента)")
    public void loginWithExistUserElementTest(TestInfo testInfo) {
        LeftNavigationColumn asideColumn = new LeftNavigationColumn();
        String screenshotName = testInfo.getTestMethod().get().getName();
        Screenshoter.makeElementScreenshot(screenshotName, asideColumn.get());

        boolean comparingResult = ScrenshotsComparer.compareScreenshots(screenshotName, allowableDiff);
        assertFalse(comparingResult, "Скриншоты различны");
    }
}
