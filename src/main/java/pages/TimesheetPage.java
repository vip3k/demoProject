package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimesheetPage {
    private final WebDriver driver;

    private final By timesheetPeriodView = By.xpath("//div[contains(text(),'2022-08-15 - 2022-08-21')]/ancestor::div[@class='oxd-table-card']/descendant::button");

    public TimesheetPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTimesheetPeriodView() {
        driver.findElement(timesheetPeriodView).click();
    }

    public String getCommentText() {
        return driver.findElement(By.xpath("//textarea[@class='oxd-textarea oxd-textarea--active oxd-textarea--resize-vertical' and @placeholder='Comment here' and @disabled]")).getAttribute("placeholder");
    }

    public void clickFirstRowCommentButton() {
        driver.findElement(By.xpath("//button[@class='oxd-icon-button oxd-icon-button--secondary orangehrm-timesheet-icon-comment' and @type='button']/i[@class='oxd-icon bi-chat-dots-fill']")).click();
    }
}
