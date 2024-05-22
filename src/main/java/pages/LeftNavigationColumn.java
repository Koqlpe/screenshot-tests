package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LeftNavigationColumn extends BasePage {
    private final static By asideColumn = By.xpath(".//aside[@data-sticky-id=\"asideColumn\"]");
    private final static By postButton = By.xpath(".//button[@data-l=\"t,pf_dropdown\"]");

    public LeftNavigationColumn() {
        checkPage();
    }

    @Override
    public void checkPage() {
        $(asideColumn).shouldBe(visible
                .because("На странице должна отобразится Левая панель навигации"));
        $(postButton).shouldBe(visible
                .because("На Левой панели навигации должна отображаться кнопка Опубликовать"));
    }

    public By get() {
        return asideColumn;
    }
}
