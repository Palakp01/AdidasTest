package pages;

import com.typesafe.config.Config;
import cucumber.api.DataTable;
import helpers.ApiHelper;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ConfigLoader;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ShopStorePage extends PageObject {


    static Config conf = ConfigLoader.load();
    String expectedAmount, Name, Country, City, Card, Month, Year, Id, Amount;
    private static final Logger log = LoggerFactory.getLogger(ShopStorePage.class);

    @FindBy(xpath = "//a[text()=\"Laptops\"]")
    WebElementFacade laptops;

    @FindBy(xpath = "//a[text()=\"Add to cart\"]")
    WebElementFacade addToCart;

    @FindBy(xpath = "//a[text()='Home ']")
    WebElementFacade home;

    @FindBy(id = "cartur")
    private WebElementFacade cart;

    @FindBy(xpath = "(//a[text()='Delete'])[1]")
    WebElementFacade deleteDell;

    @FindBy(id = "totalp")
    WebElementFacade laptopAmount;

    @FindBy(xpath = "//button[text()='Place Order']")
    WebElementFacade placeOrder;

    @FindBy(xpath = "//input[@id='name']")
    WebElementFacade name;

    @FindBy(xpath = "//input[@id='country']")
    WebElementFacade country;

    @FindBy(xpath = "//input[@id='city']")
    WebElementFacade city;

    @FindBy(xpath = "//input[@id='card']")
    WebElementFacade card;

    @FindBy(xpath = "//input[@id='month']")
    WebElementFacade month;

    @FindBy(xpath = "//input[@id='year']")
    WebElementFacade year;

    @FindBy(xpath = "//button[text()='Purchase']")
    WebElementFacade purchase;


    @FindBy(xpath = "//p[@class='lead text-muted ']")
    WebElementFacade details;

    public void openApplication() {

        openUrl(conf.getString("shopStoreUrl"));

    }

    public void selectLaptop() {
        waitFor(laptops);
        laptops.click();
    }

    public void selectSonyLaptop(String Laptop) {


        waitFor("//a[contains(text(),'" + Laptop + "')]");
        getDriver().findElement(By.xpath("//a[contains(text(),'" + Laptop + "')]")).click();
        addToCart.click();
        waitABit(2000);
        getDriver().switchTo().alert().accept();


    }

    public void backToHome() {

        home.click();
        laptops.click();
    }

    public void navigateToCartAndDelete() {
        cart.click();
        withTimeoutOf(2, TimeUnit.MINUTES).waitFor(deleteDell).waitUntilVisible();
        deleteDell.click();
    }

    public void placeOrder(DataTable orderDetails) {
        waitABit(2000);
        expectedAmount = "Amount: " + laptopAmount.getText() + " USD";

        withTimeoutOf(2, TimeUnit.MINUTES).waitFor(placeOrder).waitUntilVisible();
        waitABit(2000);
        placeOrder.click();
        for (int i = 0; i < orderDetails.asMaps(String.class, String.class).size(); i++) {
            Name = orderDetails.asMaps(String.class, String.class).get(i).get("Name");
            Country = orderDetails.asMaps(String.class, String.class).get(i).get("Country");
            City = orderDetails.asMaps(String.class, String.class).get(i).get("City");
            Card = orderDetails.asMaps(String.class, String.class).get(i).get("Credit Card");
            Month = orderDetails.asMaps(String.class, String.class).get(i).get("Month");
            Year = orderDetails.asMaps(String.class, String.class).get(i).get("Year");
        }
        withTimeoutOf(2, TimeUnit.MINUTES).waitFor(name).waitUntilVisible();
        name.sendKeys(Name);
        country.sendKeys(Country);
        city.sendKeys(City);
        card.sendKeys(Card);
        month.sendKeys(Month);
        year.sendKeys(Year);
        purchase.click();
        waitABit(2000);

    }

    public void fetchIdAmount() throws Throwable {


        Id = ((JavascriptExecutor) getDriver()).executeScript("return document.evaluate(\"//p[@class='lead text-muted ']//text()[1]\", document, null, XPathResult.ANY_TYPE, null).iterateNext().textContent;").toString();
        Amount = ((JavascriptExecutor) getDriver()).executeScript("return document.evaluate(\"//p[@class='lead text-muted ']//text()[2]\", document, null, XPathResult.ANY_TYPE, null).iterateNext().textContent;").toString();
        log.info(Id);
        log.info(Amount);
        TakesScreenshot scrShot = ((TakesScreenshot) getDriver());
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(SrcFile, new File("/home/itadmin/AdidasTest/src/test/java/orderPlaced/orderPlaced.png"));

    }


    public void assertAmount() {

        Assert.assertEquals(expectedAmount, Amount);
    }

    public void closeBrowser() {
        getDriver().close();
    }

}
