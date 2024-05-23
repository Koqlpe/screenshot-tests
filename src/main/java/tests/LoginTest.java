package tests;

import org.junit.jupiter.api.*;
import pages.FeedPage;
import pages.LeftNavigationColumn;
import pages.LoginPage;


import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginTest extends BaseTest {
    private TestBot testBot;
    private int allowableDiff = 20;
    private Supplier<String> methodName = () -> Thread.currentThread().getStackTrace()[2].getMethodName();


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
        String screenshotName = "";
        if (testInfo.getTestMethod().isPresent()) {
            screenshotName = testInfo.getTestMethod().get().getName();
        } else {
            screenshotName = methodName.get();
        }
        Screenshoter.makePageScreenshot(screenshotName);
        boolean comparingResult = ScrenshotsComparer.compareScreenshots(screenshotName, allowableDiff);
        assertFalse(comparingResult, "Скриншоты различны");
    }

    @Test
    @DisplayName("Скриншот-тест: вход пользователя (скриншот экрана без элементов)")
    public void loginWithExistUserIgnoredAreasTest(TestInfo testInfo) {
        FeedPage feedPage = new FeedPage();
        String screenshotName = "";
        if (testInfo.getTestMethod().isPresent()) {
            screenshotName = testInfo.getTestMethod().get().getName();
        } else {
            screenshotName = methodName.get();
        }
        Screenshoter.makePageScreenshotWithIgnoredElements(screenshotName,
                feedPage.getFeedArea(), feedPage.getAdditionalColumn(), feedPage.getRightColumn());

        boolean comparingResult = ScrenshotsComparer.compareScreenshots(screenshotName, allowableDiff);
        assertFalse(comparingResult, "Скриншоты различны");
    }

    @Test
    @DisplayName("Скриншот-тест: вход пользователя (скриншот элемента)")
    public void loginWithExistUserElementTest(TestInfo testInfo) {
        LeftNavigationColumn asideColumn = new LeftNavigationColumn();
        String screenshotName = "";
        if (testInfo.getTestMethod().isPresent()) {
            screenshotName = testInfo.getTestMethod().get().getName();
        } else {
            screenshotName = methodName.get();
        }
        Screenshoter.makeElementScreenshot(screenshotName, asideColumn.get());

        boolean comparingResult = ScrenshotsComparer.compareScreenshots(screenshotName, allowableDiff);
        assertFalse(comparingResult, "Скриншоты различны");
    }
}
