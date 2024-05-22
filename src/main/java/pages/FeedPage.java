package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FeedPage extends BasePage {
    private final static String URL = "/feed";
    private final static By tapeButton = By.xpath(".//a[@id=\"tab-item-0\"]");
    private final static By feedArea = By.xpath(".//div[@class='layout-content']");
    private final static By additionalColumn = By.xpath(".//div[@data-sticky-id='additionalColumn']");
    private final static By rightColumn = By.xpath(".//div[@data-sticky-id='rightColumn']");

    public FeedPage() {
        checkPage();
    }

    public void checkPage() {
        $(tapeButton).shouldBe(visible
                .because("Кнопка Лента должна отображаться на странице Новостей"));
    }

    public By getFeedArea() {
        return feedArea;
    }

    public By getAdditionalColumn() {
        return additionalColumn;
    }

    public By getRightColumn() {
        return rightColumn;
    }

}
