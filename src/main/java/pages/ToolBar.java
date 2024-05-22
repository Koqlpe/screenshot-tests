package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ToolBar extends BasePage {
    private final static By navigationToolBar = By.xpath(".//div[@data-l=\"t,navigationToolbar\"]");
    private final static By profileToolBarButton = By.xpath(".//button[@aria-controls=\"user-dropdown-menu\"]");
    private final static By logo = By.xpath(".//div[@id=\"topPanelLeftCorner\"]");
    private final static By exitButton = By.xpath(".//div[@class=\"toolbar_dropdown\"]//a[@data-l=\"t,logout\"]");
    public final static By confirmExitButton = By.xpath(".//input[@data-l=\"t,logout\"]");

    public ToolBar() {
        checkPage();
    }

    @Override
    public void checkPage() {
        $(navigationToolBar).shouldBe(visible
                .because("На странице должна отобразится Панель инструментов"));
        $(logo).shouldBe(visible
                .because("На Панели инструментов должен отображаться логотип ОК"));
        $(profileToolBarButton).shouldBe(visible
                .because("На Панели инструментов должна отображаться кнопка настроек пользователя"));
    }

    public LoginPage logOut() {
        $(profileToolBarButton)
                .shouldBe(enabled.because("Чтобы выйти, должна отображаться кнопка Настроек пользователя"))
                .click();
        $(exitButton)
                .shouldBe(visible.because("Чтобы выйти, должна быть доступна кнопка Выйти"))
                .click();
        $(confirmExitButton)
                .shouldBe(visible.because("Чтобы выйти, должно всплывать окно с кнопкой для подтверждения выхода"))
                .click();
        return new LoginPage();
    }
}
