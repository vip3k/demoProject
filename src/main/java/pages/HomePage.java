package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    private final By timeMenu = By.xpath("//span[text()='Time']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTimeMenu() {
        driver.findElement(timeMenu).click();
    }
}
