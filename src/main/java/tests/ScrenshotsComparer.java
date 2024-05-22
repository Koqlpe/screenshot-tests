package tests;

import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScrenshotsComparer {
    private static final Logger logger = Logger.getLogger(Screenshoter.class.getName());
    private static String pathToDiffScreenshots = "src/main/resources/screenshots/diff/"; // Путь к скриншотам с различиями
    private static ImageDiff diff;
    private static int diffSizeTrigger = 10;

    public static boolean compareScreenshots(String fileName, int allowableDiff) {
        Screenshot expected = Screenshoter.getExpectedScreenshot(fileName);
        Screenshot actual = Screenshoter.getActualScreenshot(fileName);

        diff = new ImageDiffer().makeDiff(expected, actual).withDiffSizeTrigger(diffSizeTrigger);
        int diffSize = diff.getDiffSize();

        boolean hasDiff = diffSize > allowableDiff;
        if (hasDiff) {
            saveDiffImage(fileName);
        }
        return hasDiff;
    }

    private static void saveDiffImage(String fileName) {
        File diffFile = new File(pathToDiffScreenshots + fileName + ".png");
        try {
            ImageIO.write(diff.getMarkedImage(), "PNG", diffFile);
        } catch (IOException e) {
            logger.log(
                    Level.WARNING,
                    String.format("Ошибка записи скриншота в файл %s - ", diffFile.getPath()),
                    e.fillInStackTrace()
            );
        }
    }

    public static void setDiffSizeTrigger(int diffSizeTrigger) {
        ScrenshotsComparer.diffSizeTrigger = diffSizeTrigger;
    }
}
