package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.ShopStorePage;

public class ShopStoreSteps {

    private ShopStorePage shopStorePage;

    @Given("^customer open the gadgets shop store$")
    public void customerOpenTheGadgetsShopStore() {
        shopStorePage.openApplication();
    }


    @When("^navigates through Phones Laptops Monitors$")
    public void customerNavigatesThroughPhonesLaptopsMonitors() {
        shopStorePage.selectLaptop();
    }

    @And("^navigates to Laptop \"([^\"]*)\" and add to the cart$")
    public void navigatesToLaptopAndAddToTheCart(String Laptop) throws Throwable {
        if (Laptop.equals("Sony vaio i5")) {
            shopStorePage.selectSonyLaptop(Laptop);
            shopStorePage.backToHome();
        } else
            shopStorePage.selectSonyLaptop(Laptop);
    }

    @Then("^navigates to cart and delete laptop \"([^\"]*)\"$")
    public void navigatesToCartAndDeleteLaptop(String arg0) throws Throwable {
        shopStorePage.navigateToCartAndDelete();
    }

    @And("^place the order after filling the details$")
    public void placeTheOrderAfterFillingTheDetails(DataTable orderDetails) {
        shopStorePage.placeOrder(orderDetails);
    }

    @Then("^verify that expected amount is equal to purchase amount$")
    public void verifyThatExpectedAmountIsEqualToPurchaseAmount() {
        shopStorePage.assertAmount();
    }

    @And("^capture the details and log the id and amount$")
    public void captureTheDetailsAndLogTheIdAndAmount() throws Throwable{
        shopStorePage.fetchIdAmount();
    }

    @Then("^close the browser$")
    public void closeTheBrowser() {
        shopStorePage.closeBrowser();
    }
}
