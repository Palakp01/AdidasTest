Feature: Place Order for laptops

  Scenario: Place Order for Laptops
    Given customer open the gadgets shop store
    When navigates through Phones Laptops Monitors
    And navigates to Laptop "Sony vaio i5" and add to the cart
    And navigates to Laptop "Dell i7 8gb" and add to the cart
    Then navigates to cart and delete laptop "Dell i7 8gb"
    Then place the order after filling the details
      | Name | Country | City  | Credit Card | Month | Year |
      | Test | India   | Noida | 1234567890  | 10    | 2020 |
    And capture the details and log the id and amount
    And verify that expected amount is equal to purchase amount
    Then close the browser