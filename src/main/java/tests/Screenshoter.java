package tests;

import com.codeborne.selenide.Selenide;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.fail;

public class Screenshoter {
    private static final Logger logger = Logger.getLogger(Screenshoter.class.getName());
    private static Screenshot expectedScreenshot; // Эталонный скриншот
    private static Screenshot actualScreenshot; // Скриншот текущего состояния страницы или элемента
    private static File expectedFile; // Путь к файлу с эталонным скриншотом
    private static File actualFile; // Путь к файлу со скриншотом текущего состояния
    private static final String pathToExpectedScreenshot = "src/main/resources/screenshots/expected/"; // Путь к эталонным скриншотам
    private static final String pathToActualScreenshot = "src/main/resources/screenshots/actual/"; // Путь к скриншотам с текущим состоянием
    private static Set<By> ignoredElements = new HashSet<>();

    public static void makePageScreenshot(String fileName) {
        actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.simple())
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(getWebDriver());
        actualFile = new File(pathToActualScreenshot + fileName + ".png");

        saveExpectedScreenshotToFile(fileName);
        saveScreenshotToFile(actualScreenshot.getImage(), actualFile);
    }

    public static void makePageScreenshotWithIgnoredElements(String fileName, By... elements) {
        Collections.addAll(ignoredElements, elements);
        disableElements(elements);

        actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.simple())
                .coordsProvider(new WebDriverCoordsProvider())
                .ignoredElements(ignoredElements)
                .takeScreenshot(getWebDriver());

        actualFile = new File(pathToActualScreenshot + fileName + ".png");
        saveExpectedScreenshotToFile(fileName);
        expectedScreenshot.setIgnoredAreas(actualScreenshot.getIgnoredAreas());

        saveScreenshotToFile(actualScreenshot.getImage(), actualFile);
    }

    public static void makeElementScreenshot(String fileName, By element) {
        actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.simple())
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(getWebDriver(), $(element));
        actualFile = new File(pathToActualScreenshot + fileName + ".png");

        saveExpectedScreenshotToFile(fileName);
        saveScreenshotToFile(actualScreenshot.getImage(), actualFile);
    }

    public static void makePageScreenshotWithScroll(String fileName, int scrollingTime) {
        actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(scrollingTime))
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(getWebDriver());
        actualFile = new File(pathToActualScreenshot + fileName + ".png");

        saveExpectedScreenshotToFile(fileName);
        saveScreenshotToFile(actualScreenshot.getImage(), actualFile);
    }

    private static void saveScreenshotToFile(BufferedImage screenshot, File file) {
        try {
            ImageIO.write(screenshot, "PNG", file);
        } catch (IOException e) {
            logger.log(
                    Level.SEVERE,
                    String.format("Ошибка записи скриншота в файл %s - ", file.getPath()),
                    e.fillInStackTrace()
            );
        }
    }

    private static void saveExpectedScreenshotToFile(String fileName) {
        expectedFile = new File(pathToExpectedScreenshot + fileName + ".png");
        if (!expectedFile.exists()) {
            expectedScreenshot = actualScreenshot;
            saveScreenshotToFile(expectedScreenshot.getImage(), expectedFile);
            fail(String.format("\nБыл создан эталонный скриншот по пути: %s.\nНеобходимо его сверить.\n", expectedFile.getPath()));
        } else {
            try {
                expectedScreenshot = new Screenshot(ImageIO.read(expectedFile));
            } catch (IOException e) {
                logger.log(
                        Level.SEVERE,
                        "Ошибка чтения эталонного скриншота из файла - ",
                        e.fillInStackTrace()
                );
            }
        }
    }

    private static void disableElements(By... locators) {
        for (By locator : locators) {
            String xpath = locator.toString().replace("By.xpath: ", "");
            Selenide.executeJavaScript(
                    "function hideElementsByXPath(xpathExpression) {\n" +
                            "const result = document.evaluate(xpathExpression, document,\n" +
                            "null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);\n" +
                            "let element = result.iterateNext();\n" +
                            "while (element) {\n" +
                            "element.style.opacity = 0;\n" +
                            "element = result.iterateNext();\n" +
                            "}}\n" +
                            String.format("hideElementsByXPath(\"%s\");", xpath)
            );
        }
    }

    private static void hideScrollbar() {
        Selenide.executeJavaScript("document.body.style.overflow = 'hidden';");
    }

    public static void removeActualScreenshots()
    {
        File directory = new File(pathToActualScreenshot);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            logger.log(
                    Level.SEVERE,
                    String.format("Ошибка удаление файлов из папки %s - ", directory.getPath()),
                    e.fillInStackTrace()
            );
        }
    }

    public static void removeDiffScreenshots()
    {
        String pathToDiffScreenshots = "src/main/resources/screenshots/diff/";
        File directory = new File(pathToDiffScreenshots);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            logger.log(
                    Level.SEVERE,
                    String.format("Ошибка удаление файлов из папки %s - ", directory.getPath()),
                    e.fillInStackTrace()
            );
        }
    }

    public static Screenshot getActualScreenshot()
    {
        return actualScreenshot;
    }
    public static Screenshot getExpectedScreenshot()
    {
        return expectedScreenshot;
    }
}
