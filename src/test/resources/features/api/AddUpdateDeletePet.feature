Feature: Add/Update/Delete the Pet from store


  Adding a new pet in the store
  then changing the status to sold
  and then deleting the pet


  Scenario: Add/Update/Delete the Pet from store

################  Adding a new Pet in the store ##########################

    When user provide the category details to add new pet in the store
    And provide the photourls of pet "https://marshallspetzone.com/blog/wp-content/uploads/2018/01/32.jpg"
    And provide the tags details of new pet
      | id  | name |
      | 098 | 81-A |
    Then add the new pet in the store with status "available" and having unique "id" and below name
      | name  |
      | Stefy |
    Then verify that the new pet is added in the store

################    Updating the status of Pet to sold and verifying it in get  ################

    Then user change the status of pet to "sold" after it gets sold
    Then verify that status is updated to "sold"

################    Deleting the sold Pet and verifying in get ##################

    Then user deletes the record of "sold" pet
    Then verify that the "Pet not found" in the data